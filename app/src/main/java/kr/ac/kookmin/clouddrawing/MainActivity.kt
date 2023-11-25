
package kr.ac.kookmin.clouddrawing

import android.Manifest
import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.location.Location
import android.location.LocationManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.ui.AppBarConfiguration
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.CancellationToken
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.gms.tasks.OnTokenCanceledListener
import com.google.firebase.Firebase
import com.google.firebase.appcheck.appCheck
import com.google.firebase.appcheck.playintegrity.PlayIntegrityAppCheckProviderFactory
import com.google.firebase.auth.auth
import com.google.firebase.initialize
import com.kakao.vectormap.*
import com.kakao.vectormap.camera.CameraAnimation
import com.kakao.vectormap.camera.CameraUpdate
import com.kakao.vectormap.camera.CameraUpdateFactory
import com.kakao.vectormap.label.LabelLayerOptions
import com.kakao.vectormap.label.LabelOptions
import com.kakao.vectormap.label.LabelStyle
import com.kakao.vectormap.label.LabelStyles
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kr.ac.kookmin.clouddrawing.components.*
import kr.ac.kookmin.clouddrawing.dto.Post
import kr.ac.kookmin.clouddrawing.dto.User
import kr.ac.kookmin.clouddrawing.interfaces.AddressService
import kr.ac.kookmin.clouddrawing.models.coord2address
import kr.ac.kookmin.clouddrawing.models.keyward2address
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import round


class MainActivity : AppCompatActivity() {
    private val appBarConfiguration: AppBarConfiguration? = null

    private lateinit var isLeftOpen: MutableState<Boolean>
    private lateinit var isCloudMindOpen: MutableState<Boolean>
    private lateinit var isCloudListModalOpen: MutableState<Boolean>
    private lateinit var isLoadingModalOpen: MutableState<Boolean>

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private val kakaoMap: MutableLiveData<KakaoMap?> by lazy {
        MutableLiveData(null)
    }
    private val mapView: MutableLiveData<MapView> by lazy {
        MutableLiveData(null)
    }

    private var lat: Double = 37.402005
    private var lng: Double = 127.108621
    private var address: String? = ""
    private var road_address: String? = ""
    private var region_1depth_name: String? = ""
    private lateinit var API_KEY: String

    private var user: User? = null
    private val profileUri = mutableStateOf<Uri?>(null)
    private val mutatePost = mutableStateOf<Post?>(null)
    private var mutatePostList = mutableListOf<Post>()
    private var postListOfRecents = mutableStateOf(listOf<Post>())

    private var testing = mutableListOf<Post>()

    private var notiService: NotificagtionService? = null

    private final val conn = object: ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            try {
                notiService = (service as NotificagtionService.NotiBinder).getService
            } catch(e: java.lang.Exception) {
                Log.e(TAG, e.message ?: "")
            }
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            notiService = null
        }
    }

    @OptIn(ExperimentalComposeUiApi::class)
    private var localKeyboardController: SoftwareKeyboardController? = null

    companion object {
        private val LOCATION_PERMISSIONS = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
        private const val REQUEST_CODE_LOCATION_PERMISSIONS = 10005
        private const val TAG = "MainActivity"
        private const val CURRENT_LOC_MARKER = "currentmarker"
        private const val CHANNEL_ID = "cloudgreenImagine" //알림 채널 아이디
    }

    private fun init() {
        Firebase.initialize(context = this)
        Firebase.appCheck.installAppCheckProviderFactory(
            PlayIntegrityAppCheckProviderFactory.getInstance(),
        )
    }

    @OptIn(ExperimentalComposeUiApi::class)
    @SuppressLint("StateoFlowValueCalledInComposition", "StateFlowValueCalledInComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()

        createNotificationChannel() // 알림 채널 생성 함수 호출
        startService(Intent(this, NotificagtionService::class.java))

        isLoadingModalOpen = mutableStateOf(true)
        CoroutineScope(Dispatchers.Main).launch {
            delay(2500)
            isLoadingModalOpen.value = false
        }

        if(Firebase.auth.currentUser == null) {
            val intent1 = Intent(this, SignupActivity::class.java)
            startActivity(intent1)
        }

        API_KEY = getString(R.string.kakao_restapi_key)

        mapView.value = MapView(applicationContext)

        val searchBar = ViewModelProvider(this)[SearchBarModel::class.java]
        val context = this

        mapView.value?.start(object : MapLifeCycleCallback() {
            override fun onMapDestroy() {
                finish()
            }

            override fun onMapError(error: Exception?) {
                Log.d(TAG, error.toString())
            }
        },
        object : KakaoMapReadyCallback() {
            @RequiresApi(Build.VERSION_CODES.P)
            override fun onMapReady(kakaoMap: KakaoMap) {
                this@MainActivity.kakaoMap.value = kakaoMap

                kakaoMap.setOnLabelClickListener { _, _, label ->
                    localKeyboardController?.hide()
                    val postId = label.layer.layerId

                    if (postId != CURRENT_LOC_MARKER) {
                        CoroutineScope(Dispatchers.Main).launch {
                            val post = Post.getPostById(postId)
                            mutatePostList = Post.getPostByLatLng(
                                post?.uid!!,
                                round(post.lat ?: 37.335887), round(post.lng ?: 126.584063)
                            ).toMutableList()

                            Log.d(TAG, "POI Size: ${mutatePostList.size}")

                            if (mutatePostList.isNotEmpty())
                                if (mutatePostList.size == 1) {
                                    mutatePost.value = mutatePostList[0]
                                    isCloudMindOpen.value = true
                                } else {
                                    Log.d(TAG, "POI[0]: ${mutatePostList[0].title}")
                                    isCloudListModalOpen.value = true
                                }
                        }
                    }
                }

                kakaoMap.setOnMapClickListener { kakaoMap, position, screenPoint, poi ->
                    localKeyboardController?.hide()
                }

                moveMapInit()
            }

            @RequiresApi(Build.VERSION_CODES.P)
            override fun getPosition(): LatLng {
                return getCurrentLatLng()
            }
        })

        val retrofit = Retrofit.Builder()
            .baseUrl("https://dapi.kakao.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        CoroutineScope(Dispatchers.Main).launch {
            launch {
                user = User.getCurrentUser()
                if (user != null) {
                    profileUri.value = Uri.parse(user!!.photoURL)
                    Log.d("MainActivity", profileUri.value.toString())
                }
            }
            launch {
                val location = loadCurrentLocation()
                if(location != null) {
                    this@MainActivity.lat = location.latitude
                    this@MainActivity.lng = location.longitude
                }
            }
        }

        setContent {
            isLeftOpen = remember { mutableStateOf(false) }
            isCloudMindOpen = remember { mutableStateOf(false) }
            isCloudListModalOpen = remember { mutableStateOf(false) }
            localKeyboardController = LocalSoftwareKeyboardController.current

            Box(Modifier.fillMaxSize()) {
                KakaoMapComponent(
                    modifier = Modifier.fillMaxSize(),
                    mapView = mapView.value!!
                )

                Row(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(start = 12.dp, end = 12.dp, top = 73.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    MyCloudBtn {
                        isLeftOpen.value = true
                    }
                    Spacer(Modifier.width(5.dp))
                    SearchBar(
                        searchBar,
                        onSearch = {
                            if (searchBar.search == "") return@SearchBar
                            val addressService = retrofit.create(AddressService::class.java)
                            addressService.getLocation(
                                API_KEY,
                                searchBar.search,
                                this@MainActivity.lat.toString(),
                                this@MainActivity.lng.toString()
                                ).enqueue(object : Callback<keyward2address> {
                                    override fun onResponse(
                                        call: Call<keyward2address>,
                                        response: Response<keyward2address>
                                    ) {
                                        val result = response.body()
                                        val a = response.raw()
                                        if (result?.documents?.isNotEmpty() == true) {
                                            this@MainActivity.lng = result.documents?.get(0)?.x?.toDouble() ?: 126.584063
                                            this@MainActivity.lat = result.documents?.get(0)?.y?.toDouble() ?: 37.335887

                                            moveMapCurrentLocation()
                                        } else {
                                            Toast.makeText(context, "검색된 장소가 없습니다!", Toast.LENGTH_LONG)
                                                .show()
                                        }
                                        Log.e(TAG, "body : $result")
                                        Log.e(TAG, "raw : $a")
                                        Log.e(
                                            TAG,
                                            "lat: ${this@MainActivity.lat} / lng: ${this@MainActivity.lng}"
                                        )
                                    }

                                    override fun onFailure(call: Call<keyward2address>, t: Throwable) {
                                        TODO("Not yet implemented")
                                    }
                                })
                           },
                        modifier = Modifier.fillMaxWidth(1f),
                        keyboardController = localKeyboardController
                    )
                }

                Row(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(bottom = 53.dp, start = 33.dp, end = 33.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    AddCloudBtn(addCloud = {
                        localKeyboardController?.hide()
                        val intent = Intent(context, CloudDrawingActivity::class.java)
                        Log.d(TAG, "lat : $lat / lng : $lng")

                        val addressService = retrofit.create(AddressService::class.java)
                        addressService.getAddress(
                            API_KEY,
                            lng.toBigDecimal().toPlainString(),
                            lat.toBigDecimal().toPlainString()
                        ).enqueue(object : Callback<coord2address> {
                                override fun onResponse(call: Call<coord2address?>, response: Response<coord2address?>) {
                                    var result = response.body()
                                    var a = response.raw()
                                    if (result?.documents?.isNotEmpty() == true) {
                                        this@MainActivity.address =
                                            result.documents?.get(0)?.address?.address_name
                                        this@MainActivity.road_address =
                                            result.documents?.get(0)?.road_address?.address_name
                                        this@MainActivity.region_1depth_name =
                                            if (result.documents?.get(0)?.road_address == null) {
                                                result.documents?.get(0)?.address?.region_1depth_name
                                            } else {
                                                result.documents?.get(0)?.road_address?.region_1depth_name
                                            }
                                        Log.d(TAG, "body : $result")
                                    } else {
                                        Log.d(TAG, "None")
                                        this@MainActivity.address = ""
                                        this@MainActivity.road_address = ""
                                        this@MainActivity.region_1depth_name = ""
                                    }
                                    Log.d(TAG, "raw : $a")

                                    if (kakaoMap.value?.labelManager?.getLayer(CURRENT_LOC_MARKER)?.labelCount != null) {
                                        Log.e("anasfasnj", "asfaf : " + kakaoMap.value?.labelManager?.getLayer(CURRENT_LOC_MARKER)?.labelCount)
                                        Log.d(TAG, "address: ${this@MainActivity.address}")
                                        intent.putExtra("address", this@MainActivity.address)
                                        intent.putExtra("road_address", this@MainActivity.road_address)
                                        intent.putExtra("region", this@MainActivity.region_1depth_name)
                                        intent.putExtra("lat", this@MainActivity.lat)
                                        intent.putExtra("lng", this@MainActivity.lng)
                                    }
                                    startActivity(intent)

                                }

                                override fun onFailure(call: Call<coord2address>, t: Throwable) {
                                    startActivity(intent)
                                }
                            })
                    })
                    CurrentLocBtn {
                        CoroutineScope(Dispatchers.Main).launch {
                            val location = loadCurrentLocation()
                            if (location != null) {
                                this@MainActivity.lat = location.latitude
                                this@MainActivity.lng = location.longitude
                            }

                            moveMapCurrentLocation()
                        }
                    }
                }

                AnimatedVisibility(
                    visible = (isLeftOpen.value || isCloudMindOpen.value || isCloudListModalOpen.value),
                    modifier = Modifier.fillMaxSize(1f),
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    localKeyboardController?.hide()
                    Box(
                        Modifier
                            .fillMaxSize(1f)
                            .background(Color.Black.copy(alpha = 0.5f))
                            .clickable {
                                listOf(
                                    isLeftOpen,
                                    isCloudMindOpen,
                                    isCloudListModalOpen
                                ).forEach { it.value = false }
                            }
                    )
                }
                HomeLeftModal(
                    logoutButton = {
                        User.logoutCurrentUser()
                        startActivity(Intent(this@MainActivity, SignupActivity::class.java))
                        isLeftOpen.value = false
                    },
                    isDrawerOpen = isLeftOpen,
                    profileUri = profileUri,
                    previousPost = postListOfRecents
                )
                CloudMindModal(
                    content = mutatePost,
                    isDrawerOpen = isCloudMindOpen
                )
                CloudListSelectPlaceListModal(
                    handler = { post ->
                        mutatePost.value = post
                        isCloudMindOpen.value = true
                        isCloudListModalOpen.value = false
                    },
                    isDrawerOpen = isCloudListModalOpen,
                    contentLists = mutatePostList
                )
                LoadingModal(isLoadingModalOpen)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onResume() {
        super.onResume()
        mapView.value?.resume()

        notiService?.getPosts()

        CoroutineScope(Dispatchers.Main).launch {
            launch {
                user = User.getCurrentUser()
                if (user != null) {
                    profileUri.value = Uri.parse(user!!.photoURL)
                    Log.d("MainActivity", profileUri.value.toString())
                }
            }
            launch {
                getCurrentLatLng()
            }
            delay(800L)

            val clouds = user?.uid?.let { Post.getPostByUID(it) } ?: listOf()
            if (clouds.isNotEmpty()) {
                postListOfRecents.value = clouds.take(3)
                val lists = mutableListOf<Clouds>()

                clouds.forEach {
                    Log.d(TAG, it.title ?: "NULL")
                    val any = lists.find { it1 ->
                        it1.lat == (it.lat ?: 0.0) && it1.lng == (it.lng ?: 0.0)
                    }
                    if (any != null)
                        any.isDuplicate = true
                    else
                        lists.add(Clouds(it.id!!, it.lat!!, it.lng!!, it.addressAlias!!))
                }

                lists.forEach {
                    val labelManager = kakaoMap.value?.labelManager
                    labelManager?.getLayer(it.id).let { p ->
                        if (p != null) labelManager?.remove(p)
                    }

                    labelManager?.addLayer(
                        LabelLayerOptions.from(it.id)
                            .setClickable(true).setZOrder(10001)
                    )?.addLabel(
                        LabelOptions.from(
                            LatLng.from(
                                it.lat,
                                it.lng
                            )
                        ).setStyles(
                            LabelStyles.from(
                                LabelStyle.from(
                                    viewConvertToBitmap(
                                        this@MainActivity,
                                        if (it.isDuplicate) R.drawable.v_cloud_icon_plus else R.drawable.v_cloud_icon,
                                        40, 40
                                    )
                                ).setZoomLevel(0),
                                LabelStyle.from(
                                    viewConvertToBitmap(
                                        this@MainActivity,
                                        if (it.isDuplicate) R.drawable.v_cloud_icon_plus else R.drawable.v_cloud_icon,
                                        60, 60
                                    )
                                ).setZoomLevel(10),
                                LabelStyle.from(
                                    viewConvertToBitmap(
                                        this@MainActivity,
                                        if (it.isDuplicate) R.drawable.v_cloud_icon_plus else R.drawable.v_cloud_icon,
                                        80, 80
                                    )
                                ).setZoomLevel(12)
                            )
                        )
                    )
                }

                // moveMapCurrentLocation()
            }
        }
    }

    override fun onPause() {
        super.onPause()
        mapView.value?.pause()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_CODE_LOCATION_PERMISSIONS -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    Log.d("MainActivity", "LocationPermission granted.")
                } else {
                    val settingsIntent = Intent()
                    settingsIntent.action =
                        android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                    settingsIntent.data = Uri.fromParts("package", packageName, null)
                    startActivity(settingsIntent)

                    Toast.makeText(this, "위치권한을 얻지 못했습니다!\n위치권한을 허용해주세요!", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.P)
    private fun moveMapInit() {
        val locationManager = this.getSystemService(LOCATION_SERVICE) as LocationManager
        var isGpsOn = locationManager.isLocationEnabled
        var camera : CameraUpdate
        this@MainActivity.lat = 37.402005
        this@MainActivity.lng = 127.108621

        if (isGpsOn) {
            var loc = getCurrentLatLng()
            this@MainActivity.lat = loc.getLatitude()
            this@MainActivity.lng = loc.getLongitude()

            camera = CameraUpdateFactory.newCenterPosition(
                LatLng.from(this@MainActivity.lat, this@MainActivity.lng),
                18
            )
        } else {
            camera = CameraUpdateFactory.newCenterPosition(
                LatLng.from(lat, lng),
                14
            )
        }

        Log.d(TAG, "loc: lat:${lat}/lng:${lng}")

        val labelManager = kakaoMap.value?.labelManager
        labelManager?.getLayer(CURRENT_LOC_MARKER).let {
            if (it != null) labelManager?.remove(it)
        }

        val styles: LabelStyles? = labelManager?.addLabelStyles(
            LabelStyles.from(
                LabelStyle.from(
                    viewConvertToBitmap(
                        this@MainActivity,
                        R.drawable.vector,
                        25, 40
                    )
                ).setZoomLevel(0),
                LabelStyle.from(
                    viewConvertToBitmap(
                        this@MainActivity,
                        R.drawable.vector,
                        45, 60
                    )
                ).setZoomLevel(10),
                LabelStyle.from(
                    viewConvertToBitmap(
                        this@MainActivity,
                        R.drawable.vector,
                        60, 80
                    )
                ).setZoomLevel(12),
            )
        )
        val options: LabelOptions =
            LabelOptions.from(
                LatLng.from(
                    this@MainActivity.lat,
                    this@MainActivity.lng
                )
            ).setStyles(styles)

        if (isGpsOn) {
            labelManager?.addLayer(
                LabelLayerOptions.from(CURRENT_LOC_MARKER).setClickable(false).setZOrder(1000)
            )?.addLabel(options)
        }

        kakaoMap.value?.moveCamera(camera, CameraAnimation.from(500, true, true))
    }
    private fun moveMapCurrentLocation() {
        val camera = CameraUpdateFactory.newCenterPosition(
            LatLng.from(this@MainActivity.lat, this@MainActivity.lng),
            18
        )

        Log.d(TAG, "loc: lat:${lat}/lng:${lng}")

        val labelManager = kakaoMap.value?.labelManager
        labelManager?.getLayer(CURRENT_LOC_MARKER).let {
            if (it != null) labelManager?.remove(it)
        }

        val styles: LabelStyles? = labelManager?.addLabelStyles(
            LabelStyles.from(
                LabelStyle.from(
                    viewConvertToBitmap(
                        this@MainActivity,
                        R.drawable.vector,
                        50, 60
                    )
                ).setZoomLevel(0),
                LabelStyle.from(
                    viewConvertToBitmap(
                        this@MainActivity,
                        R.drawable.vector,
                        60, 80
                    )
                ).setZoomLevel(12),
            )
        )
        val options: LabelOptions =
            LabelOptions.from(
                LatLng.from(
                    this@MainActivity.lat,
                    this@MainActivity.lng
                )
            ).setStyles(styles)
        labelManager?.addLayer(
            LabelLayerOptions.from(CURRENT_LOC_MARKER).setClickable(false).setZOrder(1000)
        )?.addLabel(options)

        kakaoMap.value?.moveCamera(camera, CameraAnimation.from(500, true, true))
    }

    @RequiresApi(Build.VERSION_CODES.P)
    private fun getCurrentLatLng(): LatLng {
        var uLat = 37.402005
        var uLng = 127.108621
        val locationManager = this.getSystemService(LOCATION_SERVICE) as LocationManager
        val isGpsOn = locationManager.isLocationEnabled

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            if (isGpsOn) {
                val userCurrentLocation =
                    locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)

                uLat = userCurrentLocation?.latitude ?: uLat
                uLng = userCurrentLocation?.longitude ?: uLng
            }
        } else {
            ActivityCompat.requestPermissions(
                this,
                LOCATION_PERMISSIONS,
                REQUEST_CODE_LOCATION_PERMISSIONS
            )
        }

        return LatLng.from(uLat, uLng)
    }

    private suspend fun loadCurrentLocation(): Location? {
        return if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            null
        } else {
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

            fusedLocationClient.getCurrentLocation(
                LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY,
                object : CancellationToken() {
                    override fun onCanceledRequested(p0: OnTokenCanceledListener): CancellationToken =
                        CancellationTokenSource().token
                    override fun isCancellationRequested(): Boolean = false
                }).await()
            fusedLocationClient.lastLocation.await()
        }
    }

    private fun viewConvertToBitmap(
        context: Context,
        drawableId: Int,
        resX: Int? = null,
        resY: Int? = null
    ): Bitmap? {
        val drawable = ContextCompat.getDrawable(context, drawableId)
        val bitmap = if (resX != null || resY != null) Bitmap.createScaledBitmap(
            Bitmap.createBitmap(
                drawable!!.intrinsicWidth, drawable.intrinsicHeight, Bitmap.Config.ARGB_8888
            ), resX ?: drawable.intrinsicHeight, resY ?: drawable.intrinsicWidth,
            false
        )
        else Bitmap.createBitmap(
            drawable!!.intrinsicWidth, drawable.intrinsicHeight, Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)
        return bitmap
    }


    private fun createNotificationChannel() {
        // 이미 API 26 이상임이 보장되므로 API 체크를 건너뜁니다. (minSDK = 26)
        val name = getString(R.string.channel_name) // 채널 이름
        val descriptionText = getString(R.string.channel_description) // 채널 설명
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
            description = descriptionText
        }
        // 알림 관리자를 통해 채널을 시스템에 등록합니다.
        val notificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    data class Clouds(
        val id: String,
        val lat: Double,
        val lng: Double,
        val address: String,
        var isDuplicate: Boolean = false
    )
}
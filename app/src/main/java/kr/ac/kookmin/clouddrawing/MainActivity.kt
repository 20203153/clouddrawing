
package kr.ac.kookmin.clouddrawing

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.graphics.Bitmap
import android.graphics.Canvas
import android.location.Location
import android.location.LocationManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import com.kakao.vectormap.KakaoMap
import com.kakao.vectormap.KakaoMapReadyCallback
import com.kakao.vectormap.LatLng
import com.kakao.vectormap.MapLifeCycleCallback
import com.kakao.vectormap.MapView
import com.kakao.vectormap.camera.CameraAnimation
import com.kakao.vectormap.camera.CameraUpdateFactory
import com.kakao.vectormap.label.LabelLayerOptions
import com.kakao.vectormap.label.LabelOptions
import com.kakao.vectormap.label.LabelStyle
import com.kakao.vectormap.label.LabelStyles
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kr.ac.kookmin.clouddrawing.components.AddCloudBtn
import kr.ac.kookmin.clouddrawing.components.CloudMindModal
import kr.ac.kookmin.clouddrawing.components.HomeLeftModal
import kr.ac.kookmin.clouddrawing.components.KakaoMapComponent
import kr.ac.kookmin.clouddrawing.components.MyCloudBtn
import kr.ac.kookmin.clouddrawing.components.SearchBar
import kr.ac.kookmin.clouddrawing.components.SearchBarModel
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


class MainActivity : AppCompatActivity() {
    private val appBarConfiguration: AppBarConfiguration? = null

    private lateinit var isLeftOpen: MutableState<Boolean>
    private lateinit var isCloudMindOpen: MutableState<Boolean>

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private val kakaoMap: MutableLiveData<KakaoMap> by lazy {
        MutableLiveData(null)
    }
    private val mapView: MutableLiveData<MapView> by lazy {
        MutableLiveData(null)
    }

    private var lat: Double = 0.0
    private var lng: Double = 0.0
    private var address : String? = ""
    private var road_address : String? = ""
    private val API_KEY = "KakaoAK 2d0991b1dcfec57879b691ce70c13b54"

    private var user: User? = null
    private val profileUri = mutableStateOf<Uri?>(null)

    companion object {
        private val LOCATION_PERMISSIONS = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
        private const val REQUEST_CODE_LOCATION_PERMISSIONS = 10005
        private const val TAG = "MainActivity"
    }

    @SuppressLint("StateoFlowValueCalledInComposition", "StateFlowValueCalledInComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mapView.value = MapView(applicationContext)

        val mapViewFlow = MutableStateFlow(value = mapView)
        val searchBar = ViewModelProvider(this)[SearchBarModel::class.java]
        val context = this

        val retrofit = Retrofit.Builder()
            .baseUrl("https://dapi.kakao.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        setContent {
            isLeftOpen = remember { mutableStateOf(false) }
            isCloudMindOpen = remember { mutableStateOf(false) }

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
                    SearchBar(searchBar, onSearch = {
                        if(searchBar.search == "") return@SearchBar

                        val addressService = retrofit.create(AddressService::class.java)
                        addressService.getLocation(API_KEY, searchBar.search, this@MainActivity.lat.toString(), this@MainActivity.lng.toString()).enqueue(object : Callback<keyward2address> {
                            override fun onResponse(call: Call<keyward2address>, response: Response<keyward2address>) {
                                val result = response.body()
                                val a = response.raw()
                                this@MainActivity.lng = result?.documents?.get(0)?.x?.toDouble() ?: 0.0
                                this@MainActivity.lat = result?.documents?.get(0)?.y?.toDouble() ?: 0.0
                                Log.e(TAG, "body : $result")
                                Log.e(TAG, "raw : $a")
                                Log.e(TAG, "lat: ${this@MainActivity.lat} / lng: ${this@MainActivity.lng}")

                                moveMapCurrentLocation()
                            }

                            override fun onFailure(call: Call<keyward2address>, t: Throwable) {
                                TODO("Not yet implemented")
                            }
                        })
                    }, Modifier.fillMaxWidth(1f))
                }

                Row(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(bottom = 53.dp, start = 33.dp, end = 33.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    /* FriendCloudBtn(friendCloud = { }) */
                    AddCloudBtn(addCloud = {
                        val intent = Intent(context, CloudDrawingActivity::class.java)
                        var latLng = getCurrentLatLng()
                        lat = latLng.getLatitude()
                        lng = latLng.getLongitude()
                        var strLat : String = lat.toBigDecimal().toPlainString()
                        var strLng : String = lng.toBigDecimal().toPlainString()
                        Log.e("Testing", "lat : $lat / lng : $lng")

                        val addressService = retrofit.create(AddressService::class.java)
                        addressService.getAddress(API_KEY, strLng, strLat).enqueue(object : Callback<coord2address> {
                            override fun onResponse(call: Call<coord2address>, response: Response<coord2address>) {
                                var result = response.body()
                                var a = response.raw()
                                address = result?.documents?.get(0)?.address?.address_name
                                road_address = result?.documents?.get(0)?.road_address?.address_name
                                Log.e("Testing", "body : $result")
                                Log.e("Testing", "raw : $a")
                                Log.e("Testing", "address: $address")
                                intent.putExtra("address", address)
                                intent.putExtra("road_address", road_address)
                                intent.putExtra("lat", lat)
                                intent.putExtra("lng", lng)
                                startActivity(intent)
                            }

                            override fun onFailure(call: Call<coord2address>, t: Throwable) {
                                startActivity(intent)
                            }
                        })
                    })
                }

                AnimatedVisibility(
                    visible = ( isLeftOpen.value || isCloudMindOpen.value ),
                    modifier = Modifier.fillMaxSize(1f),
                    enter = fadeIn(),
                    exit = fadeOut()
                ){
                    Box(
                        Modifier
                            .fillMaxSize(1f)
                            .background(Color.Black.copy(alpha = 0.5f))
                            .clickable {
                                listOf(isLeftOpen, isCloudMindOpen).forEach { it.value = false }
                            }
                    )
                }
                HomeLeftModal(
                    logoutButton = { User.logoutCurrentUser(); finish() },
                    isDrawerOpen = isLeftOpen,
                    profileUri = profileUri
                )
                CloudMindModal(isDrawerOpen = isCloudMindOpen)
            }
        }

        CoroutineScope(Dispatchers.Main).launch {
            val location = loadCurrentLocation()
            this@MainActivity.lat = location?.latitude ?: 0.0
            this@MainActivity.lng = location?.longitude ?: 0.0
        }
    }

    override fun onResume() {
        super.onResume()
        mapView.value?.resume()

        CoroutineScope(Dispatchers.Main).launch {
            user = User.getCurrentUser()
            if (user != null) {
                profileUri.value = Uri.parse(user!!.photoURL)
                Log.d("MainActivity", profileUri.value.toString())
            }
        }
    }

    override fun onStart() {
        super.onStart()
        val context = this

        mapView.value?.start(object : MapLifeCycleCallback() {
            override fun onMapDestroy() {
                TODO("Not yet implemented")
            }

            override fun onMapError(error: Exception?) {
                TODO("Not yet implemented")
            }
        },
            object : KakaoMapReadyCallback() {
                override fun onMapReady(kakaoMap: KakaoMap) {
                    this@MainActivity.kakaoMap.value = kakaoMap
                    val locationManager =
                        applicationContext.getSystemService(LOCATION_SERVICE) as LocationManager
                    var isGpsOn = locationManager.isLocationEnabled

                    val styles: LabelStyles? = kakaoMap.labelManager?.addLabelStyles(
                        LabelStyles.from(
                            LabelStyle.from(R.drawable.vector)
                        )
                    )
                    val options: LabelOptions =
                        LabelOptions.from(getCurrentLatLng()).setStyles(styles)
                    val layer = kakaoMap.labelManager?.layer

                    if (isGpsOn) {
                        val label = layer?.addLabel(options)
                        label?.show(true)
                    }

                    kakaoMap.setOnLabelClickListener { _, _, label ->
                        val postId = label.layer.layerId

                        val intent = Intent(context, CloudContentActivity::class.java)
                        intent.putExtra("postId", postId)

                        startActivity(intent)
                    }
                }

                override fun getPosition(): LatLng {
                    return getCurrentLatLng()
                }

                override fun getZoomLevel(): Int {
                    return 6
                }
            })

        CoroutineScope(Dispatchers.Main).launch {
            user = User.getCurrentUser()
            if (user != null) {
                profileUri.value = Uri.parse(user!!.photoURL)
                Log.d("MainActivity", profileUri.value.toString())
            }

            val clouds = user?.uid?.let { Post.getPostByUID(it) } ?: listOf()
            if (clouds.isNotEmpty()) {
                clouds.forEach {
                    kakaoMap.value?.labelManager?.addLayer(
                        LabelLayerOptions.from(it.id!!)
                            .setClickable(true)
                    )?.addLabel(
                        LabelOptions.from(
                            LatLng.from(
                                it.lat!!,
                                it.lng!!
                            )
                        ).setStyles(
                            viewConvertToBitmap(
                                this@MainActivity,
                                R.drawable.v_cloud_icon,
                                80, 80
                            )
                        )
                    )
                }
            }

            moveMapCurrentLocation()
        }
    }

    override fun onPause() {
        super.onPause()
        mapView.value?.pause()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode) {
            REQUEST_CODE_LOCATION_PERMISSIONS -> {
                if((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    Log.d("MainActivity", "LocationPermission granted.")
                } else {
                    val settingsIntent = Intent()
                    settingsIntent.action = android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                    settingsIntent.data = Uri.fromParts("package", packageName, null)
                    startActivity(settingsIntent)

                    Toast.makeText(this, "위치권한을 얻지 못했습니다!\n위치권한을 허용해주세요!", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun moveMapCurrentLocation() {
        val camera = CameraUpdateFactory.newCenterPosition(
            LatLng.from(
                this@MainActivity.lat,
                this@MainActivity.lng
            ), 14
        )
        val labelManager = kakaoMap.value?.labelManager
        val styles: LabelStyles? = labelManager?.addLabelStyles(
            LabelStyles.from(
                LabelStyle.from(R.drawable.vector)
            )
        )
        val options: LabelOptions =
            LabelOptions.from(LatLng.from(
                this@MainActivity.lat,
                this@MainActivity.lng
            )).setStyles(styles)
        val layer = kakaoMap.value?.labelManager?.addLayer(
            LabelLayerOptions.from("current")
        )

        val label = layer?.addLabel(options)
        kakaoMap.value?.moveCamera(camera, CameraAnimation.from(500, true, true))
    }

    private fun getCurrentLatLng() : LatLng {
        var uLat = 37.402005
        var uLng = 127.108621
        val locationManager = this.getSystemService(LOCATION_SERVICE) as LocationManager
        var isGpsOn = locationManager.isLocationEnabled

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (isGpsOn) {
                val userCurrentLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)

                uLat = userCurrentLocation!!.latitude
                uLng = userCurrentLocation.longitude
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
        return if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this,
                LOCATION_PERMISSIONS,
                REQUEST_CODE_LOCATION_PERMISSIONS
            )

            null
        } else {
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

            fusedLocationClient.getCurrentLocation(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY, object: CancellationToken() {
                override fun onCanceledRequested(p0: OnTokenCanceledListener): CancellationToken = CancellationTokenSource().token
                override fun isCancellationRequested(): Boolean = false
            }).await()
        }
    }

    private fun viewConvertToBitmap(context: Context, drawableId: Int, resX: Int? = null, resY: Int? = null): Bitmap? {
        val drawable = ContextCompat.getDrawable(context, drawableId)
        val bitmap = if(resX != null || resY != null) Bitmap.createScaledBitmap(
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
}

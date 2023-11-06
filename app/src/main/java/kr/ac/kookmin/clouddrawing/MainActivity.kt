
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
import com.kakao.vectormap.camera.CameraUpdateFactory
import com.kakao.vectormap.label.LabelLayerOptions
import com.kakao.vectormap.label.LabelOptions
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


class MainActivity : AppCompatActivity() {
    private val appBarConfiguration: AppBarConfiguration? = null
    private lateinit var mapView: MapView

    private lateinit var isLeftOpen: MutableState<Boolean>
    private lateinit var isCloudMindOpen: MutableState<Boolean>

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private lateinit var kakaoMap: KakaoMap

    private var lat: Double = 0.0
    private var lng: Double = 0.0

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

        mapView = MapView(applicationContext)

        val mapViewFlow = MutableStateFlow(value = mapView)
        val searchBar = ViewModelProvider(this)[SearchBarModel::class.java]
        val context = this

        setContent {
            isLeftOpen = remember { mutableStateOf(false) }
            isCloudMindOpen = remember { mutableStateOf(false) }

            Box(Modifier.fillMaxSize()) {
                KakaoMapComponent(
                    modifier = Modifier.fillMaxSize(),
                    mapView = mapView
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
                    SearchBar(searchBar, onSearch = { isCloudMindOpen.value = true }, Modifier.fillMaxWidth(1f))
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
                        intent.putExtra("lat", lat)
                        intent.putExtra("lng", lng)

                        startActivity(intent)
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

        mapView.start(object : MapLifeCycleCallback() {
            override fun onMapDestroy() {
                TODO("Not yet implemented")
            }

            override fun onMapError(error: Exception?) {
                TODO("Not yet implemented")
            }
        },
        object : KakaoMapReadyCallback() {
            override fun onMapReady(kakaoMap: KakaoMap) {
                this@MainActivity.kakaoMap = kakaoMap

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
                return 18
            }
        })

        CoroutineScope(Dispatchers.Main).launch {
            user = User.getCurrentUser()
            if(user != null) {
                profileUri.value = Uri.parse(user!!.photoURL)
                Log.d("MainActivity", profileUri.value.toString())
            }

            val location = loadCurrentLocation(500L, 1000L)

            if (location != null) {
                lat = location.latitude
                lng = location.longitude
                val camera = CameraUpdateFactory.newCenterPosition(
                    LatLng.from(
                        lat,
                        lng
                    )
                )
                Log.d(TAG, "lat: ${lat}, lng: ${lng}")
                kakaoMap.moveCamera(camera)

                val labelManager = kakaoMap.labelManager
                val labelLayer = labelManager?.layer

                val clouds = user?.uid?.let { Post.getPostByUID(it) } ?: listOf()
                if (clouds.isNotEmpty()) {
                    clouds.forEach {
                        labelManager?.addLayer(LabelLayerOptions.from(it.id!!)
                            .setClickable(true)
                        )?.addLabel(LabelOptions.from(LatLng.from(
                            it.lat!!,
                            it.lng!!
                        )).setStyles(viewConvertToBitmap(
                            this@MainActivity,
                            R.drawable.v_cloud_icon,
                            80, 80))
                        )
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        mapView.resume()

        CoroutineScope(Dispatchers.Main).launch {
            user = User.getCurrentUser()
            if (user != null) {
                profileUri.value = Uri.parse(user!!.photoURL)
                Log.d("MainActivity", profileUri.value.toString())
            }
        }
    }

    override fun onPause() {
        super.onPause()
        mapView.pause()
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

    private fun getCurrentLatLng() : LatLng {
        var uLat = 37.402005
        var uLng = 127.108621

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this,
                LOCATION_PERMISSIONS,
                REQUEST_CODE_LOCATION_PERMISSIONS
            )
        } else {
            val locationManager = this.getSystemService(LOCATION_SERVICE) as LocationManager

            val userCurrentLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)

            uLat = userCurrentLocation!!.latitude
            uLng = userCurrentLocation!!.longitude
        }

        return LatLng.from(uLat, uLng)
    }

    private suspend fun loadCurrentLocation(limitTime: Long, cachingExpiresIn: Long): Location? {
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

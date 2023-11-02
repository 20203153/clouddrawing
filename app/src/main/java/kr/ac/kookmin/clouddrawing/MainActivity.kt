
package kr.ac.kookmin.clouddrawing

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
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
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.ui.AppBarConfiguration
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.kakao.vectormap.KakaoMap
import com.kakao.vectormap.KakaoMapReadyCallback
import com.kakao.vectormap.LatLng
import com.kakao.vectormap.MapLifeCycleCallback
import com.kakao.vectormap.MapView
import kotlinx.coroutines.flow.MutableStateFlow
import kr.ac.kookmin.clouddrawing.components.AddCloudBtn
import kr.ac.kookmin.clouddrawing.components.CloudMindModal
import kr.ac.kookmin.clouddrawing.components.HomeLeftContent
import kr.ac.kookmin.clouddrawing.components.KakaoMapComponent
import kr.ac.kookmin.clouddrawing.components.MyCloudBtn
import kr.ac.kookmin.clouddrawing.components.SearchBar
import kr.ac.kookmin.clouddrawing.components.SearchBarModel


class MainActivity : AppCompatActivity() {
    private val appBarConfiguration: AppBarConfiguration? = null
    private lateinit var mapView: MapView

    private lateinit var isLeftOpen: MutableState<Boolean>
    private lateinit var isCloudMindOpen: MutableState<Boolean>

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
                    SearchBar(searchBar, onSearch = { isCloudMindOpen.value = true })
                }

                Row(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(bottom = 53.dp, start = 33.dp, end = 33.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    /*
                    FriendCloudBtn(friendCloud = {
                        startActivity(Intent(context, SignupActivity::class.java))
                    })
                     */
                    AddCloudBtn(addCloud = {
                        Firebase.auth.signOut()
                        Toast.makeText(applicationContext, "Logout!", Toast.LENGTH_LONG).show()
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
                HomeLeftContent(isDrawerOpen = isLeftOpen)
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

                }

                override fun getPosition(): LatLng {
                    return super.getPosition()
                }
            })
    }

    override fun onResume() {
        super.onResume()
        mapView.resume()
    }

    override fun onPause() {
        super.onPause()
        mapView.pause()
    }
}

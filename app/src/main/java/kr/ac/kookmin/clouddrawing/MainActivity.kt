
package kr.ac.kookmin.clouddrawing

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.ui.AppBarConfiguration
import com.kakao.vectormap.KakaoMap
import com.kakao.vectormap.KakaoMapReadyCallback
import com.kakao.vectormap.LatLng
import com.kakao.vectormap.MapLifeCycleCallback
import com.kakao.vectormap.MapView
import kotlinx.coroutines.flow.MutableStateFlow
import kr.ac.kookmin.clouddrawing.components.AddCloudBtn
import kr.ac.kookmin.clouddrawing.components.KakaoMapComponent
import kr.ac.kookmin.clouddrawing.components.LeftCloseBtn
import kr.ac.kookmin.clouddrawing.components.MyCloudBtn
import kr.ac.kookmin.clouddrawing.components.SearchBar
import kr.ac.kookmin.clouddrawing.components.SearchBarModel

class MainActivity : AppCompatActivity() {
    private val appBarConfiguration: AppBarConfiguration? = null
    private lateinit var mapView: MapView

    @SuppressLint("StateoFlowValueCalledInComposition", "StateFlowValueCalledInComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mapView = MapView(applicationContext)
        val mapViewFlow = MutableStateFlow(value = mapView)
        val searchBar = ViewModelProvider(this)[SearchBarModel::class.java]

        setContent {
            var isDrawerOpen by remember { mutableStateOf(false) }

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
                        isDrawerOpen = true
                    }
                    Spacer(Modifier.width(5.dp))
                    SearchBar(searchBar, { })
                }

                Row(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(bottom = 53.dp, start = 33.dp, end = 33.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    AddCloudBtn({ })
                }

                if (isDrawerOpen) {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = Color.Black.copy(alpha = 0.5f),
                        content = {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clickable { isDrawerOpen = false }
                            )
                        }
                    )
                    HomeLeftContent {
                        isDrawerOpen = false
                    }
                }
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

@Composable
fun HomeLeftContent(function: () -> Unit) {
    Box(Modifier.background(color = Color(0xFFFFFFFF))) {
        Box(
            Modifier
                .padding(end = 20.dp, top = 16.dp)
                .align(Alignment.TopEnd)
        ) {
            LeftCloseBtn { /* TODO: Close button action */ }
        }

        Column(
            modifier = Modifier
                .fillMaxHeight(1f)
                .width(193.dp)
                .fillMaxHeight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(Modifier.height(53.dp))
            Image(
                painter = painterResource(id = R.drawable.g_profile),
                contentDescription = "Profile Image",
                modifier = Modifier
                    .width(80.dp)
                    .height(80.dp)
            )
            Spacer(Modifier.height(44.dp))
            Text(
                text = "내 정보",
                style = TextStyle(
                    fontSize = 14.sp,
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.W600,
                    color = Color(0xFF7D7D7D)
                ),
                modifier = Modifier.clickable { /* TODO: Handle 내 정보 click event here */ }
            )
            Spacer(Modifier.height(48.dp))
            MyCloudText()
        }

        Box(
            Modifier
                .padding()
                .align(Alignment.BottomStart)
                .clickable { /* TODO: Handle Logout click event here */ }
        ) {
            Image(
                painter = painterResource(id = R.drawable.logout),
                contentDescription = "Logout",
                modifier = Modifier
                    .width(67.dp)
                    .height(17.dp)
            )
        }
    }
}
@Composable
fun MyCloudText() {
    Text(
        text = "나의 구름",
        style = TextStyle(
            fontSize = 20.sp,
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.W600,
            color = Color(0xFF454545)
        ),
        modifier = Modifier.clickable { /* TODO: Handle 나의 구름 click event here */ }
    )
}


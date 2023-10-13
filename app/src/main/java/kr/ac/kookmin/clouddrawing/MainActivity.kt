package kr.ac.kookmin.clouddrawing

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.ui.AppBarConfiguration
import com.kakao.vectormap.KakaoMap
import com.kakao.vectormap.KakaoMapReadyCallback
import com.kakao.vectormap.LatLng
import com.kakao.vectormap.MapLifeCycleCallback
import com.kakao.vectormap.MapView
import kotlinx.coroutines.flow.MutableStateFlow
import kr.ac.kookmin.clouddrawing.components.KakaoMapComponent
import kr.ac.kookmin.clouddrawing.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val appBarConfiguration: AppBarConfiguration? = null
    private var binding: ActivityMainBinding? = null

    private lateinit var mapView: MapView

    @SuppressLint("StateFlowValueCalledInComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mapView = MapView(applicationContext)
        val mapViewFlow = MutableStateFlow(mapView)

        setContent {
            Box(
                Modifier
                    .fillMaxSize(1f)
                    .background(color = Color(0xFFFFFFFF))
            ) {
                KakaoMapComponent(
                    modifier = Modifier.fillMaxSize(1f),
                    mapView = mapViewFlow.value
                )
                SearchBar(
                    onClick = {
                        // 클릭 시 동작
                        // 여기에 원하는 동작을 추가하세요
                    }
                )
                MyCloudBtn(
                    onClick = {
                        // 클릭 시 동작
                        // 여기에 원하는 동작을 추가하세요
                    }
                )
                FriendCloudBtn(
                    onClick = {
                        // 클릭 시 동작
                        // 여기에 원하는 동작을 추가하세요
                    }
                )
                AddCloudBtn(
                    onClick = {
                        // 클릭 시 동작
                        // 여기에 원하는 동작을 추가하세요
                    }
                )
                SearchBtn(
                    onClick = {
                        // 클릭 시 동작
                        // 여기에 원하는 동작을 추가하세요
                    }
                )
            }
        }

        mapView.start(object: MapLifeCycleCallback() {
            override fun onMapDestroy() {
                TODO("Not yet implemented")
            }

            override fun onMapError(error: Exception?) {
                TODO("Not yet implemented")
            }
        },
        object: KakaoMapReadyCallback() {
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

/*
AndroidView(
    modifier = Modifier.fillMaxSize(1f),
    factory = { context -> MapView(context).apply(listeners) }
)
*/

@Preview(showBackground = true)
@Composable
fun MainActivityPre() {
    Box(
        Modifier
            .fillMaxSize(1f)
            .background(color = Color(0xFFFFFFFF))
    ) {
        SearchBar(
            onClick = {
                // 클릭 시 동작
                // 여기에 원하는 동작을 추가하세요
            }
        )
        MyCloudBtn(
            onClick = {
                // 클릭 시 동작
                // 여기에 원하는 동작을 추가하세요
            }
        )
        FriendCloudBtn(
            onClick = {
                // 클릭 시 동작
                // 여기에 원하는 동작을 추가하세요
            }
        )
        AddCloudBtn(
            onClick = {
                // 클릭 시 동작
                // 여기에 원하는 동작을 추가하세요
            }
        )
        SearchBtn(
            onClick = {
                // 클릭 시 동작
                // 여기에 원하는 동작을 추가하세요
            }
        )

    }
}

@Composable
fun SearchBar(onClick: () -> Unit) {
    Image(
        painter = painterResource(id = R.drawable.r_home_search),
        contentDescription = "search bar",
        contentScale = ContentScale.None,
        modifier = Modifier
            .offset(x = 57.dp, y = 73.dp)
            .width(320.dp)
            .height(38.dp)
            .clickable { onClick() } // 클릭 핸들러 추가
    )
}
@Composable
fun SearchBtn(onClick: () -> Unit) {
    Image(
        painter = painterResource(id = R.drawable.v_home_search),
        contentDescription = "search Btn",
        contentScale = ContentScale.None,
        modifier = Modifier
            .offset(x = 347.83.dp, y = 82.4.dp)
            .width(18.11669.dp)
            .height(17.32211.dp)
            .zIndex(1f) // SearchBtn을 SearchBar 위에 배치
            .clickable { onClick() }
    )
}


@Composable
fun MyCloudBtn(onClick: () -> Unit) {
    Image(
        painter = painterResource(id = R.drawable.mycloudbtn),
        contentDescription = "my cloud btn",
        contentScale = ContentScale.None,
        modifier = Modifier
            .offset(x = 12.dp, y = 73.dp)
            .width(40.dp)
            .height(45.dp)
            .clickable { onClick() } // 클릭 핸들러 추가
    )
}

@Composable
fun FriendCloudBtn(onClick: () -> Unit) {
    Image(
        painter = painterResource(id = R.drawable.friendcloud),
        contentDescription = "friend cloud",
        contentScale = ContentScale.None,
        modifier = Modifier
            .offset(x = 33.dp, y = 751.dp)
            .width(40.dp)
            .height(40.dp)
            .clickable { onClick() } // 클릭 핸들러 추가
    )
}

@Composable
fun AddCloudBtn(onClick: () -> Unit) {
    Image(
        painter = painterResource(id = R.drawable.addcloud),
        contentDescription = "add cloud btn",
        contentScale = ContentScale.None,
        modifier = Modifier
            .offset(x = 318.dp, y = 751.dp)
            .width(40.dp)
            .height(40.dp)
            .clickable { onClick() } // 클릭 핸들러 추가
    )
}
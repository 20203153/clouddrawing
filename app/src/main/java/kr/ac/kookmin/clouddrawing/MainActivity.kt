package kr.ac.kookmin.clouddrawing

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.lifecycle.ViewModel
import androidx.navigation.ui.AppBarConfiguration
import com.kakao.vectormap.KakaoMap
import com.kakao.vectormap.KakaoMapReadyCallback
import com.kakao.vectormap.LatLng
import com.kakao.vectormap.MapLifeCycleCallback
import com.kakao.vectormap.MapView
import kotlinx.coroutines.flow.MutableStateFlow
import kr.ac.kookmin.clouddrawing.components.KakaoMapComponent
import kr.ac.kookmin.clouddrawing.databinding.ActivityMainBinding

class MyViewModel : ViewModel(){
    // ViewModel 내에서 관리할 데이터 및 상태를 정의합니다.
}

class MainActivity : AppCompatActivity() {
    private val appBarConfiguration: AppBarConfiguration? = null
    private var binding: ActivityMainBinding? = null

    private lateinit var mapView: MapView

    @SuppressLint("StateoFlowValueCalledInComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mapView = MapView(applicationContext)
        val mapViewFlow = MutableStateFlow(mapView)

        setContent {
            val myViewModel = MyViewModel()

            Box(
                Modifier
                    .fillMaxSize(1f)
                    .background(color = Color(0xFFFFFFFF))
            ) {
                KakaoMapComponent(
                    modifier = Modifier.fillMaxSize(1f),
                    mapView = mapViewFlow.value
                )
                SearchBar{
                    //클릭 핸들러 동작
                }
                MyCloudBtn{

                }
                FriendCloudBtn {

                }
                AddCloudBtn{

                }
                SearchBtn{

                }

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
    val myViewModel = MyViewModel()
    Box(
        Modifier
            .fillMaxSize(1f)
            .background(color = Color(0xFFFFFFFF))
    ) {
        SearchBar{
            // 클릭 시 동작
            // 여기에 원하는 동작을 추가하세요
        }
        SearchBtn{

        }

        MyCloudBtn{

        }

        FriendCloudBtn {

        }
        AddCloudBtn{

        }


    }
}

@Composable
fun SearchBar(onClick: () -> Unit) {
    val myViewModel = MyViewModel()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable { onClick() } // 전체 영역에 대한 클릭 핸들러
    ) {
        Box(
            modifier = Modifier
                .offset(x = 57.dp, y = 73.dp)
                .width(320.dp)
                .height(38.dp)
                //.align(Alignment.TopStart) // 원하는 위치로 설정
        ) {
            Image(
                painter = painterResource(id = R.drawable.r_home_search),
                contentDescription = "search bar",
                contentScale = ContentScale.None
            )
        }
    }
}
@Composable
fun SearchBtn(onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable { onClick() }
    ) {
        Image(
            painter = painterResource(id = R.drawable.v_home_search),
            contentDescription = "search Btn",
            contentScale = ContentScale.Crop, // 이미지의 크기를 보존하면서 잘라내기
            modifier = Modifier
                .offset(x = 347.83.dp, y = 82.4.dp)
                .width(17.dp) // 원하는 너비로 설정
                .height(17.dp) // 원하는 높이로 설정
        )
    }
}


@Composable
fun MyCloudBtn(onClick: () -> Unit) {
    val myViewModel = MyViewModel()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable { onClick() } // 클릭 핸들러 추가
    ){
        Box(
            modifier = Modifier
                .offset(x = 12.dp, y = 73.dp)
                .width(40.dp)
                .height(45.dp)

        ){
            Image(
                painter = painterResource(id = R.drawable.mycloudbtn),
                contentDescription = "my cloud btn",
                contentScale = ContentScale.None,

            )
        }
    }

    
}

@Composable
fun FriendCloudBtn(onClick: () -> Unit) {
    val myViewModel = MyViewModel()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable { onClick()}
            .padding(20.dp)

    ){
        Box(
            modifier = Modifier
                .width(40.dp)
                .height(40.dp)
                .align(Alignment.BottomStart)

        ){
            Image(
                painter = painterResource(id = R.drawable.friendcloud),
                contentDescription = "friend cloud",
                contentScale = ContentScale.None
            )
        }
    }


}

@Composable
fun AddCloudBtn(onClick: () -> Unit) {
    val myViewModel = MyViewModel()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable { onClick() } // 클릭 핸들러 추가
            .padding(20.dp)

    ){
        Box(
            modifier = Modifier
                .width(40.dp)
                .height(40.dp)
                .align(Alignment.BottomEnd)
        ){
            Image(
                painter = painterResource(id = R.drawable.addcloud),
                contentDescription = "add cloud btn",
                contentScale = ContentScale.None,

            )
        }
    }


}
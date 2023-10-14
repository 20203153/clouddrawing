package kr.ac.kookmin.clouddrawing

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalMinimumInteractiveComponentEnforcement
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.ui.AppBarConfiguration
import com.kakao.vectormap.KakaoMap
import com.kakao.vectormap.KakaoMapReadyCallback
import com.kakao.vectormap.LatLng
import com.kakao.vectormap.MapLifeCycleCallback
import com.kakao.vectormap.MapView
import kotlinx.coroutines.flow.MutableStateFlow
import kr.ac.kookmin.clouddrawing.components.KakaoMapComponent
import kr.ac.kookmin.clouddrawing.databinding.ActivityMainBinding

class SearchBarModel : ViewModel(){
    var search: String = ""
}

class MainActivity : AppCompatActivity() {
    private val appBarConfiguration: AppBarConfiguration? = null
    private var binding: ActivityMainBinding? = null

    private lateinit var mapView: MapView

    @SuppressLint("StateoFlowValueCalledInComposition", "StateFlowValueCalledInComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mapView = MapView(applicationContext)
        val mapViewFlow = MutableStateFlow(mapView)
        val searchBar = ViewModelProvider(this)[SearchBarModel::class.java]

        setContent {
            Box(
                Modifier
                    .fillMaxSize(1f)
                    .background(color = Color.Transparent)
            ) {
                KakaoMapComponent(
                    modifier = Modifier.fillMaxSize(1f),
                    mapView = mapView
                )
                Row(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(start = 12.dp, end = 12.dp, top = 73.dp)
                        .fillMaxWidth(1f),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    MyCloudBtn({ })
                    Spacer(Modifier.width(5.dp))
                    SearchBar(searchBar, { })
                }
                Row(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(bottom = 53.dp, start = 33.dp, end = 33.dp)
                        .fillMaxWidth(1f),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    FriendCloudBtn({ })
                    AddCloudBtn({ })
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
    val myViewModel = SearchBarModel()

    Box(
        Modifier
            .fillMaxSize(1f)
            .background(color = Color(0xFFFFFFFF))
    ) {
        Row(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(start = 12.dp, end = 12.dp, top = 73.dp)
                .fillMaxWidth(1f),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            MyCloudBtn({ })
            Spacer(Modifier.width(5.dp))
            SearchBar(myViewModel, { })
        }
        Row(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(bottom = 53.dp, start = 33.dp, end = 33.dp)
                .fillMaxWidth(1f),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            FriendCloudBtn({ })
            AddCloudBtn({ })
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(viewModel: SearchBarModel, onSearch: () -> Unit) {
    var searchValue by remember {
       mutableStateOf(viewModel.search)
    }

    Row(
        modifier = Modifier
            .shadow(
                elevation = 5.dp,
                spotColor = Color(0x0D000000),
                ambientColor = Color(0x0D000000)
            )
            .border(
                width = 1.dp,
                color = Color(0xFFF6F6F6),
                shape = RoundedCornerShape(size = 10.dp)
            )
            .width(340.dp)
            .height(38.dp)
            .background(
                color = Color(0xFFFFFFFF),
                shape = RoundedCornerShape(size = 10.dp)
            ),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        BasicTextField(
            modifier = Modifier
                .height(38.dp)
                .padding(horizontal = 8.dp, vertical = 10.dp)
                .fillMaxWidth(0.9f),
            value = searchValue,
            onValueChange = {
                searchValue = it
                viewModel.search = it
            },
            singleLine = true,
        )
        CompositionLocalProvider(LocalMinimumInteractiveComponentEnforcement provides false) {
            IconButton(
                onClick = onSearch,
                modifier = Modifier
                    .width(40.dp)
                    .height(38.dp)
                    .background(Color.Transparent)
            ) {
                Image(
                    modifier = Modifier
                        .height(18.dp)
                        .width(18.dp),
                    painter = painterResource(id = R.drawable.v_home_search),
                    contentDescription = "",
                    contentScale = ContentScale.Fit
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyCloudBtn(myCloud: () -> Unit) {
    CompositionLocalProvider(LocalMinimumInteractiveComponentEnforcement provides false) {
        IconButton(
            onClick = myCloud,
            modifier = Modifier
                .padding(0.dp)
                .width(40.dp)
                .height(38.dp)
                .background(
                    Color(0xFFD2E1FF),
                    RoundedCornerShape(10.dp)
                )
        ) {
            Image(
                modifier = Modifier
                    .height(17.1.dp)
                    .width(10.dp),
                painter = painterResource(id = R.drawable.v_arrow_open),
                contentDescription = "",
                contentScale = ContentScale.Fit
            )
        }
    }
}

@Composable
fun FriendCloudBtn(friendCloud: () -> Unit) {
    IconButton(
        onClick = friendCloud,
        modifier = Modifier
            .width(40.dp)
            .height(40.dp)
    ){
        Image(
            painter = painterResource(id = R.drawable.friendcloud),
            contentDescription = "friend cloud",
            contentScale = ContentScale.None
        )
    }
}

@Composable
fun AddCloudBtn(addCloud: () -> Unit) {
    IconButton(
        modifier = Modifier
            .width(40.dp)
            .height(40.dp),
        onClick = addCloud
    ) {
        Image(
            painter = painterResource(id = R.drawable.addcloud),
            contentDescription = "add cloud btn",
            contentScale = ContentScale.None,
        )
    }
}
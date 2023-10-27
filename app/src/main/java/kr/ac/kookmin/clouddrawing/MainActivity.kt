package kr.ac.kookmin.clouddrawing

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
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
import kr.ac.kookmin.clouddrawing.components.FriendCloudBtn
import kr.ac.kookmin.clouddrawing.components.KakaoMapComponent
import kr.ac.kookmin.clouddrawing.components.MyCloudBtn
import kr.ac.kookmin.clouddrawing.components.SearchBar
import kr.ac.kookmin.clouddrawing.components.SearchBarModel
import kr.ac.kookmin.clouddrawing.databinding.ActivityMainBinding

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
        val context = this

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
                    FriendCloudBtn(friendCloud = {
                        startActivity(Intent(context, SignupActivity::class.java))
                    })
                    AddCloudBtn(addCloud = {
                        Firebase.auth.signOut()
                        Toast.makeText(applicationContext, "Logout!", Toast.LENGTH_LONG).show()
                    })
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

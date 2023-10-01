package kr.ac.kookmin.clouddrawing

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.ui.AppBarConfiguration
import com.kakao.vectormap.KakaoMap
import com.kakao.vectormap.KakaoMapReadyCallback
import com.kakao.vectormap.LatLng
import com.kakao.vectormap.MapLifeCycleCallback
import com.kakao.vectormap.MapType
import com.kakao.vectormap.MapView
import com.kakao.vectormap.MapViewInfo
import kr.ac.kookmin.clouddrawing.databinding.ActivityMainBinding
import kr.ac.kookmin.clouddrawing.test.Test
import kr.ac.kookmin.clouddrawing.theme.ApplicationTheme
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    private val appBarConfiguration: AppBarConfiguration? = null
    private var binding: ActivityMainBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mapView = findViewById<MapView>(R.id.map_view)

        mapView.start(object : MapLifeCycleCallback(){
            override fun onMapDestroy() {
            }

            override fun onMapError(error: Exception?) {
            }
        }, object : KakaoMapReadyCallback(){
            override fun getPosition(): LatLng {
                return super.getPosition()
            }

            override fun onMapReady(kakaoMap: KakaoMap) {
                var loc = position
            }
        })

    }
}

/*
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ApplicationTheme {
       Test()
    }
}
*/
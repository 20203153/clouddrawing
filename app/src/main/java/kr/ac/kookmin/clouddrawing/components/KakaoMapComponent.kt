package kr.ac.kookmin.clouddrawing.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.kakao.vectormap.MapView

@Composable
fun KakaoMapComponent(modifier: Modifier, mapView: MapView) {
    AndroidView(
        factory = { mapView },
        modifier = modifier
    )
}
package kr.ac.kookmin.clouddrawing

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


class CloudMindActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CMBackground {
                CMMain()
            }
        }
    }
}

@Composable
fun CMBackground(content: @Composable () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF919191))
    ) {
        content()
    }
}

@Composable
fun CMMain() {
    Surface(
        shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp, bottomStart = 0.dp, bottomEnd = 0.dp),
        color = Color.White,
        modifier = Modifier
            .fillMaxWidth()
            .height(1100.dp)
            .padding(top = 250.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            CMWho(
                modifier = Modifier.align(Alignment.TopCenter)
                    .padding(top = 100.dp)
            )
            CMLocation(
                modifier = Modifier.align(Alignment.Center)
            )
            TLine(
                modifier = Modifier.align(Alignment.BottomCenter)
                    .padding(start = 16.dp, end = 16.dp, bottom = 560.dp)
            )
        }
    }
}

@Composable
fun CMWho(modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(id = R.drawable.v_cm_who),
        contentDescription = "CMWho",
        modifier = modifier
            .size(22.dp, 16.dp)
    )
}

@Composable
fun CMLocation(modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(id = R.drawable.f_cm_location),
        contentDescription = "CWLocation",
        modifier = modifier
            .size(22.dp, 16.dp)
            .padding(start=10.dp, end=10.dp)
    )
}
@Composable
fun TLine(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .size(111.dp, 5.dp)
            .background(color = Color(0xFFA6A6A6), shape = RoundedCornerShape(size = 25.dp))
    )
}
@Preview
@Composable
fun PreviewBackground() {
    CMBackground {
        CMMain()
    }
}

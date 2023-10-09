package kr.ac.kookmin.clouddrawing

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
        shape = RoundedCornerShape(40.dp),
        color = Color.White,
        modifier = Modifier
            .fillMaxWidth()
            .height(599.dp)
            .padding(top = 16.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            CMWho(
                modifier = Modifier.align(Alignment.Center)
            )
            CMLocation(
                modifier = Modifier.align(Alignment.Center)
            )
            tLine(
                modifier = Modifier.align(Alignment.Center)
                    .padding(start = 140.dp, end = 140.dp, top = 270.dp, bottom = 572.dp)
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
            .padding(start=108.dp, end=273.dp)
    )
}
@Composable
fun tLine(modifier: Modifier = Modifier) {
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

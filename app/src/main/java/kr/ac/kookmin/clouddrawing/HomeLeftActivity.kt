package kr.ac.kookmin.clouddrawing

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

class HomeLeftActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Logout()
                ArrowClose()
                ProfileImage()
                MyText()
                MyCloudText()
                MyScreen()
            }
        }
    }
}
@Preview
@Composable
fun MyScreen() {
    Box(
        Modifier
            .width(193.dp)
            .height(844.dp)
            .background(color = Color(0xFFFFFFFF))

    ) {
        ArrowClose()
        Logout()
        ProfileImage()
        MyText()
        MyCloudText()
    }
}
@Composable
fun ProfileImage() {
    Image(
        painter = painterResource(id = R.drawable.g_profile),
        contentDescription = "Profile Image",
        modifier = Modifier
            .padding(start = 60.dp, end = 53.dp, top = 94.dp, bottom = 670.dp)
            .width(80.dp)
            .height(80.dp)
    )
}
@Composable
fun Logout() {
    Image(
        painter = painterResource(id = R.drawable.logout),
        contentDescription = "Logout",
        modifier = Modifier
            .padding(start = 26.dp, end = 115.dp, top = 819.dp, bottom = 8.dp)
            .width(67.dp)
            .height(17.dp)
    )
}
@Composable
fun ArrowClose() {
    Image(
        painter = painterResource(id = R.drawable.v_arrow_close),
        contentDescription = "Arrow Close",
        modifier = Modifier
            .padding(start = 166.dp, end = 12.dp, top = 16.dp, bottom = 803.dp)
            .width(15.dp)
            .height(25.dp)
    )
}

@Composable
fun MyText() {
    Box(
        modifier = Modifier
            .padding(start = 79.dp, end = 71.dp, top = 218.dp, bottom = 609.dp)
            .width(43.dp)
            .height(17.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "내 정보",
            style = TextStyle(
                fontSize = 14.sp,
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.W600,
                color = Color(0xFF7D7D7D)
            )
        )
    }
}
@Composable
fun MyCloudText() {
    Box(
        modifier = Modifier
            .padding(start = 61.dp, end = 53.dp, top = 283.dp, bottom = 537.dp)
            .width(79.dp)
            .height(24.dp),
    ) {
        Text(
            text = "나의 구름",
            style = TextStyle(
                fontSize = 20.sp,
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.W600,
                color = Color(0xFF454545)
            )
        )
    }
}
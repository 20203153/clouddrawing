package kr.ac.kookmin.clouddrawing

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kr.ac.kookmin.clouddrawing.components.LeftCloseBtn
import kr.ac.kookmin.clouddrawing.components.LogoutBtn

class HomeLeftActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Box(Modifier.background(color = Color(0xFFFFFFFF))) {
                // Arrow Close Button
                Box(
                    Modifier
                        .padding(end = 20.dp, top = 16.dp)
                        .align(Alignment.TopEnd)
                )
                {
                    LeftCloseBtn({ })
                }

                // Main Content
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

                    // 내 정보 Text Button
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

                // Logout Button
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
    }
}

@Preview
@Composable
fun MyScreen() {
    Box(Modifier.background(color = Color(0xFFFFFFFF))) {
        Box(
            Modifier
                .padding(end = 20.dp, top = 16.dp)
                .align(Alignment.TopEnd)
        ) {
            LeftCloseBtn({ })
        }
        Column(
            modifier = Modifier
                .fillMaxHeight(1f)
                .width(193.dp)
                .fillMaxHeight(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(94.dp))
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
                modifier = Modifier.clickable { /* "내 정보" 클릭시 수행할 액션 */ }
            )
            Spacer(Modifier.height(48.dp))
            MyCloudText()
        }
        Box(
            Modifier
                .align(Alignment.BottomStart)
                .padding(bottom = 8.dp, start = 7.dp)
        ) {
            LogoutBtn({ })
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
        )
    )
}

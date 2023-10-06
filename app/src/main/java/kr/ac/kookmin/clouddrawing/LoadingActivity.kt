package kr.ac.kookmin.clouddrawing

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kr.ac.kookmin.clouddrawing.theme.ApplicationTheme

class LoadingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            make()
        }
    }

    companion object {
        @Composable
        fun make() {
            return ApplicationTheme {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .shadow(
                            elevation = 4.dp,
                            spotColor = Color(0x40000000),
                            ambientColor = Color(0x40000000)
                        )
                        .width(390.dp)
                        .height(844.dp)
                        .background(color = Color(0xFFE3ECFF))
                ) {
                    Spacer(Modifier
                        .height(294.dp)
                    )
                    Image(
                        painter = painterResource(R.drawable.loadinglogo),
                        contentDescription = "image description",
                        contentScale = ContentScale.None
                    )
                    Spacer(Modifier.height(11.dp))
                    Box(
                        Modifier
                            .width(239.dp)
                            .height(27.dp)
                    ) {
                        Text(
                            text = "나의 일상을 떠올리는 공간 ",
                            style = TextStyle(
                                fontSize = 22.sp,
                                fontFamily = FontFamily(Font(R.font.inter)),
                                fontWeight = FontWeight(600),
                                color = Color(0xFF001753),
                            )
                        )
                    }
                    Spacer(Modifier.height(16.dp))
                    Box(
                        Modifier
                            .width(157.dp)
                            .height(37.dp)
                    ) {
                        Text(
                            text = "나만 갖고 있는 추억의 장소를\n구름과 함께 떠올려보세요!",
                            style = TextStyle(
                                fontSize = 13.sp,
                                fontFamily = FontFamily(Font(R.font.inter)),
                                fontWeight = FontWeight(600),
                                color = Color(0xFFA0A0A0),
                                textAlign = TextAlign.Center,
                            )
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoadingPreview() {
    LoadingActivity.make()
}

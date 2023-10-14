package kr.ac.kookmin.clouddrawing


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box

import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


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
        shape = RoundedCornerShape(
            topStart = 40.dp,
            topEnd = 40.dp,
            bottomStart = 0.dp,
            bottomEnd = 0.dp
        ),
        color = Color.White,
        modifier = Modifier
            .fillMaxWidth()
            .height(1100.dp)
            .padding(top = 250.dp)
    ) {
        // CMWho에 대한 Box
        Box(
            modifier = Modifier
                .padding(16.dp)
        ) {
            CMWho(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(end = 180.dp, top = 130.dp)
            )
        }

        // CMLocation에 대한 Box
        Box(
            modifier = Modifier
                .padding(16.dp)
        ) {
            CMLocation(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(end = 180.dp, bottom = 350.dp)
            )
        }

        // TLine에 대한 Box
        Box(
            modifier = Modifier
                .padding(16.dp)
        ) {
            TLine(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(start = 16.dp, end = 16.dp, bottom = 560.dp)
            )
        }
        //예시 컴포넌트들
        eContext()
        eContext2()
        eContext3()

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
        contentDescription = "CMLocation",
        modifier = modifier
            .size(100.dp, 16.dp)
            .padding(start = 10.dp, end = 10.dp)
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


// 실제 작동하는 컴포넌트가 아니라, 어느 위치 어느 크기로 작동될지 예시로 보여주는 컴포넌트들

@Composable
fun eContext() {
    Box(
        modifier = Modifier
            .width(92.dp)
            .height(150.dp)
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(bottom = 430.dp)
        ) {
            Text(
                text = "TB_date",
                style = TextStyle(
                    fontSize = 10.sp,
                    fontFamily = FontFamily(Font(R.font.inter)),
                    fontWeight = FontWeight(600),
                    color = Color(0xFF727272),
                    textAlign = TextAlign.Center
                ),
                modifier = Modifier
                    .width(82.dp)
                    .height(12.dp)
            )
            Spacer(modifier = Modifier.height(3.dp))

            Text(
                text = "TB_title",
                style = TextStyle(
                    fontSize = 25.sp,
                    fontFamily = FontFamily(Font(R.font.inter)),
                    fontWeight = FontWeight(600),
                    color = Color(0xFF454545)
                )
            )
        }
    }
}


@Composable
fun eContext2() {
    Box(
        modifier = Modifier
            .width(92.dp)
            .height(150.dp)
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(bottom = 317.dp)
        ) {
            // TB_location 텍스트
            Text(
                text = "TB_location",
                style = TextStyle(
                    fontSize = 13.sp,
                    fontFamily = FontFamily(Font(R.font.inter)),
                    fontWeight = FontWeight(600),
                    color = Color(0xFF727272),
                    textAlign = TextAlign.Center
                ),
                modifier = Modifier
                    .width(85.dp)
                    .height(16.dp)
            )
            Spacer(modifier = Modifier.height(13.dp))
            // TB_Who 텍스트
            Text(
                text = "TB_Who",
                style = TextStyle(
                    fontSize = 13.sp,
                    fontFamily = FontFamily(Font(R.font.inter)),
                    fontWeight = FontWeight(600),
                    color = Color(0xFF727272),
                    textAlign = TextAlign.Center
                ),
                modifier = Modifier
                    .width(85.dp)
                    .height(16.dp)
            )

        }
    }
}

@Composable
fun eContext3() {
    Box(
        modifier = Modifier
            .width(200.dp)
            .height(200.dp)
    ) {
        // 사각형 및 PB_image 텍스트
        Box(
            modifier = Modifier
                .width(140.dp)
                .height(140.dp)
                .background(color = Color(0xFFFFFFFF))
                .offset(y = (-30).dp)  // Y축으로 -50.dp만큼 올립니다.
                .align(Alignment.BottomCenter) // 바닥 중앙에 배치
        ) {
            // 글자의 배경 박스를 노란색으로 설정
            Box(
                modifier = Modifier
                    .width(140.dp)
                    .height(140.dp)
                    .background(color = Color(0xFFD9D9D9))
                    .align(Alignment.Center) // 중앙 정렬
                    .padding(4.dp) // 텍스트와 박스와의 간격
            ) {
                Text(
                    text = "PB_image",
                    style = TextStyle(
                        fontSize = 15.sp,
                        fontFamily = FontFamily(Font(R.font.inter)),
                        fontWeight = FontWeight(500),
                        color = Color(0xFF454545),
                    ), modifier = Modifier
                        .align(Alignment.Center) // 중앙 정렬
                )
            }
        }
    }
}

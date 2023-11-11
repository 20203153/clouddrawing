package kr.ac.kookmin.clouddrawing

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
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
import kr.ac.kookmin.clouddrawing.components.LeftCloseBtn

class CloudListActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val verticalScroll = rememberScrollState()

            CloudList(
                verticalScroll = verticalScroll,
                leftCloseBtn = { finish() }
            )
        }
    }
}

@Preview
@Composable
fun CloudList(
    leftCloseBtn: () -> Unit = {},
    verticalScroll: ScrollState = rememberScrollState()
) {
    Column(
        modifier = Modifier
            .fillMaxSize(1f)
            .background(color = Color(0xFFFFFFFF))
    ) {
        Row(
            modifier = Modifier
                .padding(start = 31.dp, end = 31.dp, top = 60.dp)
                .fillMaxWidth(1f),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            LeftCloseBtn(leftCloseBtn)
            Text(
                text = "내가 그린 구름",
                style = TextStyle(
                    fontSize = 20.sp,
                    fontFamily = FontFamily(Font(R.font.inter)),
                    fontWeight = FontWeight(600),
                    color = Color(0xFF474747)
                )
            )
            Spacer(Modifier.width(1.dp))
        } // Header done.
        Spacer(Modifier.defaultMinSize(minHeight = 20.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 31.dp, end = 31.dp, bottom = 10.dp)
                .shadow(
                    elevation = 20.dp, spotColor = Color(0x0D000000),
                    ambientColor = Color(0x0D000000)
                )
                .border(
                    width = 1.dp, color = Color(0xFFF4F4F4),
                    shape = RoundedCornerShape(size = 20.dp)
                )
                .background(
                    color = Color(0xFFB4CCFF),
                    shape = RoundedCornerShape(size = 20.dp)
                )
                .verticalScroll(rememberScrollState())
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),
                horizontalAlignment = Alignment.CenterHorizontally, // 가운데 정렬
                verticalArrangement = Arrangement.Center // 수직 중앙 정렬
            ) {
                Text(
                    text = "오늘은 구름 1 개를 그렸어요. \n이번 달 구름 1 개를 그렸어요. \n지금까지 구름 1 개를 그렸어요. ",
                    style = TextStyle(
                        fontSize = 15.sp,
                        fontFamily = FontFamily(Font(R.font.inter)),
                        fontWeight = FontWeight(600),
                        color = Color(0xFFFFFFFF),
                    ),
                    maxLines = 4,
                    textAlign = TextAlign.End
                )
                Spacer(Modifier.height(10.dp))
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth(1f)
                .padding(start = 40.dp),

            horizontalArrangement = Arrangement.Start
        ){
            Text(text = "구름 모아보기",
                style = TextStyle(
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.inter)),
                    fontWeight = FontWeight(700),
                    color = Color(0xFF474747),
                    ))
            Spacer(Modifier.height(10.dp))
        }
        Row(
            modifier = Modifier
                .fillMaxWidth(1f)
                .padding(start = 40.dp),
            horizontalArrangement = Arrangement.Start
        ){

            CLContentCard()

        }
    }
}

@Preview
@Composable
fun CLContentCard(){
    Column(
        modifier = Modifier
            .width(100.dp)
            .height(110.dp)
            .padding(top =10.dp)
            .shadow(
                elevation = 20.dp, spotColor = Color(0x0D000000),
                ambientColor = Color(0x0D000000)
            )
            .border(
                width = 1.dp, color = Color(0xFFF4F4F4),
                shape = RoundedCornerShape(size = 20.dp)
            )
            .background(
                color = Color(0xFFFFFFFF),
                shape = RoundedCornerShape(size = 20.dp)
            )
            .fillMaxHeight(1f),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(10.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth(1f)
                .padding(end = 5.dp),
            horizontalArrangement = Arrangement.End
        ) {
            Text(
                text = "2023.11.09",
                style = TextStyle(
                    fontSize = 7.sp,
                    fontFamily = FontFamily(Font(R.font.inter)),
                    fontWeight = FontWeight(600),
                    color = Color(0xFF9C9C9C),
                )
            )
        }

        Spacer(Modifier.height(10.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth(1f)
                .padding(top=7.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "TB_Title",
                style = TextStyle(
                    fontSize = 12.sp,
                    fontFamily = FontFamily(Font(R.font.inter)),
                    fontWeight = FontWeight(600),
                    color = Color(0xFF474747)
                )
            )
        }

        Spacer(Modifier.height(18.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth(1f)
                .padding(top=10.dp, start = 5.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(Modifier.height(16.dp)) {
                Image(
                    painter = painterResource(id = R.drawable.f_cm_location),
                    contentDescription = "place icon",
                    modifier = Modifier
                        .width(10.dp)
                        .height(10.dp)
                )
                Spacer(Modifier.width(4.dp))
                Text(
                    text = "TB_Location",
                    style = TextStyle(
                        fontSize = 8.sp,
                        fontFamily = FontFamily(Font(R.font.inter)),
                        fontWeight = FontWeight(600),
                        color = Color(0xFF727272),
                    )
                )
            }

        }


    }}













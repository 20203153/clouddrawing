package kr.ac.kookmin.clouddrawing

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
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
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.modifier.modifierLocalMapOf
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
        Spacer(Modifier.defaultMinSize(minHeight = 20.dp))
        ClContentBox()

    }
}
@Preview
@Composable
fun ClContentBox() {
    Column(
        modifier = Modifier
            .padding(start = 31.dp, end = 31.dp, bottom = 10.dp)
            .shadow(
                elevation = 20.dp,
                spotColor = Color(0x0D000000),
                ambientColor = Color(0x0D000000)
            )
            .border(
                width = 1.dp, color = Color(0xFFF4F4F4),
                shape = RoundedCornerShape(size = 20.dp)
            )
            .width(328.dp)
            .height(570.dp)
            .background(color = Color(0xFFFFFFFF))
    ) {
        val locations = listOf(
            "전체", "서울", "경기", "인천", "강원", "충북",
            "충남", "대전", "세종", "경북", "경남", "대구",
            "울산", "부산", "전북", "전남", "광주", "제주"
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 17.dp, top = 17.dp)
        ) {
            Column(
                modifier = Modifier
                    .weight(0.15f)
                    .padding(end = 10.dp)

            ) {
                locations.forEach { location ->
                    Text(
                        text = location,
                        modifier = Modifier
                            .padding(top=10.dp),
                        style = TextStyle(
                            fontSize = 13.sp,
                            fontFamily = FontFamily(Font(R.font.inter)),
                            fontWeight = FontWeight(600),
                            color = Color(0xFF848484),
                            textAlign = TextAlign.Center,
                        )
                    )
                }
            }
            // 세로선 추가
            Divider(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(top=5.dp, bottom = 15.dp)
                    .width(1.dp),
                color = Color(0xFFC9C9C9),
                thickness = 1.dp
            )

            //card 자리

            // 2열로 나열된 카드
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 20.dp)
            ) {
                // 첫 번째 카드
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    CLContentCard()
                    Spacer(
                        modifier = Modifier
                            .width(15.dp)
                    )
                    CLContentCard()
                }

                // 두 번째 카드
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 15.dp)
                ) {
                    CLContentCard()
                    Spacer(
                        modifier = Modifier
                            .width(15.dp)
                    )
                    CLContentCard()
                }
            }            }

    }
}








@Preview
@Composable
fun CLContentCard(){
    Column(
        modifier = Modifier
            .width(100.dp)
            .height(110.dp)
            .padding(top = 10.dp)
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
                .padding(top = 12.dp, start = 5.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(Modifier.height(16.dp)) {
                Image(
                    painter = painterResource(id = R.drawable.f_cm_location),
                    contentDescription = "place icon",
                    modifier = Modifier
                        .width(13.dp)
                        .height(13.dp)
                )
                Spacer(Modifier.width(4.dp))
                Text(
                    text = "TB_Location",
                    style = TextStyle(
                        fontSize = 12.sp,
                        fontFamily = FontFamily(Font(R.font.inter)),
                        fontWeight = FontWeight(600),
                        color = Color(0xFF727272),
                    )
                )
            }

        }


        Spacer(Modifier.height(7.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth(1f),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "TB_Title",
                style = TextStyle(
                    fontSize = 10.sp,
                    fontFamily = FontFamily(Font(R.font.inter)),
                    fontWeight = FontWeight(500),
                    color = Color(0xFF474747)
                )
            )
        }

        Spacer(Modifier.height(18.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth(1f)
                .padding(end = 9.dp, bottom =5.dp),
            horizontalArrangement = Arrangement.End
        ) {
            Text(
                text = "2023.11.09",
                style = TextStyle(
                    fontSize = 7.sp,
                    fontFamily = FontFamily(Font(R.font.inter)),
                    fontWeight = FontWeight(600),
                    color = Color(0xFF9C9C9C),
                    textAlign = TextAlign.Center,
                )
            )
        }



    }}


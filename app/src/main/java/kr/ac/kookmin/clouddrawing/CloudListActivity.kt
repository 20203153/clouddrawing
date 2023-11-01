package kr.ac.kookmin.clouddrawing

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
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


class CloudListActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CLBackground {
            }
        }
    }
}

@Preview
@Composable
fun CLPreview(){
    CLBackground {
    }
}


@Composable
fun CLBackground(content: @Composable () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFFFFF))
    ) {
        CLTopText()
        CLTopBack()
        CLCntBox()
        CLCloudText()
        CLCloudCard()
    }
}

@Composable
fun CLTopText() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(start = 134.dp, end = 135.dp, top = 60.dp, bottom = 760.dp)
            .width(121.dp)
            .height(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "내가 그린 구름",
            style = TextStyle(
                fontSize = 20.sp,
                fontFamily = FontFamily(Font(R.font.inter)),
                fontWeight = FontWeight(600),
                color = Color(0xFF474747),

                )
        )
    }
}

@Composable
fun CLCloudText() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(start = 40.dp, end = 258.dp, top = 220.dp, bottom = 601.dp)
            .width(92.dp)
            .height(23.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "구름 모아보기",
            style = TextStyle(
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(R.font.inter)),
                fontWeight = FontWeight(700),
                color = Color(0xFF474747),
            )
        )
    }
}

@Composable
fun CLTopBack(){
    Image(
        painter = painterResource(id = R.drawable.v_cd_arrow_close),
        contentDescription = "CDArrow Close",
        modifier = Modifier
            .padding(start = 31.dp, end = 327.dp, top = 57.dp, bottom = 760.dp)
            .width(32.dp)
            .height(32.dp)
    )
}

@Composable
fun CLCntBox() {
    Box(
        modifier = Modifier
            .shadow(
                elevation = 20.dp,
                spotColor = Color(0x0D000000),
                ambientColor = Color(0x0D000000)
            )
            .border(
                width = 1.dp,
                color = Color(0xFFF4F4F4),
                shape = RoundedCornerShape(size = 20.dp)
            )
            .padding(start = 31.dp, end = 31.dp, top = 108.dp)
            .width(328.dp)
            .height(97.dp)
            .background(color = Color(0xFFB4CCFF), shape = RoundedCornerShape(size = 20.dp))
    ) {
        CLTodayText(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        )

        ClTodayCnt(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        )

        CLMonthText(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        )

        ClMonthCnt(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        )
        CLUntilText(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        )
        ClUntilCnt(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        )
        CLCntText1(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        )
        CLCntText2(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        )
        CLCntText3(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        )
    }
}

@Composable
fun CLTodayText(modifier: Modifier = Modifier) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(start = 67.dp, end = 170.dp, top = 17.dp, bottom = 59.dp)
            .width(91.dp)
            .height(21.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "오늘은 구름을",
            style = TextStyle(
                fontSize = 15.sp,
                fontFamily = FontFamily(Font(R.font.inter)),
                fontWeight = FontWeight(600),
                color = Color(0xFFFFFFFF),

                textAlign = TextAlign.Center,
            )
        )
    }
}

@Composable
fun CLMonthText(modifier: Modifier = Modifier) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(start = 51.dp, end = 170.dp, top = 41.dp, bottom = 35.dp)
            .width(91.dp)
            .height(21.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "이번 달은 구름을",
            style = TextStyle(
                fontSize = 15.sp,
                fontFamily = FontFamily(Font(R.font.inter)),
                fontWeight = FontWeight(600),
                color = Color(0xFFFFFFFF),

                textAlign = TextAlign.Center,
            )
        )
    }
}

@Composable
fun CLUntilText(modifier: Modifier = Modifier) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(start = 51.dp, end = 170.dp, top = 65.dp, bottom = 11.dp)
            .width(107.dp)
            .height(21.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "지금까지 구름을",
            style = TextStyle(
                fontSize = 15.sp,
                fontFamily = FontFamily(Font(R.font.inter)),
                fontWeight = FontWeight(600),
                color = Color(0xFFFFFFFF),

                textAlign = TextAlign.Center,
            )
        )
    }
}

@Composable
fun ClTodayCnt(modifier: Modifier = Modifier){
    Box(
        modifier = Modifier
            .padding(start = 157.dp, end = 145.dp, top = 14.dp, bottom = 62.dp)
            .width(26.dp)
            .height(21.dp),
        contentAlignment = Alignment.CenterEnd
    ){
        Text(
            text = "1",
            style = TextStyle(
                fontSize = 17.sp,
                fontFamily = FontFamily(Font(R.font.inter)),
                fontWeight = FontWeight(700),
                color = Color(0xFF6891FF),
                textAlign = TextAlign.End,

                )
        )
    }
}

@Composable
fun ClMonthCnt(modifier: Modifier = Modifier){
    Box(
        modifier = Modifier
            .padding(start = 157.dp, end = 145.dp, top = 38.dp, bottom = 38.dp)
            .width(26.dp)
            .height(21.dp),
        contentAlignment = Alignment.CenterEnd
    ){
        Text(
            text = "1",
            style = TextStyle(
                fontSize = 17.sp,
                fontFamily = FontFamily(Font(R.font.inter)),
                fontWeight = FontWeight(700),
                color = Color(0xFF6891FF),
                textAlign = TextAlign.Center,
            )
        )
    }
}

@Composable
fun ClUntilCnt(modifier: Modifier = Modifier){
    Box(
        modifier = Modifier
            .padding(start = 157.dp, end = 145.dp, top = 65.dp, bottom = 14.dp)
            .width(26.dp)
            .height(21.dp),
        contentAlignment = Alignment.CenterEnd
    ){
        Text(
            text = "1",
            style = TextStyle(
                fontSize = 17.sp,
                fontFamily = FontFamily(Font(R.font.inter)),
                fontWeight = FontWeight(700),
                color = Color(0xFF6891FF),
                textAlign = TextAlign.Center,
            )
        )
    }
}

@Composable
fun CLCntText1(modifier: Modifier = Modifier) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(start = 183.dp, end = 55.dp, top = 16.dp, bottom = 62.dp)
            .width(77.dp)
            .height(18.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "개 그렸어요. ",
            style = TextStyle(
                fontSize = 15.sp,
                fontFamily = FontFamily(Font(R.font.inter)),
                fontWeight = FontWeight(600),
                color = Color(0xFFFFFFFF),
                textAlign = TextAlign.Center,
            )
        )
    }
}

@Composable
fun CLCntText2(modifier: Modifier = Modifier) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(start = 183.dp, end = 55.dp, top = 40.dp, bottom = 38.dp)
            .width(77.dp)
            .height(18.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "개 그렸어요. ",
            style = TextStyle(
                fontSize = 15.sp,
                fontFamily = FontFamily(Font(R.font.inter)),
                fontWeight = FontWeight(600),
                color = Color(0xFFFFFFFF),
                textAlign = TextAlign.Center,
            )
        )
    }
}

@Composable
fun CLCntText3(modifier: Modifier = Modifier) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(start = 183.dp, end = 55.dp, top = 64.dp, bottom = 14.dp)
            .width(77.dp)
            .height(18.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "개 그렸어요. ",
            style = TextStyle(
                fontSize = 15.sp,
                fontFamily = FontFamily(Font(R.font.inter)),
                fontWeight = FontWeight(600),
                color = Color(0xFFFFFFFF),
                textAlign = TextAlign.Center,
            )
        )
    }
}

@Composable
fun CLCloudCard(){
    Box(
        modifier = Modifier
            .padding(start = 38.dp, end = 252.dp, top = 261.dp, bottom = 483.dp)
            .width(100.dp)
            .height(100.dp)
            .background(color = Color(0xFFFFFFFF), shape = RoundedCornerShape(size = 20.dp))
            .border(width = 1.dp, color = Color(0xFFF4F4F4), shape = RoundedCornerShape(size = 20.dp))
            .shadow(elevation = 20.dp, spotColor = Color(0x0D000000), ambientColor = Color(0x0D000000))
    ){
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(start = 6.dp, end = 13.dp, top = 30.dp, bottom = 40.dp)
                .width(81.dp)
                .height(30.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "TB_Title",
                style = TextStyle(
                    fontSize = 12.sp,
                    fontFamily = FontFamily(Font(R.font.inter)),
                    fontWeight = FontWeight(700),
                    color = Color(0xFF474747),
                ),
                modifier = Modifier.align(Alignment.TopStart)
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(start = 49.dp, end = 13.dp, top = 8.dp, bottom = 84.dp)
                .width(46.dp)
                .height(8.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "TB_date",
                style = TextStyle(
                    fontSize = 7.sp,
                    fontFamily = FontFamily(Font(R.font.inter)),
                    fontWeight = FontWeight(600),
                    color = Color(0xFF9C9C9C),
                ),
                modifier = Modifier.align(Alignment.TopStart)
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(start = 17.dp, end = 13.dp, top = 71.dp, bottom = 19.dp)
                .width(70.dp)
                .height(10.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "TB_place",
                style = TextStyle(
                    fontSize = 8.sp,
                    fontFamily = FontFamily(Font(R.font.inter)),
                    fontWeight = FontWeight(600),
                    color = Color(0xFF727272),
                ),
                modifier = Modifier.align(Alignment.TopStart)
            )
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(start = 5.dp, end = 85.dp, top = 71.dp, bottom = 19.dp)
                .width(10.dp)
                .height(10.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.f_cm_location),
                contentDescription = "image description",

                )
        }
    }
}

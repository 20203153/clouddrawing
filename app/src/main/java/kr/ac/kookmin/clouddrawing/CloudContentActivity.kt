package kr.ac.kookmin.clouddrawing

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box

import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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


class CloudContentActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CCBackground {
            }
        }
    }
}

@Preview
@Composable
fun CCPreview(){
    CCBackground {
    }
}


@Composable
fun CCBackground(content: @Composable () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFFFFF))
    ) {
        CCTopText()
        CCTopBack()
        CCContentBox()
        
    }
}

@Composable
fun CCTopText() {
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
            text = "그린 구름 보기",
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
fun CCTopBack(){
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
fun CCContentBox(){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 31.dp, end = 31.dp, top = 130.dp, bottom = 76.dp)
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
            .background(color = Color(0xFFFFFFFF), shape = RoundedCornerShape(size = 20.dp))
    ) {
        CCContentTitle(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        )
        CCPlaceIcon(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        )
        CCContentPlace(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        )
        CCFriendIcon(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        )
        CCContentFriend(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        )
        CCContentDate(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        )
        CCContentText(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        )
        CCContentImage(

        )
        // 수정된 부분 끝
    }
}

@Composable
fun CCContentTitle(modifier: Modifier = Modifier){
    Box(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(start = 120.dp, end = 145.dp, top = 23.dp, bottom = 596.dp)
            .width(63.dp)
            .height(19.dp)
    ){
        Text(
            text = "TB_Title",
            style = TextStyle(
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(R.font.inter)),
                fontWeight = FontWeight(600),
                color = Color(0xFF474747)
            )
        )
    }
}

@Composable
fun CCPlaceIcon(modifier: Modifier = Modifier){
    Image(
        painter = painterResource(id = R.drawable.f_cm_location),
        contentDescription = "place icon",
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(start = 23.dp, end = 289.dp, top = 68.dp, bottom = 554.dp)
            .width(12.dp)
            .height(12.dp)
    )
}

@Composable
fun CCContentPlace(modifier: Modifier = Modifier){
    Box(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(start = 46.dp, end = 215.dp, top = 71.dp, bottom = 555.dp)
            .width(58.dp)
            .height(12.dp)
    ){
        Text(
            text = "TB_Location",
            style = TextStyle(
                fontSize = 12.sp,
                fontFamily = FontFamily(Font(R.font.inter)),
                fontWeight = FontWeight(600),
                color = Color(0xFF727272),

                textAlign = TextAlign.Center,
            )
        )
    }
}

@Composable
fun CCFriendIcon(modifier: Modifier = Modifier){
    Image(
        painter = painterResource(id = R.drawable.v_cm_who),
        contentDescription = "friend icon",
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(start = 23.dp, end = 288.dp, top = 95.dp, bottom = 538.dp)
            .width(15.dp)
            .height(11.dp)
    )
}

@Composable
fun CCContentFriend(modifier: Modifier = Modifier){
    Box(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(start = 46.dp, end = 215.dp, top = 93.dp, bottom = 535.dp)
            .width(42.dp)
            .height(12.dp)
    ){
        Text(
            text = "TB_who",
            style = TextStyle(
                fontSize = 12.sp,
                fontFamily = FontFamily(Font(R.font.inter)),
                fontWeight = FontWeight(600),
                color = Color(0xFF727272),
                textAlign = TextAlign.Center,
            )
        )
    }
}

@Composable
fun CCContentDate(modifier: Modifier = Modifier){
    Box(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(start = 245.dp, end = 25.dp, top = 71.dp, bottom = 555.dp)
            .width(58.dp)
            .height(12.dp)
    ){
        Text(
            text = "2023.10.29",
            style = TextStyle(
                fontSize = 10.sp,
                fontFamily = FontFamily(Font(R.font.inter)),
                fontWeight = FontWeight(600),
                color = Color(0xFF9C9C9C),
                textAlign = TextAlign.Center,
            )
        )
    }
}


@Composable
fun CCContentText(modifier: Modifier = Modifier){
    Box(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(start = 25.dp, end = 25.dp, top = 129.dp, bottom = 329.dp)
            .width(270.dp)
            .height(180.dp)
    ){
        Text(
            text = "TB_Content",
            style = TextStyle(
                fontSize = 12.sp,
                fontFamily = FontFamily(Font(R.font.inter)),
                fontWeight = FontWeight(500),
                color = Color(0xFF000000),

                )
        )
    }
}

@Composable
fun CCContentImage(modifier: Modifier = Modifier) {
    Box(
        modifier = Modifier
            .padding(start = 33.dp, end = 33.dp, top = 325.dp, bottom = 49.dp)
            .width(252.dp)
            .height(250.dp)
            .background(color = Color(0xFFD9D9D9))
    ) {
    }
}










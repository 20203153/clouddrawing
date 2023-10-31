package kr.ac.kookmin.clouddrawing

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember


class CloudDrawingActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CDBackground {
                CDTopText()
                CDTopBack()
                CDTopSave()
                CDLine()
                CDMiddleText()
                CDMiddleImage()
                CDMiddleSearch()
                CDBottom()
                CDTextField()
            }
        }
    }
}
@Composable
fun CDBackground(content: @Composable () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFFFFF))
    ) {
        CDTopText()
        CDTopBack()
        CDTopSave()
        CDLine()
        CDMiddleText()
        CDMiddleImage()
        CDMiddleSearch()
        CDBottom()
        CDTextField()
    }
}
@Composable
fun CDTopText() {
    Box(
        modifier = Modifier
            .padding(start = 137.dp, end = 137.dp, top = 63.dp, bottom = 752.dp)
            .width(117.dp)
            .height(32.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "구름 그리기",
            style = TextStyle(
                fontSize = 24.sp,
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.W600,
                color = Color(0xFF454545),
            )
        )
    }
}
@Composable
fun CDTopBack(){
    Image(
        painter = painterResource(id = R.drawable.v_cd_arrow_close),
        contentDescription = "CDArrow Close",
        modifier = Modifier
            .padding(start = 15.dp, end = 162.dp, top = 65.dp, bottom = 752.dp)
            .width(15.dp)
            .height(29.dp)
    )
}
@Composable
fun CDTopSave(){
    Box(
        modifier = Modifier
            .padding(start = 344.dp, end = 14.dp, top = 65.dp, bottom = 758.dp)
            .width(117.dp)
            .height(32.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "저장",
            style = TextStyle(
                fontSize = 17.sp,
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.W600,
                color = Color(0xFF6891FF),
            )
        )
    }
}
@Composable
fun CDLine(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .padding(top = 104.dp, bottom = 740.dp)
            .size(391.02045.dp, 3.dp)
            .background(color = Color(0xFFF5F5F5), shape = RoundedCornerShape(size = 25.dp))
    )
    Box(
        modifier = modifier
            .padding(top = 422.dp, bottom = 371.dp)
            .size(391.02045.dp, 15.dp)
            .background(color = Color(0xFFF5F5F5), shape = RoundedCornerShape(size = 25.dp))
    )
    Box(
        modifier = modifier
            .padding(top = 591.dp, bottom = 238.dp)
            .size(391.02045.dp, 15.dp)
            .background(color = Color(0xFFF5F5F5), shape = RoundedCornerShape(size = 25.dp))
    )
    Box(
        modifier = modifier
            .padding(top = 473.dp, bottom = 370.dp)
            .size(391.02045.dp, 2.dp)
            .background(color = Color(0xFFF5F5F5), shape = RoundedCornerShape(size = 25.dp))
    )
    Box(
        modifier = modifier
            .padding(top = 513.dp, bottom = 330.dp)
            .size(391.02045.dp, 2.dp)
            .background(color = Color(0xFFF5F5F5), shape = RoundedCornerShape(size = 25.dp))
    )
    Box(
        modifier = modifier
            .padding(top = 553.dp, bottom = 290.dp)
            .size(391.02045.dp, 2.dp)
            .background(color = Color(0xFFF5F5F5), shape = RoundedCornerShape(size = 25.dp))
    )

}
@Composable
fun CDMiddleText(){
    Box(
        modifier = Modifier
            .padding(start = 50.dp, end = 311.dp, top = 446.dp, bottom = 380.dp)
            .width(28.dp)
            .height(20.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "제목",
            style = TextStyle(
                fontSize = 15.sp,
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.W600,
                color = Color(0xFF686868),
            )
        )
    }
    Box(
        modifier = Modifier
            .padding(start = 50.dp, end = 137.dp, top = 485.dp, bottom = 341.dp)
            .width(59.dp)
            .height(20.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "방문 날짜",
            style = TextStyle(
                fontSize = 15.sp,
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.W600,
                color = Color(0xFF686868),
            )
        )
    }
    Box(
        modifier = Modifier
            .padding(start = 48.dp, end = 313.dp, top = 524.dp, bottom = 302.dp)
            .width(36.dp)
            .height(20.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "장소",
            style = TextStyle(
                fontSize = 15.sp,
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.W600,
                color = Color(0xFF686868),
            )
        )
    }
    Box(
        modifier = Modifier
            .padding(start = 50.dp, end = 137.dp, top = 563.dp, bottom = 263.dp)
            .width(42.dp)
            .height(20.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "누구랑",
            style = TextStyle(
                fontSize = 15.sp,
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.W600,
                color = Color(0xFF686868),
            )
        )
    }
}
@Composable
fun CDMiddleImage(){
    Image(
        painter = painterResource(id = R.drawable.v_cd_title),
        contentDescription = "CDTittle",
        modifier = Modifier
            .padding(start = 13.dp, end = 362.dp, top = 451.dp, bottom = 375.dp)
            .width(15.dp)
            .height(15.dp)
    )
    Image(
        painter = painterResource(id = R.drawable.g_cd_calendar),
        contentDescription = "CDCalendar",
        modifier = Modifier
            .padding(start = 13.dp, end = 362.dp, top = 488.dp, bottom = 338.dp)
            .width(15.dp)
            .height(15.dp)
    )
    Image(
        painter = painterResource(id = R.drawable.g_cd_location),
        contentDescription = "CDLocation",
        modifier = Modifier
            .padding(start = 13.dp, end = 357.dp, top = 528.dp, bottom = 298.dp)
            .width(15.dp)
            .height(15.dp)
    )
    Image(
        painter = painterResource(id = R.drawable.v_cd_who),
        contentDescription = "CDWho",
        modifier = Modifier
            .padding(start = 13.dp, end = 354.dp, top = 568.dp, bottom = 258.dp)
            .width(15.dp)
            .height(15.dp)
    )
}
@Composable
fun CDMiddleSearch(){
    Image(
        painter = painterResource(id = R.drawable.v_cd_location_search),
        contentDescription = "CDSearch",
        modifier = Modifier
            .padding(start = 362.dp, end = 13.dp, top = 528.dp, bottom = 298.dp)
            .width(15.dp)
            .height(15.dp)
    )
}
@Composable
fun CDBottom(){
    Box(
        modifier = Modifier
            .padding(start = 15.dp, end = 349.dp, top = 620.dp, bottom = 175.dp)
            .width(30.dp)
            .height(22.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "사진",
            style = TextStyle(
                fontSize = 15.sp,
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.W600,
                color = Color(0xFF454545),
            )
        )
    }
    Box( //사진 상자
        modifier = Modifier
            .padding(start = 15.dp, end = 15.dp, top = 665.dp, bottom = 15.dp)
            .size(365.dp, 200.dp)
            .background(color = Color(0xFFF5F5F5))
    )
}

@Composable
fun CDTextField(){
    var title by remember { mutableStateOf("") }
    TextField(
        modifier = Modifier
            .padding(start = 115.dp, end = 40.dp, top = 455.dp, bottom = 407.dp),
        value = title,
        onValueChange = { textValue -> title = textValue},
        singleLine = true
    )
    var date by remember{ mutableStateOf("") }
    TextField(
        modifier = Modifier
            .padding(start = 115.dp, end = 40.dp, top = 495.dp, bottom = 367.dp),
        value = date,
        onValueChange = { textValue -> date = textValue },
        singleLine = true
    )
    var locations by remember { mutableStateOf("") }
    TextField(
        modifier = Modifier
            .padding(start = 115.dp, end = 40.dp, top = 535.dp, bottom = 327.dp),
        value = locations,
        onValueChange = { textValue -> locations = textValue },
        singleLine = true
    )
    var friends by remember {mutableStateOf("")}
    TextField(
        modifier = Modifier
            .padding(start = 115.dp, end = 40.dp, top = 575.dp, bottom = 287.dp),
        value = friends,
        onValueChange = { textValue -> friends = textValue },
        singleLine = true
    )
    var maincontent by remember { mutableStateOf("")}
    TextField(
        modifier = Modifier
            .padding(start = 5.dp, end = 5.dp, top = 110.dp, bottom = 300.dp)
            .width(380.dp)
            .height(300.dp)
            .verticalScroll(rememberScrollState()), //스크롤 옵션
        value = maincontent,
        onValueChange = { textValue -> maincontent = textValue },
        placeholder = { Text(text = "어떤 추억이 있었냐요?\n나중에 떠올리고 싶은 추억을 그려보세요 :)") },
    )
}

@Preview
@Composable
fun CDPreview(){
    CDBackground {
    }
}

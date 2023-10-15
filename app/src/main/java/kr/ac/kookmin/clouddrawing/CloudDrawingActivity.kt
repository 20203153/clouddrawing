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
import androidx.compose.foundation.shape.RoundedCornerShape
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
            .padding(top = 289.dp, bottom = 504.dp)
            .size(391.02045.dp, 15.dp)
            .background(color = Color(0xFFF5F5F5), shape = RoundedCornerShape(size = 25.dp))
    )
    Box(
        modifier = modifier
            .padding(top = 458.dp, bottom = 371.dp)
            .size(391.02045.dp, 15.dp)
            .background(color = Color(0xFFF5F5F5), shape = RoundedCornerShape(size = 25.dp))
    )
    Box(
        modifier = modifier
            .padding(top = 340.dp, bottom = 503.dp)
            .size(391.02045.dp, 2.dp)
            .background(color = Color(0xFFF5F5F5), shape = RoundedCornerShape(size = 25.dp))
    )
    Box(
        modifier = modifier
            .padding(top = 380.dp, bottom = 463.dp)
            .size(391.02045.dp, 2.dp)
            .background(color = Color(0xFFF5F5F5), shape = RoundedCornerShape(size = 25.dp))
    )
    Box(
        modifier = modifier
            .padding(top = 420.dp, bottom = 423.dp)
            .size(391.02045.dp, 2.dp)
            .background(color = Color(0xFFF5F5F5), shape = RoundedCornerShape(size = 25.dp))
    )

}
@Composable
fun CDMiddleText(){
    Box(
        modifier = Modifier
            .padding(start = 50.dp, end = 311.dp, top = 315.dp, bottom = 511.dp)
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
            .padding(start = 50.dp, end = 137.dp, top = 352.dp, bottom = 474.dp)
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
            .padding(start = 48.dp, end = 313.dp, top = 391.dp, bottom = 435.dp)
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
            .padding(start = 50.dp, end = 137.dp, top = 432.dp, bottom = 394.dp)
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
            .padding(start = 13.dp, end = 362.dp, top = 318.dp, bottom = 508.dp)
            .width(15.dp)
            .height(15.dp)
    )
    Image(
        painter = painterResource(id = R.drawable.g_cd_calendar),
        contentDescription = "CDCalendar",
        modifier = Modifier
            .padding(start = 13.dp, end = 362.dp, top = 355.dp, bottom = 471.dp)
            .width(15.dp)
            .height(15.dp)
    )
    Image(
        painter = painterResource(id = R.drawable.g_cd_location),
        contentDescription = "CDLocation",
        modifier = Modifier
            .padding(start = 13.dp, end = 357.dp, top = 395.dp, bottom = 431.dp)
            .width(15.dp)
            .height(15.dp)
    )
    Image(
        painter = painterResource(id = R.drawable.v_cd_who),
        contentDescription = "CDWho",
        modifier = Modifier
            .padding(start = 13.dp, end = 354.dp, top = 435.dp, bottom = 391.dp)
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
            .padding(start = 362.dp, end = 13.dp, top = 395.dp, bottom = 431.dp)
            .width(15.dp)
            .height(15.dp)
    )
}
@Composable
fun CDBottom(){
    Box(
        modifier = Modifier
            .padding(start = 13.dp, end = 349.dp, top = 490.dp, bottom = 336.dp)
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
}
@Preview
@Composable
fun CDPreview(){
    CDBackground {
    }
}

package kr.ac.kookmin.clouddrawing

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.Date

class CloudListSelectPlaceListActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val lat = intent.getDoubleExtra("lat", 0.0)
        val lng = intent.getDoubleExtra("lng", 0.0)
        val locations : String = intent.getStringExtra("address") ?: ""

        setContent {
            CLSPActivity(locations = locations) {

            }
        }
    }
}

@SuppressLint("MutableCollectionMutableState")
@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun CLSPActivity(
    title: MutableState<String> = mutableStateOf(""),
    date: DatePickerState = rememberDatePickerState(Date().time),
    locations: String = "일단은 아무말",
    friends: MutableState<String> = mutableStateOf(""),
    mainContent: MutableState<String> = mutableStateOf(""),
    loading: MutableState<Boolean> = mutableStateOf(false),
    scrollState: ScrollState = rememberScrollState(),

    onClickBack: () -> Unit = {},
    onClickSave: (it: List<Uri>) -> Unit = {},

    content: @Composable () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFFFFF))
    ) {
        CLSPLTopText()
        CLSPLTopBack()
        CLSPLCloudText()
        CLSPLCcontent()
    }
}

@Composable
fun CLSPLTopText() {
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
fun CLSPLCloudText() {
    var location by remember { mutableStateOf("일단은 아무말") }
    Box(
        modifier = Modifier
            .padding(start = 38.dp, end = 193.dp, top = 139.dp, bottom = 682.dp),
    ) {
        Text(
            text = "에서 그린 구름 모아보기",
            style = TextStyle(
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(R.font.inter)),
                fontWeight = FontWeight(700),
                color = Color(0xFF474747),
            )
        )
    }
    Box(
        modifier = Modifier
            .padding(start = 40.dp, top = 110.dp, bottom = 688.dp),
    ) {
        Text(
            text = location,
            style = TextStyle(
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(R.font.inter)),
                fontWeight = FontWeight(700),
                color = Color(0xFF4376FF),
            )
        )
    }
}

@Composable
fun CLSPLTopBack(){
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
fun CLSPLCcontent(){
    Box(
        modifier = Modifier
            .border(BorderStroke(1.dp, Color(0xFFD9D9D9)))
            .size(500.dp, 900.dp)
            .padding(start = 31.dp, end = 31.dp, top = 177.dp, bottom = 29.dp)
            .background(color = Color(0xFFFFFFFF), shape = RoundedCornerShape(size = 30.dp))
            .shadow(
                elevation = 7.dp,
                spotColor = Color(0xFFD9D9D9),
                ambientColor = Color(0xFFD9D9D9)
            )
            .verticalScroll(rememberScrollState()), //스크롤 옵션
    ) {

    }
}

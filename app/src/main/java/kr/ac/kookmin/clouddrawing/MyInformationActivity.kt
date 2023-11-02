package kr.ac.kookmin.clouddrawing

import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kr.ac.kookmin.clouddrawing.components.LeftCloseBtn
import kotlin.let as let

class MyInformationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_my_information)
        setContent{
            MIBackground(onBackClick = { finish() }) {

            }
        }
    }
}

@Preview
@Composable
fun MIPreview(){
    MIBackground({}) {
    }
}


@Composable
fun MIBackground(onBackClick: () -> Unit = {}, content: @Composable () -> Unit = {}) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFFFFF))
    ) {
        MITopBack(onBackClick)
        MITopText()
        MISideText()
        MIProfile()

    }
}
@Composable
fun MITopBack(onClick: () -> Unit = {}){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 31.dp, end = 327.dp, top = 57.dp, bottom = 760.dp)
    ) {
        LeftCloseBtn(onClick = onClick)
    }
}


@Composable
fun MITopText() {
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
            text = "내 정보",
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
fun MISideText() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(start = 320.dp, end = 10.dp, top = 60.dp, bottom = 760.dp)
            .width(121.dp)
            .height(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "수정",
            style = TextStyle(
                fontSize = 17.sp,
                fontFamily = FontFamily(Font(R.font.inter)),
                fontWeight = FontWeight(600),
                color = Color(0xFF6891FF),

                )
        )
    }
}

@Composable
fun MIProfile() {
    val context = LocalContext.current
    val imageUri = remember { mutableStateOf<Uri?>(null) }
    val bitmap = imageUri.value?.let { uri ->
        remember(uri) {
            BitmapFactory.decodeStream(context.contentResolver.openInputStream(uri))
        }
    }

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        imageUri.value = uri
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (bitmap != null) {
            Image(
                bitmap = bitmap.asImageBitmap(),
                contentDescription = "MIProfilePlus",
                modifier = Modifier
                    .padding(top = 100.dp, bottom = 600.dp, start = 20.dp)
                    .width(100.dp)
                    .height(100.dp)
                    .clickable {
                        launcher.launch("image/*")

                    }
            )
            MINameText()
            MIEmailText()
            MINameData()
            MIEmailData()
        } else {
            Image(
                painter = painterResource(id = R.drawable.profileplus),
                contentDescription = "MIProfileDefault",
                modifier = Modifier
                    .padding(top = 5.dp, bottom = 500.dp, start = 20.dp)
                    .width(100.dp)
                    .height(100.dp)
                    .clickable {
                        launcher.launch("image/*")
                    }
            )
            MINameText()
            MIEmailText()
            MINameData()
            MIEmailData()
        }
    }
}



@Composable
fun MINameText() {
    Text(
        text = "이름",
        modifier = Modifier.padding(top = 10.dp, bottom = 300.dp, start = 10.dp, end = 300.dp),
        style = TextStyle(
            fontSize = 15.sp,
            fontFamily = FontFamily(Font(R.font.inter)),
            fontWeight = FontWeight(600),
            color = Color(0xFF2D2D2D)
        )
    )
}

@Composable
fun MIEmailText() {
    Text(
        text = "이메일",
        modifier = Modifier.padding(top = 10.dp, bottom = 200.dp, start = 10.dp, end = 285.dp),
        style = TextStyle(
            fontSize = 15.sp,
            fontFamily = FontFamily(Font(R.font.inter)),
            fontWeight = FontWeight(600),
            color = Color(0xFF2D2D2D)
        )
    )
}

@Composable
fun MINameData() {
    Text(
        text = "User 이름",
        modifier = Modifier.padding(top = 10.dp, bottom = 300.dp, start = 293.dp, end = 30.dp),
        style = TextStyle(
            fontSize = 15.sp,
            fontFamily = FontFamily(Font(R.font.inter)),
            fontWeight = FontWeight(600),
            color = Color(0xFF2D2D2D)
        )
    )
}

@Composable
fun MIEmailData() {
    Text(
        text = "User 이메일",
        modifier = Modifier.padding(top = 10.dp, bottom = 200.dp, start = 280.dp, end = 28.dp),
        style = TextStyle(
            fontSize = 15.sp,
            fontFamily = FontFamily(Font(R.font.inter)),
            fontWeight = FontWeight(600),
            color = Color(0xFF2D2D2D)
        )
    )
}


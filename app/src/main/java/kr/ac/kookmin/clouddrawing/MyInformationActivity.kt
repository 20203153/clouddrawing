package kr.ac.kookmin.clouddrawing

import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
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

class MyInformationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val name = mutableStateOf("User 이름")
        val email = mutableStateOf("User 이메일")

        setContent {
            MyInformation(
                name = name,
                email = email,

                onBackClick = { finish() },
                onDoneClick = { /* TODO: 수정 버튼 클릭했을 때 */ }
            )
        }
    }
}


@Preview
@Composable
fun MyInformation(
    name: MutableState<String> = mutableStateOf("User 이름"),
    email: MutableState<String> = mutableStateOf("User 이메일"),

    onBackClick: () -> Unit = {},
    onDoneClick: (
        it: MutableState<Uri?>
    ) -> Unit = {},
    content: @Composable () -> Unit = {}
) {
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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFFFFF)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(1f)
                .padding(start=13.dp, top=71.dp, end=14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            LeftCloseBtn(onClick = onBackClick)
            Text(
                text = "내 정보",
                style = TextStyle(
                    fontSize = 20.sp,
                    fontFamily = FontFamily(Font(R.font.inter)),
                    fontWeight = FontWeight(600),
                    color = Color(0xFF474747),

                    )
            )
            IconButton(
                onClick = { onDoneClick(imageUri) }
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
        Spacer(Modifier.height(36.dp))
        if (bitmap != null) {
            Image(
                bitmap = bitmap.asImageBitmap(),
                contentDescription = "MIProfilePlus",
                modifier = Modifier
                    .width(100.dp)
                    .height(100.dp)
                    .clickable {
                        launcher.launch("image/*")

                    }
            )
        } else {
            Image(
                painter = painterResource(id = R.drawable.profileplus),
                contentDescription = "MIProfileDefault",
                modifier = Modifier
                    .width(100.dp)
                    .height(100.dp)
                    .clickable {
                        launcher.launch("image/*")
                    }
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(1f)
                .padding(start=39.dp, end=36.dp, top=41.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "이름",
                modifier = Modifier,
                style = TextStyle(
                    fontSize = 15.sp,
                    fontFamily = FontFamily(Font(R.font.inter)),
                    fontWeight = FontWeight(600),
                    color = Color(0xFF2D2D2D)
                )
            )
            Text(
                text = name.value,
                modifier = Modifier,
                style = TextStyle(
                    fontSize = 15.sp,
                    fontFamily = FontFamily(Font(R.font.inter)),
                    fontWeight = FontWeight(600),
                    color = Color(0xFF2D2D2D)
                )
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(1f)
                .padding(start=39.dp, end=36.dp, top=24.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "이메일",
                modifier = Modifier,
                style = TextStyle(
                    fontSize = 15.sp,
                    fontFamily = FontFamily(Font(R.font.inter)),
                    fontWeight = FontWeight(600),
                    color = Color(0xFF2D2D2D)
                )
            )

            Text(
                text = email.value,
                modifier = Modifier,
                style = TextStyle(
                    fontSize = 15.sp,
                    fontFamily = FontFamily(Font(R.font.inter)),
                    fontWeight = FontWeight(600),
                    color = Color(0xFF2D2D2D)
                )
            )
        }
    }
}

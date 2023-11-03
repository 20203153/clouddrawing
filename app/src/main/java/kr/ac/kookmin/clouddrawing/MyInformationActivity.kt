package kr.ac.kookmin.clouddrawing

import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Log
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import coil.compose.AsyncImage
import com.google.firebase.Firebase
import com.google.firebase.storage.storage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kr.ac.kookmin.clouddrawing.components.LeftCloseBtn
import kr.ac.kookmin.clouddrawing.dto.User

class MyInformationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val name = mutableStateOf("")
        val email = mutableStateOf("")
        val uri = mutableStateOf<Uri?>(null)

        var user: User? = null

        setContent {
            MyInformation(
                name = name,
                email = email,
                profileUri = uri,

                onBackClick = { finish() },
                onDoneClick = {
                    if(it.value != null) {
                        CoroutineScope(Dispatchers.Main).launch {
                            val storageRef = Firebase.storage.reference
                            val profileRef = storageRef.child("profile/${user!!.uid}")

                            profileRef.putFile(it.value!!).await()
                            user!!.photoURL = profileRef.downloadUrl.await().toString()
                            Log.d("MyInformationActivity", user?.photoURL ?: "NULL")

                            user!!.updateProfile(user!!)

                            finish()
                        }
                    } else finish()
                }
            )
        }

        CoroutineScope(Dispatchers.Main).launch {
            user = User.getCurrentUser()
            if(user != null) {
                name.value = user!!.name
                email.value = user!!.email
                uri.value = Uri.parse(user!!.photoURL)
                Log.d("MyInformationActivity", uri.value.toString())
            }
        }
    }
}


@Preview
@Composable
fun MyInformation(
    name: MutableState<String> = mutableStateOf("User 이름"),
    email: MutableState<String> = mutableStateOf("User 이메일"),
    profileUri: MutableState<Uri?> = mutableStateOf(null),

    onBackClick: () -> Unit = {},
    onDoneClick: (
        it: MutableState<Uri?>
    ) -> Unit = {},
    content: @Composable () -> Unit = {}
) {
    val context = LocalContext.current
    val isWebContent = remember { mutableStateOf(false)}

    val bitmap = profileUri.value?.let { uri ->
        remember(uri) {
            if(uri.scheme == "http" || uri.scheme == "https") {
                isWebContent.value = true
                null
            } else BitmapFactory.decodeStream(context.contentResolver.openInputStream(uri))
        }
    }

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        profileUri.value = uri
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFFFFF)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(1f)
                .padding(start=22.dp, top=10.dp, end=15.dp),
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
                onClick = { onDoneClick(profileUri) }
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
                    .clip(CircleShape)
                    .clickable {
                        launcher.launch("image/*")

                    }
            )
        } else if(isWebContent.value) {
            AsyncImage(
                model = profileUri.value.toString(),
                contentDescription = "MIProfilePlus",
                modifier = Modifier
                    .width(100.dp)
                    .height(100.dp)
                    .clip(CircleShape)
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


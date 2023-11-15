package kr.ac.kookmin.clouddrawing

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import io.woong.compose.grid.SimpleGridCells
import io.woong.compose.grid.VerticalGrid
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kr.ac.kookmin.clouddrawing.CloudDrawingActivity.Companion.timeFormat
import kr.ac.kookmin.clouddrawing.components.LeftCloseBtn
import kr.ac.kookmin.clouddrawing.dto.Post
import java.util.Date


class CloudContentActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val postId = intent.getStringExtra("postId")

        val post = mutableStateOf<Post?>(null)

        setContent {
            val verticalScroll = rememberScrollState()

            CloudContent(
                verticalScroll = verticalScroll,
                leftCloseBtn = { finish() },
                post = post
            )
        }

        CoroutineScope(Dispatchers.Main).launch {
            if (postId == null) finish()

            post.value = Post.getPostById(postId!!)
            if (post.value == null) finish()
        }
    }
}

@Preview
@Composable
fun CloudContent(
    leftCloseBtn: () -> Unit = {},
    verticalScroll: ScrollState = rememberScrollState(),
    post:MutableState<Post?> = mutableStateOf(null)
) {
    Column(
        modifier = Modifier
            .fillMaxSize(1f)
            .background(color = Color(0xFFFFFFFF)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
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
                text = "그린 구름 보기",
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
        CCContentBox(
            verticalScroll,
            post
        )
    }

    AnimatedVisibility(
        visible = post.value == null,
        modifier = Modifier.fillMaxSize(1f),
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize(1f)
                .background(Color.Black.copy(alpha = 0.5f)),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            CircularProgressIndicator(
                modifier = Modifier.width(64.dp),
                color = MaterialTheme.colorScheme.surfaceVariant,
                trackColor = MaterialTheme.colorScheme.secondary
            )
        }
    }
}

@Preview
@Composable
fun CCContentBox(
    verticalScroll: ScrollState = rememberScrollState(),
    post:MutableState<Post?> = mutableStateOf(null)
) {
    Column(
        modifier = Modifier
            .padding(top = 46.dp, start = 31.dp, end = 31.dp, bottom = 30.dp)
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
            .verticalScroll(verticalScroll)
            .fillMaxSize(1f),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(23.dp))
        Text(
            text = post.value?.title ?: "",
            style = TextStyle(
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(R.font.inter)),
                fontWeight = FontWeight(600),
                color = Color(0xFF474747)
            )
        )
        Row(
            modifier = Modifier
                .padding(top = 26.dp, start = 23.dp, end = 25.dp)
                .fillMaxWidth(1f),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(Modifier.height(16.dp)) {
                Image(
                    painter = painterResource(id = R.drawable.f_cm_location),
                    contentDescription = "place icon",
                    modifier = Modifier
                        .width(12.dp)
                        .height(12.dp)
                )
                Spacer(Modifier.width(7.dp))
                Text(
                    text = post.value?.address ?: "",
                    style = TextStyle(
                        fontSize = 12.sp,
                        fontFamily = FontFamily(Font(R.font.inter)),
                        fontWeight = FontWeight(600),
                        color = Color(0xFF727272),
                    )
                )
            }

            if (post.value == null)
                Text(
                    text = timeFormat.format(Date()),
                    style = TextStyle(
                        fontSize = 10.sp,
                        fontFamily = FontFamily(Font(R.font.inter)),
                        fontWeight = FontWeight(600),
                        color = Color(0xFF9C9C9C),
                    )
                )
            else
                Text(
                    text = timeFormat.format(post.value!!.writeTime!!.toDate()),
                    style = TextStyle(
                        fontSize = 10.sp,
                        fontFamily = FontFamily(Font(R.font.inter)),
                        fontWeight = FontWeight(600),
                        color = Color(0xFF9C9C9C),
                    )
                )
        }
        Row(
            modifier = Modifier
                .padding(top = 5.dp, start = 23.dp, end = 25.dp)
                .fillMaxWidth(1f),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.height(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.location_tag),
                    contentDescription = "LocationTag",
                    modifier = Modifier
                        .width(15.dp)
                        .height(15.dp)
                )
                Spacer(Modifier.width(7.dp))
                Text(
                    text = post.value?.addressAlias ?: "",
                    style = TextStyle(
                        fontSize = 12.sp,
                        fontFamily = FontFamily(Font(R.font.inter)),
                        fontWeight = FontWeight(600),
                        color = Color(0xFF727272),
                        textAlign = TextAlign.Center,
                    )
                )
            }
            Spacer(Modifier.width(1.dp))
        }
        Text(
            text = post.value?.comment ?: "",
            style = TextStyle(
                fontSize = 12.sp,
                fontFamily = FontFamily(Font(R.font.inter)),
                fontWeight = FontWeight(500),
                color = Color(0xFF000000),
            ),
            modifier = Modifier
                .align(Alignment.Start)
                .padding(start = 23.dp, end = 23.dp, top = 29.dp, bottom = 29.dp)
        )
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.Bottom,
            modifier = Modifier.fillMaxSize(1f)
        ) {
            VerticalGrid(
                columns = SimpleGridCells.Fixed(3),
                modifier = Modifier
                    .fillMaxSize(1f)
                    .padding(start = 14.dp, end = 14.dp, bottom=20.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                post.value?.image?.forEach { uri ->
                    AsyncImage(
                        model = uri,
                        contentDescription = "",
                        modifier = Modifier.size(120.dp),
                        contentScale = ContentScale.Crop
                    )
                }
            }
        }
    }
}

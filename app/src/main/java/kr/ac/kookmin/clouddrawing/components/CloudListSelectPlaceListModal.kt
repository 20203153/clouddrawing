package kr.ac.kookmin.clouddrawing.components


import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.spring
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
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
import io.woong.compose.grid.SimpleGridCells
import io.woong.compose.grid.VerticalGrid
import kr.ac.kookmin.clouddrawing.R
import kr.ac.kookmin.clouddrawing.dto.Post
import java.text.SimpleDateFormat
import java.util.Date


@SuppressLint("UnrememberedMutableState")
@Preview(widthDp = 390, heightDp = 844)
@Composable
fun CSPreviewBackground() {
    val isDrawerOpen = mutableStateOf(false)

    CSBackground {
        Button(
            onClick = { isDrawerOpen.value = !isDrawerOpen.value }
        ) {
            Text("Open/Close")
        }
        CloudListSelectPlaceListModal(isDrawerOpen = isDrawerOpen)
    }
}

@Composable
fun CSBackground(content: @Composable () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF919191))
    ) {
        content()
    }
}

@SuppressLint("UnrememberedMutableState")
@Composable
fun CloudListSelectPlaceListModal(
    handler: (post: Post?) -> Unit = {},
    isDrawerOpen: MutableState<Boolean> = mutableStateOf(true),
    contentLists: List<Post> = listOf()
) {
    fun onDismissRequest() {
        val it = isDrawerOpen.value
        isDrawerOpen.value = !it
    }

    AnimatedVisibility(
        visible = isDrawerOpen.value,
        enter = expandVertically(spring(1.3f), Alignment.Bottom, initialHeight = { (it * 2.5).toInt() }),
        exit = shrinkVertically(spring(1.3f), Alignment.Bottom, targetHeight = { (it * 3) })
    ) {
        Column(
            modifier = Modifier
                .padding(top = 150.dp)
                .fillMaxWidth(1f)
                .fillMaxHeight(1f)
                .background(
                    Color.White, RoundedCornerShape(
                        topStart = 40.dp,
                        topEnd = 40.dp,
                        bottomStart = 0.dp,
                        bottomEnd = 0.dp
                    )
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(21.dp))
            TLine()
            Spacer(Modifier.height(32.dp))
            VerticalGrid(
                columns = SimpleGridCells.Fixed(3),
                modifier = Modifier
                    .fillMaxSize(1f),
                horizontalArrangement = Arrangement.spacedBy(20.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                contentLists.forEach {
                    CloudItem(mutableStateOf(it), onClick = { post -> handler(post) })
                }
            }
        }
    }
}

@SuppressLint("SimpleDateFormat")
@Composable
@Preview
fun CloudItem(
    post: MutableState<Post?> = mutableStateOf(null),
    onClick: (post: Post?) -> Unit = {}
) {
    val timeFormat = SimpleDateFormat("yyyy-MM-dd")

    Column(
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
            .size(100.dp, 140.dp)
            .background(color = Color(0xFFFFFFFF), shape = RoundedCornerShape(size = 20.dp))
            .clickable { onClick(post.value) },
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(1f)
                .padding(top = 22.dp, start = 17.dp, end = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Image(
                painter = painterResource(id = R.drawable.f_cm_location),
                contentDescription = "image description",
                contentScale = ContentScale.None
            )
            Text(
                text = post.value?.addressAlias ?: "",
                style = TextStyle(
                    fontSize = 12.sp,
                    fontFamily = FontFamily(Font(R.font.inter)),
                    fontWeight = FontWeight(600),
                    color = Color(0xFF727272),
                )
            )
        }
        Spacer(Modifier.height(7.dp))
        Text(
            text = post.value?.title ?: "",
            style = TextStyle(
                fontSize = 10.sp,
                fontFamily = FontFamily(Font(R.font.inter)),
                fontWeight = FontWeight(500),
                color = Color(0xFF474747),
                textAlign = TextAlign.Center,
            )
        )
        Row(
            modifier = Modifier
                .fillMaxWidth(1f)
                .padding(end = 13.dp, top = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
            Text(
                text = timeFormat.format(post.value?.postTime?.toDate() ?: Date()),
                style = TextStyle(
                    fontSize = 7.sp,
                    fontFamily = FontFamily(Font(R.font.inter)),
                    fontWeight = FontWeight(600),
                    color = Color(0xFF9C9C9C),
                    textAlign = TextAlign.Center,
                )
            )
        }
    }
}


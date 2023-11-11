package kr.ac.kookmin.clouddrawing.components

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.spring
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
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
import kr.ac.kookmin.clouddrawing.R
import kr.ac.kookmin.clouddrawing.dto.Post
import java.text.SimpleDateFormat
import java.util.Date

@SuppressLint("UnrememberedMutableState")
@Preview(widthDp = 390, heightDp = 844)
@Composable
fun PreviewBackground() {
    val isDrawerOpen = mutableStateOf(false)

    CMBackground {
        Button(
            onClick = { isDrawerOpen.value = !isDrawerOpen.value }
        ) {
            Text("Open/Close")
        }

        CloudMindModal(isDrawerOpen = isDrawerOpen)
    }
}

@Composable
fun CMBackground(content: @Composable () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF919191))
    ) {
        content()
    }
}

@SuppressLint("SimpleDateFormat")
@Composable
fun CloudMindModal(
    function: () -> Unit = {},
    content: MutableState<Post?> = mutableStateOf(null),
    isDrawerOpen: MutableState<Boolean> = mutableStateOf(true),
    scrollState: ScrollState = rememberScrollState()
) {
    val timeFormat = SimpleDateFormat("yyyy-MM-dd")
    fun onDismissRequest() {
        val it = isDrawerOpen.value
        isDrawerOpen.value = !it
    }

    val conf = LocalConfiguration.current

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
                .verticalScroll(scrollState)
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
            Text(
                text = timeFormat.format(content.value?.postTime?.toDate() ?: Date()),
                style = TextStyle(
                    fontSize = 10.sp,
                    fontFamily = FontFamily(Font(R.font.inter)),
                    fontWeight = FontWeight(600),
                    color = Color(0xFF727272),
                    textAlign = TextAlign.Center
                ),
                modifier = Modifier
                    .width(82.dp)
                    .height(12.dp)
            )
            Spacer(modifier = Modifier.height(3.dp))

            Text(
                text = content.value?.title ?: "",
                style = TextStyle(
                    fontSize = 25.sp,
                    fontFamily = FontFamily(Font(R.font.inter)),
                    fontWeight = FontWeight(600),
                    color = Color(0xFF454545)
                )
            )
            // TB_location 텍스트
            Row(
                Modifier
                    .align(Alignment.Start)
                    .fillMaxWidth(1f)
                    .padding(start = 108.dp, top = 7.dp)
            ) {
                CMLocation(
                    modifier = Modifier
                )
                Spacer(Modifier.width(2.dp))
                Text(
                    text = content.value?.addressAlias ?: "",
                    style = TextStyle(
                        fontSize = 13.sp,
                        fontFamily = FontFamily(Font(R.font.inter)),
                        fontWeight = FontWeight(600),
                        color = Color(0xFF727272),
                        textAlign = TextAlign.Center
                    )
                )
            }
            // TB_Who 텍스트
            Row(
                Modifier
                    .align(Alignment.Start)
                    .fillMaxWidth(1f)
                    .padding(start = 108.dp, top = 13.dp)
            ) {
                CMWho(
                    modifier = Modifier
                )
                Spacer(Modifier.width(3.dp))
                Text(
                    text = content.value?.friends ?: "",
                    style = TextStyle(
                        fontSize = 13.sp,
                        fontFamily = FontFamily(Font(R.font.inter)),
                        fontWeight = FontWeight(600),
                        color = Color(0xFF727272),
                        textAlign = TextAlign.Center
                    ),
                )
            }
            Box(
                Modifier
                    .width(325.dp)
                    .defaultMinSize(minHeight = 100.dp)
                    .align(Alignment.Start)
                    .padding(top = 9.dp, start = 36.dp, end = 36.dp)
            ) {
                Text(
                    text = content.value?.comment ?: "",
                    style = TextStyle(
                        fontSize = 10.sp,
                        fontFamily = FontFamily(Font(R.font.inter)),
                        fontWeight = FontWeight(500),
                        color = Color(0xFF000000),
                        textAlign = TextAlign.Center,
                    )
                )
            }
            Spacer(Modifier.height(36.dp))
            Box(
                modifier = Modifier
                    .width(140.dp)
                    .height(140.dp)
                    .background(color = Color(0xFFFFFFFF))
            ) {
                Row(
                    modifier = Modifier
                        .height(120.dp)
                        .fillMaxWidth(1f)
                        .padding(start = 14.dp, end = 14.dp, bottom = 13.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    content.value?.image?.forEach { uri ->
                        AsyncImage(
                            model = uri,
                            contentDescription = "",
                            modifier = Modifier.size(120.dp)
                                .padding(start = 5.dp),
                            contentScale = ContentScale.Crop
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CMWho(modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(id = R.drawable.v_cm_who),
        contentDescription = "CMWho",
        modifier = modifier
            .size(22.dp, 16.dp)
    )
}

@Composable
fun CMLocation(modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(id = R.drawable.f_cm_location),
        contentDescription = "CMLocation",
        modifier = modifier
            .size(20.dp, 20.dp)
    )
}

@Composable
fun TLine(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .size(111.dp, 5.dp)
            .background(color = Color(0xFFA6A6A6), shape = RoundedCornerShape(size = 25.dp))
    )
}


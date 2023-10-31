package kr.ac.kookmin.clouddrawing.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.spring
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
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
import kr.ac.kookmin.clouddrawing.R

@Preview
@Composable
fun HomeLeftContent(
    function: () -> Unit = {},
    isDrawerOpen: MutableState<Boolean> = mutableStateOf(true)
) {
    fun onDismissRequest() {
        val it = isDrawerOpen.value
        isDrawerOpen.value = !it
    }

    AnimatedVisibility(
        visible = isDrawerOpen.value,
        enter = expandHorizontally(animationSpec = spring(dampingRatio = 2f)),
        exit = shrinkHorizontally(animationSpec = spring(dampingRatio = 2f))
    ) {
        Box(Modifier.background(color = Color(0xFFFFFFFF))) {
            Box(
                Modifier
                    .padding(end = 20.dp, top = 16.dp)
                    .align(Alignment.TopEnd)
            ) {
                LeftCloseBtn { onDismissRequest() }
            }

            Column(
                modifier = Modifier
                    .fillMaxHeight(1f)
                    .width(193.dp)
                    .padding(top = 97.dp)
                    .fillMaxHeight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                Spacer(Modifier.height(53.dp))
                Image(
                    painter = painterResource(id = R.drawable.g_profile),
                    contentDescription = "Profile Image",
                    modifier = Modifier
                        .width(80.dp)
                        .height(80.dp)
                )
                Spacer(Modifier.height(44.dp))
                Text(
                    text = "내 정보",
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontFamily = FontFamily.SansSerif,
                        fontWeight = FontWeight.W600,
                        color = Color(0xFF7D7D7D)
                    ),
                    modifier = Modifier.clickable { /* TODO: Handle 내 정보 click event here */ }
                )
                Spacer(Modifier.height(48.dp))
                MyCloudText()
            }

            Box(
                Modifier
                    .padding(bottom = 11.dp, start = 7.dp)
                    .align(Alignment.BottomStart)
                    .clickable { /* TODO: Handle Logout click event here */ }
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logout),
                    contentDescription = "Logout",
                    modifier = Modifier
                        .width(67.dp)
                        .height(17.dp)
                )
            }
        }
    }
}

@Composable
fun MyCloudText() {
    Text(
        text = "나의 구름",
        style = TextStyle(
            fontSize = 20.sp,
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.W600,
            color = Color(0xFF454545)
        ),
        modifier = Modifier.clickable { /* TODO: Handle 나의 구름 click event here */ }
    )
}
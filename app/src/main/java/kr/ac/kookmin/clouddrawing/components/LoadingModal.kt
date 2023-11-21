package kr.ac.kookmin.clouddrawing.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
import kr.ac.kookmin.clouddrawing.R

@Preview
@Composable
fun LoadingModal(
    isDrawerOpen: MutableState<Boolean> = mutableStateOf(true)
) {
    AnimatedVisibility(isDrawerOpen.value,
        enter = fadeIn(),
        exit = fadeOut(),
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .shadow(
                    elevation = 4.dp,
                    spotColor = Color(0x40000000),
                    ambientColor = Color(0x40000000)
                )
                .fillMaxSize(1f)
                .background(color = Color(0xFFE3ECFF))
        ) {
            Image(
                painter = painterResource(R.drawable.loadinglogo),
                contentDescription = "image description",
                contentScale = ContentScale.None
            )
            Spacer(Modifier.height(11.dp))
            Text(
                text = "나의 일상을 떠올리는 공간 ",
                style = TextStyle(
                    fontSize = 22.sp,
                    fontFamily = FontFamily(Font(R.font.inter)),
                    fontWeight = FontWeight(600),
                    color = Color(0xFF001753),
                )
            )
            Spacer(Modifier.height(16.dp))
            Text(
                text = "나만 갖고 있는 추억의 장소를\n구름과 함께 떠올려보세요!",
                style = TextStyle(
                    fontSize = 13.sp,
                    fontFamily = FontFamily(Font(R.font.inter)),
                    fontWeight = FontWeight(600),
                    color = Color(0xFFA0A0A0),
                    textAlign = TextAlign.Center,
                )
            )
        }
    }
}
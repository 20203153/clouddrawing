package kr.ac.kookmin.clouddrawing.components


import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.spring
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
fun PreviewBackground() {
    CMBackground {
        CMMain()
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

@Composable
fun CMMain(
    function: () -> Unit = {},
    isDrawerOpen: MutableState<Boolean> = mutableStateOf(true)
) {
    fun onDismissRequest() {
        val it = isDrawerOpen.value
        isDrawerOpen.value = !it
    }

    AnimatedVisibility(
        visible = isDrawerOpen.value,
        enter = expandVertically(animationSpec = spring(dampingRatio = 2f)),
        exit = shrinkVertically(animationSpec = spring(dampingRatio = 2f))
    ) {
        Surface(
            shape = RoundedCornerShape(
                topStart = 40.dp,
                topEnd = 40.dp,
                bottomStart = 0.dp,
                bottomEnd = 0.dp
            ),
            color = Color.White,
            modifier = Modifier
                .fillMaxWidth()
                .height(1100.dp)
                .padding(top = 250.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(1f)
                    .padding(top=21.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TLine()
                Spacer(Modifier.height(32.dp))
                Text(
                    text = "TB_date",
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
                    text = "TB_title",
                    style = TextStyle(
                        fontSize = 25.sp,
                        fontFamily = FontFamily(Font(R.font.inter)),
                        fontWeight = FontWeight(600),
                        color = Color(0xFF454545)
                    )
                )

                Column(
                    modifier = Modifier
                        .padding(top=7.dp, start = 108.dp)
                        .fillMaxWidth(1f)
                        .height(150.dp)
                ) {
                    // TB_location 텍스트
                    Row {
                        CMLocation(
                            modifier = Modifier
                        )
                        Spacer(Modifier.width(2.dp))
                        Text(
                            text = "TB_location",
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
                    Row(Modifier.padding(top=13.dp)) {
                        CMWho(
                            modifier = Modifier
                        )
                        Spacer(Modifier.width(3.dp))
                        Text(
                            text = "TB_Who",
                            style = TextStyle(
                                fontSize = 13.sp,
                                fontFamily = FontFamily(Font(R.font.inter)),
                                fontWeight = FontWeight(600),
                                color = Color(0xFF727272),
                                textAlign = TextAlign.Center
                            ),
                        )
                    }
                }

                //예시 컴포넌트들
                // eContext3()
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


// 실제 작동하는 컴포넌트가 아니라, 어느 위치 어느 크기로 작동될지 예시로 보여주는 컴포넌트들

@Composable
fun eContext3() {
    Box(
        modifier = Modifier
            .width(200.dp)
            .height(200.dp)
    ) {
        // 사각형 및 PB_image 텍스트
        Box(
            modifier = Modifier
                .width(140.dp)
                .height(140.dp)
                .background(color = Color(0xFFFFFFFF))
                .offset(y = (-30).dp)  // Y축으로 -50.dp만큼 올립니다.
                .align(Alignment.BottomCenter) // 바닥 중앙에 배치
        ) {
            // 글자의 배경 박스를 노란색으로 설정
            Box(
                modifier = Modifier
                    .width(140.dp)
                    .height(140.dp)
                    .background(color = Color(0xFFD9D9D9))
                    .align(Alignment.Center) // 중앙 정렬
                    .padding(4.dp) // 텍스트와 박스와의 간격
            ) {
                Text(
                    text = "PB_image",
                    style = TextStyle(
                        fontSize = 15.sp,
                        fontFamily = FontFamily(Font(R.font.inter)),
                        fontWeight = FontWeight(500),
                        color = Color(0xFF454545),
                    ), modifier = Modifier
                        .align(Alignment.Center) // 중앙 정렬
                )
            }
        }
    }
}

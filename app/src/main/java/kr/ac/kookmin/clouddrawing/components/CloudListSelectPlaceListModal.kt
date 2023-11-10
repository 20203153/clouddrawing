package kr.ac.kookmin.clouddrawing.components


import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.spring
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


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

@Composable
fun CloudListSelectPlaceListModal(
    function: () -> Unit = {},
    isDrawerOpen: MutableState<Boolean> = mutableStateOf(true),
    scrollState: ScrollState = rememberScrollState()
) {
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
                .background(Color.White, RoundedCornerShape(
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
        }
    }
}


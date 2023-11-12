package kr.ac.kookmin.clouddrawing.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalMinimumInteractiveComponentEnforcement
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kr.ac.kookmin.clouddrawing.R

class UnitPreviw : PreviewParameterProvider<() -> Unit> {
    override val values: Sequence<() -> Unit> = listOf( {} ).asSequence()
}

@Preview(name = "MyCloudBtn", showBackground = true)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyCloudBtn(@PreviewParameter(UnitPreviw::class) myCloud: () -> Unit) {
    CompositionLocalProvider(LocalMinimumInteractiveComponentEnforcement provides false) {
        IconButton(
            onClick = myCloud,
            modifier = Modifier
                .padding(0.dp)
                .width(40.dp)
                .height(38.dp)
                .background(
                    Color(0xFFD2E1FF),
                    RoundedCornerShape(10.dp)
                )
        ) {
            Image(
                modifier = Modifier
                    .height(17.1.dp)
                    .width(10.dp),
                painter = painterResource(id = R.drawable.v_arrow_open),
                contentDescription = "",
                contentScale = ContentScale.Fit
            )
        }
    }
}

@Preview(name = "FriendCloudBtn", showBackground = true)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FriendCloudBtn(@PreviewParameter(UnitPreviw::class) friendCloud: () -> Unit) {
    CompositionLocalProvider(LocalMinimumInteractiveComponentEnforcement provides false) {
        IconButton(
            onClick = friendCloud,
            modifier = Modifier
                .padding(0.dp)
                .width(40.dp)
                .height(40.dp)
                .background(
                    Color(0xFFFFFFFF),
                    RoundedCornerShape(10.dp)
                )
        ) {
            Image(
                painter = painterResource(id = R.drawable.v_cd_who),
                contentDescription = "friend cloud",
                contentScale = ContentScale.None
            )
        }
    }
}

@Preview(name = "AddCloudBtn", showBackground = true)
@Composable
fun AddCloudBtn(@PreviewParameter(UnitPreviw::class) addCloud: () -> Unit) {
    IconButton(
        onClick = addCloud,
        modifier = Modifier
            .padding(0.dp)
            .width(40.dp)
            .height(40.dp)
            .background(
                Color(0xFFFFFFFF),
                RoundedCornerShape(10.dp)
            )
    ) {
        Image(
            painter = painterResource(id = R.drawable.group_24),
            contentDescription = "add cloud btn",
            contentScale = ContentScale.Fit,
            modifier = Modifier.padding(7.dp)
        )
    }
}

@Preview(name = "CurrentLocBtn", showBackground = true)
@Composable
fun CurrentLocBtn(@PreviewParameter(UnitPreviw::class) currentLoc: () -> Unit) {
    IconButton(
        onClick = currentLoc,
        modifier = Modifier
            .padding(0.dp)
            .width(40.dp)
            .height(40.dp)
            .background(
                Color(0xFFFFFFFF),
                RoundedCornerShape(10.dp)
            )
    ) {
        Image(
            painter = painterResource(id = R.drawable.find_location),
            contentDescription = "add cloud btn",
            contentScale = ContentScale.Fit,
            modifier = Modifier.padding(7.dp)
        )
    }
}

@Preview(name = "GoogleSignupBtn", showBackground = true)
@Composable
fun GoogleSignupBtn(@PreviewParameter(UnitPreviw::class) onClickGoogle: () -> Unit,
    innerContent: () -> Unit = {}
) {
    IconButton(
        onClick = onClickGoogle,
        modifier = Modifier
            .width(300.dp)
            .height(40.dp)
            .background(
                color = Color(0xFF6891FF),
                shape = RoundedCornerShape(size = 12.dp)
            )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(R.drawable.flat_color_icons_google),
                contentDescription = "image description",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .width(30.dp)
                    .height(30.dp)
            )
            Spacer(Modifier.width(10.dp))
            Text(
                text = "Google로 시작하기",
                style = TextStyle(
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.inter)),
                    fontWeight = FontWeight(600),
                    color = Color(0xFFFFFFFF),
                )
            )
            innerContent
        }

    }
}

@Preview(name="LogoutBtn", showBackground = true)
@Composable
fun LogoutBtn(@PreviewParameter(UnitPreviw::class) onClickLogout: () -> Unit) {
    IconButton(
        onClickLogout,
        Modifier
            .height(20.dp)
            .width(70.dp)
            .background(Color.Transparent)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier.fillMaxSize(1f)
        ) {
            Image(
                painter = painterResource(id = R.drawable.mi_log_out),
                contentDescription = "Logout",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .height(14.dp)
                    .width(14.dp)
            )
            Text(
                text = "로그아웃",
                style = TextStyle(
                    fontSize = 14.sp,
                    fontFamily = FontFamily(Font(R.font.inter)),
                    fontWeight = FontWeight(600),
                    color = Color(0xFF454545),
                )
            )
        }
    }
}

@Preview(name="LeftCloseBtn", showBackground = true)
@Composable
fun LeftCloseBtn(@PreviewParameter(UnitPreviw::class) onClick: () -> Unit) {
    IconButton(
        onClick = onClick,
        modifier = Modifier.width(15.dp).height(25.dp).background(Color.Transparent)
    ) {
        Image(
            painter = painterResource(id = R.drawable.v_arrow_close),
            contentDescription = "Arrow Close",
            contentScale = ContentScale.Fit
        )
    }
}

@Preview(name="SaveBtn", showBackground = true)
@Composable
fun SaveButton(@PreviewParameter(UnitPreviw::class) onClick: () -> Unit) {
     Text(
        text = "저장",
        style = TextStyle(
            fontSize = 17.sp,
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.W600,
            color = Color(0xFF6891FF),
        ),
         modifier = Modifier.width(32.dp).height(21.dp)
             .clickable { onClick() }
     )
}
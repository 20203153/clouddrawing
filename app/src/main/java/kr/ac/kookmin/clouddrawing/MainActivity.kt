package kr.ac.kookmin.clouddrawing

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalMinimumInteractiveComponentEnforcement
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kr.ac.kookmin.clouddrawing.theme.ApplicationTheme

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            make()
        }
    }

    companion object {
        @OptIn(ExperimentalMaterial3Api::class)
        @Composable
        fun make() {
            return ApplicationTheme {
                Column(
                    Modifier
                        .width(390.dp)
                        .height(844.dp)
                        .background(color = Color(0xFFFFFFFF))
                ) {

                }
                TopAppBar(
                    navigationIcon = {
                        Row {
                            Spacer(Modifier.width(6.dp))
                            CompositionLocalProvider(LocalMinimumInteractiveComponentEnforcement provides false) {
                                IconButton(
                                    onClick = {},
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
                            Spacer(Modifier.width(5.dp))
                        }
                    },
                    title= {
                        Row(
                           modifier = Modifier
                               .shadow(
                                   elevation = 5.dp,
                                   spotColor = Color(0x0D000000),
                                   ambientColor = Color(0x0D000000)
                               )
                               .border(
                                   width = 1.dp,
                                   color = Color(0xFFF6F6F6),
                                   shape = RoundedCornerShape(size = 10.dp)
                               )
                               .width(320.dp)
                               .height(38.dp)
                               .background(
                                   color = Color(0xFFFFFFFF),
                                   shape = RoundedCornerShape(size = 10.dp)
                               )
                        ) {
                            BasicTextField(
                                modifier = Modifier
                                    .height(38.dp)
                                    .width(285.dp),
                                value = "",
                                onValueChange = { },
                                singleLine = true,
                            )
                            Spacer(Modifier.width(5.dp))
                            CompositionLocalProvider(LocalMinimumInteractiveComponentEnforcement provides false) {
                                IconButton(
                                    onClick = {},
                                    modifier = Modifier
                                        .padding(0.dp)
                                        .width(40.dp)
                                        .height(38.dp)
                                        .background(Color.Transparent)
                                ) {
                                    Image(
                                        modifier = Modifier
                                            .height(18.dp)
                                            .width(18.dp),
                                        painter = painterResource(id = R.drawable.v_home_search),
                                        contentDescription = "",
                                        contentScale = ContentScale.Fit
                                    )
                                }
                            }
                            Spacer(Modifier.width(11.28.dp))
                        }
                    },
                    modifier = Modifier
                        .background(color = Color.Transparent)
                        .padding(top = 65.dp, start = 0.dp)
                        .fillMaxWidth()
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainPreview() {
    MainActivity.make()
}
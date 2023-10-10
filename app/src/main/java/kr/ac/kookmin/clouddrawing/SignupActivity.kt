package kr.ac.kookmin.clouddrawing

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

class SignupActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            make()
        }
    }

    companion object {
        @Composable
        fun make(onClickGoogle: () -> Unit = {}) {
            return Box(
                Modifier.background(color=Color(0xFFE3ECFF))
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxSize(1f)
                ) {
                    Image(
                        painter = painterResource(R.drawable.loadinglogo),
                        contentDescription = "image description",
                        contentScale = ContentScale.None,
                    )
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Bottom,
                    modifier = Modifier
                        .fillMaxSize(1f)) {
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
                        Image(
                            painter = painterResource(R.drawable.register_google),
                            contentDescription = "image description",
                            contentScale = ContentScale.None
                        )
                    }
                    Spacer(Modifier.height(85.dp))
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SignupPreview() {
    SignupActivity.make()
}
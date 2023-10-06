package kr.ac.kookmin.clouddrawing

import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kr.ac.kookmin.clouddrawing.theme.ApplicationTheme

class SignupActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            make()
        }
    }

    companion object {
        @Composable
        fun make() {
            return ApplicationTheme {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .width(390.dp)
                        .height(844.dp)
                        .background(color = Color(0xFFE3ECFF))
                ) {
                    Spacer(Modifier.height(294.dp))
                    Image(
                        painter = painterResource(R.drawable.loadinglogo),
                        contentDescription = "image description",
                        contentScale = ContentScale.None,
                    )
                    Spacer(Modifier.height(290.dp))
                    Button(
                        onClick = { /*TODO*/ },
                        modifier = Modifier
                            .width(300.dp)
                            .height(40.dp)
                            .background(color = Color(0xFF6891FF),
                        shape = RoundedCornerShape(size = 12.dp))
                    ) {
                        Image(
                            painter = painterResource(R.drawable.register_google),
                            contentDescription = "image description",
                            contentScale = ContentScale.None,
                        )
                    }
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
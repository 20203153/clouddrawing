import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import kr.ac.kookmin.clouddrawing.R
import kr.ac.kookmin.clouddrawing.theme.ApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ApplicationTheme {
                Column(
                    modifier = Modifier
                        .padding(20.dp)
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    MyCloudBtn()
                    SearchBar()
                    Background()
                    SearchBtn()
                    FriendBtn()
                    AddCloudBtn()
                    Background()
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Background() {
    Box(
        modifier = Modifier
            .width(390.dp)
            .height(844.dp)
            .background(color = Color(0xFFFFFFFF))
    ) {
        MyCloudBtn()
        SearchBar()
        SearchBtn()
        FriendBtn()
        AddCloudBtn()
    }
}

@Composable
fun MyCloudBtn() {
    Image(
        painter = painterResource(id = R.drawable.mycloudbtn),
        contentDescription = "My Cloud Button",
        contentScale = ContentScale.None,
        modifier = Modifier
            .width(40.dp)
            .height(38.dp)
            .padding(start = 12.dp, end = 338.dp, top = 73.dp, bottom = 733.dp)
    )
}

@Composable
fun SearchBar() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(38.dp)
            .padding(horizontal = 13.dp, vertical = 73.dp)
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
            .background(color = Color(0xFFFFFFFF), shape = RoundedCornerShape(size = 10.dp))
    ) {
        SearchBtn()
    }
}

@Composable
fun SearchBtn(){
    Image(
        painter = painterResource(id = R.drawable.v_home_search),
        contentDescription = "Search Button",
        contentScale = ContentScale.None,
        modifier = Modifier
            .width(18.11669.dp)
            .height(17.32211.dp)
            .background(color = Color(0xFF6A6A6A))
            .padding(start = 347.83.dp, end = 24.05.dp, top = 82.4.dp, bottom = 744.28.dp)
    )
}

@Composable
fun FriendBtn(){
    Image(
        painter = painterResource(id = R.drawable.friendcloud),
        contentDescription = "Friend Cloud Btn",
        contentScale = ContentScale.None,
        modifier = Modifier
            .width(40.dp)
            .height(40.dp)
            .padding(start = 33.dp, end = 317.dp, top = 751.dp, bottom = 53.dp)
    )
}

@Composable
fun AddCloudBtn(){
    Image(
        painter = painterResource(id = R.drawable.addcloud),
        contentDescription = "Add Cloud Btn",
        contentScale = ContentScale.None,
        modifier = Modifier
            .width(40.dp)
            .height(40.dp)
            .padding(start = 318.dp, end = 32.dp, top = 751.dp, bottom = 53.dp)
    )
}



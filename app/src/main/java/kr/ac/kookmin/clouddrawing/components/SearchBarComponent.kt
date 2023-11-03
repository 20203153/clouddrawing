package kr.ac.kookmin.clouddrawing.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalMinimumInteractiveComponentEnforcement
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import kr.ac.kookmin.clouddrawing.R

class SearchBarModel : ViewModel(){
    var search: String = ""
}

@Preview(name="SearchBar", showBackground = true)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    viewModel: SearchBarModel = SearchBarModel(),
    onSearch: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    var searchValue by remember {
        mutableStateOf(viewModel.search)
    }

    Row(
        modifier = modifier
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
            .width(340.dp)
            .height(38.dp)
            .background(
                color = Color(0xFFFFFFFF),
                shape = RoundedCornerShape(size = 10.dp)
            ),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        BasicTextField(
            modifier = Modifier
                .height(38.dp)
                .padding(horizontal = 8.dp, vertical = 10.dp)
                .fillMaxWidth(1f),
            value = searchValue,
            onValueChange = {
                searchValue = it
                viewModel.search = it
            },
            singleLine = true,
        )
        CompositionLocalProvider(LocalMinimumInteractiveComponentEnforcement provides false) {
            IconButton(
                onClick = onSearch,
                modifier = Modifier
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
    }
}
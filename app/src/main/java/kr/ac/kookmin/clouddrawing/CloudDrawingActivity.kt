package kr.ac.kookmin.clouddrawing

import android.annotation.SuppressLint
import android.location.LocationManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.google.firebase.Firebase
import com.google.firebase.Timestamp
import com.google.firebase.storage.storage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kr.ac.kookmin.clouddrawing.CloudDrawingActivity.Companion.timeFormat
import kr.ac.kookmin.clouddrawing.components.LeftCloseBtn
import kr.ac.kookmin.clouddrawing.components.SaveButton
import kr.ac.kookmin.clouddrawing.dto.Post
import kr.ac.kookmin.clouddrawing.dto.User
import round
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZoneId
import java.util.*


class CloudDrawingActivity : ComponentActivity() {
    companion object {
        @SuppressLint("SimpleDateFormat")
        val timeFormat = SimpleDateFormat("yyyy-MM-dd E")
    }
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val title = mutableStateOf("")
        val friends = mutableStateOf("")
        val mainContent = mutableStateOf("")
        val loading: MutableState<Boolean> = mutableStateOf(true)

        val postId = intent.getStringExtra("postId")

        var lat = intent.getDoubleExtra("lat", 0.0)
        var lng = intent.getDoubleExtra("lng", 0.0)
        var address : String = intent.getStringExtra("address") ?: ""
        var road_address : String = intent.getStringExtra("road_address") ?: ""
        var a : String = intent.getStringExtra("region") ?: ""
        Log.e("Testing", "region : " + a)
        var region = mutableStateOf(intent.getStringExtra("region") ?: "")
        var locations = mutableStateOf(if(road_address == "") address else road_address)
        val locationAlias = mutableStateOf("")

        val date = DatePickerState(
            Date().time,
            Date().time,
            IntRange(
                LocalDate.now().atStartOfDay(ZoneId.systemDefault()).minusYears(5).year,
                LocalDate.now().atStartOfDay(ZoneId.systemDefault()).plusYears(5).year
            ),
            DisplayMode.Picker
        )

        setContent {
            val scrollState = rememberScrollState()

            CDBackground(
                title = title,
                date = date,
                locations = locations,
                locationAlias = locationAlias,
                friends = friends,
                mainContent = mainContent,
                loading = loading,
                scrollState = scrollState,
                onClickBack = { finish() },
                onClickSave = {
                    if(!loading.value) {
                        loading.value = true

                        CoroutineScope(Dispatchers.Main).launch {
                            val posts = postId?.let { it1 -> try {
                                    Post.getPostById(it1)
                                } catch (e: Exception) {
                                    return@let null
                                }
                            }
                            if(posts == null) {
                                val id = Post.getNewPostId()
                                val post = Post(
                                    id = id,
                                    uid = User.getCurrentUser()!!.uid,
                                    title = title.value,
                                    lat = round(lat),
                                    lng = round(lng),
                                    address = locations.value,
                                    addressAlias = locationAlias.value,
                                    comment = mainContent.value,
                                    postTime = Timestamp(Date(date.selectedDateMillis!!))
                                )

                                val user = User.getCurrentUser()
                                val storageRef = Firebase.storage.reference

                                it.forEachIndexed { index, uri ->
                                    val photoRef =
                                        storageRef.child("post/${user!!.uid}/${id}/${index}")

                                    photoRef.putFile(uri).await()
                                    post.image.add(photoRef.downloadUrl.await().toString())
                                }

                                Post.addPost(post)

                                Toast.makeText(
                                    this@CloudDrawingActivity,
                                    "구름이 그려졌습니다! :D",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
                                val user = User.getCurrentUser()
                                val storageRef = Firebase.storage.reference

                                it.forEachIndexed { index, uri ->
                                    val photoRef =
                                        storageRef.child("post/${user!!.uid}/${posts.id ?: ""}/${index}")

                                    photoRef.putFile(uri).await()
                                    posts.image.add(photoRef.downloadUrl.await().toString())
                                }

                                posts.update(Post(
                                    title = title.value,
                                    address = locations.value,
                                    addressAlias = locationAlias.value,
                                    comment = mainContent.value,
                                    postTime = Timestamp(Date(date.selectedDateMillis!!)),
                                    image = posts.image
                                ))
                            }
                            finish()
                        }
                    }
                }
            )
        }
        CoroutineScope(Dispatchers.Main).launch {
            if(postId == null) {
                loading.value = false
                return@launch
            }

            val post = Post.getPostById(postId)
            if(post == null) finish()
            else {
                lat = post.lat ?: 0.0
                lng = post.lng ?: 0.0
                locations.value = post.address ?: ""
                locationAlias.value = post.addressAlias ?: ""
                title.value = post.title ?: ""
                mainContent.value = post.comment ?: ""
                date.setSelection(post.postTime?.toDate()?.time ?: Date().time)

                loading.value = false
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.P)
@SuppressLint("MutableCollectionMutableState")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Preview
@Composable
fun CDBackground(
    title: MutableState<String> = mutableStateOf(""),
    date: DatePickerState = rememberDatePickerState(Date().time),
    locations: MutableState<String> = mutableStateOf(""),
    locationAlias: MutableState<String> = mutableStateOf(""),
    friends: MutableState<String> = mutableStateOf(""),
    mainContent: MutableState<String> = mutableStateOf(""),
    loading: MutableState<Boolean> = mutableStateOf(false),
    scrollState: ScrollState = rememberScrollState(),

    onClickBack: () -> Unit = {},
    onClickSave: (it: List<Uri>) -> Unit = {},

    content: @Composable () -> Unit = {}
) {
    val context = LocalContext.current
    var selectImages by remember {
        mutableStateOf(mutableListOf<Uri>())
    }
    val getPhotoFromGallery =
        rememberLauncherForActivityResult(ActivityResultContracts.GetMultipleContents()) {
            selectImages = selectImages.union(it).toMutableList()
            selectImages.filterIndexed { index, _ ->
                index < 3
            }
        }
    var calendarVisible by remember {
        mutableStateOf(false)
    }

    val locationManager =
        context.getSystemService(AppCompatActivity.LOCATION_SERVICE) as LocationManager
    var isGpsOn = locationManager.isLocationEnabled
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFFFFF))
            .verticalScroll(scrollState)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(1f)
                .padding(start = 13.dp, top = 33.dp, end = 14.dp, bottom = 13.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            LeftCloseBtn(onClick = onClickBack)
            Text(
                text = "구름 그리기",
                style = TextStyle(
                    fontSize = 24.sp,
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.W600,
                    color = Color(0xFF454545),
                )
            )
            SaveButton { onClickSave(selectImages) }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth(1f)
                .height(3.dp)
                .background(
                    color = Color(0xFFF5F5F5),
                    shape = RoundedCornerShape(size = 25.dp)
                )
        )

        BasicTextField(
            modifier = Modifier
                .padding(top = 5.dp, start = 5.dp, end = 5.dp)
                .fillMaxWidth(1f)
                .defaultMinSize(minHeight = 250.dp),
            value = mainContent.value,
            onValueChange = { textValue -> mainContent.value = textValue },
            decorationBox = { innerTextField ->
                Box(
                    Modifier
                        .fillMaxWidth(1f)
                        .padding(10.dp, 0.dp)
                        .background(Color.White, RoundedCornerShape(10.dp))
                ) {
                    if (mainContent.value.isEmpty()) {
                        Text(text = "어떤 추억이 있었나요?\n나중에 떠올리고 싶은 추억을 그려보세요 :)")
                    }
                    innerTextField.invoke()
                }
            }
        )
        Box(
            modifier = Modifier
                .fillMaxWidth(1f)
                .height(15.dp)
                .background(
                    color = Color(0xFFF5F5F5),
                    shape = RoundedCornerShape(size = 25.dp)
                )
        )

        Row(
            Modifier
                .fillMaxWidth(1f)
                .padding(start = 13.dp, top = 5.dp, end = 14.dp, bottom = 0.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.v_cd_title),
                    contentDescription = "CDTittle",
                    modifier = Modifier
                        .width(15.dp)
                        .height(15.dp)
                )
                Spacer(Modifier.width(23.dp))
                Text(
                    text = "제목",
                    style = TextStyle(
                        fontSize = 15.sp,
                        fontFamily = FontFamily.SansSerif,
                        fontWeight = FontWeight.W600,
                        color = Color(0xFF686868),
                    )
                )
            }
            BasicTextField(
                modifier = Modifier.size(height = 18.dp, width = 200.dp),
                value = title.value,
                onValueChange = { textValue -> title.value = textValue },
                singleLine = true
            )
        }
        Row(
            Modifier
                .fillMaxWidth(1f)
                .padding(start = 13.dp, top = 12.dp, end = 14.dp, bottom = 0.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.g_cd_calendar),
                    contentDescription = "CDCalendar",
                    modifier = Modifier
                        .width(15.dp)
                        .height(15.dp)
                )
                Spacer(Modifier.width(22.dp))
                Text(
                    text = "방문 날짜",
                    style = TextStyle(
                        fontSize = 15.sp,
                        fontFamily = FontFamily.SansSerif,
                        fontWeight = FontWeight.W600,
                        color = Color(0xFF686868),
                    )
                )
            }
            Row(
                Modifier
                    .width(200.dp)
                    .clickable {
                        keyboardController?.hide()
                        calendarVisible = true
                    }) {
                Text(
                    text = timeFormat.format(Date(date.selectedDateMillis ?: 0)),
                    style = TextStyle(
                        fontSize = 15.sp,
                        fontFamily = FontFamily.SansSerif,
                        fontWeight = FontWeight.W600,
                        color = Color(0xFF686868),
                    ),
                )
                Spacer(Modifier.width(70.dp))
                Image(
                    painter = painterResource(id = R.drawable.calendar), // 'calendar_image'는 XML 이미지 파일의 리소스 이름입니다.
                    contentDescription = "달력"
                )
            }
        }
        Row(
            Modifier
                .fillMaxWidth(1f)
                .padding(start = 13.dp, top = 12.dp, end = 14.dp, bottom = 0.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.g_cd_location),
                    contentDescription = "CDLocation",
                    modifier = Modifier
                        .width(15.dp)
                        .height(15.dp)
                )
                Spacer(Modifier.width(22.dp))
                Text(
                    text = "주소",
                    style = TextStyle(
                        fontSize = 15.sp,
                        fontFamily = FontFamily.SansSerif,
                        fontWeight = FontWeight.W600,
                        color = Color(0xFF686868),
                    )
                )
            }

            Text(
                text = locations.value,
                modifier = Modifier.size(height = 18.dp, width = 200.dp),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

        }
        Row(
            Modifier
                .fillMaxWidth(1f)
                .padding(start = 13.dp, top = 12.dp, end = 14.dp, bottom = 0.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.location_tag),
                    contentDescription = "LocationTag",
                    modifier = Modifier
                        .width(15.dp)
                        .height(15.dp)
                )
                Spacer(Modifier.width(22.dp))
                Text(
                    text = "장소명",
                    style = TextStyle(
                        fontSize = 15.sp,
                        fontFamily = FontFamily.SansSerif,
                        fontWeight = FontWeight.W600,
                        color = Color(0xFF686868),
                    )
                )
            }
            BasicTextField(
                modifier = Modifier.size(height = 18.dp, width = 200.dp),
                value = locationAlias.value,
                onValueChange = { textValue -> locationAlias.value = textValue },
                singleLine = true
            )
        }
        Spacer(Modifier.height(8.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth(1f)
                .height(15.dp)
                .background(
                    color = Color(0xFFF5F5F5),
                    shape = RoundedCornerShape(size = 25.dp)
                )
        )
        Text(
            text = "사진",
            style = TextStyle(
                fontSize = 15.sp,
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.W600,
                color = Color(0xFF454545),
            ),
            modifier = Modifier.padding(start = 13.dp, top = 10.dp)
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = Modifier
                .padding(start = 15.dp, end = 15.dp, top = 32.dp, bottom = 15.dp)
                .height(200.dp)
                .fillMaxWidth(1f)
                .background(color = Color.White)
                .border(width = 3.dp, color = Color(0xFFF5F5F5))
                .clickable {
                    getPhotoFromGallery.launch("image/*")
                }
        ) {
            if (selectImages.isEmpty()) {
                item(span = { GridItemSpan(maxLineSpan) }) { // 전체 너비를 사용하여 하나의 아이템으로 만듦
                    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                        Text("사진을 추가해 보세요.",  modifier = Modifier.align(Alignment.Center).offset(y = 80.dp))
                    }
                }
            } else {
                items(selectImages) { uri ->
                    if (selectImages.size > 3) {
                        Toast.makeText(context, "사진은 4장 이상 고를 수 없습니다", Toast.LENGTH_LONG).show()
                        selectImages = mutableListOf()
                    } else {
                        Image(
                            painter = rememberAsyncImagePainter(uri),
                            contentScale = ContentScale.Crop,
                            contentDescription = null,
                            modifier = Modifier
                                .padding(16.dp, 8.dp)
                                .size(100.dp)
                        )
                    }
                }
            }
        }
    }

        AnimatedVisibility(
            visible = loading.value,
            modifier = Modifier.fillMaxSize(1f),
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize(1f)
                    .background(Color.Black.copy(alpha = 0.5f)),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.width(64.dp),
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    trackColor = MaterialTheme.colorScheme.secondary
                )
            }
        }
        AnimatedVisibility(
            visible = calendarVisible,
            modifier = Modifier.fillMaxSize(1f),
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize(1f)
                    .background(Color.Black.copy(alpha = 0.5f))
                    .clickable { calendarVisible = false },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                DatePicker(
                    modifier = Modifier
                        .fillMaxWidth(1f)
                        .background(Color.White, RoundedCornerShape(10.dp)),
                    state = date
                )
            }
        }
    }


    @Composable
    fun CDMiddleSearch() {
        Image(
            painter = painterResource(id = R.drawable.v_cd_location_search),
            contentDescription = "CDSearch",
            modifier = Modifier
                .width(15.dp)
                .height(15.dp)
        )
    }



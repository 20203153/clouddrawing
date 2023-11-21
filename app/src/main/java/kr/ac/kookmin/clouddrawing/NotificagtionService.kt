package kr.ac.kookmin.clouddrawing

import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.IBinder
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.kakao.vectormap.LatLng
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kr.ac.kookmin.clouddrawing.dto.Post
import kr.ac.kookmin.clouddrawing.dto.User
import round
import java.util.*

class NotificagtionService : Service() {
    private lateinit var locationManager: LocationManager
    private val postList: MutableList<Post> = mutableListOf()
    private val notifiedLocation: MutableList<LatLng> = mutableListOf()
    private val lastNotificationTime: MutableState<Date> = mutableStateOf(Date())

    private val locationListener = LocationListener { location ->
        // 지정된 위치에 도달했는지 확인
        if (Date().time - lastNotificationTime.value.time >= 10000L && checkLocation(location)) {
            lastNotificationTime.value = Date()
            notifiedLocation.add(LatLng.from(round(location.latitude), (location.longitude)))
            sendNotification()
        }
    }

    override fun onCreate() {
        super.onCreate()
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        try {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0f, locationListener)

            CoroutineScope(Dispatchers.IO).launch {
                postList.addAll(Post.getAllPost())
            }
        } catch (e: SecurityException) {
            // 권한 처리
        }
    }

    private fun checkLocation(location: Location): Boolean {
        return postList.any {
            !notifiedLocation.any { loc ->
                round(loc.latitude, 10000.0) == round(location.latitude, 10000.0) &&
                round(loc.longitude, 10000.0) == round(location.longitude, 10000.0)
            } &&
                round(it.lat?:0.0, 10000.0) == round(location.latitude, 10000.0) &&
                round(it.lng ?: 0.0, 10000.0) == round(location.longitude, 10000.0)
        }
    }

    // 알림 전송 코드
    private fun sendNotification() {
        val notificationManager = ContextCompat.getSystemService(this, NotificationManager::class.java) as NotificationManager
        CoroutineScope(Dispatchers.Main).launch {
            val notification = NotificationCompat.Builder(this@NotificagtionService, CHANNEL_ID)
                .setContentTitle("알림 제목")
                .setContentText("${User.getCurrentUser()?.name ?: "OO"}님이 최근에 추억을 그렸던 곳이에요.")
                .setSmallIcon(R.drawable.v_gurmmy)
                .setContentIntent(PendingIntent.getActivity(
                    this@NotificagtionService,
                    NOTIFICATION_ID,
                    Intent(this@NotificagtionService ,MainActivity::class.java),
                    PendingIntent.FLAG_IMMUTABLE
                ))
                .setAutoCancel(true)
                .build()
            notificationManager.notify(NOTIFICATION_ID, notification)
        }
    }

    companion object {
        private const val CHANNEL_ID = "cloudgreenImagine"
        private const val NOTIFICATION_ID = 20231111
    }


    override fun onBind(intent: Intent): IBinder? {
        return null
    }

}




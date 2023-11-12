package kr.ac.kookmin.clouddrawing

import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService

class NotificagtionService : Service() {
    private lateinit var locationManager: LocationManager
    private val locationListener = LocationListener { location ->
        // 지정된 위치에 도달했는지 확인
        if (checkLocation(location)) {
            sendNotification()
        }
    }

    override fun onCreate() {
        super.onCreate()
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        try {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0f, locationListener)
        } catch (e: SecurityException) {
            // 권한 처리
        }
    }

    private fun checkLocation(location: Location): Boolean {
        // 지정된 위치와 현재 위치를 비교
        return false // 임시 코드
    }

    // 알림 전송 코드
    private fun sendNotification() {
        val notificationManager = ContextCompat.getSystemService(this, NotificationManager::class.java) as NotificationManager
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("알림 제목")
            .setContentText("소영님 1년 전에 추억을 그렸던 곳이에요.")
            .setSmallIcon(R.drawable.v_gurmmy)
            .build()
        notificationManager.notify(NOTIFICATION_ID, notification)
    }

    companion object {
        private const val CHANNEL_ID = "cloudgreenImagine"
        private const val NOTIFICATION_ID = 20231111
    }


    override fun onBind(intent: Intent): IBinder? {
        return null
    }

}




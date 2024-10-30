package semicolon.dev.yourpersona.util.firebase

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import semicolon.dev.yourpersona.R
import semicolon.dev.yourpersona.model.ContentPopup
import semicolon.dev.yourpersona.model.Popup
import semicolon.dev.yourpersona.presentation.MainActivity
import semicolon.dev.yourpersona.util.datastore.DataStoreHelper
import javax.inject.Inject

@AndroidEntryPoint
class FirebaseMessagingService : FirebaseMessagingService() {

    @Inject
    lateinit var firebaseMessaging: FirebaseMessaging

    override fun onNewToken(token: String) {
        Log.d(TAG, "Refreshed token: $token")
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: ${remoteMessage.from}")
        Log.d(TAG, "Message data payload: " + remoteMessage.data)
        Log.d(TAG, "Message Notification Body: ${remoteMessage.notification?.body}")

        handleNotification(remoteMessage)
    }

    private fun handleNotification(remoteMessage: RemoteMessage) {
        if (remoteMessage.data.containsKey("data")) {
            val mData = getPopup(remoteMessage)
            /**
             * 1 : Program
             * 2 : Promo
             */
            when {
                mData.content?.ctaCategory.equals("1") -> saveNotification(mData, "contentList")

                mData.content?.ctaCategory.equals("2") -> {
                    saveNotification(mData, "contentPromo")
                    sendNotification(
                        remoteMessage.notification?.title, remoteMessage.notification?.body
                    )
                }

                else -> sendNotification(
                    remoteMessage.notification?.title, remoteMessage.notification?.body
                )
            }
        } else {
            sendNotification(remoteMessage.notification?.title, remoteMessage.notification?.body)
        }
    }

    private fun getPopup(remoteMessage: RemoteMessage): Popup {
        val content = Gson().fromJson(remoteMessage.data["data"], ContentPopup::class.java)
        val data = Popup(
            title = remoteMessage.notification?.title,
            message = remoteMessage.notification?.body,
            linkImage = remoteMessage.notification?.imageUrl.toString(),
            content = content
        )
        return data
    }

    private fun saveNotification(popup: Popup, key: String) {
        CoroutineScope(Dispatchers.IO).launch {
            if (DataStoreHelper.getList<Popup>(applicationContext, key)
                    .isEmpty()
            ) DataStoreHelper.putList(
                applicationContext, key, listOf(popup)
            )
            else DataStoreHelper.addToList(applicationContext, key, popup)
        }
    }

    private fun sendNotification(title: String?, messageBody: String?) {
        val contentIntent = Intent(applicationContext, MainActivity::class.java)

        // Set Sound
        val soundUri: Uri = Uri.parse("android.resource://${packageName}/${R.raw.man_high_sounds}")

        RingtoneManager.getRingtone(applicationContext, soundUri)
            ?.setStreamType(RingtoneManager.TYPE_NOTIFICATION)

        val contentPendingIntent = PendingIntent.getActivity(
            applicationContext, NOTIFICATION_ID, contentIntent, PendingIntent.FLAG_IMMUTABLE
        )

        val audioAttributes =
            AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_NOTIFICATION).build()

        val notificationBuilder = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(title)
            .setContentText(messageBody)
            .setContentIntent(contentPendingIntent)
            .setSound(soundUri)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                NOTIFICATION_CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                setSound(soundUri, audioAttributes)
            }
            notificationBuilder.setChannelId(NOTIFICATION_CHANNEL_ID)
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build())
    }

    companion object {
        private val TAG = FirebaseMessagingService::class.java.simpleName
        private const val NOTIFICATION_ID = 1
        private const val NOTIFICATION_CHANNEL_ID = "Firebase Channel"
        private const val NOTIFICATION_CHANNEL_NAME = "Firebase Notification"
    }
}
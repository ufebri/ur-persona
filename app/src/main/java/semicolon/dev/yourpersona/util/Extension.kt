package semicolon.dev.yourpersona.util

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.NotificationCompat
import androidx.fragment.app.FragmentActivity
import com.bumptech.glide.Glide
import semicolon.dev.yourpersona.R
import semicolon.dev.yourpersona.databinding.FiamCardBinding
import semicolon.dev.yourpersona.model.Popup
import semicolon.dev.yourpersona.presentation.MainActivity

fun FragmentActivity.sendNotification(title: String?, messageBody: String?) {
    val notificationID = 1
    val notificationChannelID = "Firebase Channel"
    val notificationChannelName = "Firebase Notification"

    val contentIntent = Intent(applicationContext, MainActivity::class.java)
    // Set suara notifikasi dari res/raw
    val soundUri: Uri =
        Uri.parse("android.resource://" + applicationContext.packageName + "/" + R.raw.man_high_sounds)

    RingtoneManager.getRingtone(applicationContext, soundUri)
        ?.setStreamType(RingtoneManager.TYPE_NOTIFICATION)
    val audioAttributes = AudioAttributes.Builder()
        .setUsage(AudioAttributes.USAGE_NOTIFICATION)
        .build()

    val contentPendingIntent = PendingIntent.getActivity(
        applicationContext,
        notificationID,
        contentIntent,
        PendingIntent.FLAG_IMMUTABLE
    )

    val notificationBuilder = NotificationCompat.Builder(this, notificationChannelID)
        .setSmallIcon(R.drawable.ic_launcher_foreground)
        .setContentTitle(title)
        .setContentText(messageBody)
        .setContentIntent(contentPendingIntent)
        .setSound(soundUri)
        .setAutoCancel(true)

    val notificationManager =
        getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val channel = NotificationChannel(
            notificationChannelID,
            notificationChannelName,
            NotificationManager.IMPORTANCE_DEFAULT
        ).apply {
            setSound(soundUri, audioAttributes)
        }
        notificationBuilder.setChannelId(notificationChannelID)
        notificationManager.createNotificationChannel(channel)
    }

    notificationManager.notify(notificationID, notificationBuilder.build())
}

fun String.copyToClipboard(context: Context, label: String = "Copied Text") {
    val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText(label, this)
    clipboard.setPrimaryClip(clip)
    Toast.makeText(context, "Text copied to clipboard!", Toast.LENGTH_SHORT).show()
}

// Extension function to create and show custom dialog for FiamCardBinding
fun Context.showFiamDialog(
    inAppMessage: Popup?,
    onClick: (Popup) -> Unit,
    binding: FiamCardBinding = FiamCardBinding.inflate(
        LayoutInflater.from(this)
    )
) {
    binding.apply {
        inAppMessage?.let {
            tvTitle.text = it.title
            tvBody.text = it.message

            Glide.with(this@showFiamDialog).load(it.linkImage).into(ivBanner)

            btnCta.apply {
                text = it.content?.ctaBanner
                setOnClickListener { onClick(inAppMessage) }
            }

            // Create and show dialog
            AlertDialog.Builder(this@showFiamDialog)
                .setView(binding.root)
                .setCancelable(true)
                .create()
                .show()
        }
    }
}

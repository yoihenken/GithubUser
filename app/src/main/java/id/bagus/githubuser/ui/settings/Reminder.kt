package id.bagus.githubuser.ui.settings

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import id.bagus.githubuser.R
import id.bagus.githubuser.ui.dashboard.DashboardActivity
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class Reminder : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        // This method is called when the BroadcastReceiver is receiving an Intent broadcast.
        showAlarmNotification(context, intent.extras?.getString(EXTRA_MESSAGE).toString())
    }


    private fun showToast(context: Context, message: String?) {
        Toast.makeText(context, "$message", Toast.LENGTH_SHORT).show()
    }

    private fun showAlarmNotification(
            context: Context,
            message: String
    ) {
        val notificationManagerCompat = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val intent = Intent(context, DashboardActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK

        val pendingIntent = PendingIntent.getActivity(
                context,
                NOTIF_REQUEST_CODE,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT
        )

        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_codicon_github)
                .setContentTitle(NOTIF_TITLE)
                .setContentText(message)
                .setColor(ContextCompat.getColor(context, android.R.color.transparent))
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
                .setSound(alarmSound)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val channel = NotificationChannel(
                    CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_HIGH
            )

            channel.enableVibration(true)
            channel.vibrationPattern = longArrayOf(1000, 1000, 1000, 1000, 1000)

            builder.setChannelId(CHANNEL_ID)

            notificationManagerCompat.createNotificationChannel(channel)
        }

        val notification = builder.build()

        notificationManagerCompat.notify(NOTIF_ID, notification)
    }

    fun isAlarmSet(context: Context): Boolean {
        val intent = Intent(context, Reminder::class.java)
        val requestCode = NOTIF_ID

        return PendingIntent.getBroadcast(
                context,
                requestCode,
                intent,
                PendingIntent.FLAG_NO_CREATE
        ) != null
    }

    fun setRepeatingAlarm(context: Context, message: String) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, Reminder::class.java)
        intent.putExtra(EXTRA_MESSAGE, message)

        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 9)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
        }

        val pendingIntent = PendingIntent.getBroadcast(context, NOTIF_ID, intent, 0)
        alarmManager.setInexactRepeating(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                AlarmManager.INTERVAL_DAY,
                pendingIntent
        )

        showToast(context, context.getString(R.string.set_reminder))
    }

    fun cancelAlarm(context: Context) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, Reminder::class.java)
        val requestCode = NOTIF_ID
        val pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, 0)
        pendingIntent.cancel()

        alarmManager.cancel(pendingIntent)
        showToast(context, context.getString(R.string.unset_reminder))
    }


    companion object {
        const val EXTRA_MESSAGE = "message"
        private const val NOTIF_ID = 101
        const val NOTIF_REQUEST_CODE = 102
        const val NOTIF_TITLE = "Github User Reminder"
        const val CHANNEL_ID = "Reminder"
        const val CHANNEL_NAME = "Time to see your friends !"
    }
}
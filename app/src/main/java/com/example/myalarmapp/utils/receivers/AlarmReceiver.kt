package com.example.myalarmapp.utils.receivers

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.Ringtone
import android.media.RingtoneManager
import android.net.Uri
import android.provider.AlarmClock
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.myalarmapp.AlarmActivity
import com.example.myalarmapp.R
import kotlin.random.Random

class AlarmReceiver : BroadcastReceiver() {
    private lateinit var ringtone: Ringtone

    override fun onReceive(context: Context?, intent: Intent?) {
        context?.let {

            val hour = intent?.getStringExtra("Hour")
            val time = intent?.getStringExtra("Time")
            Log.d("NULLL","${hour}:${time}")

            val randomNumber =  Random.nextInt(0, 50000)
            Log.d("NotificationID","KetkenId : $randomNumber")

            val fullScreenIntent = Intent(context, AlarmActivity::class.java).apply {
                putExtra("hour",hour)
                putExtra("time",time)
            }
            val fullScreenPendingIntent = PendingIntent.getActivity(
                context,
                2,
                fullScreenIntent,
                PendingIntent.FLAG_IMMUTABLE
            )

            var notificationUri: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
            ringtone = RingtoneManager.getRingtone(context, notificationUri)
            if (ringtone == null) {
                notificationUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE)
                Log.d("UriTTT", notificationUri.toString())
                ringtone = RingtoneManager.getRingtone(context, notificationUri)
            } else {
                ringtone.play()
            }

            val turnOffPendingIntent = PendingIntent.getBroadcast(
                context,
                0,
                Intent(context, ListenerClickActionsReceiver::class.java).apply {
                    putExtra("task", "turnoff")
                    putExtra("notification_id",randomNumber)
                    putExtra("ringtone_uri", notificationUri.toString())
                },
                PendingIntent.FLAG_IMMUTABLE)

            val snoozePendingIntent = PendingIntent.getBroadcast(
                context,
                1,
                Intent(context, ListenerClickActionsReceiver::class.java).apply {
                    action = AlarmClock.ACTION_SNOOZE_ALARM
                    putExtra("task", "snooze")
                },
                PendingIntent.FLAG_IMMUTABLE)


            val notification = NotificationCompat.Builder(it, "alarmChannel")
                .setSmallIcon(R.drawable.bell)
                .setContentTitle("Test Notification")
                .setContentText("Test").setStyle(
                    NotificationCompat.BigTextStyle()
                        .bigText("Test Notification Mature language and environment. Since its creation in 2011, Kotlin has developed continuously, not only as a language but as a whole ecosystem with robust tooling. Now it's seamlessly integrated in Android Studio and is actively used by many companies for developing Android applications.")
                )
                .setAutoCancel(true)
                .setSound(null)
                .setOngoing(true)
                .setFullScreenIntent(fullScreenPendingIntent, true)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .addAction(R.drawable.ic_alarm_off, "Отложить", snoozePendingIntent)
                .addAction(R.drawable.ic_stop, "Выключить", turnOffPendingIntent)


            val notificationManager: NotificationManager =
                it.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.notify("TTT",randomNumber, notification.build())
        }
    }
}
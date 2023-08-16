package com.example.myalarmapp.utils.receivers

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.Ringtone
import android.media.RingtoneManager
import android.net.Uri
import android.util.Log

class ListenerClickActionsReceiver:BroadcastReceiver() {
    private lateinit var ringtone: Ringtone
    override fun onReceive(context: Context?, intent: Intent?) {

        context?.let {

            val ringtoneUri = intent?.getStringExtra("ringtone_uri")
//            if (ringtoneUri != null) {
                ringtone = RingtoneManager.getRingtone(context, ringtoneUri as Uri)
//            }

            val notificationId = intent?.getIntExtra("notification_id",0)
            Log.d("NotificationID","KelgenId : $notificationId")

            ringtone.stop()

            if (intent?.getStringExtra("task") == "turnoff") {
                val notificationManager: NotificationManager =
                    context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                if (notificationId != null) {
                    notificationManager.cancel("TTT",notificationId)
                }
            }

            if (intent?.getStringExtra("task") == "snooze"){
                Log.d("TTT","SNOOZE")
            }
        }



    }
}
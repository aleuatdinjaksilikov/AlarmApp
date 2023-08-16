package com.example.myalarmapp.ui.timer

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.myalarmapp.R
import com.example.myalarmapp.databinding.FragmentTimerBinding
import us.romanandroiddev.scheduled_notification_builder.ScheduledNotification
import us.romanandroiddev.scheduled_notification_builder.data.models.NotificationType

class TimerFragment :Fragment(R.layout.fragment_timer){
    private lateinit var binding: FragmentTimerBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentTimerBinding.bind(view)



    }


}
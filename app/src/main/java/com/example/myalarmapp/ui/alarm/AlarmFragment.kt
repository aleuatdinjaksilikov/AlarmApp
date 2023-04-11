package com.example.myalarmapp.ui.alarm

import android.annotation.SuppressLint
import android.graphics.Color
import android.icu.util.Calendar
import android.os.Bundle
import android.text.format.DateFormat
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myalarmapp.R
import com.example.myalarmapp.data.AppDatabase
import com.example.myalarmapp.data.dao.AppDao
import com.example.myalarmapp.data.models.AlarmData
import com.example.myalarmapp.databinding.FragmentAlarmBinding
import com.example.myalarmapp.databinding.RcItemAlarmBinding
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.MaterialTimePicker.INPUT_MODE_CLOCK
import com.google.android.material.timepicker.TimeFormat

class AlarmFragment:Fragment(R.layout.fragment_alarm) {

    private lateinit var binding: FragmentAlarmBinding
    private var adapter = AlarmAdapter()
    private lateinit var dao: AppDao

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAlarmBinding.bind(view)
        dao = AppDatabase.getInstance(requireContext()).getDao()

        initVariable()
        initListeners()
    }

    private fun initVariable(){
        binding.rvAlarmFragment.adapter = adapter

        lifecycleScope.launchWhenResumed {
            adapter.list = dao.getAllAlarms().toMutableList()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun initListeners(){

        binding.btnAddAlarm.setOnClickListener {
            openTimePicker()
        }

        adapter.setOnClickTvClock { alarmData,binding ->
            openTimePickerTvClock(alarmData,binding)
        }

        adapter.setOnSwitchClickListener { binding,alarmData ->
            if (binding.switchAlarmFragment.isChecked){
                binding.tvClock.setTextColor(Color.parseColor("#FFFFFF"))
            }
            else{
                binding.tvClock.setTextColor(Color.parseColor("#8F8F8F"))
                alarmData.installed = false
            }
        }

        adapter.setOnDeleteItemListener { binding,alarmData,position ->
            lifecycleScope.launchWhenResumed {
                dao.deleteAlarm(alarmData)
                adapter.list.removeAt(position)
                adapter.notifyItemRemoved(position)
            }
        }

        adapter.setOnClickDaysOfWeek { alarmData ->
            lifecycleScope.launchWhenResumed {
                dao.updateAlarm(alarmData)
            }
        }

        binding.rvAlarmFragment.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = binding.rvAlarmFragment.layoutManager!! as LinearLayoutManager
                if (layoutManager.findFirstCompletelyVisibleItemPosition()>0){
                    binding.toolbar.alpha = 1F
                    val window = requireActivity().window
                    window.statusBarColor = ContextCompat.getColor(requireContext(),R.color.statusBarColor)
                }else{
                    binding.toolbar.alpha = 0F
                    val window = requireActivity().window
                    window.statusBarColor = ContextCompat.getColor(requireContext(),R.color.bg_fragments)
                }
            }
        })
    }

    private fun openTimePicker() {

        val isSystem24Hour = DateFormat.is24HourFormat(requireContext())
        val clockFormat = if (isSystem24Hour) TimeFormat.CLOCK_24H else TimeFormat.CLOCK_12H

        val calendar = Calendar.getInstance()

        val picker = MaterialTimePicker.Builder()
            .setTimeFormat(clockFormat)
            .setHour(calendar.get(Calendar.HOUR_OF_DAY))
            .setMinute(calendar.get(Calendar.MINUTE))
            .setInputMode(INPUT_MODE_CLOCK)
            .setTheme(R.style.MyThemeTimePicker)
            .setTitleText("Выбор времени")
            .build()
        picker.show(parentFragmentManager,"TAG")


        picker.addOnPositiveButtonClickListener {

            var hour = picker.hour.toString() //00
            var minute = picker.minute.toString()

            if (minute.length<2){
                minute = "0${minute}"
            }
            if (hour.length<2){
                hour = "0${hour}"
            }

            lifecycleScope.launchWhenResumed {
                dao.addAlarm(AlarmData(0,hour,minute,true,"",
                    isMondayActivated = false,
                    isTuesdayActivated = false,
                    isWednesdayActivated = false,
                    isThursdayActivated = false,
                    isFridayActivated = false,
                    isSaturdayActivated = false,
                    isSundayActivated = false
                ))
                adapter.list = dao.getAllAlarms().toMutableList()
            }

        }
        picker.addOnNegativeButtonClickListener {
            Log.d("TimeLOg","Negative")
        }
        picker.addOnCancelListener {
            Log.d("TimeLOg","Cancel")
        }
        picker.addOnDismissListener {
            Log.d("TimeLOg","Dismiss")
        }
    }

    private fun openTimePickerTvClock(alarmData: AlarmData,binding: RcItemAlarmBinding) {

        val isSystem24Hour = DateFormat.is24HourFormat(requireContext())
        val clockFormat = if (isSystem24Hour) TimeFormat.CLOCK_24H else TimeFormat.CLOCK_12H

        val calendar = Calendar.getInstance()

        val picker = MaterialTimePicker.Builder()
            .setTimeFormat(clockFormat)
            .setHour(calendar.get(Calendar.HOUR_OF_DAY))
            .setMinute(calendar.get(Calendar.MINUTE))
            .setInputMode(INPUT_MODE_CLOCK)
            .setTheme(R.style.MyThemeTimePicker)
            .setTitleText("Выбор времени")
            .build()
        picker.show(parentFragmentManager,"TAG")


        picker.addOnPositiveButtonClickListener {

            var hour = picker.hour.toString()
            var minute = picker.minute.toString()

            if (minute.length<2){
                minute = "0${minute}"
            }
            if (hour.length<2){
                hour = "0${hour}"
            }

            alarmData.timeHour = hour
            alarmData.timeMinute = minute

            binding.tvClock.text = "${hour}:${minute}"

            lifecycleScope.launchWhenResumed {
                dao.updateAlarm(alarmData)
            }

        }
        picker.addOnNegativeButtonClickListener {
            Log.d("TimeLOg","Negative")
        }
        picker.addOnCancelListener {
            Log.d("TimeLOg","Cancel")
        }
        picker.addOnDismissListener {
            Log.d("TimeLOg","Dismiss")
        }
    }




}
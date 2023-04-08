package com.example.myalarmapp.ui.alarm

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
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.MaterialTimePicker.INPUT_MODE_CLOCK
import com.google.android.material.timepicker.TimeFormat

class AlarmFragment:Fragment(R.layout.fragment_alarm) {

    private lateinit var binding: FragmentAlarmBinding
    private var adapter = AlarmAdapter()
    private lateinit var dao: AppDao
    private var isSelectedDay1:Boolean = false
    private var isSelectedDay2:Boolean = false
    private var isSelectedDay3:Boolean = false

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

    private fun initListeners(){

        binding.btnAddAlarm.setOnClickListener {
            openTimePicker()
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

        adapter.setOnClickDaysOfWeek1 { rcItemAlarmBinding, alarmData,list,->

            val day = list[0]
            if (day.isChecked){
                rcItemAlarmBinding.tvWednesday.setBackgroundResource(R.drawable.ripple_effect_daysofweek)
                rcItemAlarmBinding.tvWednesday.setTextColor(resources.getColor(R.color.colorTextViewDaysDefault))
                day.isChecked = false
            }else{
                rcItemAlarmBinding.tvWednesday.setBackgroundResource(R.drawable.ripple_effect_daysofweek_selected)
                rcItemAlarmBinding.tvWednesday.setTextColor(Color.parseColor("#ffffff"))
                day.isChecked = true
            }


//            when(day){
//                1-> {
//                    if (isSelectedDay1){
//                        rcItemAlarmBinding.tvMonday.setBackgroundResource(R.drawable.ripple_effect_daysofweek)
//                        rcItemAlarmBinding.tvMonday.setTextColor(resources.getColor(R.color.colorTextViewDaysDefault))
//                        isSelectedDay1 = false
//                    }else{
//                        rcItemAlarmBinding.tvMonday.setBackgroundResource(R.drawable.ripple_effect_daysofweek_selected)
//                        rcItemAlarmBinding.tvMonday.setTextColor(Color.parseColor("#ffffff"))
//                        isSelectedDay1 = true
//                    }
//                }
//                2->{
//
//                    if (isSelectedDay2){
//                        rcItemAlarmBinding.tvTuesday.setBackgroundResource(R.drawable.ripple_effect_daysofweek)
//                        rcItemAlarmBinding.tvTuesday.setTextColor(resources.getColor(R.color.colorTextViewDaysDefault))
//                        isSelectedDay2 = false
//                    }else{
//                        rcItemAlarmBinding.tvTuesday.setBackgroundResource(R.drawable.ripple_effect_daysofweek_selected)
//                        rcItemAlarmBinding.tvTuesday.setTextColor(Color.parseColor("#ffffff"))
//                        isSelectedDay2 = true
//                    }
//                }
//            }
        }

        adapter.setOnClickDaysOfWeek2 { rcItemAlarmBinding, alarmData,list ->

            var day = list[1]
            if (day.isChecked){
                rcItemAlarmBinding.tvTuesday.setBackgroundResource(R.drawable.ripple_effect_daysofweek)
                rcItemAlarmBinding.tvTuesday.setTextColor(resources.getColor(R.color.colorTextViewDaysDefault))
                day.isChecked = false
            }else{
                rcItemAlarmBinding.tvTuesday.setBackgroundResource(R.drawable.ripple_effect_daysofweek_selected)
                rcItemAlarmBinding.tvTuesday.setTextColor(Color.parseColor("#ffffff"))
                day.isChecked = true
            }

//            if (isSelectedDay2){
//                rcItemAlarmBinding.tvTuesday.setBackgroundResource(R.drawable.ripple_effect_daysofweek)
//                rcItemAlarmBinding.tvTuesday.setTextColor(resources.getColor(R.color.colorTextViewDaysDefault))
//                isSelectedDay2 = false
//            }else{
//                rcItemAlarmBinding.tvTuesday.setBackgroundResource(R.drawable.ripple_effect_daysofweek_selected)
//                rcItemAlarmBinding.tvTuesday.setTextColor(Color.parseColor("#ffffff"))
//                isSelectedDay2 = true
//            }
        }

        adapter.setOnClickDaysOfWeek3 { rcItemAlarmBinding, alarmData ->
            if (isSelectedDay3){
                rcItemAlarmBinding.tvWednesday.setBackgroundResource(R.drawable.ripple_effect_daysofweek)
                rcItemAlarmBinding.tvWednesday.setTextColor(resources.getColor(R.color.colorTextViewDaysDefault))
                isSelectedDay3 = false
            }else{
                rcItemAlarmBinding.tvWednesday.setBackgroundResource(R.drawable.ripple_effect_daysofweek_selected)
                rcItemAlarmBinding.tvWednesday.setTextColor(Color.parseColor("#ffffff"))
                isSelectedDay3 = true
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
                dao.addAlarm(AlarmData(0,hour.toInt(),minute.toInt(),true,2))
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




}
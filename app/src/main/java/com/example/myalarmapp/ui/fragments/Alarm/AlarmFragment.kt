package com.example.myalarmapp.ui.fragments.Alarm

import android.icu.util.Calendar
import android.os.Bundle
import android.text.format.DateFormat
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.myalarmapp.R
import com.example.myalarmapp.data.locale.AppDatabase
import com.example.myalarmapp.data.locale.dao.AppDao
import com.example.myalarmapp.data.locale.models.AlarmData
import com.example.myalarmapp.databinding.FragmentAlarmBinding
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat

class AlarmFragment:Fragment(R.layout.fragment_alarm) {

    private lateinit var binding: FragmentAlarmBinding
    private var adapter = AlarmRv()
    private lateinit var dao: AppDao

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAlarmBinding.bind(view)
        dao = AppDatabase.getInstance(requireContext()).getDao()

        initVariable()
        initListeners()

    }

    override fun onResume() {
        super.onResume()
        initVariable()
        initListeners()
    }

    private fun initVariable(){
        binding.rvAlarmFragment.adapter = adapter

        lifecycleScope.launchWhenResumed {
            adapter.list = dao.getAllAlarms()
        }
    }

    private fun initListeners(){
        binding.btnAdd.setOnClickListener {
            openTimePicker()
        }

//        binding.rvAlarmFragment.addOnScrollListener(object : RecyclerView.OnScrollListener(){
//            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//                super.onScrolled(recyclerView, dx, dy)
//                val layoutManager = binding.rvAlarmFragment.layoutManager!! as LinearLayoutManager
//                if (layoutManager.findFirstCompletelyVisibleItemPosition()>0){
//                    binding.toolbar.alpha = 1F
//                    val window = requireActivity().window
//                    window.statusBarColor = ContextCompat.getColor(requireContext(),R.color.statusBarColor)
//                }else{
//                    binding.toolbar.alpha = 0F
//                    val window = requireActivity().window
//                    window.statusBarColor = ContextCompat.getColor(requireContext(),R.color.bg_fragments)
//                }
//            }
//        })
    }

    private fun openTimePicker() {
        val isSystem24Hour = DateFormat.is24HourFormat(requireContext())
        val clockFormat = if (isSystem24Hour) TimeFormat.CLOCK_24H else TimeFormat.CLOCK_12H
        val calendar = Calendar.getInstance()

        val picker = MaterialTimePicker.Builder()
            .setTimeFormat(clockFormat)
            .setHour(calendar.get(Calendar.HOUR_OF_DAY))
            .setMinute(calendar.get(Calendar.MINUTE))
            .setTitleText("Выбор времени")
            .build()
        picker.show(parentFragmentManager,"TAG")


        picker.addOnPositiveButtonClickListener {
            val hour = picker.hour
            val minute = picker.minute

            lifecycleScope.launchWhenResumed {
                dao.addAlarm(AlarmData(0,hour,minute,true,2))
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
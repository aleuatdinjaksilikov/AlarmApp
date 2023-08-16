package com.example.myalarmapp.ui.alarm

import android.annotation.SuppressLint
import android.app.*
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.icu.util.Calendar
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.text.format.DateFormat
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.util.dropFtsSyncTriggers
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.myalarmapp.MainActivity
import com.example.myalarmapp.R
import com.example.myalarmapp.data.AppDatabase
import com.example.myalarmapp.data.dao.AppDao
import com.example.myalarmapp.data.models.AlarmData
import com.example.myalarmapp.databinding.FragmentAlarmBinding
import com.example.myalarmapp.databinding.LayoutDialogLabelBinding
import com.example.myalarmapp.databinding.RcItemAlarmBinding
import com.example.myalarmapp.ui.alarm.adapter.AlarmAdapter
import com.example.myalarmapp.utils.receivers.AlarmReceiver
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.MaterialTimePicker.INPUT_MODE_CLOCK
import com.google.android.material.timepicker.TimeFormat
import java.text.SimpleDateFormat
import kotlin.math.min


class AlarmFragment : Fragment(R.layout.fragment_alarm) {

    private lateinit var binding: FragmentAlarmBinding
    private var adapter = AlarmAdapter()
    private lateinit var dao: AppDao
    private var vibration = false
    private lateinit var simpleDateFormat: SimpleDateFormat
    private lateinit var picker: MaterialTimePicker

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAlarmBinding.bind(view)
        dao = AppDatabase.getInstance(requireContext()).getDao()
        picker = MaterialTimePicker()

        initVariable()
        initListeners()
    }

    private fun initVariable() {

        binding.rvAlarmFragment.adapter = adapter

        lifecycleScope.launchWhenResumed {
            adapter.submitList(dao.getAllAlarms().toMutableList())
        }

        simpleDateFormat = SimpleDateFormat()

        createNotificationChannel()
    }


    @SuppressLint("SetTextI18n")
    private fun initListeners() {


        binding.btnAddAlarm.setOnClickListener {
            openTimePickerTvClock(isUpdate = false)
        }

//        adapter.setOnClickSwitch { it, boolean ->
//
//
//
//            lifecycleScope.launchWhenResumed {
//                dao.updateAlarm(it)
//            }
//
//            if (boolean) {
//                settingAlarm(true)
//            } else {
//                settingAlarm(false)
//            }
//        }

        adapter.setOnClickSetAnAlarm {
            openDatePicker(it.timeHour, it.timeMinute)
        }

        adapter.setOnClickTvClock { alarmData, binding ->
            openTimePickerTvClock(alarmData, binding)
        }

        adapter.setOnDeleteItemListener { binding, alarmData, position ->
            val list = adapter.currentList.toMutableList()
            lifecycleScope.launchWhenResumed {
                dao.deleteAlarm(alarmData)
                list.removeAt(position)
                adapter.submitList(list)
            }
        }

        adapter.setOnClickDaysOfWeek { alarmData ->
            lifecycleScope.launchWhenResumed {
                dao.updateAlarm(alarmData)
            }
        }
        adapter.setOnClickVibration {
            if (vibration) {
                vibration = false
            } else {
                vibratePhone(200)
                vibration = true
            }
        }

        adapter.setOnClickLabelLayout { binding, alarmData ->
            val dialogLayout = LayoutDialogLabelBinding.inflate(layoutInflater)
            val etDialog = dialogLayout.etDialogLabel

            etDialog.setText(alarmData.textLabel)
            etDialog.setTextColor(Color.parseColor("#ffffff"))

            val dialog =
                AlertDialog.Builder(requireContext(), R.style.DialogThemeTransparent).apply {
                    setView(dialogLayout.root)
                    setCancelable(false)

                    setPositiveButton("OK") { dialogInterface, i ->
                        if (etDialog.text.toString().isNotEmpty()) {
                            binding.tvLabel.text = etDialog.text
                            alarmData.textLabel = etDialog.text.toString()
                            lifecycleScope.launchWhenResumed {
                                dao.updateAlarm(alarmData)
                            }
                        }
                    }
                    setNegativeButton("Отмена") { dialogInterface, i ->
                        dialogInterface.dismiss()
                    }
                }.create()

            dialogLayout.etDialogLabel.requestFocus()
            dialog.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

            dialog.show()

        }

        binding.rvAlarmFragment.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = binding.rvAlarmFragment.layoutManager!! as LinearLayoutManager
                if (layoutManager.findFirstCompletelyVisibleItemPosition() > 0) {
                    binding.toolbar.alpha = 1F
                    val window = requireActivity().window
                    window.statusBarColor =
                        ContextCompat.getColor(requireContext(), R.color.statusBarColor)
                } else {
                    binding.toolbar.alpha = 0F
                    val window = requireActivity().window
                    window.statusBarColor =
                        ContextCompat.getColor(requireContext(), R.color.bg_fragments)
                }
            }
        })
    }


    private fun openTimePickerTvClock(
        alarmData: AlarmData? = null, binding: RcItemAlarmBinding? = null, isUpdate: Boolean = true
    ) {

        val isSystem24Hour = DateFormat.is24HourFormat(requireContext())
        val clockFormat = if (isSystem24Hour) TimeFormat.CLOCK_24H else TimeFormat.CLOCK_12H

        val calendar = Calendar.getInstance()

        picker = MaterialTimePicker.Builder().setTimeFormat(clockFormat)
            .setHour(calendar.get(Calendar.HOUR_OF_DAY)).setMinute(calendar.get(Calendar.MINUTE))
            .setInputMode(INPUT_MODE_CLOCK).setTheme(R.style.MyThemeTimePicker)
            .setTitleText("Выбор времени").build()
        picker.show(parentFragmentManager, "TAG")


        picker.addOnPositiveButtonClickListener {

            var hour = picker.hour.toString()
            var minute = picker.minute.toString()

            if (minute.length < 2) {
                minute = "0${minute}"
            }
            if (hour.length < 2) {
                hour = "0${hour}"
            }

            val calendar = Calendar.getInstance()
            calendar.set(Calendar.HOUR_OF_DAY, picker.hour)
            calendar.set(Calendar.MINUTE, picker.minute)
            calendar.set(Calendar.SECOND, 0)
            calendar.set(Calendar.MILLISECOND, 0)

            val alarmManager =
                requireActivity().getSystemService(Context.ALARM_SERVICE) as AlarmManager

            val intent = Intent(requireContext(), AlarmReceiver::class.java).apply {
                putExtra("Hour",hour)
                putExtra("Time",minute)
            }

            val pendingIntent =
                PendingIntent.getBroadcast(requireContext(), 0, intent, PendingIntent.FLAG_MUTABLE)

            alarmManager.setAlarmClock(
                AlarmManager.AlarmClockInfo(
                    calendar.timeInMillis,
                    pendingIntent
                ), pendingIntent
            )


            Toast.makeText(
                requireContext(),
                "Будильник установлен на ${simpleDateFormat.format(calendar.time)}",
                Toast.LENGTH_SHORT
            ).show()


            lifecycleScope.launchWhenResumed {

                if (isUpdate) {
                    alarmData?.let {
                        it.timeHour = hour
                        it.timeMinute = minute
                        binding?.tvClock?.text = "${hour}:${minute}"
                        dao.updateAlarm(it)
                    }
                } else {
                    dao.addAlarm(
                        AlarmData(
                            0,
                            hour,
                            minute,
                            true,
                            "",
                            isMondayActivated = false,
                            isTuesdayActivated = false,
                            isWednesdayActivated = false,
                            isThursdayActivated = false,
                            isFridayActivated = false,
                            isSaturdayActivated = false,
                            isSundayActivated = false,
                            textLabel = ""
                        )
                    )
                    adapter.submitList(dao.getAllAlarms().toMutableList())
                }
            }

        }
        picker.addOnNegativeButtonClickListener {
            Log.d("TimeLOg", "Negative")
        }
        picker.addOnCancelListener {
            Log.d("TimeLOg", "Cancel")
        }
        picker.addOnDismissListener {
            Log.d("TimeLOg", "Dismiss")
        }
    }

    private fun settingAlarm(boolean:Boolean) {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, picker.hour)
        calendar.set(Calendar.MINUTE, picker.minute)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)

        val alarmManager =
            requireActivity().getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val intent = Intent(requireContext(), AlarmReceiver::class.java)

        val pendingIntent =
            PendingIntent.getBroadcast(requireContext(), 0, intent, PendingIntent.FLAG_MUTABLE)

        if (boolean) {

            alarmManager.setAlarmClock(
                AlarmManager.AlarmClockInfo(
                    calendar.timeInMillis,
                    pendingIntent
                ), pendingIntent
            )

            Toast.makeText(
                requireActivity(),
                "Будильник установлен на ${simpleDateFormat.format(calendar.time)}",
                Toast.LENGTH_SHORT
            ).show()

        } else {

            alarmManager.cancel(pendingIntent)

            Toast.makeText(
                requireActivity(),
                "Будильник отменен",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun vibratePhone(time: Long) {
        val vibrator =
            (requireActivity() as MainActivity).getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        if (vibrator.hasVibrator()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(
                    VibrationEffect.createOneShot(
                        time, VibrationEffect.DEFAULT_AMPLITUDE
                    )
                )
            } else {
                vibrator.vibrate(time)
            }
        }
    }

    private fun openDatePicker(timeH: String, timeM: String) {
        val datePicker = MaterialDatePicker.Builder.datePicker().setTitleText(
            "Выберите дату для будильника" + "на ${timeH}:${timeM}"
        )
//                .setTheme(R.style.MyThemeDatePicker)
//                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
            .build()

        datePicker.addOnPositiveButtonClickListener {

        }
        datePicker.addOnNegativeButtonClickListener {
            datePicker.dismiss()
        }

        datePicker.show(parentFragmentManager, "DATEPICKER")
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "alarmChannel"
            val desc = "This is Alarm Channel"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val sound =
                Uri.parse("${ContentResolver.SCHEME_ANDROID_RESOURCE}://${requireContext().packageName}/${R.raw.rington_homecoming}")
            Notification.AUDIO_ATTRIBUTES_DEFAULT

            val channel = NotificationChannel("alarmChannel", name, importance).apply {
                description = desc
            }

            val notificationManager: NotificationManager =
                requireActivity().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }


}
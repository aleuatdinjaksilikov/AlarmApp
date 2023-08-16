package com.example.myalarmapp.ui.alarm.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myalarmapp.R
import com.example.myalarmapp.databinding.RcItemAlarmBinding
import com.example.myalarmapp.data.models.AlarmData

class AlarmAdapter : ListAdapter<AlarmData, AlarmAdapter.MyViewHolder>(diffUtil) {

    private var onClickSwitchListener: ((RcItemAlarmBinding, AlarmData) -> Unit)? = null
    private var onClickDeleteItemListener:
            ((RcItemAlarmBinding, AlarmData, position: Int) -> Unit)? = null
    private var onClickDaysOfWeekListener: ((AlarmData) -> Unit)? = null
    private var onClickTvClockListener: ((AlarmData, RcItemAlarmBinding) -> Unit)? = null
    private var onClickLabelLayout: ((RcItemAlarmBinding, AlarmData) -> Unit)? = null
    private var onClickVibration: ((RcItemAlarmBinding)->Unit)? = null
    private var onClickSetAnAlarm:((AlarmData)->Unit)? = null
    private var onClickSwitch:((AlarmData,boolean:Boolean)->Unit)? = null








    inner class MyViewHolder(private val binding: RcItemAlarmBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun setData(position: Int) {


            val alarmData = getItem(position)

            binding.tvClock.text = "${alarmData.timeHour}:${alarmData.timeMinute}"
            daysToTextView(alarmData, binding)
            if (alarmData.textLabel == "") {
                binding.tvLabel.text = "Добавить ярлык"
            } else {
                binding.tvLabel.text = alarmData.textLabel
            }

            binding.tvMonday.isSelected = alarmData.isMondayActivated
            binding.tvTuesday.isSelected = alarmData.isTuesdayActivated
            binding.tvWednesday.isSelected = alarmData.isWednesdayActivated
            binding.tvThursday.isSelected = alarmData.isThursdayActivated
            binding.tvFriday.isSelected = alarmData.isFridayActivated
            binding.tvSaturday.isSelected = alarmData.isSaturdayActivated
            binding.tvSunday.isSelected = alarmData.isSundayActivated



            if (alarmData.installed) {
                binding.tvClock.setTextColor(Color.parseColor("#FFFFFF"))
                binding.switchAlarmFragment.isChecked = true
            } else {
                binding.tvClock.setTextColor(Color.parseColor("#8F8F8F"))
                binding.switchAlarmFragment.isChecked = false
            }



//            binding.switchAlarmFragment.setOnCheckedChangeListener { compoundButton, isChecked ->
//
//                if (alarmData.installed){
//                    alarmData.installed = true
//                    binding.tvClock.setTextColor(Color.parseColor("#FFFFFF"))
//                    onClickSwitch?.invoke(alarmData,true)
//                }else{
//                    alarmData.installed = false
//                    binding.tvClock.setTextColor(Color.parseColor("#8F8F8F"))
//                    onClickSwitch?.invoke(alarmData,false)
//                }

//                if (alarmData.installed) {
//                    binding.tvClock.setTextColor(Color.parseColor("#FFFFFF"))
//                    binding.switchAlarmFragment.isChecked = true
//                } else {
//                    binding.tvClock.setTextColor(Color.parseColor("#8F8F8F"))
//                    binding.switchAlarmFragment.isChecked = false
//                }
//
//                if (binding.switchAlarmFragment.isChecked){
//                    alarmData.installed = false
//                    onClickSwitch?.invoke(alarmData,false)
//                }else{
//                    alarmData.installed = true
//                    onClickSwitch?.invoke(alarmData,true)
//                }
//            }


            binding.tvClock.setOnClickListener {
                onClickTvClockListener?.invoke(alarmData, binding)
            }

            binding.btnExpandableOC.setOnClickListener {
                if (binding.expandableLayout.isExpanded) {
                    binding.expandableLayout.collapse()
                    binding.expandableLayoutAddLabel.collapse()
                    binding.ivBtnExpandableLayout.setImageResource(R.drawable.downarrow)
                } else {
                    binding.expandableLayout.expand()
                    binding.expandableLayoutAddLabel.expand()
                    binding.ivBtnExpandableLayout.setImageResource(R.drawable.ic_up_arrow)
                }
            }

            binding.root.setOnClickListener {
                if (binding.expandableLayout.isExpanded) {
                    binding.expandableLayout.collapse()
                    binding.expandableLayoutAddLabel.collapse()
                    binding.ivBtnExpandableLayout.setImageResource(R.drawable.downarrow)
                } else {
                    binding.expandableLayout.expand()
                    binding.expandableLayoutAddLabel.expand()
                    binding.ivBtnExpandableLayout.setImageResource(R.drawable.ic_up_arrow)
                }
            }

            binding.tvMonday.setOnClickListener {
                binding.tvMonday.isSelected = alarmData.isMondayActivated.not()
                alarmData.isMondayActivated = alarmData.isMondayActivated.not()
                daysToTextView(alarmData, binding)
                onClickDaysOfWeekListener?.invoke(alarmData)

            }
            binding.tvTuesday.setOnClickListener {
                binding.tvTuesday.isSelected = alarmData.isTuesdayActivated.not()
                alarmData.isTuesdayActivated = alarmData.isTuesdayActivated.not()
                daysToTextView(alarmData, binding)
                onClickDaysOfWeekListener?.invoke(alarmData)
            }
            binding.tvWednesday.setOnClickListener {
                binding.tvWednesday.isSelected = alarmData.isWednesdayActivated.not()
                alarmData.isWednesdayActivated = alarmData.isWednesdayActivated.not()
                daysToTextView(alarmData, binding)
                onClickDaysOfWeekListener?.invoke(alarmData)
            }
            binding.tvThursday.setOnClickListener {
                binding.tvThursday.isSelected = alarmData.isThursdayActivated.not()
                alarmData.isThursdayActivated = alarmData.isThursdayActivated.not()
                daysToTextView(alarmData, binding)
                onClickDaysOfWeekListener?.invoke(alarmData)
            }
            binding.tvFriday.setOnClickListener {
                binding.tvFriday.isSelected = alarmData.isFridayActivated.not()
                alarmData.isFridayActivated = alarmData.isFridayActivated.not()
                daysToTextView(alarmData, binding)
                onClickDaysOfWeekListener?.invoke(alarmData)
            }
            binding.tvSaturday.setOnClickListener {
                binding.tvSaturday.isSelected = alarmData.isSaturdayActivated.not()
                alarmData.isSaturdayActivated = alarmData.isSaturdayActivated.not()
                daysToTextView(alarmData, binding)
                onClickDaysOfWeekListener?.invoke(alarmData)
            }
            binding.tvSunday.setOnClickListener {
                binding.tvSunday.isSelected = alarmData.isSundayActivated.not()
                alarmData.isSundayActivated = alarmData.isSundayActivated.not()
                daysToTextView(alarmData, binding)
                onClickDaysOfWeekListener?.invoke(alarmData)
            }

//            binding.switchAlarmFragment.setOnClickListener {
//                onClickSwitchListener?.invoke(binding, alarmData)
//            }

            binding.tvDelete.setOnClickListener {
                onClickDeleteItemListener?.invoke(binding, alarmData, position)
            }

            binding.expandableLayoutAddLabel.setOnClickListener {
                onClickLabelLayout?.invoke(binding, alarmData)
            }

            binding.tvVibration.setOnClickListener {
                onClickVibration?.invoke(binding)
            }

            binding.tvSetAnAlarm.setOnClickListener {
                onClickSetAnAlarm?.invoke(alarmData)
            }



        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            RcItemAlarmBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.setData(position)
    }


    fun setOnSwitchClickListener(block: (RcItemAlarmBinding, AlarmData) -> Unit) {
        onClickSwitchListener = block
    }

    fun setOnDeleteItemListener(block: (RcItemAlarmBinding, AlarmData, position: Int) -> Unit) {
        onClickDeleteItemListener = block
    }

    fun setOnClickDaysOfWeek(block: (AlarmData) -> Unit) {
        onClickDaysOfWeekListener = block
    }

    fun setOnClickTvClock(block: (AlarmData, RcItemAlarmBinding) -> Unit) {
        onClickTvClockListener = block
    }

    fun setOnClickLabelLayout(block: (RcItemAlarmBinding, AlarmData) -> Unit) {
        onClickLabelLayout = block
    }

    fun setOnClickVibration(block: (RcItemAlarmBinding) -> Unit){
        onClickVibration = block
    }

    fun setOnClickSetAnAlarm(block:(AlarmData)->Unit){
        onClickSetAnAlarm = block
    }

    fun setOnClickSwitch(block: (AlarmData,boolean:Boolean) -> Unit){
        onClickSwitch = block
    }


    private fun daysToTextView(alarmData: AlarmData, binding: RcItemAlarmBinding) {
        val isActivatedDaysList = booleanArrayOf(
            alarmData.isMondayActivated,
            alarmData.isTuesdayActivated,
            alarmData.isWednesdayActivated,
            alarmData.isThursdayActivated,
            alarmData.isFridayActivated,
            alarmData.isSaturdayActivated,
            alarmData.isSundayActivated
        )
        val weekDays = listOf(
            "Понедельник",
            "Вторник",
            "Среда",
            "Четверг",
            "Пятница",
            "Суббота",
            "Воскресенье"
        )
        val shortWeekDays = listOf("пн", "вт", "ср", "чт", "пт", "сб", "вс")
        var oneDay = ""
        val selectedDays = mutableListOf<String>()
        for (i in isActivatedDaysList.indices) {
            if (isActivatedDaysList[i]) {
                selectedDays.add(shortWeekDays[i])
                oneDay = weekDays[i]
            }
        }
        if (selectedDays.size == 7) {
            binding.tvSetTime.text = "Каждый день"
        } else if (selectedDays.size == 1) {
            binding.tvSetTime.text = oneDay
        } else if (selectedDays.isEmpty()) {
            binding.tvSetTime.text = "Не установлен"
        } else {
            val lastIndex = selectedDays.toString().length - 1
            val daySubstring = selectedDays.toString().substring(1, 2)
                .uppercase() + selectedDays.toString().substring(2, lastIndex)
            binding.tvSetTime.text = daySubstring
        }
    }


    private object diffUtil : DiffUtil.ItemCallback<AlarmData>() {
        override fun areItemsTheSame(oldItem: AlarmData, newItem: AlarmData): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: AlarmData, newItem: AlarmData): Boolean {
            return oldItem.id == newItem.id &&
                    oldItem.timeHour == newItem.timeHour &&
                    oldItem.timeMinute == newItem.timeMinute &&
                    oldItem.installed == newItem.installed &&
                    oldItem.textDaysSelected == newItem.textDaysSelected &&
                    oldItem.textLabel == newItem.textLabel

        }

    }


}
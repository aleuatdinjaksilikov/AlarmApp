package com.example.myalarmapp.ui.alarm

import android.annotation.SuppressLint
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myalarmapp.R
import com.example.myalarmapp.databinding.RcItemAlarmBinding
import com.example.myalarmapp.data.models.AlarmData

class AlarmAdapter:RecyclerView.Adapter<AlarmAdapter.MyViewHolder>() {

    private var onClickSwitchListener : ((RcItemAlarmBinding, AlarmData)->Unit)? = null
    private var onClickDeleteItemListener : ((RcItemAlarmBinding, AlarmData, position:Int)->Unit)? = null
    private var onClickDaysOfWeekListener : ((AlarmData)->Unit)? = null
    private var onClickTvClockListener : ((AlarmData,RcItemAlarmBinding)->Unit)? = null


    var list = mutableListOf<AlarmData>()
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    inner class MyViewHolder(private val binding: RcItemAlarmBinding):RecyclerView.ViewHolder(binding.root){
        @SuppressLint("SetTextI18n")
        fun setData(alarmData: AlarmData, position: Int){

            binding.tvClock.text = "${alarmData.timeHour}:${alarmData.timeMinute}"
            binding.tvSetTime.text = isDayClicked(alarmData)

            binding.tvMonday.isSelected = alarmData.isMondayActivated
            binding.tvTuesday.isSelected = alarmData.isTuesdayActivated
            binding.tvWednesday.isSelected = alarmData.isWednesdayActivated
            binding.tvThursday.isSelected = alarmData.isThursdayActivated
            binding.tvFriday.isSelected = alarmData.isFridayActivated
            binding.tvSaturday.isSelected = alarmData.isSaturdayActivated
            binding.tvSunday.isSelected = alarmData.isSundayActivated


            if(alarmData.installed){
                binding.tvClock.setTextColor(Color.parseColor("#FFFFFF"))
                binding.switchAlarmFragment.isChecked = true
            }else{
                binding.tvClock.setTextColor(Color.parseColor("#8F8F8F"))
                binding.switchAlarmFragment.isChecked = false
            }


            binding.tvClock.setOnClickListener {
                onClickTvClockListener?.invoke(alarmData,binding)
            }

            binding.btnExpandableOC.setOnClickListener {
                if (binding.expandableLayout.isExpanded){
                    binding.expandableLayout.collapse()
                    binding.expandableLayoutAddLabel.collapse()
                    binding.ivBtnExpandableLayout.setImageResource(R.drawable.downarrow)
                }else{
                    binding.expandableLayout.expand()
                    binding.expandableLayoutAddLabel.expand()
                    binding.ivBtnExpandableLayout.setImageResource(R.drawable.ic_up_arrow)
                }
            }

            binding.root.setOnClickListener {
                if (binding.expandableLayout.isExpanded){
                    binding.expandableLayout.collapse()
                    binding.expandableLayoutAddLabel.collapse()
                    binding.ivBtnExpandableLayout.setImageResource(R.drawable.downarrow)
                }else{
                    binding.expandableLayout.expand()
                    binding.expandableLayoutAddLabel.expand()
                    binding.ivBtnExpandableLayout.setImageResource(R.drawable.ic_up_arrow)
                }
            }

            binding.tvMonday.setOnClickListener {
                binding.tvMonday.isSelected = alarmData.isMondayActivated.not()
                alarmData.isMondayActivated = alarmData.isMondayActivated.not()
                binding.tvSetTime.text = isDayClicked(alarmData)
                alarmData.textDaysSelected = isDayClicked(alarmData)
                onClickDaysOfWeekListener?.invoke(alarmData)

            }
            binding.tvTuesday.setOnClickListener {
                binding.tvTuesday.isSelected = alarmData.isTuesdayActivated.not()
                alarmData.isTuesdayActivated = alarmData.isTuesdayActivated.not()
                binding.tvSetTime.text = isDayClicked(alarmData)
                alarmData.textDaysSelected = isDayClicked(alarmData)
                onClickDaysOfWeekListener?.invoke(alarmData)
            }
            binding.tvWednesday.setOnClickListener {
                binding.tvWednesday.isSelected = alarmData.isWednesdayActivated.not()
                alarmData.isWednesdayActivated = alarmData.isWednesdayActivated.not()
                binding.tvSetTime.text = isDayClicked(alarmData)
                alarmData.textDaysSelected = isDayClicked(alarmData)
                onClickDaysOfWeekListener?.invoke(alarmData)
            }
            binding.tvThursday.setOnClickListener {
                binding.tvThursday.isSelected = alarmData.isThursdayActivated.not()
                alarmData.isThursdayActivated = alarmData.isThursdayActivated.not()
                binding.tvSetTime.text = isDayClicked(alarmData)
                alarmData.textDaysSelected = isDayClicked(alarmData)
                onClickDaysOfWeekListener?.invoke(alarmData)
            }
            binding.tvFriday.setOnClickListener {
                binding.tvFriday.isSelected = alarmData.isFridayActivated.not()
                alarmData.isFridayActivated = alarmData.isFridayActivated.not()
                binding.tvSetTime.text = isDayClicked(alarmData)
                alarmData.textDaysSelected = isDayClicked(alarmData)
                onClickDaysOfWeekListener?.invoke(alarmData)
            }
            binding.tvSaturday.setOnClickListener {
                binding.tvSaturday.isSelected = alarmData.isSaturdayActivated.not()
                alarmData.isSaturdayActivated = alarmData.isSaturdayActivated.not()
                binding.tvSetTime.text = isDayClicked(alarmData)
                alarmData.textDaysSelected = isDayClicked(alarmData)
                onClickDaysOfWeekListener?.invoke(alarmData)
            }
            binding.tvSunday.setOnClickListener {
                binding.tvSunday.isSelected = alarmData.isSundayActivated.not()
                alarmData.isSundayActivated = alarmData.isSundayActivated.not()
                binding.tvSetTime.text = isDayClicked(alarmData)
                alarmData.textDaysSelected = isDayClicked(alarmData)
                onClickDaysOfWeekListener?.invoke(alarmData)
            }

            binding.switchAlarmFragment.setOnClickListener {
                onClickSwitchListener?.invoke(binding,alarmData)
            }

            binding.tvDelete.setOnClickListener {
                onClickDeleteItemListener?.invoke(binding,alarmData,position)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(RcItemAlarmBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.setData(list[position],position)
    }

    override fun getItemCount(): Int = list.size


    fun setOnSwitchClickListener(block:(RcItemAlarmBinding, AlarmData)->Unit){
        onClickSwitchListener = block
    }

    fun setOnDeleteItemListener(block:(RcItemAlarmBinding, AlarmData, position:Int)->Unit){
        onClickDeleteItemListener = block
    }

    fun setOnClickDaysOfWeek(block:(AlarmData)->Unit){
        onClickDaysOfWeekListener = block
    }

    fun setOnClickTvClock(block:(AlarmData,RcItemAlarmBinding)->Unit){
        onClickTvClockListener = block
    }

    private fun isDayClicked(alarmData: AlarmData):String{

        val listDayText = mutableListOf<String>()

        if (alarmData.isMondayActivated) listDayText.add("Пн") else listDayText.remove("Пн")
        if (alarmData.isTuesdayActivated) listDayText.add("Вт") else listDayText.remove("Вт")
        if (alarmData.isWednesdayActivated) listDayText.add("Ср") else listDayText.remove("Ср")
        if (alarmData.isThursdayActivated) listDayText.add("Чт") else listDayText.remove("Чт")
        if (alarmData.isFridayActivated) listDayText.add("Пт") else listDayText.remove("Пт")
        if (alarmData.isSaturdayActivated) listDayText.add("Сб") else listDayText.remove("Сб")
        if (alarmData.isSundayActivated) listDayText.add("Вс") else listDayText.remove("Вс")

        if (listDayText.isEmpty()){
            return "Не установлено"
        }else if (listDayText.size == 7){
            return "Каждый день"
        }



        var res = ""
        listDayText.forEach {
            if (listDayText.indexOf(it)==listDayText.lastIndex) {
                res+=it
            }else{
                res+="$it,"
            }
        }

        Log.d("ListEms",res)

        return res
    }


}
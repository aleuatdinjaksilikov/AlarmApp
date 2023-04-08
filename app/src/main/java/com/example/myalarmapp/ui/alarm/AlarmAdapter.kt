package com.example.myalarmapp.ui.alarm

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myalarmapp.R
import com.example.myalarmapp.databinding.RcItemAlarmBinding
import com.example.myalarmapp.data.models.AlarmData
import com.example.myalarmapp.data.models.DaysData

class AlarmAdapter:RecyclerView.Adapter<AlarmAdapter.MyViewHolder>() {
    private var onClickSwitchListener : ((RcItemAlarmBinding, AlarmData)->Unit)? = null
    private var onClickDeleteItemListener : ((RcItemAlarmBinding, AlarmData, position:Int)->Unit)? = null

    private var onClickDaysOfWeekListener1 : ((RcItemAlarmBinding, AlarmData, MutableList<DaysData>)->Unit)? = null
    private var onClickDaysOfWeekListener2 : ((RcItemAlarmBinding, AlarmData, MutableList<DaysData>)->Unit)? = null
    private var onClickDaysOfWeekListener3 : ((RcItemAlarmBinding, AlarmData)->Unit)? = null

    var daysDataList = mutableListOf<DaysData>()

    var list = mutableListOf<AlarmData>()
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    inner class MyViewHolder(private val binding: RcItemAlarmBinding):RecyclerView.ViewHolder(binding.root){
        fun setData(alarmData: AlarmData, position: Int){

            binding.tvClock.text = "${alarmData.timeHour}:${alarmData.timeMinute}"
            Log.d("TimeTime","${alarmData.timeHour}:${alarmData.timeMinute}")

            daysDataList.add(DaysData(binding.tvMonday,false))
            daysDataList.add(DaysData(binding.tvTuesday,false))
            daysDataList.add(DaysData(binding.tvWednesday,false))
            daysDataList.add(DaysData(binding.tvThursday,false))
            daysDataList.add(DaysData(binding.tvFriday,false))
            daysDataList.add(DaysData(binding.tvSaturday,false))
            daysDataList.add(DaysData(binding.tvSunday,false))


            if(alarmData.installed){
                binding.tvClock.setTextColor(Color.parseColor("#FFFFFF"))
                binding.switchAlarmFragment.isChecked = true
            }else{
                binding.tvClock.setTextColor(Color.parseColor("#8F8F8F"))
                binding.switchAlarmFragment.isChecked = false
            }


            binding.btnExpandableOC.setOnClickListener {
                if (binding.expandableLayout.isExpanded){
                    binding.expandableLayout.collapse()
                    binding.ivBtnExpandableLayout.setImageResource(R.drawable.downarrow)
                }else{
                    binding.expandableLayout.expand()
                    binding.ivBtnExpandableLayout.setImageResource(R.drawable.ic_up_arrow)
                }
            }

            binding.root.setOnClickListener {
                if (binding.expandableLayout.isExpanded){
                    binding.expandableLayout.collapse()
                    binding.ivBtnExpandableLayout.setImageResource(R.drawable.downarrow)
                }else{
                    binding.expandableLayout.expand()
                    binding.ivBtnExpandableLayout.setImageResource(R.drawable.ic_up_arrow)
                }
            }

            binding.tvMonday.setOnClickListener {
                onClickDaysOfWeekListener1?.invoke(binding,alarmData,daysDataList)
            }
            binding.tvTuesday.setOnClickListener {
                onClickDaysOfWeekListener2?.invoke(binding,alarmData,daysDataList)
            }


            binding.tvWednesday.setOnClickListener {
                onClickDaysOfWeekListener3?.invoke(binding,alarmData)
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

    fun setOnClickDaysOfWeek1(block:(RcItemAlarmBinding, AlarmData, MutableList<DaysData>)->Unit){
        onClickDaysOfWeekListener1 = block
    }
    fun setOnClickDaysOfWeek2(block:(RcItemAlarmBinding, AlarmData, MutableList<DaysData>)->Unit){
        onClickDaysOfWeekListener2 = block
    }
    fun setOnClickDaysOfWeek3(block:(RcItemAlarmBinding, AlarmData)->Unit){
        onClickDaysOfWeekListener3 = block
    }
}
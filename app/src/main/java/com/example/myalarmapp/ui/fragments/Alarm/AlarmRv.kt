package com.example.myalarmapp.ui.fragments.Alarm

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myalarmapp.databinding.RcItemAlarmBinding
import com.example.myalarmapp.data.locale.models.AlarmData

class AlarmRv:RecyclerView.Adapter<AlarmRv.MyViewHolder>() {

    var list = listOf<AlarmData>()
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    inner class MyViewHolder(private val binding: RcItemAlarmBinding):RecyclerView.ViewHolder(binding.root){
        fun setData(alarmData: AlarmData){

            binding.tvClock.text = "${alarmData.timeHour}:${alarmData.timeMinute}"


            binding.btnExpandableOC.setOnClickListener {
                if (binding.expandableLayout.isExpanded){
                    binding.expandableLayout.collapse()
                }else{
                    binding.expandableLayout.expand()
                }
            }

            binding.root.setOnClickListener {
                if (binding.expandableLayout.isExpanded){
                    binding.expandableLayout.collapse()
                }else{
                    binding.expandableLayout.expand()
                }
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(RcItemAlarmBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.setData(list[position])
    }

    override fun getItemCount(): Int = list.size
}
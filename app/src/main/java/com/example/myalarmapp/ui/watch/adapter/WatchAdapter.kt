package com.example.myalarmapp.ui.watch.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myalarmapp.data.models.WatchData
import com.example.myalarmapp.databinding.RcItemWatchBinding

class WatchAdapter:ListAdapter<WatchData,WatchAdapter.WatchViewHolder>(myDiffUtil) {



    inner class WatchViewHolder(private val binding: RcItemWatchBinding):RecyclerView.ViewHolder(binding.root){
        fun setData(position: Int){
            val watchData = getItem(position)
            binding.tvCityName.text = watchData.countryName
            binding.tvCityTime.timeZone = watchData.countryName
            binding.tvPastTime.text = watchData.differenceBetweenCountries
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WatchViewHolder {
        return WatchViewHolder(RcItemWatchBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: WatchViewHolder, position: Int) {
        holder.setData(position)
    }



    private object myDiffUtil: DiffUtil.ItemCallback<WatchData>(){
        override fun areItemsTheSame(oldItem: WatchData, newItem: WatchData): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: WatchData, newItem: WatchData): Boolean {
            return oldItem.countryName == newItem.countryName && oldItem.id==newItem.id &&
                    oldItem.differenceBetweenCountries==newItem.differenceBetweenCountries
        }

    }




}
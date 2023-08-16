package com.example.myalarmapp.ui.watch.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myalarmapp.data.models.SearchItemData
import com.example.myalarmapp.data.models.WatchData
import com.example.myalarmapp.databinding.RcItemSearchBinding

class SearchAdapter:ListAdapter<SearchItemData,SearchAdapter.SearchViewHolder>(myDiffUtil){

    private var onClickItemListener : ((RcItemSearchBinding,SearchItemData)->Unit)?=null

    fun setOnClickItemListener(block : (RcItemSearchBinding,SearchItemData)->Unit){
        onClickItemListener = block
    }

    inner class SearchViewHolder(private val binding:RcItemSearchBinding):RecyclerView.ViewHolder(binding.root){
        fun setData(position: Int){
            val searchBinding = getItem(position)
            binding.countriesName.text = searchBinding.countryName
            binding.textClockCountries.timeZone = searchBinding.countryName
            binding.root.setOnClickListener {
                onClickItemListener?.invoke(binding,searchBinding)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        return SearchViewHolder(RcItemSearchBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.setData(position)
    }

    private object myDiffUtil: DiffUtil.ItemCallback<SearchItemData>(){
        override fun areItemsTheSame(oldItem: SearchItemData, newItem: SearchItemData): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: SearchItemData, newItem: SearchItemData): Boolean {
            return oldItem.countryName == newItem.countryName && oldItem.id==newItem.id
        }

    }
}
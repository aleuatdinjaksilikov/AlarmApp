package com.example.myalarmapp.ui.fragments.Watch

import android.icu.util.Calendar
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.myalarmapp.R
import com.example.myalarmapp.databinding.FragmentWatchBinding
import java.text.SimpleDateFormat

class WatchFragment:Fragment(R.layout.fragment_watch) {
    private lateinit var binding: FragmentWatchBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentWatchBinding.bind(view)


        binding.tvCurrentdata.text = getFormattedCurrentData()


    }


    private fun getFormattedCurrentData():String{
        val dayOfWeek = when(Calendar.getInstance().get(Calendar.DAY_OF_WEEK)){
            Calendar.MONDAY -> "пн"
            Calendar.TUESDAY -> "вт"
            Calendar.WEDNESDAY -> "ср"
            Calendar.THURSDAY -> "чт"
            Calendar.FRIDAY -> "пт"
            Calendar.SATURDAY -> "сб"
            else -> "вс"
        }

        val monthOfYear = when(Calendar.getInstance().get(Calendar.MONTH)){
            Calendar.JANUARY -> "янв."
            Calendar.FEBRUARY -> "фев."
            Calendar.MARCH -> "мар."
            Calendar.APRIL -> "апр."
            Calendar.MAY -> "май"
            Calendar.JUNE -> "июнь"
            Calendar.JULY -> "июль"
            Calendar.AUGUST -> "авг."
            Calendar.SEPTEMBER -> "сен."
            Calendar.OCTOBER -> "окт."
            Calendar.NOVEMBER -> "ноябрь"
            else -> "дек."
        }

        val date = Calendar.getInstance().time
        val formatter = SimpleDateFormat("EEE, d MMM")
        return formatter.format(date)

    }
}
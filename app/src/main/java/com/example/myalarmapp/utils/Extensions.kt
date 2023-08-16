package com.example.myalarmapp.utils

import android.icu.util.TimeZone

fun String.getDifferenceInTimeZone(): String {
    val edtOffset =
        TimeZone.getTimeZone("Asia/Tashkent").getOffset(System.currentTimeMillis())
    val gmtOffset =
        TimeZone.getTimeZone(this).getOffset(System.currentTimeMillis())
    val hourDifference = (gmtOffset - edtOffset) / (1000 * 60 * 60)
    val minutes = ((gmtOffset - edtOffset ) / (60 * 1000))

    return if (hourDifference > 0) {
        "$hourDifference ч."
    } else if (hourDifference <=0) {
        "$minutes мин."
    }else{
        ""
    }


}
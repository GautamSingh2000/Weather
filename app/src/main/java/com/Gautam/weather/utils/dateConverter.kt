package com.Gautam.weather.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

fun DateConveter(timeStemp : Long):String {
    val sdf = SimpleDateFormat("EEE, MMM dd", Locale.getDefault()) // Includes day of the week
    val date = Date(timeStemp)
    return sdf.format(date)
}


fun timeStempToHour(timeStemp : Long):String {
    val sdf = SimpleDateFormat("hh:mm a", Locale.getDefault()) // Includes day of the week
    val date = Date(timeStemp)
    return sdf.format(date)
}
fun timeStempToDay(timeStemp : Long):String {
    val sdf = SimpleDateFormat("EEE", Locale.getDefault()) // Includes day of the week
    val date = Date(timeStemp)
    return sdf.format(date)
}
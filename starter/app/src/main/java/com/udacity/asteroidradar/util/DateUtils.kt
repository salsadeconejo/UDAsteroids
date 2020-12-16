package com.udacity.asteroidradar.util

import java.text.SimpleDateFormat
import java.util.*

fun getCurrentDay(): String {
    val calendar = Calendar.getInstance()
    val currentDay = calendar.time
    val dateFormat = SimpleDateFormat(Constants.API_QUERY_DATE_FORMAT, Locale.getDefault())
    return dateFormat.format(currentDay)
}

fun getYesterday(): String {
    val calendar = Calendar.getInstance()
    calendar.add(
        Calendar.DAY_OF_YEAR,
        -1
    )
    val currentDay = calendar.time
    val dateFormat = SimpleDateFormat(Constants.API_QUERY_DATE_FORMAT, Locale.getDefault())
    return dateFormat.format(currentDay)
}

fun sumDaysToCurrent(): String {
    val calendar = Calendar.getInstance()
    calendar.add(
        Calendar.DAY_OF_YEAR,
        Constants.DEFAULT_END_DATE_DAYS
    )
    val currentDay = calendar.time
    val dateFormat = SimpleDateFormat(Constants.API_QUERY_DATE_FORMAT, Locale.getDefault())
    return dateFormat.format(currentDay)
}

fun getNextSevenDays(): List<String> {
    val calendar = Calendar.getInstance()
    val listOfDays = mutableListOf<String>()
    listOfDays.add(getCurrentDay())
    for (i in 1..7) {
        calendar.add(
            Calendar.DAY_OF_YEAR,
            1
        )
        val currentDay = calendar.time
        val dateFormat = SimpleDateFormat(Constants.API_QUERY_DATE_FORMAT, Locale.getDefault())
        listOfDays.add(dateFormat.format(currentDay))
    }

    return listOfDays
}
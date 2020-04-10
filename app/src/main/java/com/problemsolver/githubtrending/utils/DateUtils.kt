package com.problemsolver.githubtrending.utils

import com.problemsolver.githubtrending.utils.DateUtils.Companion.DATE_TIME_PATTERN
import com.problemsolver.githubtrending.utils.DateUtils.Companion.ZONE_ASIA_JAKARTA
import java.text.SimpleDateFormat
import java.util.*

class DateUtils {
    companion object {
        const val DATE_TIME_PATTERN = "MM/dd/yyyy HH:mm:ss"
        const val ZONE_ASIA_JAKARTA = "Asia/Jakarta"
        const val LOCALE_ENGLISH = "en"
    }
}

fun setDataValidUntil(): String? {
    val cal = Calendar.getInstance()
    cal.add(Calendar.HOUR_OF_DAY, 2)
    return SimpleDateFormat(DATE_TIME_PATTERN, Locale.getDefault()).format(cal.time)
}

fun stringToDate(dateString: String, pattern: String, locale: String, zone: String? = null): Date {
    return try {
        val localeId = Locale(locale)
        val sdf = SimpleDateFormat(pattern, localeId)
        sdf.timeZone =
            if (zone != null) TimeZone.getTimeZone(zone) else TimeZone.getTimeZone(ZONE_ASIA_JAKARTA)
        sdf.parse(dateString) ?: Date()
    } catch (e: Exception) {
        Date()
    }
}
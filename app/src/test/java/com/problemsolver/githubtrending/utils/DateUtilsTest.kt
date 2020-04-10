package com.problemsolver.githubtrending.utils

import org.junit.Assert.*
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.*


class DateUtilsTest {
    @Test
    fun `Date parsing check`() {
        // GIVEN
        val cal = Calendar.getInstance()
        cal.add(Calendar.HOUR_OF_DAY, 2)
        val validUntil = SimpleDateFormat(DateUtils.DATE_TIME_PATTERN, Locale.getDefault()).format(cal.time)

        // WHEN
        val validUntilTest = setDataValidUntil()
        val stringToDateTest = stringToDate(validUntil, DateUtils.DATE_TIME_PATTERN, DateUtils.LOCALE_ENGLISH)

        // THEN
        assertEquals(validUntil, validUntilTest)
        @Suppress("DEPRECATION")
        assertEquals(stringToDateTest, Date(validUntil))
    }
}

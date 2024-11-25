package com.cashbox.android.utils

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

object DateHelper {
    fun convertDateToIndonesianFormat(dateString: String): String {
        val date = LocalDate.parse(
            dateString,
            DateTimeFormatter.ofPattern("yyyy-[M][MM]-[d][dd]")
        )
        val indonesianDateFormat = DateTimeFormatter.ofPattern(
            "dd MMMM yyyy",
            Locale("id", "ID")
        )
        return date.format(indonesianDateFormat)
    }
}
package com.cashbox.android.utils

import android.widget.EditText
import androidx.fragment.app.FragmentManager
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

object DateHelper {
    fun setupDateEditText(editText: EditText, fragmentManager: FragmentManager) {
        editText.setOnClickListener {
            DatePickerDialog().apply {
                onDateSetListener = { year, month, day ->
                    val selectedDate = "$year-${month + 1}-$day"
                    editText.setText(convertDateToIndonesianFormat(selectedDate))
                }
            }.show(fragmentManager, "DATE_PICKER_DIALOG")
        }
    }

    private fun convertDateToIndonesianFormat(dateString: String): String {
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
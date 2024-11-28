package com.cashbox.android.utils

import java.text.NumberFormat
import java.util.Locale

object NumberFormatHelper {
    fun formatToRupiah(amount: Long): String {
        val localeID = Locale("in", "ID")
        val formatter = NumberFormat.getCurrencyInstance(localeID)
        formatter.maximumFractionDigits = 0
        return formatter.format(amount)
    }
}
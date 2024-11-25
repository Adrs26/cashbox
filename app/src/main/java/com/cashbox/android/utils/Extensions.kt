package com.cashbox.android.utils

import com.cashbox.android.R
import java.text.NumberFormat
import java.util.Locale

fun Long.toIndonesianNumberString(): String {
    val numberFormat = NumberFormat.getNumberInstance(Locale("in", "ID"))
    return numberFormat.format(this)
}

fun String.toOriginalNumber(): Long {
    return this.replace(".", "").toLong()
}

fun String.getImageResource(): Int {
    return when (this) {
        "Gaji" -> R.drawable.ic_income_salary
        "Bonus" -> R.drawable.ic_income_bonuses
        "Usaha Sampingan" -> R.drawable.ic_income_side_business
        "Investasi (Pemasukan)" -> R.drawable.ic_income_investment
        "Pemasukan Lain" -> R.drawable.ic_income_more
        "Makanan & Minuman" -> R.drawable.ic_expense_food
        "Transportasi" -> R.drawable.ic_expense_transportation
        "Kesehatan" -> R.drawable.ic_expense_health
        "Tagihan & Utilitas" -> R.drawable.ic_expense_utility
        "Pendidikan" -> R.drawable.ic_expense_education
        "Hiburan" -> R.drawable.ic_expense_entertainment
        "Belanja" -> R.drawable.ic_expense_shopping
        "Investasi (Pengeluaran)" -> R.drawable.ic_expense_investment
        "Perawatan Pribadi" -> R.drawable.ic_expense_personal_care
        "Donasi" -> R.drawable.ic_expense_donation
        "Asuransi" -> R.drawable.ic_expense_insurance
        "Kebutuhan Rumah Tangga" -> R.drawable.ic_expense_household
        "Pajak" -> R.drawable.ic_expense_tax
        "Pengeluaran Lain" -> R.drawable.ic_expense_more
        else -> 0
    }
}
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
        "Gaji" -> R.drawable.ic_salary
        "Bonus" -> R.drawable.ic_bonuses
        "Usaha Sampingan" -> R.drawable.ic_side_business
        "Investasi (Pemasukan)" -> R.drawable.ic_investment_income
        "Pemasukan Lain" -> R.drawable.ic_more_income
        "Makanan & Minuman" -> R.drawable.ic_food
        "Transportasi" -> R.drawable.ic_transportation
        "Kesehatan" -> R.drawable.ic_health
        "Tagihan & Utilitas" -> R.drawable.ic_utility
        "Pendidikan" -> R.drawable.ic_education
        "Hiburan" -> R.drawable.ic_entertainment
        "Belanja" -> R.drawable.ic_shopping
        "Investasi (Pengeluaran)" -> R.drawable.ic_investmen_expense
        "Perawatan Pribadi" -> R.drawable.ic_personal_care
        "Donasi" -> R.drawable.ic_donation
        "Asuransi" -> R.drawable.ic_insurance
        "Kebutuhan Rumah Tangga" -> R.drawable.ic_household
        "Pajak" -> R.drawable.ic_tax
        "Pengeluaran Lain" -> R.drawable.ic_more_expense
        else -> 0
    }
}
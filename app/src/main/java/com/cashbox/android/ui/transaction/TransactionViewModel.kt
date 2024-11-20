package com.cashbox.android.ui.transaction

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cashbox.android.R

class TransactionViewModel : ViewModel() {
    private val _transactionType = MutableLiveData<String>().apply { value = "Pemasukan" }
    val transactionType: LiveData<String> = _transactionType

    private val _transactionCategory = MutableLiveData<String>()
    val transactionCategory: LiveData<String> = _transactionCategory

    private val _incomeButtonBackground = MutableLiveData<Int>()
    val incomeButtonBackground: LiveData<Int> = _incomeButtonBackground

    private val _expenseButtonBackground = MutableLiveData<Int>()
    val expenseButtonBackground: LiveData<Int> = _expenseButtonBackground

    fun changeTransactionType(transactionType: String) {
        this._transactionType.value = transactionType
        updateButtonBackground(transactionType)
    }

    fun setTransactionCategory(transactionCategory: String) {
        this._transactionCategory.value = transactionCategory
    }

    private fun updateButtonBackground(transactionType: String) {
        val isIncome = transactionType == "Pemasukan"
        _incomeButtonBackground.value = if (isIncome) R.drawable.bg_btn_light_blue else
            R.drawable.bg_btn_white
        _expenseButtonBackground.value = if (isIncome) R.drawable.bg_btn_white else
            R.drawable.bg_btn_light_blue
    }
}
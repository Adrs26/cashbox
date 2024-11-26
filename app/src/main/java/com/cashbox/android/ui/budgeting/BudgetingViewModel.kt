package com.cashbox.android.ui.budgeting

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class BudgetingViewModel : ViewModel() {
    private val _isFirstTime = MutableLiveData<Boolean>().apply { value = true }
    val isFirstTime: LiveData<Boolean> = _isFirstTime

    fun changeFirstTimeValue() {
        _isFirstTime.value = false
    }
}
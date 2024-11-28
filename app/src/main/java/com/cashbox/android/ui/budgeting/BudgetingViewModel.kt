package com.cashbox.android.ui.budgeting

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class BudgetingViewModel : ViewModel() {
    private val _isFirstTime = MutableLiveData<Boolean>().apply { value = true }
    private val _progress = MutableLiveData<Int>()

    val isFirstTime: LiveData<Boolean> = _isFirstTime
    val progress: LiveData<Int> = _progress

    fun changeFirstTimeValue() {
        _isFirstTime.value = false
    }

    fun getProgress(progress: Int) {
        _progress.value = progress
    }
}
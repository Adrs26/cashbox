package com.cashbox.android.ui.analysis

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cashbox.android.R

class AnalysisViewModel : ViewModel() {
    private val _isFirstTime = MutableLiveData<Boolean>().apply { value = true }
    private val _menuId = MutableLiveData<Int>()
    private val _thisMonthButtonStyle = MutableLiveData<Int>()
    private val _lastSevenDaysButtonStyle = MutableLiveData<Int>()
    private val _lastThirtyDaysButtonStyle = MutableLiveData<Int>()

    val isFirstTime: LiveData<Boolean> = _isFirstTime
    val thisMonthButtonStyle: LiveData<Int> = _thisMonthButtonStyle
    val lastSevenDaysButtonStyle: LiveData<Int> = _lastSevenDaysButtonStyle
    val lastThirtyDaysButtonStyle: LiveData<Int> = _lastThirtyDaysButtonStyle

    fun changeFirstTimeValue() {
        _isFirstTime.value = false
    }

    fun changeMenuId(menuId: Int) {
        _menuId.value = menuId
        updateButtonStyles(menuId)
    }

    private fun updateButtonStyles(menuId: Int) {
        _thisMonthButtonStyle.value = getButtonStyle(menuId, 1)
        _lastSevenDaysButtonStyle.value = getButtonStyle(menuId, 2)
        _lastThirtyDaysButtonStyle.value = getButtonStyle(menuId, 3)
    }

    private fun getButtonStyle(menuId: Int, targetMenuId: Int): Int {
        return if (menuId == targetMenuId) {
            R.drawable.bg_rounded_corner_light_blue
        } else {
            R.drawable.bg_rounded_corner_white
        }
    }
}
package com.cashbox.android.ui.analysis

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cashbox.android.R
import com.cashbox.android.data.model.AnalysisData
import com.cashbox.android.data.repository.TransactionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AnalysisViewModel(private val transactionRepository: TransactionRepository) : ViewModel() {
    private val _isFirstTime = MutableLiveData<Boolean>().apply { value = true }
    private val _menuId = MutableLiveData<Int>()
    private val _thisMonthButtonStyle = MutableLiveData<Int>()
    private val _lastSevenDaysButtonStyle = MutableLiveData<Int>()
    private val _lastThirtyDaysButtonStyle = MutableLiveData<Int>()
    private val _expenseCategories = MutableLiveData<List<AnalysisData>>()
    private val _exception = MutableLiveData<Boolean>()

    val isFirstTime: LiveData<Boolean> = _isFirstTime
    val thisMonthButtonStyle: LiveData<Int> = _thisMonthButtonStyle
    val lastSevenDaysButtonStyle: LiveData<Int> = _lastSevenDaysButtonStyle
    val lastThirtyDaysButtonStyle: LiveData<Int> = _lastThirtyDaysButtonStyle
    val expenseCategories: LiveData<List<AnalysisData>> = _expenseCategories
    val exception: LiveData<Boolean> = _exception

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

    fun getTransitionOnSpecificMonth(uid: String, month: Int, year: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _expenseCategories.postValue(
                    transactionRepository.getTransactionOnSpecificMonth(month, year).data.filter {
                        it.type == "pengeluaran" && it.uid == uid
                    }.groupBy { it.category }.map { (key, groupedItems) ->
                        AnalysisData(
                            uid,
                            key,
                            groupedItems.sumOf { it.amount },
                            "pengeluaran"
                        )
                    }
                )
                _exception.postValue(false)
            } catch (e: Exception) {
                _exception.postValue(true)
            }
        }
    }

    fun resetExceptionValue() {
        _exception.value = false
    }
}
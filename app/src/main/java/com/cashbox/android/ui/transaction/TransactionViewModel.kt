package com.cashbox.android.ui.transaction

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cashbox.android.data.model.TransactionData
import com.cashbox.android.data.repository.TransactionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TransactionViewModel(private val transactionRepository: TransactionRepository): ViewModel() {
    private val _transaction = MutableLiveData<List<TransactionData>>()
    private val _exception = MutableLiveData<Boolean>()

    val transaction: LiveData<List<TransactionData>> = _transaction
    val exception: LiveData<Boolean> = _exception

    fun getAllTransaction(uid: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _transaction.postValue(
                    transactionRepository.getAllTransaction().data.filter { it.uid == uid }
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
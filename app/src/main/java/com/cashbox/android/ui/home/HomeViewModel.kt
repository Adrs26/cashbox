package com.cashbox.android.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cashbox.android.data.model.ListGoals
import com.cashbox.android.data.repository.GoalsRepository
import com.cashbox.android.data.repository.TransactionRepository
import com.cashbox.android.data.repository.WalletRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(
    private val walletRepository: WalletRepository,
    private val transactionRepository: TransactionRepository,
    private val goalsRepository: GoalsRepository
) : ViewModel() {
    private val _walletTotalAmount = MutableLiveData<Long>()
    private val _topGoals = MutableLiveData<List<ListGoals>>()
    private val _exception = MutableLiveData<Boolean>()

    val walletTotalAmount: LiveData<Long> = _walletTotalAmount
    val topGoals: LiveData<List<ListGoals>> = _topGoals
    val exception: LiveData<Boolean> = _exception

    fun getWalletTotalAmount(uid: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _walletTotalAmount.postValue(
                    walletRepository.getWallet().data.filter { it.uid == uid}.sumOf { it.amount }
                )
                _exception.postValue(false)
            } catch (e: Exception) {
                _exception.postValue(true)
            }
        }
    }

    fun getTopGoals(uid: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val goals = goalsRepository.getAllGoals().data.filter { it.uid == uid }
                val saves = goalsRepository.getAllSaves().data.filter { it.uid == uid }

                val groupedSaves = saves.groupBy { it.goalId }

                val exGoalsList = goals.map { goal ->
                    val targetAmount = goal.amount
                    val currentAmount = groupedSaves[goal.id]?.sumOf { it.amount } ?: 0L
                    ListGoals(
                        idGoals = goal.id,
                        name = goal.name,
                        targetAmount = targetAmount,
                        currentAmount = currentAmount
                    )
                }.take(3)
                _topGoals.postValue(exGoalsList)
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
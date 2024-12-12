package com.cashbox.android.data.repository

import com.cashbox.android.data.api.ApiService
import com.cashbox.android.data.model.TransactionBody
import com.cashbox.android.data.model.TransactionHeader
import com.cashbox.android.data.model.TransactionResponse
import com.cashbox.android.data.model.WalletGetHeader

class TransactionRepository(private val apiService: ApiService) {
    suspend fun getAllTransaction(): TransactionHeader {
        return apiService.getAllTransaction()
    }

    suspend fun getAllWallet(): WalletGetHeader {
        return apiService.getWallet()
    }

    suspend fun addTransaction(
        transactionType: String,
        transactionBody: TransactionBody
    ): TransactionResponse {
        return when (transactionType) {
            "Pemasukan" -> apiService.addIncomeTransaction(transactionBody)
            else -> apiService.addExpenseTransaction(transactionBody)
        }
    }
}
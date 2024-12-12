package com.cashbox.android.data.model

import com.google.gson.annotations.SerializedName

data class TransactionHeader(
    val data: List<TransactionData>
)

data class TransactionData(
    @SerializedName("id_transaksi")
    val transactionId: Int,
    @SerializedName("deskripsi")
    val description: String,
    @SerializedName("nominal")
    val amount: Long,
    @SerializedName("sumber")
    val source: Int,
    @SerializedName("tanggal")
    val date: String,
    @SerializedName("kategori")
    val category: Int,
    @SerializedName("tipe")
    val transactionType: String,
    @SerializedName("nama_sumber_uang")
    val sourceName: String
)

data class TransactionBody(
    val uid: String,
    @SerializedName("deskripsi")
    val description: String,
    @SerializedName("nominal")
    val amount: Long,
    @SerializedName("sumber")
    val source: Int,
    @SerializedName("tanggal")
    val date: String,
    @SerializedName("kategori")
    val category: Int,
    @SerializedName("nama_sumber_uang")
    val sourceName: String
)

data class TransactionResponse(
    val message: String
)
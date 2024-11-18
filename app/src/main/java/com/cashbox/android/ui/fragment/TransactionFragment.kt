package com.cashbox.android.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.cashbox.android.R
import com.cashbox.android.adapter.TransactionAdapter
import com.cashbox.android.databinding.FragmentTransactionBinding

@SuppressLint("DiscouragedApi")
class TransactionFragment : Fragment(R.layout.fragment_transaction) {
    private val binding by viewBinding(FragmentTransactionBinding::bind)
    private lateinit var transactionAdapter: TransactionAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupSearchView()
        setupAdapter()
    }

    private fun setupSearchView() {
        binding.apply {
            searchView.queryHint = "Cari transaksimu"
            searchView.findViewById<View>(androidx.appcompat.R.id.search_plate).background = null
        }
    }

    private fun setupAdapter() {
        transactionAdapter = TransactionAdapter()
        binding.rvTransaction.layoutManager = LinearLayoutManager(requireContext())
        binding.rvTransaction.adapter = transactionAdapter
        transactionAdapter.submitList(listOf(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1))
    }
}
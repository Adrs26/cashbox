package com.cashbox.android.ui.transaction

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.cashbox.android.R
import com.cashbox.android.databinding.FragmentTransactionBinding
import com.cashbox.android.ui.main.MainActivity

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
        transactionAdapter = TransactionAdapter(object : TransactionAdapter.OnItemClickListener {
            override fun onItemClick() {
                findNavController().navigate(R.id.action_nav_transaction_to_nav_edit_transaction)
                (activity as MainActivity).hideBottomNav()
            }
        })
        binding.rvTransaction.layoutManager = LinearLayoutManager(requireContext())
        binding.rvTransaction.adapter = transactionAdapter
        transactionAdapter.submitList(listOf(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1))
    }
}
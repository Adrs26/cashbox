package com.cashbox.android.ui.transaction

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.cashbox.android.R
import com.cashbox.android.data.api.ApiClientBearer
import com.cashbox.android.data.datastore.DataStoreInstance
import com.cashbox.android.data.datastore.UserPreference
import com.cashbox.android.data.repository.TransactionRepository
import com.cashbox.android.databinding.FragmentTransactionBinding
import com.cashbox.android.ui.main.MainActivity
import com.cashbox.android.ui.viewmodel.TransactionViewModelFactory
import kotlinx.coroutines.launch

class TransactionFragment : Fragment(R.layout.fragment_transaction) {
    private val binding by viewBinding(FragmentTransactionBinding::bind)
    private lateinit var transactionViewModel: TransactionViewModel
    private lateinit var transactionAdapter: TransactionAdapter
    private val userPreference by lazy {
        UserPreference(DataStoreInstance.getInstance(requireContext()))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupSearchView()
        setupAdapter()
        setupDataStore()
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
    }

    private fun setupDataStore() {
        viewLifecycleOwner.lifecycleScope.launch {
            userPreference.userToken.collect {
                setupViewModel(it)
                transactionViewModel.getAllTransaction()
                setupObservers()
            }
        }
    }

    private fun setupViewModel(token: String) {
        val factory = TransactionViewModelFactory(
            TransactionRepository(ApiClientBearer.create(token))
        )
        transactionViewModel = ViewModelProvider(
            requireActivity(),
            factory
        )[TransactionViewModel::class.java]
    }

    private fun setupObservers() {
        transactionViewModel.transaction.observe(viewLifecycleOwner) { listTransaction ->
            transactionAdapter.submitList(listTransaction)
        }

        transactionViewModel.exception.observe(viewLifecycleOwner) { exception ->
            if (exception) {
                Toast.makeText(
                    requireContext(),
                    resources.getString(R.string.no_internet_connection),
                    Toast.LENGTH_SHORT
                ).show()
                transactionViewModel.resetExceptionValue()
            }
        }
    }
}
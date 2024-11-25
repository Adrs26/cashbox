package com.cashbox.android.ui.transaction

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.cashbox.android.R
import com.cashbox.android.adapter.MoneySourceAdapter
import com.cashbox.android.databinding.FragmentMoneySourceBinding

class MoneySourceFragment : Fragment(R.layout.fragment_money_source) {
    private val binding by viewBinding(FragmentMoneySourceBinding::bind)
    private lateinit var moneySourceAdapter: MoneySourceAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupBackButton()
        setupAdapter()
    }

    private fun setupBackButton() {
        binding.ibBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setupAdapter() {
        moneySourceAdapter = MoneySourceAdapter()
        binding.rvMoneySource.layoutManager = LinearLayoutManager(requireContext())
        binding.rvMoneySource.adapter = moneySourceAdapter
        moneySourceAdapter.submitList(listOf(1, 1, 1, 1, 1))
    }
}
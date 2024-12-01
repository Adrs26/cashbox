package com.cashbox.android.ui.goals

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.cashbox.android.R
import com.cashbox.android.ui.wallet.WalletAdapter
import com.cashbox.android.databinding.FragmentMoneySourceBinding

class SaveMoneySourceFragment : Fragment(R.layout.fragment_money_source) {
    private val binding by viewBinding(FragmentMoneySourceBinding::bind)
    private lateinit var walletAdapter: WalletAdapter

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
        walletAdapter = WalletAdapter(object : WalletAdapter.OnItemClickListener {
            override fun onItemClick() {
                findNavController().popBackStack()
            }
        })
        binding.rvMoneySource.layoutManager = LinearLayoutManager(requireContext())
        binding.rvMoneySource.adapter = walletAdapter
        walletAdapter.submitList(listOf(1, 1, 1, 1, 1))
    }
}
package com.cashbox.android.ui.wallet

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.cashbox.android.R
import com.cashbox.android.databinding.FragmentWalletBinding
import com.cashbox.android.ui.main.MainActivity

class WalletFragment : Fragment(R.layout.fragment_wallet) {
    private val binding by viewBinding(FragmentWalletBinding::bind)
    private lateinit var walletAdapter: WalletAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupButtons()
        setupBackPressedDispatcher()
        setupAdapter()
    }

    private fun setupButtons() {
        binding.ibBack.setOnClickListener {
            findNavController().popBackStack()
            (activity as MainActivity).showBottomNav()
        }

        binding.btnAddWallet.setOnClickListener {
            findNavController().navigate(R.id.action_nav_wallet_to_nav_add_wallet)
        }
    }

    private fun setupBackPressedDispatcher() {
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    findNavController().popBackStack()
                    (activity as MainActivity).showBottomNav()
                }
            }
        )
    }

    private fun setupAdapter() {
        walletAdapter = WalletAdapter(object : WalletAdapter.OnItemClickListener {
            override fun onItemClick() {
                findNavController().navigate(R.id.action_nav_wallet_to_nav_edit_wallet)
            }
        })
        binding.rvWallet.layoutManager = LinearLayoutManager(requireContext())
        binding.rvWallet.adapter = walletAdapter
        walletAdapter.submitList(listOf(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1))
    }
}
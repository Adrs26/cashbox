package com.cashbox.android.ui.wallet

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.cashbox.android.R
import com.cashbox.android.databinding.FragmentAddWalletBinding
import com.cashbox.android.utils.AnimationHelper
import com.cashbox.android.utils.NumberFormatHelper

class AddWalletFragment : Fragment(R.layout.fragment_add_wallet) {
    private val binding by viewBinding(FragmentAddWalletBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupButtons()
        setupAmountEditText()
    }

    private fun setupButtons() {
        binding.apply {
            ibBack.setOnClickListener {
                findNavController().popBackStack()
            }

            AnimationHelper.applyTouchAnimation(btnAddWallet)
            btnAddWallet.setOnClickListener {}
        }
    }

    private fun setupAmountEditText() {
        NumberFormatHelper.setupAmountEditText(binding.edtAmount)
    }
}
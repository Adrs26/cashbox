package com.cashbox.android.ui.wallet

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.cashbox.android.R
import com.cashbox.android.databinding.FragmentEditWalletBinding
import com.cashbox.android.utils.AnimationHelper

class EditWalletFragment : Fragment(R.layout.fragment_edit_wallet) {
    private val binding by viewBinding(FragmentEditWalletBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupButtons()
    }

    private fun setupButtons() {
        binding.apply {
            ibBack.setOnClickListener {
                findNavController().popBackStack()
            }

            AnimationHelper.applyTouchAnimation(btnSaveWallet)
            btnSaveWallet.setOnClickListener {}
        }
    }
}
package com.cashbox.android.ui.goals

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.cashbox.android.R
import com.cashbox.android.databinding.FragmentAddSaveBinding
import com.cashbox.android.utils.AnimationHelper
import com.cashbox.android.utils.DateHelper
import com.cashbox.android.utils.NumberFormatHelper

class AddSaveFragment : Fragment(R.layout.fragment_add_save) {
    private val binding by viewBinding(FragmentAddSaveBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupButtons()
        setupEditText()
    }

    private fun setupButtons() {
        binding.ibBack.setOnClickListener {
            findNavController().popBackStack()
        }

        AnimationHelper.applyTouchAnimation(binding.btnSave)
        binding.btnSave.setOnClickListener {}
    }

    private fun setupEditText() {
        NumberFormatHelper.setupAmountEditText(binding.edtAmount)
        DateHelper.setupDateEditText(binding.edtDate, parentFragmentManager)
        binding.edtWallet.setOnClickListener {
            findNavController().navigate(R.id.action_nav_add_save_to_nav_save_money_source)
        }
    }
}
package com.cashbox.android.ui.account

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.cashbox.android.R
import com.cashbox.android.databinding.FragmentEditAccountBinding
import com.cashbox.android.utils.AnimationHelper
import com.cashbox.android.utils.DateHelper

class EditAccountFragment : Fragment(R.layout.fragment_edit_account) {
    private val binding by viewBinding(FragmentEditAccountBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupButtons()
        setupBirthDateEditText()
        setupGenderRadioButton()
    }

    private fun setupButtons() {
        binding.apply {
            ibBack.setOnClickListener {
                findNavController().popBackStack()
            }

            AnimationHelper.applyTouchAnimation(btnSave)
            btnSave.setOnClickListener {}
        }
    }

    private fun setupBirthDateEditText() {
        DateHelper.setupDateEditText(binding.edtUserBirthDate, parentFragmentManager)
    }

    private fun setupGenderRadioButton() {
        binding.btnRadioMale.setOnClickListener {
            binding.btnRadioMale.setImageResource(R.drawable.ic_btn_radio_checked)
            binding.btnRadioFemale.setImageResource(R.drawable.ic_btn_radio_unchecked)
        }

        binding.btnRadioFemale.setOnClickListener {
            binding.btnRadioMale.setImageResource(R.drawable.ic_btn_radio_unchecked)
            binding.btnRadioFemale.setImageResource(R.drawable.ic_btn_radio_checked)
        }
    }
}
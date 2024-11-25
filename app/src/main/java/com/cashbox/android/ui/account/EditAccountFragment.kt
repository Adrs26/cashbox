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
import com.cashbox.android.utils.DatePickerDialog

class EditAccountFragment : Fragment(R.layout.fragment_edit_account) {
    private val binding by viewBinding(FragmentEditAccountBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupButtons()
        setupBirthDateEditText()
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
        binding.edtUserBirthDate.setOnClickListener {
            DatePickerDialog().apply {
                onDateSetListener = { year, month, day ->
                    val selectedDate = "$year-${month + 1}-$day"
                    binding.edtUserBirthDate.setText(
                        DateHelper.convertDateToIndonesianFormat(selectedDate)
                    )
                }
            }.show(parentFragmentManager, "DATE_PICKER_DIALOG")
        }
    }
}
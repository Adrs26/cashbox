package com.cashbox.android.ui.account

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.cashbox.android.R
import com.cashbox.android.data.datastore.DataStoreInstance
import com.cashbox.android.data.datastore.UserPreference
import com.cashbox.android.databinding.FragmentEditAccountBinding
import com.cashbox.android.utils.AnimationHelper
import com.cashbox.android.utils.DateHelper
import kotlinx.coroutines.launch

class EditAccountFragment : Fragment(R.layout.fragment_edit_account) {
    private val binding by viewBinding(FragmentEditAccountBinding::bind)
    private val userPreference by lazy {
        UserPreference(DataStoreInstance.getInstance(requireContext()))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupButtons()
        setupBirthDateEditText()
        setupGenderRadioButton()
        setupUserData()
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

    private fun setupUserData() {
        viewLifecycleOwner.lifecycleScope.launch {
            launch {
                userPreference.username.collect {
                    binding.edtUserName.setText(it)
                }
            }
            launch {
                userPreference.userEmail.collect {
                    binding.edtUserEmail.setText(it)
                }
            }
        }
    }
}
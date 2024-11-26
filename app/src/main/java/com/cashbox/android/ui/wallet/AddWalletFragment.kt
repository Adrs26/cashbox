package com.cashbox.android.ui.wallet

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.cashbox.android.R
import com.cashbox.android.databinding.FragmentAddWalletBinding
import com.cashbox.android.utils.AnimationHelper
import com.cashbox.android.utils.toIndonesianNumberString
import com.cashbox.android.utils.toOriginalNumber

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
        binding.edtAmount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                val inputAmount = s.toString()
                if (inputAmount.isNotEmpty()) {
                    val formattedAmount = inputAmount.toOriginalNumber().toIndonesianNumberString()
                    updateAmountEditText(binding.edtAmount, formattedAmount, this)
                }
            }
        })
    }

    private fun updateAmountEditText(editText: EditText, newText: String, watcher: TextWatcher) {
        editText.removeTextChangedListener(watcher)
        editText.setText(newText)
        editText.setSelection(newText.length)
        editText.addTextChangedListener(watcher)
    }
}
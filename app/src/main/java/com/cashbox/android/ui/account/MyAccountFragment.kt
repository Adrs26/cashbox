package com.cashbox.android.ui.account

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.cashbox.android.R
import com.cashbox.android.databinding.FragmentMyAccountBinding

class MyAccountFragment : Fragment(R.layout.fragment_my_account) {
    private val binding by viewBinding(FragmentMyAccountBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupButton()
    }

    private fun setupButton() {
        binding.ibBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}
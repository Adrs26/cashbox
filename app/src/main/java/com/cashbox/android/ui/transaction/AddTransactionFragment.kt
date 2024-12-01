package com.cashbox.android.ui.transaction

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.cashbox.android.R
import com.cashbox.android.databinding.FragmentAddTransactionBinding
import com.cashbox.android.ui.main.MainActivity
import com.cashbox.android.utils.AnimationHelper
import com.cashbox.android.utils.DateHelper
import com.cashbox.android.utils.NumberFormatHelper

class AddTransactionFragment : Fragment(R.layout.fragment_add_transaction) {
    private val binding by viewBinding(FragmentAddTransactionBinding::bind)
    private val addTransactionViewModel by lazy {
        ViewModelProvider(requireActivity())[AddTransactionViewModel::class.java]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupButtons()
        setupBackPressedDispatcher()
        setupEditText()
        setupObservers()
    }

    private fun setupButtons() {
        binding.apply {
            ibClose.setOnClickListener {
                findNavController().popBackStack()
                (activity as MainActivity).showBottomNav()
            }

            AnimationHelper.applyTouchAnimation(btnIncome)
            AnimationHelper.applyTouchAnimation(btnExpense)
            AnimationHelper.applyTouchAnimation(btnAdd)

            btnIncome.setOnClickListener {
                addTransactionViewModel.changeTransactionType(resources.getString(R.string.income))
            }
            btnExpense.setOnClickListener {
                addTransactionViewModel.changeTransactionType(resources.getString(R.string.expense))
            }
            btnAdd.setOnClickListener {}
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

    private fun setupEditText() {
        NumberFormatHelper.setupAmountEditText(binding.edtAmount)
        DateHelper.setupDateEditText(binding.edtDate, parentFragmentManager)

        binding.edtCategory.setOnClickListener {
            findNavController().navigate(
                R.id.action_nav_add_transaction_to_nav_transaction_categories
            )
        }
        binding.edtWallet.setOnClickListener {
            findNavController().navigate(
                R.id.action_nav_add_transaction_to_nav_money_source
            )
        }
    }

    private fun setupObservers() {
        addTransactionViewModel.transactionCategory.observe(viewLifecycleOwner) { category ->
            binding.edtCategory.setText(category)
        }
        addTransactionViewModel.incomeButtonBackground.observe(viewLifecycleOwner) { background ->
            binding.btnIncome.background = ContextCompat.getDrawable(requireContext(), background)
        }
        addTransactionViewModel.expenseButtonBackground.observe(viewLifecycleOwner) { background ->
            binding.btnExpense.background = ContextCompat.getDrawable(requireContext(), background)
        }
    }
}
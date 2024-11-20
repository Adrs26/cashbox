package com.cashbox.android.ui.transaction

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.cashbox.android.R
import com.cashbox.android.databinding.FragmentAddTransactionBinding
import com.cashbox.android.ui.main.MainActivity
import com.cashbox.android.utils.DateUtils
import com.cashbox.android.utils.toFormattedString
import com.cashbox.android.utils.toOriginalNumber

class AddTransactionFragment : Fragment(R.layout.fragment_add_transaction) {
    private val binding by viewBinding(FragmentAddTransactionBinding::bind)
    private val addTransactionViewModel by lazy {
        ViewModelProvider(requireActivity())[AddTransactionViewModel::class.java]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupButton()
        setupBackPressedDispatcher()
        setupAmountEditText()
        setupCategoryDateEditText()
        setupObservers()
    }

    private fun setupButton() {
        binding.ibClose.setOnClickListener {
            findNavController().popBackStack()
            (activity as MainActivity).showBottomNav()
        }
        binding.btnIncome.setOnClickListener {
            addTransactionViewModel.changeTransactionType(resources.getString(R.string.income))
        }
        binding.btnExpense.setOnClickListener {
            addTransactionViewModel.changeTransactionType(resources.getString(R.string.expense))
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

    private fun setupAmountEditText() {
        binding.edtAmount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                val inputAmount = s.toString()
                if (inputAmount.isNotEmpty()) {
                    val formattedAmount = inputAmount.toOriginalNumber().toFormattedString()
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

    private fun setupCategoryDateEditText() {
        binding.edtDate.setOnClickListener {
            DatePickerDialog().apply {
                onDateSetListener = { year, month, day ->
                    val selectedDate = "$year-${month + 1}-$day"
                    binding.edtDate.setText(DateUtils.convertDateToIndonesianFormat(selectedDate))
                }
            }.show(parentFragmentManager, "DATE_PICKER_DIALOG")
        }

        binding.edtCategory.setOnClickListener {
            findNavController().navigate(R.id.action_nav_add_transaction_to_nav_transaction_categories)
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
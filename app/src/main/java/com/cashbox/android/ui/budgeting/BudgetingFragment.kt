package com.cashbox.android.ui.budgeting

import android.animation.ValueAnimator
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.cashbox.android.R
import com.cashbox.android.adapter.BudgetingAdapter
import com.cashbox.android.databinding.FragmentBudgetingBinding

class BudgetingFragment : Fragment(R.layout.fragment_budgeting) {
    private val binding by viewBinding(FragmentBudgetingBinding::bind)
    private val budgetingViewModel by lazy {
        ViewModelProvider(requireActivity())[BudgetingViewModel::class.java]
    }
    private lateinit var budgetingAdapter: BudgetingAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapter()
        getProgress()
        setupObservers()
    }

    private fun setupAdapter() {
        budgetingAdapter = BudgetingAdapter()
        binding.rvBudgeting.layoutManager = LinearLayoutManager(requireContext())
        binding.rvBudgeting.adapter = budgetingAdapter
        budgetingAdapter.submitList(listOf(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1))
    }

    private fun getProgress() {
        budgetingViewModel.getProgress(50)
    }

    private fun setupObservers() {
        budgetingViewModel.isFirstTime.observe(viewLifecycleOwner) { isFirstTime ->
            if (isFirstTime) {
                animateCircularProgress(budgetingViewModel.progress.value!!)
            }
        }

        budgetingViewModel.progress.observe(viewLifecycleOwner) { progress ->
            binding.cpBudgeting.progress = progress
        }
    }

    private fun animateCircularProgress(progress: Int) {
        val animator = ValueAnimator.ofInt(0, progress)
        animator.duration = 1000
        animator.addUpdateListener { animation ->
            val animatedValue = animation.animatedValue as Int
            binding.cpBudgeting.progress = animatedValue
        }
        animator.start()
        budgetingViewModel.changeFirstTimeValue()
    }
}
package com.cashbox.android.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.cashbox.android.R
import com.cashbox.android.adapter.GoalsAdapter
import com.cashbox.android.adapter.TransactionAdapter
import com.cashbox.android.databinding.FragmentHomeBinding
import com.cashbox.android.ui.main.MainActivity
import java.util.Calendar

class HomeFragment : Fragment(R.layout.fragment_home) {
    private val binding by viewBinding(FragmentHomeBinding::bind)
    private lateinit var transactionAdapter: TransactionAdapter
    private lateinit var goalsAdapter: GoalsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupMenu()
        setupGreetingText()
        setupAdapter()
    }

    private fun setupMenu() {
        binding.ivProfile.setOnClickListener {
            findNavController().navigate(R.id.action_nav_home_to_nav_profile)
            (activity as MainActivity).hideBottomNav()
        }
    }

    private fun setupGreetingText() {
        val calendar = Calendar.getInstance()
        val hourOfDay = calendar.get(Calendar.HOUR_OF_DAY)
        val greetingMessage = when (hourOfDay) {
            in 6..10 -> resources.getString(R.string.good_morning)
            in 11..14 -> resources.getString(R.string.good_afternoon)
            in 15..17 -> resources.getString(R.string.good_evening)
            else -> resources.getString(R.string.good_night)
        }

        binding.tvGreeting.text = greetingMessage
    }

    private fun setupAdapter() {
        transactionAdapter = TransactionAdapter()
        binding.rvLastTransaction.layoutManager = LinearLayoutManager(requireContext())
        binding.rvLastTransaction.adapter = transactionAdapter
        transactionAdapter.submitList(listOf(1, 1, 1))

        goalsAdapter = GoalsAdapter()
        binding.rvGoalsTracker.layoutManager = LinearLayoutManager(requireContext())
        binding.rvGoalsTracker.adapter = goalsAdapter
        goalsAdapter.submitList(listOf(1, 1, 1))
    }
}
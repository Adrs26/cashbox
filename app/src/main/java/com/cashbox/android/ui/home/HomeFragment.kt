package com.cashbox.android.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.cashbox.android.R
import com.cashbox.android.data.datastore.DataStoreInstance
import com.cashbox.android.data.datastore.UserPreference
import com.cashbox.android.ui.goals.GoalsAdapter
import com.cashbox.android.ui.transaction.TransactionAdapter
import com.cashbox.android.databinding.FragmentHomeBinding
import com.cashbox.android.ui.main.MainActivity
import com.cashbox.android.utils.AnimationHelper
import kotlinx.coroutines.launch
import java.util.Calendar

class HomeFragment : Fragment(R.layout.fragment_home) {
    private val binding by viewBinding(FragmentHomeBinding::bind)
    private val userPreference by lazy {
        UserPreference(DataStoreInstance.getInstance(requireContext()))
    }
    private lateinit var transactionAdapter: TransactionAdapter
    private lateinit var goalsAdapter: GoalsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupButtons()
        setupGreetingText()
        setupAdapter()
    }

    private fun setupButtons() {
        binding.apply {
            ivProfile.setOnClickListener {
                findNavController().navigate(R.id.action_nav_home_to_nav_account)
                (activity as MainActivity).hideBottomNav()
            }
            ibWallet.setOnClickListener {
                findNavController().navigate(R.id.action_nav_home_to_nav_wallet)
                (activity as MainActivity).hideBottomNav()
            }
            ibGoals.setOnClickListener {
                findNavController().navigate(R.id.action_nav_home_to_nav_goals)
                (activity as MainActivity).hideBottomNav()
            }

            AnimationHelper.applyTouchAnimation(btnMoreGoals)
            btnMoreGoals.setOnClickListener {
                findNavController().navigate(R.id.action_nav_home_to_nav_goals)
                (activity as MainActivity).hideBottomNav()
            }
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

        viewLifecycleOwner.lifecycleScope.launch {
            userPreference.username.collect {
                binding.tvUsername.text = it
            }
        }
    }

    private fun setupAdapter() {
        transactionAdapter = TransactionAdapter(object : TransactionAdapter.OnItemClickListener {
            override fun onItemClick() {
                findNavController().navigate(R.id.action_nav_home_to_nav_edit_transaction)
                (activity as MainActivity).hideBottomNav()
            }
        })
        binding.rvLastTransaction.layoutManager = LinearLayoutManager(requireContext())
        binding.rvLastTransaction.adapter = transactionAdapter
        transactionAdapter.submitList(listOf(1, 1, 1))

        goalsAdapter = GoalsAdapter(object : GoalsAdapter.OnItemClickListener {
            override fun onItemClick() {
                findNavController().navigate(R.id.action_nav_home_to_nav_goals_detail)
                (activity as MainActivity).hideBottomNav()
            }
        })
        binding.rvGoalsTracker.layoutManager = LinearLayoutManager(requireContext())
        binding.rvGoalsTracker.adapter = goalsAdapter
        goalsAdapter.submitList(listOf(1, 1, 1))
    }
}
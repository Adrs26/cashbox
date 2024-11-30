package com.cashbox.android.ui.goals

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.cashbox.android.R
import com.cashbox.android.adapter.GoalsAdapter
import com.cashbox.android.databinding.FragmentGoalsBinding
import com.cashbox.android.ui.main.MainActivity

class GoalsFragment : Fragment(R.layout.fragment_goals) {
    private val binding by viewBinding(FragmentGoalsBinding::bind)
    private lateinit var goalsAdapter: GoalsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupButtons()
        setupBackPressedDispatcher()
        setupAdapter()
    }

    private fun setupButtons() {
        binding.ibBack.setOnClickListener {
            findNavController().popBackStack()
            (activity as MainActivity).showBottomNav()
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

    private fun setupAdapter() {
        goalsAdapter = GoalsAdapter(object : GoalsAdapter.OnItemClickListener {
            override fun onItemClick() {
                findNavController().navigate(R.id.action_nav_goals_to_nav_goals_detail)
            }
        })
        binding.rvGoals.layoutManager = LinearLayoutManager(requireContext())
        binding.rvGoals.adapter = goalsAdapter
        goalsAdapter.submitList(listOf(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1))
    }
}
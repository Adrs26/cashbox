package com.cashbox.android.ui.goals

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.cashbox.android.R
import com.cashbox.android.adapter.SaveHistoryAdapter
import com.cashbox.android.databinding.FragmentGoalsDetailBinding

class GoalsDetailFragment : Fragment(R.layout.fragment_goals_detail) {
    private val binding by viewBinding(FragmentGoalsDetailBinding::bind)
    private lateinit var saveHistoryAdapter: SaveHistoryAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapter()
        setupButtons()
    }

    private fun setupButtons() {
        binding.ibBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setupAdapter() {
        saveHistoryAdapter = SaveHistoryAdapter()
        binding.rvSaveHistory.layoutManager = LinearLayoutManager(requireContext())
        binding.rvSaveHistory.adapter = saveHistoryAdapter
        saveHistoryAdapter.submitList(listOf(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1))
    }
}
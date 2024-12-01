package com.cashbox.android.ui.goals

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.cashbox.android.R
import com.cashbox.android.databinding.FragmentGoalsDetailBinding
import com.cashbox.android.utils.AnimationHelper

class GoalsDetailFragment : Fragment(R.layout.fragment_goals_detail) {
    private val binding by viewBinding(FragmentGoalsDetailBinding::bind)
    private lateinit var saveAdapter: SaveAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapter()
        setupButtons()
    }

    private fun setupButtons() {
        binding.ibBack.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.ibEdit.setOnClickListener {
            findNavController().navigate(R.id.action_nav_goals_detail_to_nav_edit_goals)
        }

        AnimationHelper.applyTouchAnimation(binding.btnAddSave)
        binding.btnAddSave.setOnClickListener {
            findNavController().navigate(R.id.action_nav_goals_detail_to_nav_add_save)
        }
    }

    private fun setupAdapter() {
        saveAdapter = SaveAdapter(object : SaveAdapter.OnItemClickListener {
            override fun onItemClick() {
                findNavController().navigate(R.id.action_nav_goals_detail_to_nav_edit_save)
            }
        })
        binding.rvSaveHistory.layoutManager = LinearLayoutManager(requireContext())
        binding.rvSaveHistory.adapter = saveAdapter
        saveAdapter.submitList(listOf(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1))
    }
}
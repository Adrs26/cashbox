package com.cashbox.android.ui.account

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.cashbox.android.R
import com.cashbox.android.databinding.FragmentAccountBinding
import com.cashbox.android.ui.main.MainActivity
import com.cashbox.android.utils.AnimationHelper

class AccountFragment : Fragment(R.layout.fragment_account) {
    private val binding by viewBinding(FragmentAccountBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupButtons()
        setupBackPressedDispatcher()
    }

    private fun setupButtons() {
        binding.apply {
            ibClose.setOnClickListener {
                findNavController().popBackStack()
                (activity as MainActivity).showBottomNav()
            }

            setupButtonWithNavigation(btnMyAccount, R.id.action_nav_account_to_nav_my_account)
            setupButtonWithNavigation(btnNotifications, R.id.action_nav_account_to_nav_notifications)
        }
    }

    private fun setupButtonWithNavigation(button: View, actionId: Int) {
        AnimationHelper.applyTouchAnimation(button)
        button.setOnClickListener {
            findNavController().navigate(actionId)
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
}
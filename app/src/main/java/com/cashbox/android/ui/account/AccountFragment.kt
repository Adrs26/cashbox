package com.cashbox.android.ui.account

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.cashbox.android.R
import com.cashbox.android.data.datastore.DataStoreInstance
import com.cashbox.android.data.datastore.UserPreference
import com.cashbox.android.databinding.FragmentAccountBinding
import com.cashbox.android.ui.auth.LoginActivity
import com.cashbox.android.ui.main.MainActivity
import com.cashbox.android.utils.AnimationHelper
import kotlinx.coroutines.launch

class AccountFragment : Fragment(R.layout.fragment_account) {
    private val binding by viewBinding(FragmentAccountBinding::bind)
    private val userPreference by lazy {
        UserPreference(DataStoreInstance.getInstance(requireContext()))
    }

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

            AnimationHelper.applyTouchAnimation(btnMyAccount)
            AnimationHelper.applyTouchAnimation(btnNotifications)
            AnimationHelper.applyTouchAnimation(btnLogout)

            btnMyAccount.setOnClickListener {
                findNavController().navigate(R.id.action_nav_account_to_nav_my_account)
            }
            btnNotifications.setOnClickListener {
                findNavController().navigate(R.id.action_nav_account_to_nav_notifications)
            }
            btnLogout.setOnClickListener {
                viewLifecycleOwner.lifecycleScope.launch {
                    userPreference.updateUserLoginStatus(false)
                    startActivity(Intent(requireContext(), LoginActivity::class.java))
                    requireActivity().finish()
                }
            }
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
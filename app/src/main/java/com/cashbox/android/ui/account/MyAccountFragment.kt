package com.cashbox.android.ui.account

import android.os.Bundle
import android.view.View
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.cashbox.android.R
import com.cashbox.android.data.datastore.DataStoreInstance
import com.cashbox.android.data.datastore.UserPreference
import com.cashbox.android.databinding.FragmentMyAccountBinding
import kotlinx.coroutines.launch

class MyAccountFragment : Fragment(R.layout.fragment_my_account) {
    private val binding by viewBinding(FragmentMyAccountBinding::bind)
    private val userPreference by lazy {
        UserPreference(DataStoreInstance.getInstance(requireContext()))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupButtons()
        setupUserData()
    }

    private fun setupButtons() {
        binding.ibBack.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.editAccount.setOnClickListener {
            findNavController().navigate(R.id.action_nav_my_account_to_nav_edit_account)
        }
    }

    private fun setupUserData() {
        viewLifecycleOwner.lifecycleScope.launch {
            launch {
                userPreference.userPhoto.collect {
                    Glide.with(requireContext())
                        .load(it.toUri())
                        .centerCrop()
                        .transform(CircleCrop())
                        .placeholder(R.drawable.ic_account)
                        .error(R.drawable.ic_account)
                        .into(binding.ivProfile)
                }
            }
            launch {
                userPreference.username.collect {
                    binding.tvName.text = it
                }
            }
            launch {
                userPreference.userEmail.collect {
                    binding.tvEmail.text = it
                }
            }
        }
    }
}
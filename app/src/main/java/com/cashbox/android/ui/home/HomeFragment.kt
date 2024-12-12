package com.cashbox.android.ui.home

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.cashbox.android.R
import com.cashbox.android.data.api.ApiClientBearer
import com.cashbox.android.data.datastore.DataStoreInstance
import com.cashbox.android.data.datastore.UserPreference
import com.cashbox.android.data.repository.GoalsRepository
import com.cashbox.android.data.repository.TransactionRepository
import com.cashbox.android.data.repository.WalletRepository
import com.cashbox.android.databinding.FragmentHomeBinding
import com.cashbox.android.ui.goals.GoalsAdapter
import com.cashbox.android.ui.main.MainActivity
import com.cashbox.android.ui.transaction.TransactionAdapter
import com.cashbox.android.ui.viewmodel.HomeViewModelFactory
import com.cashbox.android.utils.AnimationHelper
import com.cashbox.android.utils.DataHelper
import com.cashbox.android.utils.NumberFormatHelper.formatToRupiah
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import java.util.Calendar

class HomeFragment : Fragment(R.layout.fragment_home) {
    private val binding by viewBinding(FragmentHomeBinding::bind)
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var transactionAdapter: TransactionAdapter
    private lateinit var goalsAdapter: GoalsAdapter
    private val userPreference by lazy {
        UserPreference(DataStoreInstance.getInstance(requireContext()))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupButtons()
        setupGreetingText()
        setupUserData()
        setupAdapter()
        setupDataStore()
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
    }

    private fun setupUserData() {
        viewLifecycleOwner.lifecycleScope.launch {
            launch {
                userPreference.username.collect {
                    binding.tvUsername.text = it
                }
            }
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

        goalsAdapter = GoalsAdapter(object : GoalsAdapter.OnItemClickListener {
            override fun onItemClick(id: Int, name: String, amount: Long) {
                DataHelper.goalsId = id
                DataHelper.goalsName = name
                DataHelper.goalsAmount = amount
                findNavController().navigate(R.id.action_nav_home_to_nav_goals_detail)
                (activity as MainActivity).hideBottomNav()
            }
        })
        binding.rvGoalsTracker.layoutManager = LinearLayoutManager(requireContext())
        binding.rvGoalsTracker.adapter = goalsAdapter
    }

    private fun setupDataStore() {
        viewLifecycleOwner.lifecycleScope.launch {
            combine(userPreference.userToken, userPreference.userUid) { token, uid ->
                Pair(token, uid)
            }.collect { (token, uid) ->
                setupViewModel(token)
                homeViewModel.getWalletTotalAmount(uid)
                homeViewModel.getTopGoals(uid)
                setupObservers()
            }
        }
    }

    private fun setupViewModel(token: String) {
        val factory = HomeViewModelFactory(
            WalletRepository(ApiClientBearer.create(token)),
            TransactionRepository(ApiClientBearer.create(token)),
            GoalsRepository(ApiClientBearer.create(token))
        )
        homeViewModel = ViewModelProvider(requireActivity(), factory)[HomeViewModel::class.java]
    }

    private fun setupObservers() {
        homeViewModel.walletTotalAmount.observe(viewLifecycleOwner) { amount ->
            binding.tvBalance.text = formatToRupiah(amount)
        }

        homeViewModel.topGoals.observe(viewLifecycleOwner) { topGoals ->
            goalsAdapter.submitList(topGoals)
        }

        homeViewModel.exception.observe(viewLifecycleOwner) { exception ->
            if (exception) {
                showToast(resources.getString(R.string.no_internet_connection))
                homeViewModel.resetExceptionValue()
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}
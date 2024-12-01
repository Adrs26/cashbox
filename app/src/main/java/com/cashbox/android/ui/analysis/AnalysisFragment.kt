package com.cashbox.android.ui.analysis

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.cashbox.android.R
import com.cashbox.android.data.model.Example
import com.cashbox.android.databinding.FragmentAnalysisBinding
import com.cashbox.android.utils.AnimationHelper
import com.cashbox.android.utils.NumberFormatHelper
import com.cashbox.android.utils.getColorResource
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry

class AnalysisFragment : Fragment(R.layout.fragment_analysis) {
    private val binding by viewBinding(FragmentAnalysisBinding::bind)
    private val analysisViewModel by lazy {
        ViewModelProvider(requireActivity())[AnalysisViewModel::class.java]
    }
    private lateinit var analysisAdapter: AnalysisAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val exampleData = listOf(
            Example("Makanan & Minuman", 1200000),
            Example("Transportasi", 200000),
            Example("Kesehatan", 350000),
            Example("Pendidikan", 400000),
            Example("Hiburan", 200000),
            Example("Belanja", 600000),
            Example("Asuransi", 200000),
            Example("Pengeluaran Lain", 450000)
        )

        binding.tvTotalExpense.text = NumberFormatHelper.formatToRupiah(exampleData.sumOf { it.amount })
        setupButtons()
        setupPieChart(
            getPieEntries(exampleData.sortedByDescending { it.amount }),
            getPieSliceColors(exampleData.sortedByDescending { it.amount })
        )
        setupAdapter()
        analysisAdapter.submitList(exampleData.sortedByDescending { it.amount })
        setupObservers()
    }

    private fun setupButtons() {
        AnimationHelper.applyTouchAnimation(binding.btnThisMonth)
        AnimationHelper.applyTouchAnimation(binding.btnLastSevenDays)
        AnimationHelper.applyTouchAnimation(binding.btnLastThirtyDays)

        binding.btnThisMonth.setOnClickListener {
            analysisViewModel.changeMenuId(THIS_MONTH)
        }
        binding.btnLastSevenDays.setOnClickListener {
            analysisViewModel.changeMenuId(LAST_SEVEN_DAYS)
        }
        binding.btnLastThirtyDays.setOnClickListener {
            analysisViewModel.changeMenuId(LAST_THIRTY_DAYS)
        }
    }

    private fun setupPieChart(pieEntries: List<PieEntry>, pieColors: List<Int>) {
        val pieDataSet = PieDataSet(pieEntries, resources.getString(R.string.expense_category))
        pieDataSet.setDrawValues(false)
        pieDataSet.xValuePosition = PieDataSet.ValuePosition.OUTSIDE_SLICE
        pieDataSet.colors = pieColors

        val pieData = PieData(pieDataSet)
        binding.apply {
            pieChart.data = pieData
            pieChart.description.isEnabled = false
            pieChart.legend.isEnabled = false
            pieChart.isHighlightPerTapEnabled = false
            pieChart.isDrawHoleEnabled = true
            pieChart.holeRadius = 50f
            pieChart.transparentCircleRadius = 0f
            pieChart.setEntryLabelColor(Color.BLACK)
            pieChart.setEntryLabelTextSize(10f)
            pieChart.setExtraOffsets(12f, 12f, 12f, 12f)
            pieChart.invalidate()
        }
    }

    private fun getPieEntries(data: List<Example>): List<PieEntry> {
        val pieEntries = ArrayList<PieEntry>()
        for (i in data.indices) {
            pieEntries.add(PieEntry(data[i].amount.toFloat(), data[i].category))
        }
        return pieEntries
    }

    private fun getPieSliceColors(data: List<Example>): List<Int> {
        val pieSliceColors = ArrayList<Int>()
        for (i in data.indices) {
            pieSliceColors.add(Color.parseColor(data[i].category.getColorResource()))
        }
        return pieSliceColors
    }

    private fun setupAdapter() {
        analysisAdapter = AnalysisAdapter()
        binding.rvAnalysis.layoutManager = LinearLayoutManager(requireContext())
        binding.rvAnalysis.adapter = analysisAdapter
    }

    private fun setupObservers() {
        analysisViewModel.isFirstTime.observe(viewLifecycleOwner) { isFirstTime ->
            if (isFirstTime) {
                binding.pieChart.animateY(1000)
                analysisViewModel.changeFirstTimeValue()
            }
        }

        analysisViewModel.thisMonthButtonStyle.observe(viewLifecycleOwner) { background ->
            setupButtonBackground(binding.btnThisMonth, background)
        }
        analysisViewModel.lastSevenDaysButtonStyle.observe(viewLifecycleOwner) { background ->
            setupButtonBackground(binding.btnLastSevenDays, background)
        }
        analysisViewModel.lastThirtyDaysButtonStyle.observe(viewLifecycleOwner) { background ->
            setupButtonBackground(binding.btnLastThirtyDays, background)
        }
    }

    private fun setupButtonBackground(button: TextView, background: Int) {
        button.background = ContextCompat.getDrawable(requireContext(), background)
    }

    companion object {
        const val THIS_MONTH = 1
        const val LAST_SEVEN_DAYS = 2
        const val LAST_THIRTY_DAYS = 3
    }
}
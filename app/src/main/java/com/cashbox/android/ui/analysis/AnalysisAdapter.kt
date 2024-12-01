package com.cashbox.android.ui.analysis

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cashbox.android.data.model.Example
import com.cashbox.android.databinding.ItemAnalysisBinding
import com.cashbox.android.utils.NumberFormatHelper
import com.cashbox.android.utils.getImageResource

class AnalysisAdapter : ListAdapter<Example, AnalysisAdapter.ItemViewHolder>(
    object : DiffUtil.ItemCallback<Example>() {
        override fun areItemsTheSame(oldItem: Example, newItem: Example): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Example, newItem: Example): Boolean {
            return oldItem == newItem
        }
    }
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemAnalysisBinding.inflate(inflater, parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    fun getTotalAmount(): Long {
        return currentList.sumOf { it.amount }
    }

    inner class ItemViewHolder(
        private val itemBinding: ItemAnalysisBinding
    ) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(data: Example) {
            itemBinding.ivCategory.setImageResource(data.category.getImageResource())
            itemBinding.tvTitle.text = data.category
            itemBinding.tvAmount.text = NumberFormatHelper.formatToRupiah(data.amount)

            val percentage = ((data.amount.toDouble() / getTotalAmount()) * 100)
            itemBinding.tvPercentage.text = "(${String.format("%.2f", percentage)}%)"
        }
    }
}
package com.cashbox.android.ui.transaction

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.cashbox.android.R
import com.cashbox.android.data.model.TransactionData
import com.cashbox.android.databinding.ItemTransactionBinding
import com.cashbox.android.utils.DateHelper
import com.cashbox.android.utils.NumberFormatHelper
import com.cashbox.android.utils.getImageResource
import com.cashbox.android.utils.toExpenseCategoryText
import com.cashbox.android.utils.toIncomeCategoryText

class TransactionAdapter(
    private val onItemClickListener: OnItemClickListener
) : ListAdapter<TransactionData, TransactionAdapter.ItemViewHolder>(
    object : DiffUtil.ItemCallback<TransactionData>() {
        override fun areItemsTheSame(oldItem: TransactionData, newItem: TransactionData): Boolean {
            return oldItem.transactionId == newItem.transactionId
        }

        override fun areContentsTheSame(
            oldItem: TransactionData,
            newItem: TransactionData
        ): Boolean {
            return oldItem == newItem
        }
    }
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemTransactionBinding.inflate(inflater, parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ItemViewHolder(
        private val itemBinding: ItemTransactionBinding
    ) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(data: TransactionData) {
            if (data.transactionType == "pemasukan") {
                itemBinding.apply {
                    Glide.with(itemView)
                        .load(data.category.toIncomeCategoryText().getImageResource())
                        .transform(CircleCrop())
                        .into(ivCategory)
                    tvSign.text = "+"
                    tvSign.setTextColor(ContextCompat.getColor(itemView.context, R.color.green))
                    tvAmount.setTextColor(ContextCompat.getColor(itemView.context, R.color.green))
                }
            } else {
                itemBinding.apply {
                    Glide.with(itemView)
                        .load(data.category.toExpenseCategoryText().getImageResource())
                        .transform(CircleCrop())
                        .into(ivCategory)
                    tvSign.text = "-"
                    tvSign.setTextColor(ContextCompat.getColor(itemView.context, R.color.red))
                    tvAmount.setTextColor(ContextCompat.getColor(itemView.context, R.color.red))
                }
            }

            itemBinding.tvTitle.text = data.description
            itemBinding.tvDate.text = DateHelper
                .convertDateToIndonesianFormat(data.date.substring(0, 10))
            itemBinding.tvAmount.text = NumberFormatHelper.formatToRupiah(data.amount)
            itemBinding.tvSource.text = data.sourceName

            itemBinding.root.setOnClickListener {
                onItemClickListener.onItemClick()
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick()
    }
}
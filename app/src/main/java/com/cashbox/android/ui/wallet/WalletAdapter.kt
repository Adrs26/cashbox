package com.cashbox.android.ui.wallet

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cashbox.android.databinding.ItemWalletBinding

class WalletAdapter(
    private val onItemClickListener: OnItemClickListener
) : ListAdapter<Int, WalletAdapter.ItemViewHolder>(
    object : DiffUtil.ItemCallback<Int>() {
        override fun areItemsTheSame(oldItem: Int, newItem: Int): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Int, newItem: Int): Boolean {
            return oldItem == newItem
        }
    }
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemWalletBinding.inflate(inflater, parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ItemViewHolder(
        private val itemBinding: ItemWalletBinding
    ) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(data: Int) {
            itemBinding.root.setOnClickListener {
                onItemClickListener.onItemClick()
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick()
    }
}

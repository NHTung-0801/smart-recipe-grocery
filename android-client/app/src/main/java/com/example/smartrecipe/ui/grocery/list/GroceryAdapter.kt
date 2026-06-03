package com.example.smartrecipe.ui.grocery.list

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.smartrecipe.databinding.ItemGroceryBinding
import com.example.smartrecipe.domain.model.GroceryItem

class GroceryAdapter(
    private val onItemChecked: (GroceryItem) -> Unit
) : ListAdapter<GroceryItem, GroceryAdapter.GroceryViewHolder>(GroceryDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroceryViewHolder {
        val binding = ItemGroceryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GroceryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GroceryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class GroceryViewHolder(private val binding: ItemGroceryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: GroceryItem) {
            binding.tvGroceryName.text = item.name

            // Xử lý hiển thị số lượng (nếu là số nguyên thì bỏ phần .0 đi cho đẹp)
            val amountStr = if (item.amount % 1.0 == 0.0) item.amount.toInt().toString() else item.amount.toString()
            binding.tvGroceryAmount.text = "$amountStr ${item.unit}"

            // Ngắt sự kiện lắng nghe cũ trước khi đổi trạng thái checkbox
            binding.cbGrocery.setOnCheckedChangeListener(null)
            binding.cbGrocery.isChecked = item.isChecked

            // Gạch ngang chữ nếu đã mua xong
            if (item.isChecked) {
                binding.tvGroceryName.paintFlags = binding.tvGroceryName.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                binding.tvGroceryName.setTextColor(0xFF9E9E9E.toInt()) // Màu xám
            } else {
                binding.tvGroceryName.paintFlags = binding.tvGroceryName.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
                binding.tvGroceryName.setTextColor(0xFF212121.toInt()) // Màu đen
            }

            // Gửi sự kiện lên Fragment khi người dùng tick vào ô
            binding.cbGrocery.setOnCheckedChangeListener { _, _ ->
                onItemChecked(item)
            }
        }
    }

    class GroceryDiffCallback : DiffUtil.ItemCallback<GroceryItem>() {
        override fun areItemsTheSame(oldItem: GroceryItem, newItem: GroceryItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: GroceryItem, newItem: GroceryItem): Boolean {
            return oldItem == newItem
        }
    }
}
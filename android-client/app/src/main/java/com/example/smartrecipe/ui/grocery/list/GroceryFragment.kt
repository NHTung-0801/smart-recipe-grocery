package com.example.smartrecipe.ui.grocery.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.smartrecipe.core.base.BaseFragment
import com.example.smartrecipe.databinding.FragmentGroceryBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class GroceryFragment : BaseFragment<FragmentGroceryBinding>() {

    private val viewModel: GroceryViewModel by viewModels()
    private lateinit var adapter: GroceryAdapter

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentGroceryBinding
        get() = FragmentGroceryBinding::inflate

    override fun setupViews() {
        // 1. Khởi tạo Adapter và truyền sự kiện click Checkbox về ViewModel
        adapter = GroceryAdapter { item ->
            viewModel.toggleCheck(item)
        }

        // 2. Cấu hình RecyclerView
        binding.rvGroceries.layoutManager = LinearLayoutManager(requireContext())
        binding.rvGroceries.adapter = adapter

        // 3. Xử lý nút dọn dẹp giỏ hàng (Xóa các món đã đánh dấu xám)
        binding.btnClearChecked.setOnClickListener {
            viewModel.clearCheckedItems()
        }

        // Xử lý nút tạo dữ liệu Test
        binding.btnMockData.setOnClickListener {
            viewModel.generateMockData()
        }

    }

    override fun observeData() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                // Lắng nghe danh sách mới và đẩy vào Adapter
                viewModel.groceries.collect { list ->
                    adapter.submitList(list)
                }
            }
        }
    }
}
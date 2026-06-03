package com.example.smartrecipe.ui.recipe.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.smartrecipe.R
import com.example.smartrecipe.core.base.BaseFragment
import com.example.smartrecipe.databinding.FragmentRecipeDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RecipeDetailFragment : BaseFragment<FragmentRecipeDetailBinding>() {

    private val viewModel: RecipeDetailViewModel by viewModels()
    private var currentRecipeId: Long = -1L

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentRecipeDetailBinding
        get() = FragmentRecipeDetailBinding::inflate

    override fun setupViews() {
        // Nhận ID truyền từ màn hình Danh sách (đã thiết lập trong nav_graph)
        currentRecipeId = arguments?.getLong("recipeId", -1L) ?: -1L

        if (currentRecipeId != -1L) {
            viewModel.loadRecipeDetails(currentRecipeId)
        } else {
            Toast.makeText(requireContext(), "Không tìm thấy công thức!", Toast.LENGTH_SHORT).show()
            findNavController().popBackStack()
        }

        // Xử lý nút Chỉnh sửa
        binding.btnEdit.setOnClickListener {
            val bundle = Bundle().apply { putLong("recipeId", currentRecipeId) }
            findNavController().navigate(R.id.action_recipeDetail_to_recipeEdit, bundle)
        }

        // Xử lý nút Bắt đầu nấu (Đã gộp chung logic kiểm tra dữ liệu an toàn)
        binding.btnCookingMode.setOnClickListener {
            val currentRecipe = viewModel.recipe.value
            if (currentRecipe != null) {
                // Đóng gói ID món ăn và chuyển hướng sang CookingModeFragment
                val bundle = Bundle().apply {
                    putLong("recipeId", currentRecipe.id)
                }
                findNavController().navigate(R.id.action_recipeDetail_to_cookingMode, bundle)
            } else {
                Toast.makeText(requireContext(), "Dữ liệu chưa tải xong", Toast.LENGTH_SHORT).show()
            }
        }

        // Luồng 2: Người dùng thiếu đồ -> Thêm vào danh sách Đi chợ
        binding.btnAddGrocery.setOnClickListener {
            val currentRecipe = viewModel.recipe.value
            if (currentRecipe != null) {
                // Gọi ViewModel để kích hoạt UseCase thuật toán
                viewModel.addRecipeToGroceryList(currentRecipe)
                Toast.makeText(requireContext(), "Đã thêm vào Danh sách đi chợ!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Dữ liệu chưa tải xong", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun observeData() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.recipe.collect { recipe ->
                    recipe?.let {
                        // Cập nhật giao diện khi có dữ liệu
                        binding.tvTitle.text = it.title
                        binding.tvInfo.text = "⏱ ${it.prepTime} phút   •   🔥 ${it.calories} Calo"
                    }
                }
            }
        }
    }
}
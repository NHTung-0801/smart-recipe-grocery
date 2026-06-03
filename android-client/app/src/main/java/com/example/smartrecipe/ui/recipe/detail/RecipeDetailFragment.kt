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

        // Xử lý nút Bắt đầu nấu
        binding.btnCookingMode.setOnClickListener {
            val bundle = Bundle().apply { putLong("recipeId", currentRecipeId) }
            findNavController().navigate(R.id.action_recipeDetail_to_cookingMode, bundle)
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
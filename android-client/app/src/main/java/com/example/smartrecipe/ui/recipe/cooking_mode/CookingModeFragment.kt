package com.example.smartrecipe.ui.recipe.cooking_mode

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.smartrecipe.core.base.BaseFragment
import com.example.smartrecipe.databinding.FragmentCookingModeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CookingModeFragment : BaseFragment<FragmentCookingModeBinding>() {

    private val viewModel: CookingModeViewModel by viewModels()

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentCookingModeBinding
        get() = FragmentCookingModeBinding::inflate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Yêu cầu giữ màn hình luôn sáng khi vào chế độ này
        requireActivity().window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Tắt chế độ luôn sáng khi thoát Fragment để thiết bị tự tắt màn hình bình thường
        requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
    }

    override fun setupViews() {
        val recipeId = arguments?.getLong("recipeId", -1L) ?: -1L
        if (recipeId != -1L) {
            viewModel.loadRecipe(recipeId)
        }

        binding.btnDone.setOnClickListener {
            // Khi nấu xong, quay về màn hình Chi tiết công thức
            findNavController().popBackStack()
        }
    }

    override fun observeData() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.recipe.collect { recipe ->
                    recipe?.let {
                        binding.tvCookingTitle.text = it.title
                        binding.tvStep.text = "Hướng dẫn chi tiết:\n(Tạm thời hiển thị giao diện mẫu. Tính năng tách bóc từng bước nấu sẽ được tích hợp khi chúng ta xử lý Dữ liệu Nguyên liệu ở Phase 2)"
                    }
                }
            }
        }
    }
}
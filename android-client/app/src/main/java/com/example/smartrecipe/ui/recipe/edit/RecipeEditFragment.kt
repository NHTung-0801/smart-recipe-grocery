package com.example.smartrecipe.ui.recipe.edit

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.smartrecipe.core.base.BaseFragment
import com.example.smartrecipe.databinding.FragmentRecipeEditBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RecipeEditFragment : BaseFragment<FragmentRecipeEditBinding>() {

    private val viewModel: RecipeEditViewModel by viewModels()

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentRecipeEditBinding
        get() = FragmentRecipeEditBinding::inflate

    override fun setupViews() {
        binding.btnSave.setOnClickListener {
            val title = binding.etTitle.text.toString()
            val time = binding.etTime.text.toString()
            val calo = binding.etCalories.text.toString()

            if (title.isBlank()) {
                Toast.makeText(requireContext(), "Vui lòng nhập tên món ăn", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Gọi ViewModel để lưu xuống Room Database
            viewModel.saveRecipe(title, time, calo)
        }
    }

    override fun observeData() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                // Lắng nghe sự kiện lưu thành công
                viewModel.saveSuccess.collect {
                    Toast.makeText(requireContext(), "Đã lưu thành công!", Toast.LENGTH_SHORT).show()
                    findNavController().popBackStack() // Quay trở lại màn hình danh sách
                }
            }
        }
    }
}
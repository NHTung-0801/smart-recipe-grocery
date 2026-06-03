package com.example.smartrecipe.ui.recipe.edit

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.smartrecipe.android_client.domain.model.Ingredient
import com.example.smartrecipe.core.base.BaseFragment
import com.example.smartrecipe.databinding.FragmentRecipeEditBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RecipeEditFragment : BaseFragment<FragmentRecipeEditBinding>() {

    private val viewModel: RecipeEditViewModel by viewModels()
    private val ingredientAdapter = EditIngredientAdapter()

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentRecipeEditBinding
        get() = FragmentRecipeEditBinding::inflate

    override fun setupViews() {
        // Cấu hình danh sách nguyên liệu
        binding.rvIngredientsInput.layoutManager = LinearLayoutManager(requireContext())
        binding.rvIngredientsInput.adapter = ingredientAdapter

        // Nút Thêm nguyên liệu vào danh sách tạm
        binding.btnAddIngredient.setOnClickListener {
            val name = binding.etIngredientName.text.toString().trim()
            val amount = binding.etIngredientAmount.text.toString().toDoubleOrNull() ?: 0.0
            val unit = binding.etIngredientUnit.text.toString().trim()

            if (name.isNotBlank()) {
                viewModel.addIngredient(name, amount, unit)
                // Xóa trắng ô nhập
                binding.etIngredientName.text?.clear()
                binding.etIngredientAmount.text?.clear()
                binding.etIngredientUnit.text?.clear()
            } else {
                Toast.makeText(requireContext(), "Vui lòng nhập tên nguyên liệu", Toast.LENGTH_SHORT).show()
            }
        }

        // Nút Lưu toàn bộ công thức
        binding.btnSave.setOnClickListener {
            val title = binding.etTitle.text.toString().trim()
            val time = binding.etTime.text.toString().trim()
            val calo = binding.etCalories.text.toString().trim()

            if (title.isBlank()) {
                Toast.makeText(requireContext(), "Vui lòng nhập tên món ăn", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            viewModel.saveRecipe(title, time, calo)
        }
    }

    override fun observeData() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                // Lắng nghe sự kiện lưu thành công
                launch {
                    viewModel.saveSuccess.collect {
                        Toast.makeText(requireContext(), "Đã lưu thành công!", Toast.LENGTH_SHORT).show()
                        findNavController().popBackStack()
                    }
                }

                // Lắng nghe danh sách nguyên liệu thay đổi
                launch {
                    viewModel.ingredients.collect { list ->
                        ingredientAdapter.submitList(list)
                    }
                }
            }
        }
    }
}

// Adapter dành riêng cho danh sách nguyên liệu trong lúc tạo món ăn
class EditIngredientAdapter : ListAdapter<Ingredient, EditIngredientAdapter.ViewHolder>(DiffCallback()) {
    class ViewHolder(val textView: TextView) : RecyclerView.ViewHolder(textView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val textView = TextView(parent.context).apply {
            textSize = 16f
            setPadding(16, 16, 16, 16)
        }
        return ViewHolder(textView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.textView.text = "• ${item.name}: ${item.amount} ${item.unit}"
    }

    class DiffCallback : DiffUtil.ItemCallback<Ingredient>() {
        override fun areItemsTheSame(oldItem: Ingredient, newItem: Ingredient) = oldItem.name == newItem.name
        override fun areContentsTheSame(oldItem: Ingredient, newItem: Ingredient) = oldItem == newItem
    }
}
package com.example.smartrecipe.ui.recipe.edit

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.smartrecipe.R // Đã bổ sung import R
import com.example.smartrecipe.android_client.domain.model.Ingredient
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
        // 1. Mặc định mở lên sẽ có sẵn 1 dòng nhập liệu đầu tiên cho người dùng
        addIngredientRowView()

        // 2. Lắng nghe nút Thêm Nguyên Liệu (đã đưa ra ngoài)
        binding.btnAddIngredient.setOnClickListener {
            addIngredientRowView()
        }

        // 3. Xử lý sự kiện nhấn nút Lưu Công Thức
        binding.btnSave.setOnClickListener {
            val title = binding.etTitle.text.toString().trim()
            val time = binding.etTime.text.toString().trim()
            val calo = binding.etCalories.text.toString().trim()

            // Kiểm tra điều kiện bắt buộc
            if (title.isBlank()) {
                Toast.makeText(requireContext(), "Vui lòng nhập tên món ăn", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Quét qua toàn bộ các dòng nguyên liệu trên màn hình để thu thập dữ liệu
            val ingredientList = mutableListOf<Ingredient>()
            for (i in 0 until binding.llIngredientsContainer.childCount) {
                val view = binding.llIngredientsContainer.getChildAt(i)
                val name = view.findViewById<EditText>(R.id.etIngName).text.toString().trim()
                val amountStr = view.findViewById<EditText>(R.id.etIngAmount).text.toString().trim()
                val unit = view.findViewById<EditText>(R.id.etIngUnit).text.toString().trim()

                // Chỉ lấy những dòng có nhập đủ thông tin
                if (name.isNotEmpty() && amountStr.isNotEmpty() && unit.isNotEmpty()) {
                    val amount = amountStr.toDoubleOrNull() ?: 0.0
                    ingredientList.add(Ingredient(name = name, amount = amount, unit = unit))
                }
            }

            // Gọi ViewModel để lưu xuống Room Database
            // Hiện tại tạm giữ hàm cũ để code không báo đỏ.
            // Ở bước tiếp theo, ta sẽ thay dòng này bằng: viewModel.saveRecipe(title, time, calo, ingredientList)
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

    private fun addIngredientRowView() {
        // Lấy khuôn mẫu XML ra
        val view = layoutInflater.inflate(R.layout.item_ingredient_input, binding.llIngredientsContainer, false)

        // Cài đặt nút Xóa cho chính dòng đó
        val btnRemove = view.findViewById<ImageButton>(R.id.btnRemove)
        btnRemove.setOnClickListener {
            binding.llIngredientsContainer.removeView(view)
        }

        // Nhét vào hộp chứa
        binding.llIngredientsContainer.addView(view)
    }
}
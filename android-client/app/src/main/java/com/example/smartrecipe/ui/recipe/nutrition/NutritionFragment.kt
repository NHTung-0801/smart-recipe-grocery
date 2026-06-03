package com.example.smartrecipe.ui.recipe.nutrition

import android.R
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.smartrecipe.android_client.core.utils.NutritionCalculator
import com.example.smartrecipe.core.base.BaseFragment
import com.example.smartrecipe.databinding.FragmentNutritionBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NutritionFragment : BaseFragment<FragmentNutritionBinding>() {

    private val viewModel: NutritionViewModel by viewModels()

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentNutritionBinding
        get() = FragmentNutritionBinding::inflate

    override fun setupViews() {
        // 1. Cấu hình danh sách Mức độ vận động cho Spinner
        val activityOptions = arrayOf(
            "Ít vận động (Việc văn phòng)",
            "Vận động nhẹ (Tập 1-3 ngày/tuần)",
            "Vận động vừa (Tập 3-5 ngày/tuần)",
            "Vận động nhiều (Tập 6-7 ngày/tuần)",
            "Vận động cực nhiều (Ngày 2 lần)"
        )
        // Hệ số tương ứng theo công thức chuẩn y khoa
        val activityMultipliers = arrayOf(1.2, 1.375, 1.55, 1.725, 1.9)

        val adapter =
            ArrayAdapter(requireContext(), R.layout.simple_spinner_dropdown_item, activityOptions)
        binding.spinnerActivity.adapter = adapter
        binding.spinnerActivity.setSelection(2) // Chọn mặc định là "Vận động vừa"

        // 2. Xử lý sự kiện bấm nút Cập nhật
        binding.btnUpdateTDEE.setOnClickListener {
            val weight = binding.etWeight.text.toString().toDoubleOrNull()
            val height = binding.etHeight.text.toString().toDoubleOrNull()
            val age = binding.etAge.text.toString().toIntOrNull()

            // Lấy thông tin Giới tính và Mức độ vận động từ giao diện
            val isMale = binding.rbMale.isChecked
            val selectedActivityIndex = binding.spinnerActivity.selectedItemPosition
            val activityMultiplier = activityMultipliers[selectedActivityIndex]

            if (weight != null && height != null && age != null) {
                // Truyền toàn bộ biến động vào ViewModel
                viewModel.updateGoalsFromTDEE(weight, height, age, isMale, activityMultiplier)
                Toast.makeText(requireContext(), "Đã cập nhật mục tiêu Macro thành công!", Toast.LENGTH_SHORT).show()

                binding.etWeight.text?.clear()
                binding.etHeight.text?.clear()
                binding.etAge.text?.clear()
            } else {
                Toast.makeText(requireContext(), "Vui lòng nhập đầy đủ thông tin hợp lệ", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun observeData() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {

                // Theo dõi Calo
                launch {
                    combine(viewModel.consumedCalories, viewModel.dailyCalorieGoal) { consumed, goal ->
                        Pair(consumed, goal)
                    }.collect { (consumed, goal) ->
                        binding.tvCalorieText.text = "$consumed / $goal Calo"
                        binding.progressCalories.progress = NutritionCalculator.calculateProgressPercentage(consumed, goal)
                    }
                }

                // Theo dõi Protein
                launch {
                    combine(viewModel.consumedProtein, viewModel.dailyProteinGoal) { consumed, goal ->
                        Pair(consumed, goal)
                    }.collect { (consumed, goal) ->
                        binding.tvProteinText.text = "${consumed}g / ${goal}g"
                        binding.progressProtein.progress = NutritionCalculator.calculateProgressPercentage(consumed, goal)
                    }
                }

                // Theo dõi Carbs
                launch {
                    combine(viewModel.consumedCarbs, viewModel.dailyCarbsGoal) { consumed, goal ->
                        Pair(consumed, goal)
                    }.collect { (consumed, goal) ->
                        binding.tvCarbsText.text = "${consumed}g / ${goal}g"
                        binding.progressCarbs.progress = NutritionCalculator.calculateProgressPercentage(consumed, goal)
                    }
                }

                // Theo dõi Fat
                launch {
                    combine(viewModel.consumedFat, viewModel.dailyFatGoal) { consumed, goal ->
                        Pair(consumed, goal)
                    }.collect { (consumed, goal) ->
                        binding.tvFatText.text = "${consumed}g / ${goal}g"
                        binding.progressFat.progress = NutritionCalculator.calculateProgressPercentage(consumed, goal)
                    }
                }
            }
        }
    }
}
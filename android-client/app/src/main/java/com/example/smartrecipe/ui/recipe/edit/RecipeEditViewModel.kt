package com.example.smartrecipe.ui.recipe.edit

import androidx.lifecycle.viewModelScope
import com.example.smartrecipe.android_client.domain.model.Recipe
import com.example.smartrecipe.android_client.domain.repository.IRecipeRepository
import com.example.smartrecipe.core.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeEditViewModel @Inject constructor(
    private val repository: IRecipeRepository
) : BaseViewModel() {

    // Bắn sự kiện khi lưu thành công để Fragment biết đường quay lại màn hình trước
    private val _saveSuccess = MutableSharedFlow<Unit>()
    val saveSuccess: SharedFlow<Unit> = _saveSuccess

    fun saveRecipe(title: String, timeText: String, caloText: String) {
        if (title.isBlank()) return // Tạm thời bỏ qua nếu tên trống

        val recipe = Recipe(
            title = title,
            description = "", // Tạm thời để trống
            instructions = "", // Tạm thời để trống
            prepTime = timeText.toIntOrNull() ?: 0,
            defaultServings = 1,
            calories = caloText.toIntOrNull() ?: 0,
            imageUrl = null
        )

        viewModelScope.launch(exceptionHandler) {
            repository.saveRecipe(recipe)
            _saveSuccess.emit(Unit) // Báo hiệu đã lưu xong!
        }
    }
}
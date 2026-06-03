package com.example.smartrecipe.ui.recipe.edit

import androidx.lifecycle.viewModelScope
import com.example.smartrecipe.android_client.domain.model.Ingredient
import com.example.smartrecipe.android_client.domain.model.Recipe
import com.example.smartrecipe.android_client.domain.repository.IRecipeRepository
import com.example.smartrecipe.core.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeEditViewModel @Inject constructor(
    private val repository: IRecipeRepository
) : BaseViewModel() {

    private val _saveSuccess = MutableSharedFlow<Unit>()
    val saveSuccess: SharedFlow<Unit> = _saveSuccess

    private val _ingredients = MutableStateFlow<List<Ingredient>>(emptyList())
    val ingredients: StateFlow<List<Ingredient>> = _ingredients.asStateFlow()

    fun addIngredient(name: String, amount: Double, unit: String) {
        val currentList = _ingredients.value.toMutableList()
        currentList.add(Ingredient(name = name, amount = amount, unit = unit))
        _ingredients.value = currentList
    }

    fun saveRecipe(title: String, timeText: String, caloText: String) {
        if (title.isBlank()) return

        val recipe = Recipe(
            title = title,
            description = "",
            instructions = "",
            prepTime = timeText.toIntOrNull() ?: 0,
            defaultServings = 1,
            calories = caloText.toIntOrNull() ?: 0,
            imageUrl = null,
            ingredients = _ingredients.value
        )

        viewModelScope.launch(exceptionHandler) {
            repository.saveRecipe(recipe)
            _saveSuccess.emit(Unit)
        }
    }
}
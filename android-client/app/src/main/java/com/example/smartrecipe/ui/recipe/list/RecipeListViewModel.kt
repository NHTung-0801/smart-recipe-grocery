package com.example.smartrecipe.ui.recipe.list

import androidx.lifecycle.viewModelScope
import com.example.smartrecipe.android_client.domain.model.Recipe
import com.example.smartrecipe.android_client.domain.repository.IRecipeRepository
import com.example.smartrecipe.core.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeListViewModel @Inject constructor(
    private val repository: IRecipeRepository
) : BaseViewModel() {

    // Trạng thái chứa danh sách công thức, UI sẽ lắng nghe biến này
    private val _recipes = MutableStateFlow<List<Recipe>>(emptyList())
    val recipes: StateFlow<List<Recipe>> = _recipes.asStateFlow()

    init {
        loadRecipes()
    }

    private fun loadRecipes() {
        // Sử dụng exceptionHandler từ BaseViewModel để bắt lỗi an toàn
        viewModelScope.launch(exceptionHandler) {
            repository.getAllRecipes()
                .catch { e -> throw e }
                .collect { recipeList ->
                    _recipes.value = recipeList
                }
        }
    }
}
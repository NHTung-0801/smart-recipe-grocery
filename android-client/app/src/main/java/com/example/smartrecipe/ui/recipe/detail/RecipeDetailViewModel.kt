package com.example.smartrecipe.ui.recipe.detail

import androidx.lifecycle.viewModelScope
import com.example.smartrecipe.android_client.domain.model.Recipe
import com.example.smartrecipe.android_client.domain.repository.IRecipeRepository
import com.example.smartrecipe.core.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeDetailViewModel @Inject constructor(
    private val repository: IRecipeRepository
) : BaseViewModel() {

    private val _recipe = MutableStateFlow<Recipe?>(null)
    val recipe: StateFlow<Recipe?> = _recipe.asStateFlow()

    fun loadRecipeDetails(id: Long) {
        viewModelScope.launch(exceptionHandler) {
            val result = repository.getRecipeById(id)
            _recipe.value = result
        }
    }
}
package com.example.smartrecipe.ui.recipe.cooking_mode

import androidx.lifecycle.viewModelScope
import com.example.smartrecipe.android_client.domain.model.Recipe
import com.example.smartrecipe.android_client.domain.repository.IRecipeRepository
import com.example.smartrecipe.core.base.BaseViewModel
import com.example.smartrecipe.domain.usecase.journal.AddJournalEntryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CookingModeViewModel @Inject constructor(
    private val repository: IRecipeRepository,
    private val addJournalEntryUseCase: AddJournalEntryUseCase
) : BaseViewModel() {

    private val _recipe = MutableStateFlow<Recipe?>(null)
    val recipe: StateFlow<Recipe?> = _recipe.asStateFlow()

    fun loadRecipe(id: Long) {
        viewModelScope.launch(exceptionHandler) {
            _recipe.value = repository.getRecipeById(id)
        }
    }

    private val _finishEvent = MutableSharedFlow<Unit>()
    val finishEvent: SharedFlow<Unit> = _finishEvent

    fun finishCooking() {
        val currentRecipe = _recipe.value ?: return
        viewModelScope.launch(exceptionHandler) {
            // Ghi vào nhật ký
            addJournalEntryUseCase(currentRecipe.title, currentRecipe.calories)
            _finishEvent.emit(Unit) // Báo cho Fragment biết để thoát
        }
    }

}
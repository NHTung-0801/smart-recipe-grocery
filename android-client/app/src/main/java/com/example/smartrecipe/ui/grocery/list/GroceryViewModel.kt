package com.example.smartrecipe.ui.grocery.list

import androidx.lifecycle.viewModelScope
import com.example.smartrecipe.core.base.BaseViewModel
import com.example.smartrecipe.domain.model.GroceryItem
import com.example.smartrecipe.domain.repository.IGroceryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GroceryViewModel @Inject constructor(
    private val repository: IGroceryRepository
) : BaseViewModel() {

    private val _groceries = MutableStateFlow<List<GroceryItem>>(emptyList())
    val groceries = _groceries.asStateFlow()

    init {
        loadGroceries()
    }

    private fun loadGroceries() {
        viewModelScope.launch(exceptionHandler) {
            // Lắng nghe dữ liệu realtime từ Room DB
            repository.getAllGroceries().collect { list ->
                _groceries.value = list
            }
        }
    }

    fun toggleCheck(item: GroceryItem) {
        viewModelScope.launch(exceptionHandler) {
            repository.toggleCheckStatus(item)
        }
    }

    fun clearCheckedItems() {
        viewModelScope.launch(exceptionHandler) {
            repository.clearChecked()
        }
    }
}
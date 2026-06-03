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
    // Hàm tạm thời để test giao diện
    fun generateMockData() {
        viewModelScope.launch {
            val mockItems = listOf(
                GroceryItem(name = "Thịt ba chỉ heo", amount = 500.0, unit = "g"),
                GroceryItem(name = "Trứng cút", amount = 20.0, unit = "quả"),
                GroceryItem(name = "Nước dừa tươi", amount = 1.0, unit = "lít"),
                GroceryItem(name = "Hành tím", amount = 3.0, unit = "củ")
            )
            // Đẩy thẳng xuống Database, Flow sẽ tự động nhả dữ liệu ngược lên UI
            repository.addGroceries(mockItems)
        }
    }
}
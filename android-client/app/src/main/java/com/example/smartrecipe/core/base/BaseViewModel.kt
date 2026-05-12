package com.example.smartrecipe.core.base
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

abstract class BaseViewModel : ViewModel() {

    private val _errorFlow = MutableSharedFlow<Throwable>(extraBufferCapacity = 1)
    val errorFlow: SharedFlow<Throwable> get() = _errorFlow

    protected val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        _errorFlow.tryEmit(exception)
    }
}
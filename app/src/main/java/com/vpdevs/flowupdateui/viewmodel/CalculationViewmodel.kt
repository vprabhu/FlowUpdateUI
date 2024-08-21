package com.vpdevs.flowupdateui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vpdevs.flowupdateui.UiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class CalculationViewmodel() : ViewModel() {

    private val _result = MutableStateFlow<UiState>(UiState.InitValue)
    val result = _result.asStateFlow()

    fun getResult() {
        viewModelScope.launch {
            getSimpleFlow()
                .onStart {
                    _result.value = UiState.Loading
                }
                .onEach {
                    _result.value = UiState.Success(it.toString())
                }
                .onCompletion {
                    if (it != null) {
                        _result.value = UiState.Error(it.message.toString())
                    }
                }
                .collect { }
        }
    }
}


private fun getSimpleFlow() = flow<Int> {
    delay(2500)
    Log.d("HomeScreen", "getSimpleFlow")
    emit(10)
}

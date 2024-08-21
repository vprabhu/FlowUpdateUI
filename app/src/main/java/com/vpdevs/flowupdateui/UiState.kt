package com.vpdevs.flowupdateui


sealed class UiState {
    data object InitValue : UiState()
    data object Loading : UiState()
    data class Success(val stockList: String) : UiState()
    data class Error(val message: String) : UiState()
}
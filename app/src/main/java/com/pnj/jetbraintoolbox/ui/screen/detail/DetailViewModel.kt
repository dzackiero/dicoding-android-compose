package com.pnj.jetbraintoolbox.ui.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pnj.jetbraintoolbox.data.repository.JetbrainsRepository
import com.pnj.jetbraintoolbox.model.JetbrainsTool
import com.pnj.jetbraintoolbox.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailViewModel(
    private val repository: JetbrainsRepository,
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<JetbrainsTool>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<JetbrainsTool>>
        get() = _uiState

    fun getToolByName(name: String) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            _uiState.value = UiState.Success(repository.getToolByName(name))
        }
    }
}
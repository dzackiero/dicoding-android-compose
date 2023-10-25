package com.pnj.jetbraintoolbox.ui.screen.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pnj.jetbraintoolbox.data.repository.JetbrainsRepository
import com.pnj.jetbraintoolbox.model.JetbrainsTool
import com.pnj.jetbraintoolbox.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: JetbrainsRepository) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<List<JetbrainsTool>>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<JetbrainsTool>>>
        get() = _uiState

    fun getAllTools() {
        viewModelScope.launch {
            repository.getAllTools()
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect { tools ->
                    _uiState.value = UiState.Success(tools)
                }
        }
    }

    private val _query = mutableStateOf("")
    val query: State<String> get() = _query

    fun search(newQuery: String) {
        _query.value = newQuery
        _uiState.value = UiState.Success(
            repository.searchTools(_query.value)
                .sortedBy { it.name }
        )
    }
}
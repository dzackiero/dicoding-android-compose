package com.pnj.jetbraintoolbox.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.pnj.jetbraintoolbox.data.repository.JetbrainsRepository
import com.pnj.jetbraintoolbox.ui.screen.detail.DetailViewModel
import com.pnj.jetbraintoolbox.ui.screen.home.HomeViewModel

class ViewModelFactory(
    private val repository: JetbrainsRepository,
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(repository) as T
        }
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(repository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}
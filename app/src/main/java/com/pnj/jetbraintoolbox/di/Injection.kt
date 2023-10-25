package com.pnj.jetbraintoolbox.di

import com.pnj.jetbraintoolbox.data.repository.JetbrainsRepository


object Injection {
    fun provideRepository(): JetbrainsRepository {
        return JetbrainsRepository.getInstance()
    }
}
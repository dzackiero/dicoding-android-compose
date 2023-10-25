package com.pnj.jetbraintoolbox.data.repository

import com.pnj.jetbraintoolbox.model.FakeJetbrainsAppDataSource
import com.pnj.jetbraintoolbox.model.JetbrainsTool
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class JetbrainsRepository {
    private val toolList = FakeJetbrainsAppDataSource.appList

    fun searchTools(query: String): List<JetbrainsTool> {
        return toolList.filter {
            it.name.contains(query, ignoreCase = true)
        }
    }

    fun getAllTools(): Flow<List<JetbrainsTool>> {
        return flowOf(toolList)
    }

    fun getToolByName(name: String): JetbrainsTool {
        return toolList.first {
            it.name == name
        }
    }

    companion object {
        @Volatile
        private var instance: JetbrainsRepository? = null

        fun getInstance(): JetbrainsRepository =
            instance ?: synchronized(this) {
                JetbrainsRepository().apply {
                    instance = this
                }
            }
    }
}
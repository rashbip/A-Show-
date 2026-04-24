package com.biploborg.a_show.data.repository

import com.biploborg.a_show.data.PreferenceManager
import com.biploborg.a_show.domain.repository.PreferenceRepository
import kotlinx.coroutines.flow.Flow

class PreferenceRepositoryImpl(
    private val preferenceManager: PreferenceManager
) : PreferenceRepository {
    
    override fun getUserName(): Flow<String?> = preferenceManager.userName
    
    override fun isOnboardingCompleted(): Flow<Boolean> = preferenceManager.isOnboardingCompleted
    
    override suspend fun saveUserName(name: String) {
        preferenceManager.saveUserName(name)
    }

    override suspend fun setOnboardingCompleted(completed: Boolean) {
        preferenceManager.setOnboardingCompleted(completed)
    }
}

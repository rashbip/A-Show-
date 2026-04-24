package com.biploborg.a_show.domain.repository

import kotlinx.coroutines.flow.Flow

interface PreferenceRepository {
    fun getUserName(): Flow<String?>
    fun isOnboardingCompleted(): Flow<Boolean>
    suspend fun saveUserName(name: String)
    suspend fun setOnboardingCompleted(completed: Boolean)
}

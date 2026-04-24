package com.biploborg.a_show.ui.onboarding

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.biploborg.a_show.domain.repository.PreferenceRepository
import kotlinx.coroutines.launch

class OnboardingViewModel(private val preferenceRepository: PreferenceRepository) : ViewModel() {
    var name by mutableStateOf("")
        private set

    fun onNameChange(newName: String) {
        name = newName
    }

    fun completeOnboarding(onComplete: () -> Unit) {
        viewModelScope.launch {
            if (name.isNotBlank()) {
                preferenceRepository.saveUserName(name)
                preferenceRepository.setOnboardingCompleted(true)
                onComplete()
            }
        }
    }
}

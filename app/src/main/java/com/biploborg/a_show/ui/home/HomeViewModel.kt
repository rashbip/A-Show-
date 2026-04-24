package com.biploborg.a_show.ui.home

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.biploborg.a_show.domain.model.Countdown
import com.biploborg.a_show.domain.repository.PreferenceRepository
import com.biploborg.a_show.domain.usecase.GetCountdownUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HomeViewModel(
    private val preferenceRepository: PreferenceRepository,
    private val getCountdownUseCase: GetCountdownUseCase
) : ViewModel() {
    
    var userName by mutableStateOf("")
        private set

    var countdown by mutableStateOf(Countdown(0, 0, 0, 0))
        private set

    init {
        viewModelScope.launch {
            preferenceRepository.getUserName().collectLatest {
                userName = it ?: "Student"
            }
        }
        
        viewModelScope.launch {
            while (true) {
                countdown = getCountdownUseCase.execute()
                delay(1000)
            }
        }
    }
}

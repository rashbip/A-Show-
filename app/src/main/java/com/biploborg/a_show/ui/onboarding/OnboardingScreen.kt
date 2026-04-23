package com.biploborg.a_show.ui.onboarding

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.biploborg.a_show.data.PreferenceManager
import kotlinx.coroutines.launch

class OnboardingViewModel(private val preferenceManager: PreferenceManager) : ViewModel() {
    var name by mutableStateOf("")
        private set

    fun onNameChange(newName: String) {
        name = newName
    }

    fun completeOnboarding(onComplete: () -> Unit) {
        viewModelScope.launch {
            if (name.isNotBlank()) {
                preferenceManager.saveUserName(name)
                preferenceManager.setOnboardingCompleted(true)
                onComplete()
            }
        }
    }
}

@Composable
fun OnboardingScreen(
    viewModel: OnboardingViewModel,
    onComplete: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Welcome!",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Text(
            text = "Let's prepare for HSC 2026",
            fontSize = 18.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        
        Spacer(modifier = Modifier.height(48.dp))
        
        OutlinedTextField(
            value = viewModel.name,
            onValueChange = { viewModel.onNameChange(it) },
            label = { Text("What's your name?") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        Button(
            onClick = { viewModel.completeOnboarding(onComplete) },
            modifier = Modifier.fillMaxWidth(),
            enabled = viewModel.name.isNotBlank(),
            shape = MaterialTheme.shapes.medium
        ) {
            Text("Get Started", modifier = Modifier.padding(8.dp))
        }
    }
}

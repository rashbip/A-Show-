package com.biploborg.a_show.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import com.biploborg.a_show.AppConfig
import com.biploborg.a_show.data.PreferenceManager
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import java.util.Calendar

class HomeViewModel(private val preferenceManager: PreferenceManager) : ViewModel() {
    var userName by mutableStateOf("")
        private set

    var timeLeft by mutableStateOf(CountdownValue(0, 0, 0, 0))
        private set

    init {
        // Collect user name
        // In a real app, we'd use a repository or similar
        // For simplicity, we just collect here.
    }
    
    suspend fun startFlows() {
        preferenceManager.userName.collectLatest {
            userName = it ?: "Student"
        }
    }

    fun updateCountdown() {
        val now = Calendar.getInstance().timeInMillis
        val diff = AppConfig.EXAM_DATE.timeInMillis - now
        
        if (diff > 0) {
            val days = diff / (1000 * 60 * 60 * 24)
            val hours = (diff / (1000 * 60 * 60)) % 24
            val minutes = (diff / (1000 * 60)) % 60
            val seconds = (diff / 1000) % 60
            timeLeft = CountdownValue(days, hours, minutes, seconds)
        } else {
            timeLeft = CountdownValue(0, 0, 0, 0)
        }
    }
}

data class CountdownValue(val days: Long, val hours: Long, val minutes: Long, val seconds: Long)

@Composable
fun HomeScreen(viewModel: HomeViewModel) {
    LaunchedEffect(Unit) {
        viewModel.startFlows()
    }

    LaunchedEffect(Unit) {
        while(true) {
            viewModel.updateCountdown()
            delay(1000)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Spacer(modifier = Modifier.height(40.dp))
        
        Text(
            text = "Hello, ${viewModel.userName}!",
            fontSize = 24.sp,
            fontWeight = FontWeight.Medium
        )
        
        Spacer(modifier = Modifier.height(60.dp))
        
        Text(
            text = AppConfig.EXAM_NAME,
            fontSize = 18.sp,
            color = MaterialTheme.colorScheme.secondary
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Text(
            text = "Countdown",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold
        )
        
        Spacer(modifier = Modifier.height(32.dp))
        
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            CountdownItem(label = "Days", value = viewModel.timeLeft.days)
            CountdownItem(label = "Hours", value = viewModel.timeLeft.hours)
            CountdownItem(label = "Mins", value = viewModel.timeLeft.minutes)
            CountdownItem(label = "Secs", value = viewModel.timeLeft.seconds)
        }
    }
}

@Composable
fun CountdownItem(label: String, value: Long) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = value.toString().padStart(2, '0'),
            fontSize = 36.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = label,
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

package com.biploborg.a_show.ui.navigation

import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.*
import com.biploborg.a_show.data.PreferenceManager
import com.biploborg.a_show.ui.home.HomeScreen
import com.biploborg.a_show.ui.home.HomeViewModel
import com.biploborg.a_show.ui.onboarding.OnboardingScreen
import com.biploborg.a_show.ui.onboarding.OnboardingViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.flow.first

sealed class Screen(val route: String) {
    object Onboarding : Screen("onboarding")
    object Home : Screen("home")
}

@Composable
fun AppNavigation() {
    val context = LocalContext.current
    val preferenceManager = remember { PreferenceManager(context) }
    val navController = rememberNavController()
    
    var startDestination by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        val completed = preferenceManager.isOnboardingCompleted.first()
        startDestination = if (completed) Screen.Home.route else Screen.Onboarding.route
    }

    if (startDestination != null) {
        NavHost(navController = navController, startDestination = startDestination!!) {
            composable(Screen.Onboarding.route) {
                val viewModel: OnboardingViewModel = viewModel(
                    factory = object : ViewModelProvider.Factory {
                        override fun <T : ViewModel> create(modelClass: Class<T>): T {
                            return OnboardingViewModel(preferenceManager) as T
                        }
                    }
                )
                OnboardingScreen(
                    viewModel = viewModel,
                    onComplete = {
                        navController.navigate(Screen.Home.route) {
                            popUpTo(Screen.Onboarding.route) { inclusive = true }
                        }
                    }
                )
            }
            composable(Screen.Home.route) {
                val viewModel: HomeViewModel = viewModel(
                    factory = object : ViewModelProvider.Factory {
                        override fun <T : ViewModel> create(modelClass: Class<T>): T {
                            return HomeViewModel(preferenceManager) as T
                        }
                    }
                )
                HomeScreen(viewModel = viewModel)
            }
        }
    }
}

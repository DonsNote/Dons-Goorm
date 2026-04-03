package com.donsgoorm.app.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.donsgoorm.app.presentation.main.MainTabScreen
import com.donsgoorm.app.presentation.onboarding.DeceasedDateInputScreen
import com.donsgoorm.app.presentation.splash.SplashScreen

@Composable
fun AppNavigation() {
    var showSplash by remember { mutableStateOf(true) }
    var deceasedDate by remember { mutableStateOf<Long?>(null) }

    when {
        showSplash -> SplashScreen(onFinish = { showSplash = false })
        deceasedDate == null -> DeceasedDateInputScreen(onComplete = { date -> deceasedDate = date })
        else -> MainTabScreen(deceasedDateMillis = deceasedDate!!)
    }
}

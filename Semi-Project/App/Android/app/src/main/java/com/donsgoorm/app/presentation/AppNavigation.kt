package com.donsgoorm.app.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.donsgoorm.app.presentation.auth.AuthScreen
import com.donsgoorm.app.presentation.main.MainTabScreen
import com.donsgoorm.app.presentation.onboarding.ChecklistSummaryScreen
import com.donsgoorm.app.presentation.onboarding.DeceasedDateInputScreen
import com.donsgoorm.app.presentation.onboarding.OnboardingSlideScreen
import com.donsgoorm.app.presentation.splash.SplashScreen

private enum class AppStep {
    SPLASH, DECEASED_DATE_INPUT, CHECKLIST_SUMMARY, ONBOARDING, LOGIN, MAIN
}

@Composable
fun AppNavigation() {
    var step by remember { mutableStateOf(AppStep.SPLASH) }
    var deceasedDateMillis by remember { mutableStateOf(System.currentTimeMillis()) }

    when (step) {
        AppStep.SPLASH ->
            SplashScreen(onFinish = { step = AppStep.DECEASED_DATE_INPUT })

        AppStep.DECEASED_DATE_INPUT ->
            DeceasedDateInputScreen(onComplete = { date ->
                deceasedDateMillis = date
                step = AppStep.CHECKLIST_SUMMARY
            })

        AppStep.CHECKLIST_SUMMARY ->
            ChecklistSummaryScreen(
                deceasedDateMillis = deceasedDateMillis,
                onFinish = { step = AppStep.ONBOARDING }
            )

        AppStep.ONBOARDING ->
            OnboardingSlideScreen(onFinish = { step = AppStep.LOGIN })

        AppStep.LOGIN ->
            AuthScreen(onLoginSuccess = { step = AppStep.MAIN })

        AppStep.MAIN ->
            MainTabScreen(deceasedDateMillis = deceasedDateMillis)
    }
}

package com.donsgoorm.app.presentation.onboarding

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor() : ViewModel() {

    private val _selectedDateMillis = MutableStateFlow(System.currentTimeMillis())
    val selectedDateMillis: StateFlow<Long> = _selectedDateMillis

    private val _showConfirmDialog = MutableStateFlow(false)
    val showConfirmDialog: StateFlow<Boolean> = _showConfirmDialog

    fun onDateSelected(millis: Long) {
        _selectedDateMillis.value = millis
    }

    fun confirm() {
        _showConfirmDialog.value = true
    }

    fun dismissDialog() {
        _showConfirmDialog.value = false
    }

    fun formattedDate(millis: Long): String {
        val sdf = SimpleDateFormat("yyyy년 M월 d일", Locale.KOREAN)
        return sdf.format(Date(millis))
    }
}

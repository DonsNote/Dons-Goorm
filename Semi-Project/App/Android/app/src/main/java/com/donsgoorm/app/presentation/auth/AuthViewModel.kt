package com.donsgoorm.app.presentation.auth

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.gotrue.auth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val supabase: SupabaseClient
) : ViewModel() {

    private val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn

    // TODO: 실제 소셜 로그인 연동 후 제거
    fun signInMock() {
        _isLoggedIn.value = true
    }

    fun signOut() {
        _isLoggedIn.value = false
    }
}

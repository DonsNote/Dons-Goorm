package com.donsgoorm.app.presentation.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.donsgoorm.app.presentation.ui.AppColor

@Composable
fun AuthScreen(
    onLoginSuccess: () -> Unit,
    viewModel: AuthViewModel = hiltViewModel()
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColor.LightBg)
    ) {
        // 헤드라인
        Column(
            modifier = Modifier.padding(start = 28.dp, end = 28.dp, top = 72.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "처음부터 끝까지\n함께 동행하겠습니다",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                lineHeight = 36.sp
            )
            Text(
                text = "고인께서 남기신 것들\n저희가 끝까지 곁에서 정리해드리겠습니다",
                fontSize = 14.sp,
                color = Color.Gray,
                lineHeight = 20.sp
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        // 로그인 버튼 영역
        Column(
            modifier = Modifier.padding(horizontal = 24.dp, vertical = 40.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Text(
                text = "목록 저장과 D-day 알림\n로그인 후 시작 됩니다",
                fontSize = 13.sp,
                color = AppColor.AccentDim,
                lineHeight = 20.sp
            )

            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                SocialLoginButton(
                    label = "카카오로 시작하기",
                    leadingText = "K",
                    backgroundColor = AppColor.Kakao,
                    contentColor = AppColor.KakaoFg,
                    onClick = { viewModel.signInMock(); onLoginSuccess() }
                )
                SocialLoginButton(
                    label = "Google로 시작하기",
                    leadingText = "G",
                    backgroundColor = Color.White,
                    contentColor = Color.Black,
                    hasBorder = true,
                    onClick = { viewModel.signInMock(); onLoginSuccess() }
                )
                SocialLoginButton(
                    label = "Apple로 시작하기",
                    leadingText = "",
                    backgroundColor = Color.Black,
                    contentColor = Color.White,
                    onClick = { viewModel.signInMock(); onLoginSuccess() }
                )
            }
        }
    }
}

@Composable
private fun SocialLoginButton(
    label: String,
    leadingText: String,
    backgroundColor: Color,
    contentColor: Color,
    hasBorder: Boolean = false,
    onClick: () -> Unit
) {
    val shape = RoundedCornerShape(12.dp)

    if (hasBorder) {
        OutlinedButton(
            onClick = onClick,
            modifier = Modifier.fillMaxWidth(),
            shape = shape,
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = backgroundColor,
                contentColor = contentColor
            ),
            border = BorderStroke(1.dp, Color.Gray.copy(alpha = 0.3f))
        ) { SocialButtonContent(label, leadingText, contentColor) }
    } else {
        Button(
            onClick = onClick,
            modifier = Modifier.fillMaxWidth(),
            shape = shape,
            colors = ButtonDefaults.buttonColors(
                containerColor = backgroundColor,
                contentColor = contentColor
            )
        ) { SocialButtonContent(label, leadingText, contentColor) }
    }
}

@Composable
private fun SocialButtonContent(label: String, leadingText: String, contentColor: Color) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = leadingText, fontWeight = FontWeight.Bold, color = contentColor, modifier = Modifier.width(20.dp))
        Text(text = label, fontWeight = FontWeight.Medium, color = contentColor, modifier = Modifier.weight(1f), textAlign = TextAlign.Center)
        Spacer(modifier = Modifier.width(20.dp))
    }
}

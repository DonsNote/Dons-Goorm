package com.donsgoorm.app.presentation.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.donsgoorm.app.presentation.ui.AppColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeceasedDateInputScreen(
    onComplete: (Long) -> Unit,
    viewModel: OnboardingViewModel = hiltViewModel()
) {
    val selectedDateMillis by viewModel.selectedDateMillis.collectAsStateWithLifecycle()
    val showConfirmDialog by viewModel.showConfirmDialog.collectAsStateWithLifecycle()

    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = selectedDateMillis,
        selectableDates = object : androidx.compose.material3.SelectableDates {
            override fun isSelectableDate(utcTimeMillis: Long): Boolean =
                utcTimeMillis <= System.currentTimeMillis()
        }
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColor.LightBg)
    ) {
        // 헤드라인
        Column(
            modifier = Modifier.padding(start = 28.dp, end = 28.dp, top = 60.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "이제 혼자\n챙기지 않아도 됩니다",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                lineHeight = 36.sp
            )
            Text(
                text = "고인께서 영면에 드신 날을 입력해주시면\n처리해야 할 모든 것을 알려드릴게요",
                fontSize = 14.sp,
                color = Color.Gray,
                lineHeight = 20.sp
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        // 날짜 선택 카드
        Column(
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .background(AppColor.CardBg, RoundedCornerShape(20.dp))
        ) {
            Text(
                text = "고인께서 영면에 드신 날",
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                color = AppColor.Accent,
                modifier = Modifier.padding(start = 18.dp, end = 18.dp, top = 18.dp, bottom = 4.dp)
            )

            DatePicker(
                state = datePickerState,
                showModeToggle = false,
                colors = DatePickerDefaults.colors(
                    containerColor = Color.Transparent,
                    selectedDayContainerColor = AppColor.Accent,
                    todayDateBorderColor = AppColor.Accent,
                    todayContentColor = AppColor.Accent,
                )
            )

            Text(
                text = "법적 기한 계산에 사용됩니다",
                fontSize = 12.sp,
                color = Color.Gray,
                modifier = Modifier.padding(horizontal = 18.dp, vertical = 14.dp)
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        // 하단 버튼
        Column(
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 40.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = {
                    datePickerState.selectedDateMillis?.let { viewModel.onDateSelected(it) }
                    viewModel.confirm()
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = AppColor.Accent,
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = "처리 목록 확인하기",
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(vertical = 6.dp)
                )
            }
            Text(
                text = "입력하신 내용은 안전하게 저장됩니다",
                fontSize = 12.sp,
                color = Color.Gray
            )
        }
    }

    if (showConfirmDialog) {
        AlertDialog(
            onDismissRequest = { viewModel.dismissDialog() },
            title = { Text("영면일자 확인") },
            text = { Text("${viewModel.formattedDate(selectedDateMillis)}\n영면일이 맞나요?") },
            dismissButton = {
                TextButton(onClick = { viewModel.dismissDialog() }) { Text("취소") }
            },
            confirmButton = {
                TextButton(onClick = {
                    viewModel.dismissDialog()
                    onComplete(selectedDateMillis)
                }) { Text("확인", color = AppColor.Accent) }
            }
        )
    }
}

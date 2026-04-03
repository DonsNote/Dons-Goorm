package com.donsgoorm.app.presentation.onboarding

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

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
            override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                return utcTimeMillis <= System.currentTimeMillis()
            }
        }
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Spacer(modifier = Modifier.height(60.dp))

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "고인의 사망일자를 입력해 주세요.",
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "입력하신 날짜를 기준으로\n처리해야 할 일들을 안내드립니다.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
        }

        DatePicker(
            state = datePickerState,
            showModeToggle = false,
            colors = DatePickerDefaults.colors(
                containerColor = MaterialTheme.colorScheme.background
            )
        )

        Button(
            onClick = {
                datePickerState.selectedDateMillis?.let { viewModel.onDateSelected(it) }
                viewModel.confirm()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.onBackground,
                contentColor = MaterialTheme.colorScheme.background
            )
        ) {
            Text(text = "확인", modifier = Modifier.padding(vertical = 4.dp))
        }
    }

    if (showConfirmDialog) {
        AlertDialog(
            onDismissRequest = { viewModel.dismissDialog() },
            title = { Text("사망일자 확인") },
            text = { Text("${viewModel.formattedDate(selectedDateMillis)}이 맞으신가요?") },
            dismissButton = {
                TextButton(onClick = { viewModel.dismissDialog() }) {
                    Text("취소")
                }
            },
            confirmButton = {
                TextButton(onClick = {
                    viewModel.dismissDialog()
                    onComplete(selectedDateMillis)
                }) {
                    Text("확인")
                }
            }
        )
    }
}

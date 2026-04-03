package com.donsgoorm.app.presentation.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Checklist
import androidx.compose.material.icons.filled.QuestionAnswer
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.hilt.navigation.compose.hiltViewModel
import com.donsgoorm.app.presentation.checklist.ChecklistDetailScreen
import com.donsgoorm.app.presentation.checklist.ChecklistScreen
import com.donsgoorm.app.presentation.checklist.ChecklistViewModel
import com.donsgoorm.app.presentation.checklist.model.ChecklistSection
import com.donsgoorm.app.presentation.guide.GuideScreen
import com.donsgoorm.app.presentation.progress.ProgressScreen

private enum class Tab(val label: String, val icon: ImageVector) {
    GUIDE("가이드", Icons.Default.QuestionAnswer),
    CHECKLIST("체크리스트", Icons.Default.Checklist),
    PROGRESS("진행상황", Icons.Default.BarChart)
}

@Composable
fun MainTabScreen(deceasedDateMillis: Long) {
    val checklistViewModel: ChecklistViewModel = hiltViewModel()
    var selectedTab by remember { mutableStateOf(Tab.CHECKLIST) }
    var selectedSection by remember { mutableStateOf<ChecklistSection?>(null) }

    // 체크리스트 탭에서 세부 화면 진입 시 탭바 숨김
    if (selectedSection != null) {
        ChecklistDetailScreen(
            section = selectedSection!!,
            viewModel = checklistViewModel,
            onBack = { selectedSection = null }
        )
        return
    }

    Scaffold(
        bottomBar = {
            NavigationBar {
                Tab.entries.forEach { tab ->
                    NavigationBarItem(
                        selected = selectedTab == tab,
                        onClick = { selectedTab = tab },
                        icon = { Icon(tab.icon, contentDescription = tab.label) },
                        label = { Text(tab.label) }
                    )
                }
            }
        }
    ) { paddingValues ->
        Box(modifier = Modifier.fillMaxSize().padding(paddingValues)) {
            when (selectedTab) {
                Tab.GUIDE -> GuideScreen()
                Tab.CHECKLIST -> ChecklistScreen(
                    viewModel = checklistViewModel,
                    onSectionClick = { section -> selectedSection = section }
                )
                Tab.PROGRESS -> ProgressScreen(
                    deceasedDateMillis = deceasedDateMillis,
                    viewModel = checklistViewModel
                )
            }
        }
    }
}

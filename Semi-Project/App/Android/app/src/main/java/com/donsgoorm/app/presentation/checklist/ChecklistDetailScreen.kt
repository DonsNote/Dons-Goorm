package com.donsgoorm.app.presentation.checklist

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.outlined.Circle
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.donsgoorm.app.presentation.checklist.model.ChecklistSection
import com.donsgoorm.app.presentation.checklist.model.ChecklistTask
import com.donsgoorm.app.presentation.ui.AppColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChecklistDetailScreen(
    section: ChecklistSection,
    viewModel: ChecklistViewModel,
    onBack: () -> Unit,
    onTaskClick: (ChecklistTask) -> Unit
) {
    val sections by viewModel.sections.collectAsStateWithLifecycle()
    val currentSection = sections.find { it.id == section.id } ?: section

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(section.category.title, fontWeight = FontWeight.SemiBold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "뒤로", tint = AppColor.Accent)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = AppColor.LightBg)
            )
        },
        containerColor = AppColor.LightBg
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            item {
                Column(
                    modifier = Modifier
                        .clip(RoundedCornerShape(14.dp))
                        .background(AppColor.CardBg)
                ) {
                    itemsIndexed(currentSection.tasks) { index, task ->
                        TaskRow(
                            task = task,
                            onClick = { onTaskClick(task) }
                        )
                        if (index < currentSection.tasks.size - 1) {
                            HorizontalDivider(
                                modifier = Modifier.padding(start = 56.dp),
                                color = Color.Black.copy(alpha = 0.06f)
                            )
                        }
                    }
                }
            }
            item { Spacer(modifier = Modifier.height(24.dp)) }
        }
    }
}

// LazyColumn 내부에 itemsIndexed를 직접 쓸 수 없으므로 별도 Column으로 감쌈
@Composable
private fun TaskListContent(
    section: ChecklistSection,
    onTaskClick: (ChecklistTask) -> Unit
) {
    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(14.dp))
            .background(AppColor.CardBg)
    ) {
        section.tasks.forEachIndexed { index, task ->
            TaskRow(task = task, onClick = { onTaskClick(task) })
            if (index < section.tasks.size - 1) {
                HorizontalDivider(
                    modifier = Modifier.padding(start = 56.dp),
                    color = Color.Black.copy(alpha = 0.06f)
                )
            }
        }
    }
}

@Composable
private fun TaskRow(task: ChecklistTask, onClick: () -> Unit) {
    val readyCount = task.documents.count { it.isReady }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 18.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Icon(
            imageVector = if (task.isCompleted) Icons.Default.CheckCircle else Icons.Outlined.Circle,
            contentDescription = null,
            tint = if (task.isCompleted) AppColor.Accent else AppColor.Accent.copy(alpha = 0.3f),
            modifier = Modifier.size(22.dp)
        )
        Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(
                text = task.title,
                fontSize = 15.sp,
                textDecoration = if (task.isCompleted) TextDecoration.LineThrough else null,
                color = if (task.isCompleted) Color.Gray else Color.Black
            )
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                task.dDay?.let { dDay ->
                    Text(
                        text = "D-$dDay",
                        fontSize = 12.sp,
                        color = if (dDay <= 7) AppColor.Warning else AppColor.AccentDim
                    )
                }
                if (task.documents.isNotEmpty()) {
                    Text(
                        text = "서류 $readyCount/${task.documents.size}",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }
            }
        }
        Icon(
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
            contentDescription = null,
            tint = AppColor.Accent.copy(alpha = 0.4f),
            modifier = Modifier.size(20.dp)
        )
    }
}

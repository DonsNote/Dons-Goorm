package com.donsgoorm.app.presentation.progress

import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.donsgoorm.app.presentation.checklist.ChecklistViewModel
import com.donsgoorm.app.presentation.checklist.model.ChecklistSection
import com.donsgoorm.app.presentation.ui.AppColor
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProgressScreen(
    deceasedDateMillis: Long,
    viewModel: ChecklistViewModel
) {
    val sections by viewModel.sections.collectAsStateWithLifecycle()
    val totalCompleted = sections.sumOf { it.completedCount }
    val totalTasks = sections.sumOf { it.totalCount }
    val overallRatio = if (totalTasks > 0) totalCompleted.toFloat() / totalTasks else 0f
    val formattedDate = SimpleDateFormat("yyyy년 M월 d일", Locale.KOREAN).format(Date(deceasedDateMillis))

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("진행상황", fontWeight = FontWeight.SemiBold) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = AppColor.LightBg)
            )
        },
        containerColor = AppColor.LightBg
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            item { Spacer(modifier = Modifier.height(4.dp)) }
            item {
                OverallCard(
                    ratio = overallRatio,
                    totalCompleted = totalCompleted,
                    totalTasks = totalTasks,
                    formattedDate = formattedDate
                )
            }
            items(sections, key = { it.id }) { section ->
                SectionCard(section = section)
            }
            item { Spacer(modifier = Modifier.height(16.dp)) }
        }
    }
}

@Composable
private fun OverallCard(ratio: Float, totalCompleted: Int, totalTasks: Int, formattedDate: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(AppColor.CardBg)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text("전체 진행률", fontWeight = FontWeight.SemiBold, fontSize = 15.sp)
                Text("영면일: $formattedDate", fontSize = 12.sp, color = Color.Gray)
            }
            Text(
                text = "${(ratio * 100).toInt()}%",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = AppColor.Accent
            )
        }

        LinearProgressIndicator(
            progress = { ratio },
            modifier = Modifier
                .fillMaxWidth()
                .height(6.dp)
                .clip(RoundedCornerShape(4.dp)),
            color = AppColor.Accent,
            trackColor = AppColor.Accent.copy(alpha = 0.15f)
        )

        Text(
            text = "${totalCompleted}개 완료 / 총 ${totalTasks}개",
            fontSize = 12.sp,
            color = Color.Gray,
            modifier = Modifier.align(Alignment.End)
        )
    }
}

@Composable
private fun SectionCard(section: ChecklistSection) {
    val ratio = if (section.totalCount > 0) section.completedCount.toFloat() / section.totalCount else 0f

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(AppColor.CardBg)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(section.category.icon, contentDescription = null, modifier = Modifier.size(18.dp), tint = AppColor.Accent)
            Text(section.category.title, fontSize = 14.sp, fontWeight = FontWeight.Medium, modifier = Modifier.weight(1f))
            Text("${section.completedCount}/${section.totalCount}", fontSize = 12.sp, color = Color.Gray)
        }

        LinearProgressIndicator(
            progress = { ratio },
            modifier = Modifier
                .fillMaxWidth()
                .height(6.dp)
                .clip(RoundedCornerShape(4.dp)),
            color = AppColor.Accent,
            trackColor = AppColor.Accent.copy(alpha = 0.15f)
        )
    }
}

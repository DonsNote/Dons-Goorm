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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.donsgoorm.app.presentation.checklist.model.ChecklistSection
import com.donsgoorm.app.presentation.ui.AppColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChecklistScreen(
    viewModel: ChecklistViewModel,
    onSectionClick: (ChecklistSection) -> Unit
) {
    val sections by viewModel.sections.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("체크리스트", fontWeight = FontWeight.SemiBold) },
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
            items(sections, key = { it.id }) { section ->
                ChecklistCard(section = section, onClick = { onSectionClick(section) })
            }
            item { Spacer(modifier = Modifier.height(16.dp)) }
        }
    }
}

@Composable
private fun ChecklistCard(section: ChecklistSection, onClick: () -> Unit) {
    val ratio = if (section.totalCount > 0) section.completedCount.toFloat() / section.totalCount else 0f

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(AppColor.CardBg)
            .clickable(onClick = onClick)
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = section.category.icon,
                contentDescription = null,
                modifier = Modifier.size(20.dp),
                tint = AppColor.Accent
            )
            Spacer(modifier = Modifier.size(8.dp))
            Text(
                text = section.category.title,
                fontWeight = FontWeight.SemiBold,
                fontSize = 15.sp,
                modifier = Modifier.weight(1f)
            )
            CircularProgress(ratio = ratio, modifier = Modifier.size(28.dp))
        }

        HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp), color = Color.Black.copy(alpha = 0.07f))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Bottom
        ) {
            Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    text = section.nextTask?.title ?: "모든 항목 완료",
                    fontSize = 14.sp,
                    color = if (section.nextTask == null) AppColor.AccentDim else Color.Black,
                    maxLines = 1
                )
                Text(
                    text = "${section.completedCount}/${section.totalCount} 완료",
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }
            section.earliestDDay?.let { dDay ->
                Text(
                    text = "D-$dDay",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = if (dDay <= 7) AppColor.Warning else AppColor.AccentDim
                )
            }
        }
    }
}

@Composable
private fun CircularProgress(ratio: Float, modifier: Modifier = Modifier) {
    Spacer(
        modifier = modifier.drawWithContent {
            val stroke = Stroke(width = 3.dp.toPx(), cap = StrokeCap.Round)
            drawArc(color = AppColor.Accent.copy(alpha = 0.2f), startAngle = -90f, sweepAngle = 360f, useCenter = false, style = stroke)
            drawArc(color = AppColor.Accent, startAngle = -90f, sweepAngle = 360f * ratio, useCenter = false, style = stroke)
        }
    )
}

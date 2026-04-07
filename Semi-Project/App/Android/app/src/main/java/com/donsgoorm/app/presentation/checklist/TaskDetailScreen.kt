package com.donsgoorm.app.presentation.checklist

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.outlined.Circle
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
fun TaskDetailScreen(
    sectionId: String,
    taskId: String,
    viewModel: ChecklistViewModel,
    onBack: () -> Unit
) {
    val sections by viewModel.sections.collectAsStateWithLifecycle()
    val section = sections.find { it.id == sectionId } ?: return
    val task = section.tasks.find { it.id == taskId } ?: return

    val readyCount = task.documents.count { it.isReady }
    val allReady = task.documents.isNotEmpty() && readyCount == task.documents.size
    val docRatio = if (task.documents.isEmpty()) 0f else readyCount.toFloat() / task.documents.size

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(task.title, fontWeight = FontWeight.SemiBold, fontSize = 16.sp) },
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
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // 완료 상태 카드
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(14.dp))
                        .background(AppColor.CardBg)
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text(task.title, fontWeight = FontWeight.SemiBold, fontSize = 15.sp)
                        task.dDay?.let { dDay ->
                            Text(
                                text = "D-$dDay 마감",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium,
                                color = if (dDay <= 7) AppColor.Warning else AppColor.AccentDim
                            )
                        }
                    }
                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .background(if (task.isCompleted) AppColor.AccentSubtle else Color.Gray.copy(alpha = 0.1f))
                            .clickable { viewModel.toggleTask(sectionId, taskId) }
                            .padding(horizontal = 12.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Icon(
                            imageVector = if (task.isCompleted) Icons.Default.CheckCircle else Icons.Outlined.Circle,
                            contentDescription = null,
                            tint = if (task.isCompleted) AppColor.Accent else Color.Gray,
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = if (task.isCompleted) "완료" else "미완료",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            color = if (task.isCompleted) AppColor.Accent else Color.Gray
                        )
                    }
                }
            }

            // 필요 서류 섹션
            item {
                Column(
                    modifier = Modifier
                        .clip(RoundedCornerShape(14.dp))
                        .background(AppColor.CardBg)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 18.dp)
                            .padding(top = 16.dp, bottom = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("필요 서류", fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
                        Text(
                            text = "$readyCount/${task.documents.size} 준비됨",
                            fontSize = 12.sp,
                            color = if (allReady) AppColor.Accent else Color.Gray
                        )
                    }

                    LinearProgressIndicator(
                        progress = { docRatio },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 18.dp)
                            .padding(bottom = 12.dp)
                            .height(5.dp)
                            .clip(RoundedCornerShape(4.dp)),
                        color = AppColor.Accent,
                        trackColor = AppColor.Accent.copy(alpha = 0.15f)
                    )

                    HorizontalDivider(color = Color.Black.copy(alpha = 0.06f))

                    task.documents.forEachIndexed { index, doc ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { viewModel.toggleDocument(sectionId, taskId, doc.id) }
                                .padding(horizontal = 18.dp, vertical = 14.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(14.dp)
                        ) {
                            Icon(
                                imageVector = if (doc.isReady) Icons.Default.CheckCircle else Icons.Outlined.Circle,
                                contentDescription = null,
                                tint = if (doc.isReady) AppColor.Accent else AppColor.Accent.copy(alpha = 0.3f),
                                modifier = Modifier.size(22.dp)
                            )
                            Text(
                                text = doc.title,
                                fontSize = 14.sp,
                                textDecoration = if (doc.isReady) TextDecoration.LineThrough else null,
                                color = if (doc.isReady) Color.Gray else Color.Black,
                                modifier = Modifier.weight(1f)
                            )
                        }
                        if (index < task.documents.size - 1) {
                            HorizontalDivider(
                                modifier = Modifier.padding(start = 56.dp),
                                color = Color.Black.copy(alpha = 0.06f)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(4.dp))
                }
            }

            // 가까운 발급처 섹션
            item {
                Column(
                    modifier = Modifier
                        .clip(RoundedCornerShape(14.dp))
                        .background(AppColor.CardBg)
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(Icons.Default.LocationOn, contentDescription = null, tint = AppColor.Accent, modifier = Modifier.size(20.dp))
                        Text("가까운 발급처", fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
                    }

                    Text(
                        text = "필요 서류를 발급받을 수 있는\n가장 가까운 기관을 안내해 드릴게요.",
                        fontSize = 12.sp,
                        color = Color.Gray,
                        lineHeight = 18.sp
                    )

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(14.dp))
                            .background(AppColor.AccentSubtle)
                            .padding(vertical = 14.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "가까운 기관 찾기",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            color = AppColor.AccentDim.copy(alpha = 0.6f)
                        )
                    }

                    Text(
                        text = "* 해당 기능은 준비 중입니다.",
                        fontSize = 11.sp,
                        color = Color.Gray.copy(alpha = 0.6f)
                    )
                }
            }

            item { Spacer(modifier = Modifier.height(16.dp)) }
        }
    }
}

package com.donsgoorm.app.presentation.onboarding

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.donsgoorm.app.presentation.checklist.model.placeholderSections
import com.donsgoorm.app.presentation.ui.AppColor
import kotlinx.coroutines.delay

@Composable
fun ChecklistSummaryScreen(
    deceasedDateMillis: Long,
    onFinish: () -> Unit
) {
    val sections = remember { placeholderSections() }
    val totalCount = sections.sumOf { it.totalCount }
    val earliestDDay = sections.mapNotNull { it.earliestDDay }.minOrNull()
    val numbers = listOf("①", "②", "③", "④")

    var started by remember { mutableStateOf(false) }
    val progress by animateFloatAsState(
        targetValue = if (started) 1f else 0f,
        animationSpec = tween(durationMillis = 5000, easing = LinearEasing),
        label = "summary_progress"
    )

    LaunchedEffect(Unit) {
        started = true
        delay(5000)
        onFinish()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColor.LightBg)
    ) {
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(
                text = "고객님이\n처리해야 할 일이",
                fontSize = 22.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center,
                color = Color.Black,
                lineHeight = 30.sp
            )
            Text(
                text = "${totalCount}건",
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                color = AppColor.Accent
            )
            Text(
                text = "있습니다",
                fontSize = 22.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(14.dp))

            if (earliestDDay != null) {
                Text(
                    text = "가장 빠른 기한까지 D-${earliestDDay}일 남았습니다",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = AppColor.Warning,
                    modifier = Modifier
                        .background(AppColor.WarningBg, RoundedCornerShape(20.dp))
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(18.dp))

            Column(
                modifier = Modifier.padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                sections.forEachIndexed { index, section ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(AppColor.CardBg, RoundedCornerShape(14.dp))
                            .padding(horizontal = 18.dp, vertical = 14.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "${numbers[index]} ${section.category.title}",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.Black
                        )
                        val dDayText = section.earliestDDay?.let { "·D-$it" } ?: ""
                        Text(
                            text = "${section.totalCount}건$dDayText",
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                    }
                }
            }
        }

        // 하단
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(horizontal = 20.dp)
                .padding(bottom = 36.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "서비스를 상세하게 소개드릴게요",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = AppColor.AccentDim,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(AppColor.AccentSubtle, RoundedCornerShape(14.dp))
                    .padding(vertical = 16.dp)
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(3.dp)
                    .clip(RoundedCornerShape(2.dp))
                    .background(Color.Black.copy(alpha = 0.1f))
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(progress)
                        .height(3.dp)
                        .background(AppColor.AccentDark)
                )
            }
        }
    }
}

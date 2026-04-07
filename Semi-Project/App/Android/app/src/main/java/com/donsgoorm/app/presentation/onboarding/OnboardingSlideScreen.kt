package com.donsgoorm.app.presentation.onboarding

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Canvas
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Circle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.donsgoorm.app.presentation.checklist.model.placeholderSections
import com.donsgoorm.app.presentation.ui.AppColor
import kotlinx.coroutines.launch

private val captions = listOf(
    Pair(
        "이제 혼자 챙기지\n않아도 됩니다",
        "사망일자 하나만 입력하면 행정, 금융,\n상속까지 처리 목록이 자동으로 만들어집니다"
    ),
    Pair(
        "필요한 것들을\n저희가 미리 챙겨두었습니다",
        "필요한 서류와 기관을 순서대로\n알려드릴게요"
    ),
    Pair(
        "놓치는 것 없이\n저희가 기억하고 있습니다",
        "6개 영역 진행 상황을 한눈에\n확인할 수 있습니다"
    ),
    Pair(
        "혼자 애쓰지 마세요\n저희가 밤낮없이 곁에 있을게요",
        "복잡한 절차부터 사소한 질문까지\n실시간으로 답해드려요"
    )
)

@Composable
fun OnboardingSlideScreen(onFinish: () -> Unit) {
    val pagerState = rememberPagerState(pageCount = { 4 })
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColor.LightBg)
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.weight(1f)
        ) { index ->
            Column(modifier = Modifier.fillMaxSize()) {
                AppScreenPreview {
                    when (index) {
                        0 -> ChecklistPreview()
                        1 -> TaskDetailPreview()
                        2 -> ProgressPreview()
                        else -> GuidePreview()
                    }
                }

                Spacer(modifier = Modifier.weight(1f))

                val (title, sub) = captions[index]
                SlideCaption(title = title, sub = sub)
            }
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .padding(bottom = 52.dp, top = 16.dp)
        ) {
            PageIndicator(total = 4, current = pagerState.currentPage)

            Button(
                onClick = {
                    if (pagerState.currentPage < 3) {
                        scope.launch { pagerState.animateScrollToPage(pagerState.currentPage + 1) }
                    } else {
                        onFinish()
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = AppColor.AccentDark)
            ) {
                Text(
                    text = if (pagerState.currentPage < 3) "다음" else "지금 시작하기 →",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(vertical = 6.dp)
                )
            }
        }
    }
}

// MARK: - AppScreenPreview 래퍼

@Composable
private fun AppScreenPreview(content: @Composable () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
            .padding(top = 36.dp)
            .shadow(elevation = 8.dp, shape = RoundedCornerShape(20.dp))
            .clip(RoundedCornerShape(20.dp))
            .height(420.dp)
            .background(AppColor.LightBg)
    ) {
        content()
        // 터치 이벤트 소비하여 pager 스와이프 방해 방지
        Box(modifier = Modifier.fillMaxSize())
    }
}

// MARK: - 슬라이드 1: 체크리스트 화면

@Composable
private fun ChecklistPreview() {
    val sections = remember { placeholderSections() }
    Column(modifier = Modifier.fillMaxSize().background(AppColor.LightBg)) {
        // 네비게이션 바
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(AppColor.LightBg)
                .padding(horizontal = 16.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("체크리스트", fontWeight = FontWeight.SemiBold, fontSize = 17.sp, modifier = Modifier.weight(1f))
            Icon(Icons.Default.Person, contentDescription = null, tint = AppColor.Accent, modifier = Modifier.size(22.dp))
        }
        HorizontalDivider(color = Color.Black.copy(alpha = 0.07f))

        Column(
            modifier = Modifier.padding(horizontal = 16.dp).padding(top = 12.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            sections.forEach { section ->
                val ratio = section.completedCount.toFloat() / section.totalCount.coerceAtLeast(1)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(AppColor.CardBg)
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(section.category.icon, null, modifier = Modifier.size(18.dp), tint = AppColor.Accent)
                    Spacer(modifier = Modifier.size(8.dp))
                    Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(2.dp)) {
                        Text(section.category.title, fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
                        Text(
                            section.nextTask?.title ?: "모든 항목 완료",
                            fontSize = 12.sp,
                            color = Color.Gray,
                            maxLines = 1
                        )
                    }
                    Spacer(modifier = Modifier.size(8.dp))
                    MiniCircularProgress(ratio = ratio, size = 26.dp)
                }
            }
        }
    }
}

// MARK: - 슬라이드 2: 태스크 상세 화면 (사망신고)

@Composable
private fun TaskDetailPreview() {
    val task = remember { placeholderSections()[0].tasks[0] }
    Column(modifier = Modifier.fillMaxSize().background(AppColor.LightBg)) {
        // 네비게이션 바
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(AppColor.LightBg)
                .padding(horizontal = 8.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = null,
                tint = AppColor.Accent,
                modifier = Modifier.size(22.dp).padding(start = 4.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(task.title, fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
        }
        HorizontalDivider(color = Color.Black.copy(alpha = 0.07f))

        Column(
            modifier = Modifier.padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // 완료 상태 카드
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(AppColor.CardBg)
                    .padding(14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(3.dp)) {
                    Text(task.title, fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
                    task.dDay?.let {
                        Text("D-$it 마감", fontSize = 12.sp, color = AppColor.Warning, fontWeight = FontWeight.Medium)
                    }
                }
                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .background(Color.Gray.copy(alpha = 0.1f))
                        .padding(horizontal = 10.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    Icon(Icons.Outlined.Circle, null, tint = Color.Gray, modifier = Modifier.size(14.dp))
                    Text("미완료", fontSize = 11.sp, color = Color.Gray)
                }
            }

            // 필요 서류 카드
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(AppColor.CardBg)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .padding(top = 14.dp, bottom = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("필요 서류", fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
                    Text("0/${task.documents.size} 준비됨", fontSize = 12.sp, color = Color.Gray)
                }
                LinearProgressIndicator(
                    progress = { 0f },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .padding(bottom = 10.dp)
                        .height(4.dp)
                        .clip(RoundedCornerShape(4.dp)),
                    color = AppColor.Accent,
                    trackColor = AppColor.Accent.copy(alpha = 0.15f)
                )
                HorizontalDivider(color = Color.Black.copy(alpha = 0.06f))

                task.documents.forEachIndexed { index, doc ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Icon(
                            Icons.Outlined.Circle, null,
                            tint = AppColor.Accent.copy(alpha = 0.3f),
                            modifier = Modifier.size(20.dp)
                        )
                        Text(doc.title, fontSize = 13.sp, color = Color.Black)
                    }
                    if (index < task.documents.size - 1) {
                        HorizontalDivider(
                            modifier = Modifier.padding(start = 48.dp),
                            color = Color.Black.copy(alpha = 0.06f)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(4.dp))
            }
        }
    }
}

// MARK: - 슬라이드 3: 진행상황 화면

@Composable
private fun ProgressPreview() {
    val sections = remember { placeholderSections() }
    Column(modifier = Modifier.fillMaxSize().background(AppColor.LightBg)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(AppColor.LightBg)
                .padding(horizontal = 16.dp, vertical = 14.dp)
        ) {
            Text("진행상황", fontWeight = FontWeight.SemiBold, fontSize = 17.sp)
        }
        HorizontalDivider(color = Color.Black.copy(alpha = 0.07f))

        Column(
            modifier = Modifier.padding(horizontal = 14.dp).padding(top = 12.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // 전체 진행률 카드 (원형 프로그레스)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(AppColor.CardBg)
                    .padding(14.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(3.dp)) {
                    Text("전체 진행률", fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
                    Text("영면일: 2025년 1월 1일", fontSize = 11.sp, color = Color.Gray)
                }
                SmallCircularProgress(ratio = 0f, size = 60.dp)
            }

            // 카테고리별 섹션 카드
            sections.take(3).forEach { section ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(AppColor.CardBg)
                        .padding(12.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Icon(section.category.icon, null, modifier = Modifier.size(15.dp), tint = AppColor.Accent)
                        Text(section.category.title, fontSize = 13.sp, fontWeight = FontWeight.Medium, modifier = Modifier.weight(1f))
                        Text("0/${section.totalCount}", fontSize = 11.sp, color = Color.Gray)
                    }
                    LinearProgressIndicator(
                        progress = { 0f },
                        modifier = Modifier.fillMaxWidth().height(5.dp).clip(RoundedCornerShape(4.dp)),
                        color = AppColor.Accent,
                        trackColor = AppColor.Accent.copy(alpha = 0.15f)
                    )
                }
            }
        }
    }
}

// MARK: - 슬라이드 4: 가이드 화면

@Composable
private fun GuidePreview() {
    Column(modifier = Modifier.fillMaxSize().background(AppColor.LightBg)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(AppColor.LightBg)
                .padding(horizontal = 16.dp, vertical = 14.dp)
        ) {
            Text("가이드", fontWeight = FontWeight.SemiBold, fontSize = 17.sp)
        }
        HorizontalDivider(color = Color.Black.copy(alpha = 0.07f))

        Column(
            modifier = Modifier.padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // AI 코디네이터 헤더 카드
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(14.dp))
                    .background(AppColor.CardBg)
                    .padding(horizontal = 14.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape)
                        .background(AppColor.AccentSubtle),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.Group, null, tint = AppColor.Accent, modifier = Modifier.size(22.dp))
                }
                Column(verticalArrangement = Arrangement.spacedBy(3.dp)) {
                    Text("AI 코디네이터", fontWeight = FontWeight.SemiBold, fontSize = 13.sp, color = Color.Black)
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(5.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(6.dp)
                                .clip(CircleShape)
                                .background(Color(0xFF4CAF50))
                        )
                        Text("24시간 실시간 상담중", fontSize = 11.sp, color = Color.Gray)
                    }
                }
            }

            // 웰컴 메시지 버블
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start) {
                Box(
                    modifier = Modifier
                        .widthIn(max = 260.dp)
                        .clip(
                            RoundedCornerShape(
                                topStart = 14.dp, topEnd = 14.dp,
                                bottomStart = 4.dp, bottomEnd = 14.dp
                            )
                        )
                        .background(AppColor.CardBg)
                        .padding(horizontal = 14.dp, vertical = 10.dp)
                ) {
                    Text(
                        text = "안녕하세요. 어렵고 힘드신 상황에서 찾아주셨군요.\n처리해야 할 일들에 대해 궁금한 점을 편하게 물어보세요.",
                        fontSize = 13.sp,
                        color = Color.Black,
                        lineHeight = 19.sp
                    )
                }
            }
        }
    }
}

// MARK: - 공통 헬퍼

@Composable
private fun SlideCaption(title: String, sub: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
            .padding(bottom = 8.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(title, fontWeight = FontWeight.Bold, fontSize = 22.sp, color = Color.Black, lineHeight = 30.sp)
        Text(sub, fontSize = 14.sp, color = Color.Gray, lineHeight = 20.sp)
    }
}

@Composable
private fun MiniCircularProgress(ratio: Float, size: Dp) {
    Canvas(modifier = Modifier.size(size)) {
        val stroke = Stroke(width = 2.5.dp.toPx(), cap = StrokeCap.Round)
        drawArc(AppColor.Accent.copy(alpha = 0.2f), -90f, 360f, false, style = stroke)
        drawArc(AppColor.Accent, -90f, 360f * ratio, false, style = stroke)
    }
}

@Composable
private fun SmallCircularProgress(ratio: Float, size: Dp) {
    Box(contentAlignment = Alignment.Center, modifier = Modifier.size(size)) {
        Canvas(modifier = Modifier.matchParentSize()) {
            val strokeWidth = 6.dp.toPx()
            val stroke = Stroke(width = strokeWidth, cap = StrokeCap.Round)
            drawArc(AppColor.Accent.copy(alpha = 0.15f), -90f, 360f, false, style = stroke)
            drawArc(AppColor.Accent, -90f, 360f * ratio, false, style = stroke)
        }
        Text(
            text = "${(ratio * 100).toInt()}%",
            fontSize = (size.value * 0.26f).sp,
            fontWeight = FontWeight.Bold,
            color = AppColor.Accent
        )
    }
}

@Composable
private fun PageIndicator(total: Int, current: Int) {
    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
        repeat(total) { index ->
            val width by animateDpAsState(
                targetValue = if (index == current) 20.dp else 6.dp,
                label = "indicator_width"
            )
            Box(
                modifier = Modifier
                    .width(width)
                    .height(6.dp)
                    .clip(CircleShape)
                    .background(
                        if (index == current) AppColor.AccentDark
                        else AppColor.Accent.copy(alpha = 0.25f)
                    )
            )
        }
    }
}

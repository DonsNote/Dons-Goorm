package com.donsgoorm.app.presentation.onboarding

import androidx.compose.animation.core.animateDpAsState
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
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Checklist
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.QuestionAnswer
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

private data class OnboardingPage(
    val icon: ImageVector,
    val title: String,
    val description: String
)

private val pages = listOf(
    OnboardingPage(
        icon = Icons.Default.Checklist,
        title = "해야 할 일들을 한눈에",
        description = "사무행정, 금융, 디지털, 법원행정\n4가지 영역으로 정리된 체크리스트를\n단계별로 안내드립니다."
    ),
    OnboardingPage(
        icon = Icons.Default.QuestionAnswer,
        title = "궁금한 점은 언제든지",
        description = "어렵고 복잡한 절차들을\n가이드를 통해 편하게\n질문하고 도움받으세요."
    ),
    OnboardingPage(
        icon = Icons.Default.BarChart,
        title = "진행 현황을 한눈에",
        description = "처리된 일과 남은 일을\n보고서 형식으로 확인하며\n놓치는 것 없이 마무리하세요."
    ),
    OnboardingPage(
        icon = Icons.Default.Favorite,
        title = "홀로 남겨지지 않도록",
        description = "힘든 시간, 동행이\n곁에서 함께하겠습니다."
    )
)

@Composable
fun OnboardingSlideScreen(onFinish: () -> Unit) {
    val pagerState = rememberPagerState(pageCount = { pages.size })
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.weight(1f)
        ) { index ->
            OnboardingPageContent(page = pages[index])
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp),
            modifier = Modifier.padding(bottom = 52.dp)
        ) {
            PageIndicator(total = pages.size, current = pagerState.currentPage)

            Button(
                onClick = {
                    if (pagerState.currentPage < pages.size - 1) {
                        scope.launch { pagerState.animateScrollToPage(pagerState.currentPage + 1) }
                    } else {
                        onFinish()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.onBackground,
                    contentColor = MaterialTheme.colorScheme.background
                )
            ) {
                Text(
                    text = if (pagerState.currentPage < pages.size - 1) "다음" else "시작하기",
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }
        }
    }
}

@Composable
private fun OnboardingPageContent(page: OnboardingPage) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.weight(1f))

        Icon(
            imageVector = page.icon,
            contentDescription = null,
            modifier = Modifier.size(80.dp),
            tint = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = page.title,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = page.description,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
            lineHeight = MaterialTheme.typography.bodyMedium.lineHeight * 1.4f
        )

        Spacer(modifier = Modifier.weight(1f))
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
                        if (index == current) MaterialTheme.colorScheme.onBackground
                        else MaterialTheme.colorScheme.onBackground.copy(alpha = 0.2f)
                    )
            )
        }
    }
}

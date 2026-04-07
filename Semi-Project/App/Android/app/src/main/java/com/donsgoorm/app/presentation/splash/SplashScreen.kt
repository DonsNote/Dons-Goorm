package com.donsgoorm.app.presentation.splash

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

private val BgColor = Color(0xFF1B2B24)
private val IconColor = Color(0xFF263C32)

@Composable
fun SplashScreen(onFinish: () -> Unit) {
    var visible by remember { mutableStateOf(false) }

    val contentAlpha by animateFloatAsState(
        targetValue = if (visible) 1f else 0f,
        animationSpec = tween(durationMillis = 800),
        label = "splash_alpha"
    )

    val progressFraction by animateFloatAsState(
        targetValue = if (visible) 1f else 0f,
        animationSpec = tween(durationMillis = 3000, easing = LinearEasing),
        label = "splash_progress"
    )

    LaunchedEffect(Unit) {
        visible = true
        delay(3200)
        visible = false
        delay(400)
        onFinish()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BgColor)
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .alpha(contentAlpha),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // App icon
            Box(
                modifier = Modifier
                    .width(72.dp)
                    .height(72.dp)
                    .clip(RoundedCornerShape(18.dp))
                    .background(IconColor)
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Title + divider: width constrained to title width via IntrinsicSize.Min
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.width(IntrinsicSize.Min)
            ) {
                Text(
                    text = "동행",
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(20.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(Color.White.copy(alpha = 0.2f))
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "장례가 끝난 순간,\n곁에서 함께합니다",
                color = Color.White,
                fontSize = 15.sp,
                textAlign = TextAlign.Center,
                lineHeight = 22.sp
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "복잡한 사후 행정 절차,\n처음부터 끝까지 동행합니다",
                color = Color.White.copy(alpha = 0.5f),
                fontSize = 12.sp,
                textAlign = TextAlign.Center,
                lineHeight = 18.sp
            )
        }

        // Progress bar
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(horizontal = 24.dp)
                .padding(bottom = 44.dp)
                .alpha(contentAlpha)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(2.dp)
                    .clip(RoundedCornerShape(1.dp))
                    .background(Color.White.copy(alpha = 0.15f))
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth(progressFraction)
                    .height(2.dp)
                    .clip(RoundedCornerShape(1.dp))
                    .background(Color.White.copy(alpha = 0.55f))
            )
        }
    }
}

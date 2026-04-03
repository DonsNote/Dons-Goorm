package com.donsgoorm.app.presentation.checklist.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.Computer
import androidx.compose.material.icons.filled.Description
import androidx.compose.ui.graphics.vector.ImageVector
import java.util.UUID

data class ChecklistTask(
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val dDay: Int?,
    val isCompleted: Boolean = false
)

data class ChecklistSection(
    val id: String = UUID.randomUUID().toString(),
    val category: Category,
    val tasks: List<ChecklistTask>
) {
    enum class Category {
        ADMINISTRATIVE, FINANCIAL, DIGITAL, LEGAL;

        val title: String get() = when (this) {
            ADMINISTRATIVE -> "사무행정"
            FINANCIAL      -> "금융"
            DIGITAL        -> "디지털"
            LEGAL          -> "법원행정"
        }

        val icon: ImageVector get() = when (this) {
            ADMINISTRATIVE -> Icons.Default.Description
            FINANCIAL      -> Icons.Default.AccountBalanceWallet
            DIGITAL        -> Icons.Default.Computer
            LEGAL          -> Icons.Default.AccountBalance
        }
    }

    val earliestDDay: Int? get() =
        tasks.filter { !it.isCompleted }.mapNotNull { it.dDay }.minOrNull()

    val nextTask: ChecklistTask? get() =
        tasks.filter { !it.isCompleted }.minByOrNull { it.dDay ?: Int.MAX_VALUE }

    val completedCount: Int get() = tasks.count { it.isCompleted }
    val totalCount: Int get() = tasks.size
}

fun placeholderSections(): List<ChecklistSection> = listOf(
    ChecklistSection(
        category = ChecklistSection.Category.ADMINISTRATIVE,
        tasks = listOf(
            ChecklistTask(title = "사망신고", dDay = 7),
            ChecklistTask(title = "사망진단서 발급", dDay = 3),
            ChecklistTask(title = "건강보험 자격 상실 신고", dDay = 14),
        )
    ),
    ChecklistSection(
        category = ChecklistSection.Category.FINANCIAL,
        tasks = listOf(
            ChecklistTask(title = "은행 계좌 동결 신청", dDay = 7),
            ChecklistTask(title = "생명보험 청구", dDay = 30),
            ChecklistTask(title = "국민연금 사망 신고", dDay = 14),
        )
    ),
    ChecklistSection(
        category = ChecklistSection.Category.DIGITAL,
        tasks = listOf(
            ChecklistTask(title = "SNS 계정 처리", dDay = null),
            ChecklistTask(title = "이메일 계정 정리", dDay = null),
            ChecklistTask(title = "구독 서비스 해지", dDay = 30),
        )
    ),
    ChecklistSection(
        category = ChecklistSection.Category.LEGAL,
        tasks = listOf(
            ChecklistTask(title = "상속 포기 신청", dDay = 90),
            ChecklistTask(title = "유언장 검인 신청", dDay = null),
            ChecklistTask(title = "후견인 지정", dDay = null),
        )
    )
)

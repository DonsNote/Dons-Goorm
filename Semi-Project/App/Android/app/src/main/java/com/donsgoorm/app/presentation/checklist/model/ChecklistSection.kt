package com.donsgoorm.app.presentation.checklist.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.Computer
import androidx.compose.material.icons.filled.Description
import androidx.compose.ui.graphics.vector.ImageVector
import java.util.UUID

data class RequiredDocument(
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val isReady: Boolean = false
)

data class ChecklistTask(
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val dDay: Int?,
    val isCompleted: Boolean = false,
    val documents: List<RequiredDocument> = emptyList()
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
            ChecklistTask(title = "사망신고", dDay = 7, documents = listOf(
                RequiredDocument(title = "사망진단서 (원본)"),
                RequiredDocument(title = "신고인 신분증"),
                RequiredDocument(title = "가족관계증명서"),
            )),
            ChecklistTask(title = "사망진단서 발급", dDay = 3, documents = listOf(
                RequiredDocument(title = "신청인 신분증"),
                RequiredDocument(title = "진료비 영수증"),
                RequiredDocument(title = "가족관계증명서"),
            )),
            ChecklistTask(title = "건강보험 자격 상실 신고", dDay = 14, documents = listOf(
                RequiredDocument(title = "사망진단서"),
                RequiredDocument(title = "건강보험증"),
                RequiredDocument(title = "신청인 신분증"),
            )),
        )
    ),
    ChecklistSection(
        category = ChecklistSection.Category.FINANCIAL,
        tasks = listOf(
            ChecklistTask(title = "은행 계좌 동결 신청", dDay = 7, documents = listOf(
                RequiredDocument(title = "사망진단서"),
                RequiredDocument(title = "가족관계증명서"),
                RequiredDocument(title = "신청인 신분증"),
                RequiredDocument(title = "인감증명서"),
            )),
            ChecklistTask(title = "생명보험 청구", dDay = 30, documents = listOf(
                RequiredDocument(title = "사망진단서"),
                RequiredDocument(title = "보험증권 또는 증권번호"),
                RequiredDocument(title = "가족관계증명서"),
                RequiredDocument(title = "수익자 신분증"),
            )),
            ChecklistTask(title = "국민연금 사망 신고", dDay = 14, documents = listOf(
                RequiredDocument(title = "사망진단서"),
                RequiredDocument(title = "가족관계증명서"),
                RequiredDocument(title = "신청인 통장 사본"),
            )),
        )
    ),
    ChecklistSection(
        category = ChecklistSection.Category.DIGITAL,
        tasks = listOf(
            ChecklistTask(title = "SNS 계정 처리", dDay = null, documents = listOf(
                RequiredDocument(title = "사망확인서류 사본"),
                RequiredDocument(title = "신청인 신분증 사본"),
                RequiredDocument(title = "고인과의 관계 증명서류"),
            )),
            ChecklistTask(title = "이메일 계정 정리", dDay = null, documents = listOf(
                RequiredDocument(title = "사망확인서류"),
                RequiredDocument(title = "신청인 신분증"),
            )),
            ChecklistTask(title = "구독 서비스 해지", dDay = 30, documents = listOf(
                RequiredDocument(title = "사망진단서 또는 사망확인서"),
                RequiredDocument(title = "신청인 신분증"),
            )),
        )
    ),
    ChecklistSection(
        category = ChecklistSection.Category.LEGAL,
        tasks = listOf(
            ChecklistTask(title = "상속 포기 신청", dDay = 90, documents = listOf(
                RequiredDocument(title = "사망진단서"),
                RequiredDocument(title = "가족관계증명서 (전부 사항)"),
                RequiredDocument(title = "인감증명서"),
                RequiredDocument(title = "인감도장"),
                RequiredDocument(title = "신청인 신분증"),
            )),
            ChecklistTask(title = "유언장 검인 신청", dDay = null, documents = listOf(
                RequiredDocument(title = "유언장 원본"),
                RequiredDocument(title = "사망진단서"),
                RequiredDocument(title = "가족관계증명서"),
                RequiredDocument(title = "신청인 신분증"),
            )),
            ChecklistTask(title = "후견인 지정", dDay = null, documents = listOf(
                RequiredDocument(title = "가족관계증명서"),
                RequiredDocument(title = "신청인 신분증"),
                RequiredDocument(title = "인감증명서"),
            )),
        )
    )
)

//
//  ChecklistSection.swift
//  Accompany
//

import Foundation

struct ChecklistTask: Identifiable {
    let id = UUID()
    let title: String
    let dDay: Int?         // nil이면 기한 없음
    var isCompleted: Bool = false
}

struct ChecklistSection: Identifiable {
    let id = UUID()
    let category: Category
    var tasks: [ChecklistTask]

    enum Category: CaseIterable {
        case administrative, financial, digital, legal

        var title: String {
            switch self {
            case .administrative: return "사무행정"
            case .financial:     return "금융"
            case .digital:       return "디지털"
            case .legal:         return "법원행정"
            }
        }

        var icon: String {
            switch self {
            case .administrative: return "doc.text"
            case .financial:     return "creditcard"
            case .digital:       return "laptopcomputer"
            case .legal:         return "building.columns"
            }
        }
    }

    var earliestDDay: Int? {
        tasks.filter { !$0.isCompleted }.compactMap(\.dDay).min()
    }

    var nextTask: ChecklistTask? {
        tasks.filter { !$0.isCompleted }
             .min { ($0.dDay ?? Int.max) < ($1.dDay ?? Int.max) }
    }

    var completedCount: Int { tasks.filter(\.isCompleted).count }
    var totalCount: Int { tasks.count }
}

extension ChecklistSection {
    static func placeholder(deceasedDate: Date) -> [ChecklistSection] {
        [
            ChecklistSection(category: .administrative, tasks: [
                ChecklistTask(title: "사망신고", dDay: 7),
                ChecklistTask(title: "사망진단서 발급", dDay: 3),
                ChecklistTask(title: "건강보험 자격 상실 신고", dDay: 14),
            ]),
            ChecklistSection(category: .financial, tasks: [
                ChecklistTask(title: "은행 계좌 동결 신청", dDay: 7),
                ChecklistTask(title: "생명보험 청구", dDay: 30),
                ChecklistTask(title: "국민연금 사망 신고", dDay: 14),
            ]),
            ChecklistSection(category: .digital, tasks: [
                ChecklistTask(title: "SNS 계정 처리", dDay: nil),
                ChecklistTask(title: "이메일 계정 정리", dDay: nil),
                ChecklistTask(title: "구독 서비스 해지", dDay: 30),
            ]),
            ChecklistSection(category: .legal, tasks: [
                ChecklistTask(title: "상속 포기 신청", dDay: 90),
                ChecklistTask(title: "유언장 검인 신청", dDay: nil),
                ChecklistTask(title: "후견인 지정", dDay: nil),
            ]),
        ]
    }
}

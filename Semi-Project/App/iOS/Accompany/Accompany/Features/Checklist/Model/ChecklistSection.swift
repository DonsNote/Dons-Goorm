//
//  ChecklistSection.swift
//  Accompany
//

import Foundation

struct RequiredDocument: Identifiable {
    let id = UUID()
    let title: String
    var isReady: Bool = false
}

struct ChecklistTask: Identifiable {
    let id = UUID()
    let title: String
    let dDay: Int?
    var isCompleted: Bool = false
    var documents: [RequiredDocument]

    init(title: String, dDay: Int? = nil, documents: [RequiredDocument] = []) {
        self.title = title
        self.dDay = dDay
        self.documents = documents
    }
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
            case .financial:      return "금융"
            case .digital:        return "디지털"
            case .legal:          return "법원행정"
            }
        }

        var icon: String {
            switch self {
            case .administrative: return "doc.text"
            case .financial:      return "creditcard"
            case .digital:        return "laptopcomputer"
            case .legal:          return "building.columns"
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
                ChecklistTask(title: "사망신고", dDay: 7, documents: [
                    RequiredDocument(title: "사망진단서 (원본)"),
                    RequiredDocument(title: "신고인 신분증"),
                    RequiredDocument(title: "가족관계증명서"),
                ]),
                ChecklistTask(title: "사망진단서 발급", dDay: 3, documents: [
                    RequiredDocument(title: "신청인 신분증"),
                    RequiredDocument(title: "진료비 영수증"),
                    RequiredDocument(title: "가족관계증명서"),
                ]),
                ChecklistTask(title: "건강보험 자격 상실 신고", dDay: 14, documents: [
                    RequiredDocument(title: "사망진단서"),
                    RequiredDocument(title: "건강보험증"),
                    RequiredDocument(title: "신청인 신분증"),
                ]),
            ]),
            ChecklistSection(category: .financial, tasks: [
                ChecklistTask(title: "은행 계좌 동결 신청", dDay: 7, documents: [
                    RequiredDocument(title: "사망진단서"),
                    RequiredDocument(title: "가족관계증명서"),
                    RequiredDocument(title: "신청인 신분증"),
                    RequiredDocument(title: "인감증명서"),
                ]),
                ChecklistTask(title: "생명보험 청구", dDay: 30, documents: [
                    RequiredDocument(title: "사망진단서"),
                    RequiredDocument(title: "보험증권 또는 증권번호"),
                    RequiredDocument(title: "가족관계증명서"),
                    RequiredDocument(title: "수익자 신분증"),
                ]),
                ChecklistTask(title: "국민연금 사망 신고", dDay: 14, documents: [
                    RequiredDocument(title: "사망진단서"),
                    RequiredDocument(title: "가족관계증명서"),
                    RequiredDocument(title: "신청인 통장 사본"),
                ]),
            ]),
            ChecklistSection(category: .digital, tasks: [
                ChecklistTask(title: "SNS 계정 처리", dDay: nil, documents: [
                    RequiredDocument(title: "사망확인서류 사본"),
                    RequiredDocument(title: "신청인 신분증 사본"),
                    RequiredDocument(title: "고인과의 관계 증명서류"),
                ]),
                ChecklistTask(title: "이메일 계정 정리", dDay: nil, documents: [
                    RequiredDocument(title: "사망확인서류"),
                    RequiredDocument(title: "신청인 신분증"),
                ]),
                ChecklistTask(title: "구독 서비스 해지", dDay: 30, documents: [
                    RequiredDocument(title: "사망진단서 또는 사망확인서"),
                    RequiredDocument(title: "신청인 신분증"),
                ]),
            ]),
            ChecklistSection(category: .legal, tasks: [
                ChecklistTask(title: "상속 포기 신청", dDay: 90, documents: [
                    RequiredDocument(title: "사망진단서"),
                    RequiredDocument(title: "가족관계증명서 (전부 사항)"),
                    RequiredDocument(title: "인감증명서"),
                    RequiredDocument(title: "인감도장"),
                    RequiredDocument(title: "신청인 신분증"),
                ]),
                ChecklistTask(title: "유언장 검인 신청", dDay: nil, documents: [
                    RequiredDocument(title: "유언장 원본"),
                    RequiredDocument(title: "사망진단서"),
                    RequiredDocument(title: "가족관계증명서"),
                    RequiredDocument(title: "신청인 신분증"),
                ]),
                ChecklistTask(title: "후견인 지정", dDay: nil, documents: [
                    RequiredDocument(title: "가족관계증명서"),
                    RequiredDocument(title: "신청인 신분증"),
                    RequiredDocument(title: "인감증명서"),
                ]),
            ]),
        ]
    }
}

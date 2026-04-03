//
//  ProgressView.swift
//  Accompany
//

import SwiftUI

struct ProgressReportView: View {
    let sections: [ChecklistSection]
    let deceasedDate: Date

    private var totalCompleted: Int { sections.reduce(0) { $0 + $1.completedCount } }
    private var totalTasks: Int { sections.reduce(0) { $0 + $1.totalCount } }
    private var overallRatio: Double {
        totalTasks > 0 ? Double(totalCompleted) / Double(totalTasks) : 0
    }

    private var formattedDeceasedDate: String {
        let formatter = DateFormatter()
        formatter.dateFormat = "yyyy년 M월 d일"
        return formatter.string(from: deceasedDate)
    }

    var body: some View {
        NavigationStack {
            ScrollView {
                VStack(spacing: 20) {
                    overallCard
                    ForEach(sections) { section in
                        sectionCard(section)
                    }
                }
                .padding()
            }
            .navigationTitle("진행상황")
            .navigationBarTitleDisplayMode(.large)
        }
    }

    private var overallCard: some View {
        VStack(spacing: 16) {
            HStack {
                VStack(alignment: .leading, spacing: 4) {
                    Text("전체 진행률")
                        .font(.headline)
                    Text("사망일: \(formattedDeceasedDate)")
                        .font(.caption)
                        .foregroundColor(.secondary)
                }
                Spacer()
                Text("\(Int(overallRatio * 100))%")
                    .font(.largeTitle)
                    .fontWeight(.bold)
            }

            ProgressBar(ratio: overallRatio)

            Text("\(totalCompleted)개 완료 / 총 \(totalTasks)개")
                .font(.caption)
                .foregroundColor(.secondary)
                .frame(maxWidth: .infinity, alignment: .trailing)
        }
        .padding()
        .background(Color(uiColor: .secondarySystemGroupedBackground))
        .cornerRadius(14)
    }

    private func sectionCard(_ section: ChecklistSection) -> some View {
        let ratio = section.totalCount > 0
            ? Double(section.completedCount) / Double(section.totalCount)
            : 0

        return VStack(spacing: 10) {
            HStack(spacing: 8) {
                Image(systemName: section.category.icon)
                Text(section.category.title)
                    .font(.subheadline)
                    .fontWeight(.medium)
                Spacer()
                Text("\(section.completedCount)/\(section.totalCount)")
                    .font(.caption)
                    .foregroundColor(.secondary)
            }
            ProgressBar(ratio: ratio)
        }
        .padding()
        .background(Color(uiColor: .secondarySystemGroupedBackground))
        .cornerRadius(14)
    }
}

private struct ProgressBar: View {
    let ratio: Double

    var body: some View {
        GeometryReader { geo in
            ZStack(alignment: .leading) {
                RoundedRectangle(cornerRadius: 4)
                    .fill(Color.secondary.opacity(0.2))
                RoundedRectangle(cornerRadius: 4)
                    .fill(Color.primary)
                    .frame(width: geo.size.width * ratio)
            }
        }
        .frame(height: 6)
    }
}

#Preview {
    ProgressReportView(
        sections: ChecklistSection.placeholder(deceasedDate: Date()),
        deceasedDate: Date()
    )
}

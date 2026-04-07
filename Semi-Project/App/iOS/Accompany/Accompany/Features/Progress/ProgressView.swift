//
//  ProgressView.swift
//  Accompany
//

import SwiftUI

struct ProgressReportView: View {
    let sections: [ChecklistSection]
    let deceasedDate: Date
    var onProfileTap: () -> Void = {}

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
                VStack(spacing: 14) {
                    overallCard
                    ForEach(sections) { section in
                        sectionCard(section)
                    }
                }
                .padding()
            }
            .background(Color.App.lightBg)
            .navigationTitle("진행상황")
            .navigationBarTitleDisplayMode(.inline)
            .toolbar {
                ToolbarItem(placement: .navigationBarTrailing) {
                    Button { onProfileTap() } label: {
                        Image(systemName: "person.circle")
                            .font(.title3)
                            .foregroundColor(Color.App.accent)
                    }
                }
            }
        }
    }

    private var overallCard: some View {
        VStack(spacing: 16) {
            HStack(alignment: .center) {
                VStack(alignment: .leading, spacing: 4) {
                    Text("전체 진행률")
                        .font(.headline)
                    Text("영면일: \(formattedDeceasedDate)")
                        .font(.caption)
                        .foregroundColor(.secondary)
                }
                Spacer()
                CircularProgressView(ratio: overallRatio, size: 80)
            }

            Text("\(totalCompleted)개 완료 / 총 \(totalTasks)개")
                .font(.caption)
                .foregroundColor(.secondary)
                .frame(maxWidth: .infinity, alignment: .trailing)
        }
        .padding()
        .background(Color.App.cardBg)
        .cornerRadius(AppSpacing.cardRadius)
    }

    private func sectionCard(_ section: ChecklistSection) -> some View {
        let ratio = section.totalCount > 0
            ? Double(section.completedCount) / Double(section.totalCount)
            : 0

        return VStack(spacing: 10) {
            HStack(spacing: 8) {
                Image(systemName: section.category.icon)
                    .foregroundColor(Color.App.accent)
                Text(section.category.title)
                    .font(.subheadline)
                    .fontWeight(.medium)
                Spacer()
                Text("\(section.completedCount)/\(section.totalCount)")
                    .font(.caption)
                    .foregroundColor(.secondary)
            }
            AppProgressBar(ratio: ratio)
        }
        .padding()
        .background(Color.App.cardBg)
        .cornerRadius(AppSpacing.cardRadius)
    }
}

// 원형 프로그레스
private struct CircularProgressView: View {
    let ratio: Double
    let size: CGFloat

    var body: some View {
        ZStack {
            Circle()
                .stroke(Color.App.accent.opacity(0.15), lineWidth: 8)

            Circle()
                .trim(from: 0, to: ratio)
                .stroke(Color.App.accent, style: StrokeStyle(lineWidth: 8, lineCap: .round))
                .rotationEffect(.degrees(-90))
                .animation(.easeInOut(duration: 0.6), value: ratio)

            Text("\(Int(ratio * 100))%")
                .font(.system(size: size * 0.26, weight: .bold))
                .foregroundColor(Color.App.accent)
        }
        .frame(width: size, height: size)
    }
}

private struct AppProgressBar: View {
    let ratio: Double

    var body: some View {
        GeometryReader { geo in
            ZStack(alignment: .leading) {
                RoundedRectangle(cornerRadius: 4)
                    .fill(Color.App.accent.opacity(0.15))
                RoundedRectangle(cornerRadius: 4)
                    .fill(Color.App.accent)
                    .frame(width: geo.size.width * ratio)
            }
        }
        .frame(height: 6)
        .animation(.easeInOut(duration: 0.4), value: ratio)
    }
}

#Preview {
    ProgressReportView(
        sections: ChecklistSection.placeholder(deceasedDate: Date()),
        deceasedDate: Date()
    )
}

//
//  ChecklistView.swift
//  Accompany
//

import SwiftUI

struct ChecklistView: View {
    @Binding var sections: [ChecklistSection]

    var body: some View {
        NavigationStack {
            ScrollView {
                LazyVStack(spacing: 14) {
                    ForEach($sections) { $section in
                        NavigationLink {
                            ChecklistDetailView(section: section, sections: $sections)
                        } label: {
                            ChecklistCard(section: section)
                        }
                        .buttonStyle(.plain)
                    }
                }
                .padding()
            }
            .navigationTitle("체크리스트")
            .navigationBarTitleDisplayMode(.large)
        }
    }
}

private struct ChecklistCard: View {
    let section: ChecklistSection

    var body: some View {
        VStack(alignment: .leading, spacing: 12) {
            HStack(spacing: 10) {
                Image(systemName: section.category.icon)
                    .font(.title3)
                Text(section.category.title)
                    .font(.headline)
                Spacer()
                progressBadge
            }

            Divider()

            HStack(alignment: .bottom) {
                VStack(alignment: .leading, spacing: 4) {
                    if let task = section.nextTask {
                        Text(task.title)
                            .font(.subheadline)
                            .foregroundColor(.primary)
                            .lineLimit(1)
                    } else {
                        Text("모든 항목 완료")
                            .font(.subheadline)
                            .foregroundColor(.secondary)
                    }

                    Text("\(section.completedCount)/\(section.totalCount) 완료")
                        .font(.caption)
                        .foregroundColor(.secondary)
                }

                Spacer()

                if let dDay = section.earliestDDay {
                    Text("D-\(dDay)")
                        .font(.subheadline)
                        .fontWeight(.semibold)
                        .foregroundColor(dDay <= 7 ? .red : .primary)
                }
            }
        }
        .padding()
        .background(Color(uiColor: .secondarySystemGroupedBackground))
        .cornerRadius(14)
    }

    private var progressBadge: some View {
        let ratio = section.totalCount > 0
            ? Double(section.completedCount) / Double(section.totalCount)
            : 0
        return ZStack {
            Circle()
                .stroke(Color.secondary.opacity(0.2), lineWidth: 3)
            Circle()
                .trim(from: 0, to: ratio)
                .stroke(Color.primary, lineWidth: 3)
                .rotationEffect(.degrees(-90))
        }
        .frame(width: 28, height: 28)
    }
}

#Preview {
    ChecklistView(sections: .constant(ChecklistSection.placeholder(deceasedDate: Date())))
}

//
//  ChecklistDetailView.swift
//  Accompany
//

import SwiftUI

struct ChecklistDetailView: View {
    let section: ChecklistSection
    @Binding var sections: [ChecklistSection]

    private var sectionIndex: Int? {
        sections.firstIndex(where: { $0.id == section.id })
    }

    var body: some View {
        ScrollView {
            LazyVStack(spacing: 0) {
                ForEach(section.tasks.indices, id: \.self) { taskIndex in
                    if let si = sectionIndex {
                        NavigationLink {
                            TaskDetailView(
                                task: sections[si].tasks[taskIndex],
                                onToggleDocument: { docIndex in
                                    sections[si].tasks[taskIndex].documents[docIndex].isReady.toggle()
                                },
                                onToggleTask: {
                                    sections[si].tasks[taskIndex].isCompleted.toggle()
                                }
                            )
                        } label: {
                            TaskRow(task: sections[si].tasks[taskIndex])
                        }
                        .buttonStyle(.plain)

                        if taskIndex < section.tasks.count - 1 {
                            Divider()
                                .padding(.leading, AppSpacing.screenHorizontal)
                        }
                    }
                }
            }
            .background(Color.App.cardBg)
            .cornerRadius(AppSpacing.cardRadius)
            .padding()
        }
        .background(Color.App.lightBg)
        .navigationTitle(section.category.title)
        .navigationBarTitleDisplayMode(.large)
    }
}

private struct TaskRow: View {
    let task: ChecklistTask

    private var readyCount: Int { task.documents.filter(\.isReady).count }

    var body: some View {
        HStack(spacing: 14) {
            Image(systemName: task.isCompleted ? "checkmark.circle.fill" : "circle")
                .foregroundColor(task.isCompleted ? Color.App.accent : Color.App.accent.opacity(0.3))
                .font(.title3)

            VStack(alignment: .leading, spacing: 4) {
                Text(task.title)
                    .font(.body)
                    .strikethrough(task.isCompleted)
                    .foregroundColor(task.isCompleted ? .secondary : .primary)

                HStack(spacing: 8) {
                    if let dDay = task.dDay {
                        Text("D-\(dDay)")
                            .font(.caption)
                            .foregroundColor(dDay <= 7 ? Color.App.warning : Color.App.accentDim)
                    }
                    if !task.documents.isEmpty {
                        Text("서류 \(readyCount)/\(task.documents.count)")
                            .font(.caption)
                            .foregroundColor(.secondary)
                    }
                }
            }

            Spacer()

            Image(systemName: "chevron.right")
                .font(.caption)
                .foregroundColor(Color.App.accent.opacity(0.5))
        }
        .padding(.horizontal, AppSpacing.screenHorizontal)
        .padding(.vertical, AppSpacing.cardVertical)
    }
}

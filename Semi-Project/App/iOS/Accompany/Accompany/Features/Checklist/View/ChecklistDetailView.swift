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
        List {
            ForEach(section.tasks.indices, id: \.self) { taskIndex in
                HStack(spacing: 14) {
                    Image(systemName: taskIsCompleted(taskIndex) ? "checkmark.circle.fill" : "circle")
                        .foregroundColor(taskIsCompleted(taskIndex) ? .primary : .secondary)
                        .font(.title3)

                    VStack(alignment: .leading, spacing: 4) {
                        Text(section.tasks[taskIndex].title)
                            .font(.body)
                            .strikethrough(taskIsCompleted(taskIndex))
                            .foregroundColor(taskIsCompleted(taskIndex) ? .secondary : .primary)

                        if let dDay = section.tasks[taskIndex].dDay {
                            Text("D-\(dDay)")
                                .font(.caption)
                                .foregroundColor(dDay <= 7 ? .red : .secondary)
                        }
                    }

                    Spacer()
                }
                .contentShape(Rectangle())
                .onTapGesture {
                    toggleTask(taskIndex)
                }
            }
        }
        .listStyle(.insetGrouped)
        .navigationTitle(section.category.title)
        .navigationBarTitleDisplayMode(.large)
    }

    private func taskIsCompleted(_ index: Int) -> Bool {
        guard let si = sectionIndex else { return false }
        return sections[si].tasks[index].isCompleted
    }

    private func toggleTask(_ index: Int) {
        guard let si = sectionIndex else { return }
        sections[si].tasks[index].isCompleted.toggle()
    }
}

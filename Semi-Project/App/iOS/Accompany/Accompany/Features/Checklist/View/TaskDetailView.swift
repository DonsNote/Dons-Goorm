//
//  TaskDetailView.swift
//  Accompany
//

import SwiftUI

struct TaskDetailView: View {
    let task: ChecklistTask
    var onToggleDocument: (Int) -> Void
    var onToggleTask: () -> Void

    private var readyCount: Int { task.documents.filter(\.isReady).count }
    private var allReady: Bool { !task.documents.isEmpty && readyCount == task.documents.count }

    var body: some View {
        ScrollView {
            VStack(spacing: 16) {

                // 완료 상태 카드
                HStack(spacing: 14) {
                    VStack(alignment: .leading, spacing: 4) {
                        Text(task.title)
                            .font(.headline)
                            .foregroundColor(.primary)

                        if let dDay = task.dDay {
                            Text("D-\(dDay) 마감")
                                .font(.caption)
                                .fontWeight(.medium)
                                .foregroundColor(dDay <= 7 ? Color.App.warning : Color.App.accentDim)
                        }
                    }

                    Spacer()

                    Button(action: onToggleTask) {
                        HStack(spacing: 6) {
                            Image(systemName: task.isCompleted ? "checkmark.circle.fill" : "circle")
                            Text(task.isCompleted ? "완료" : "미완료")
                                .font(.caption)
                                .fontWeight(.medium)
                        }
                        .foregroundColor(task.isCompleted ? Color.App.accent : .secondary)
                        .padding(.horizontal, 12)
                        .padding(.vertical, 8)
                        .background(task.isCompleted ? Color.App.accentSubtle : Color.secondary.opacity(0.1))
                        .cornerRadius(20)
                    }
                }
                .padding()
                .background(Color.App.cardBg)
                .cornerRadius(AppSpacing.cardRadius)

                // 필요 서류 섹션
                VStack(alignment: .leading, spacing: 0) {
                    HStack {
                        Text("필요 서류")
                            .font(.subheadline)
                            .fontWeight(.semibold)
                            .foregroundColor(.primary)
                        Spacer()
                        Text("\(readyCount)/\(task.documents.count) 준비됨")
                            .font(.caption)
                            .foregroundColor(allReady ? Color.App.accent : .secondary)
                    }
                    .padding(.horizontal, AppSpacing.cardHorizontal)
                    .padding(.top, 16)
                    .padding(.bottom, 12)

                    // 서류 진행 바
                    AppProgressBar(ratio: task.documents.isEmpty ? 0 : Double(readyCount) / Double(task.documents.count))
                        .padding(.horizontal, AppSpacing.cardHorizontal)
                        .padding(.bottom, 12)

                    Divider()

                    ForEach(task.documents.indices, id: \.self) { docIndex in
                        VStack(spacing: 0) {
                            DocumentRow(
                                document: task.documents[docIndex],
                                onTap: { onToggleDocument(docIndex) }
                            )
                            if docIndex < task.documents.count - 1 {
                                Divider()
                                    .padding(.leading, AppSpacing.screenHorizontal + 14 + 24)
                            }
                        }
                    }

                    Spacer().frame(height: 4)
                }
                .background(Color.App.cardBg)
                .cornerRadius(AppSpacing.cardRadius)

                // 가까운 발급처 섹션 (기능 준비 중)
                VStack(alignment: .leading, spacing: 12) {
                    HStack {
                        Image(systemName: "location.circle")
                            .foregroundColor(Color.App.accent)
                        Text("가까운 발급처")
                            .font(.subheadline)
                            .fontWeight(.semibold)
                    }

                    Text("필요 서류를 발급받을 수 있는\n가장 가까운 기관을 안내해 드릴게요.")
                        .font(.caption)
                        .foregroundColor(.secondary)
                        .lineSpacing(3)

                    Button {
                        // TODO: 지도 연동
                    } label: {
                        HStack {
                            Image(systemName: "map")
                            Text("가까운 기관 찾기")
                                .fontWeight(.medium)
                        }
                        .font(.subheadline)
                        .foregroundColor(Color.App.accentDim)
                        .frame(maxWidth: .infinity)
                        .padding(.vertical, 14)
                        .background(Color.App.accentSubtle)
                        .cornerRadius(AppSpacing.cardRadius)
                    }
                    .disabled(true)
                    .opacity(0.6)

                    Text("* 해당 기능은 준비 중입니다.")
                        .font(.caption2)
                        .foregroundColor(Color.secondary.opacity(0.6))
                }
                .padding()
                .background(Color.App.cardBg)
                .cornerRadius(AppSpacing.cardRadius)
            }
            .padding()
        }
        .background(Color.App.lightBg)
        .navigationTitle(task.title)
        .navigationBarTitleDisplayMode(.inline)
    }
}

private struct DocumentRow: View {
    let document: RequiredDocument
    var onTap: () -> Void

    var body: some View {
        Button(action: onTap) {
            HStack(spacing: 14) {
                Image(systemName: document.isReady ? "checkmark.circle.fill" : "circle")
                    .foregroundColor(document.isReady ? Color.App.accent : Color.App.accent.opacity(0.3))
                    .font(.title3)

                Text(document.title)
                    .font(.subheadline)
                    .strikethrough(document.isReady)
                    .foregroundColor(document.isReady ? .secondary : .primary)

                Spacer()
            }
            .padding(.horizontal, AppSpacing.screenHorizontal)
            .padding(.vertical, AppSpacing.cardVertical)
            .contentShape(Rectangle())
        }
        .buttonStyle(.plain)
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
        .frame(height: 5)
        .animation(.easeInOut(duration: 0.3), value: ratio)
    }
}

#Preview {
    NavigationStack {
        TaskDetailView(
            task: ChecklistSection.placeholder(deceasedDate: Date())[0].tasks[0],
            onToggleDocument: { _ in },
            onToggleTask: {}
        )
    }
}

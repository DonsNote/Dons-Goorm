//
//  ChecklistSummaryView.swift
//  Accompany
//

import SwiftUI

struct ChecklistSummaryView: View {
    let deceasedDate: Date
    var onFinish: () -> Void

    @State private var progressValue: CGFloat = 0

    private let numbers = ["①", "②", "③", "④"]

    private var sections: [ChecklistSection] {
        ChecklistSection.placeholder(deceasedDate: deceasedDate)
    }

    private var totalCount: Int {
        sections.reduce(0) { $0 + $1.totalCount }
    }

    private var earliestDDay: Int? {
        sections.compactMap(\.earliestDDay).min()
    }

    var body: some View {
        ZStack {
            Color.App.lightBg.ignoresSafeArea()

            VStack(spacing: 0) {
                Spacer()

                // 헤드라인
                VStack(spacing: 6) {
                    Text("고객님이\n처리해야 할 일이")
                        .font(Font.App.headlineMd)
                        .multilineTextAlignment(.center)
                        .foregroundColor(.primary)

                    Text("\(totalCount)건")
                        .font(Font.App.accentNumber)
                        .foregroundColor(Color.App.accent)

                    Text("있습니다")
                        .font(Font.App.headlineMd)
                        .foregroundColor(.primary)
                }

                Spacer().frame(height: 20)

                // D-day 배지
                if let dDay = earliestDDay {
                    Text("가장 빠른 기한까지 D-\(dDay)일 남았습니다")
                        .font(.caption)
                        .fontWeight(.medium)
                        .foregroundColor(Color.App.warning)
                        .padding(.horizontal, 16)
                        .padding(.vertical, 8)
                        .background(Color.App.warningBg)
                        .cornerRadius(20)
                }

                Spacer().frame(height: 28)

                // 카테고리 리스트
                VStack(spacing: 10) {
                    ForEach(Array(sections.enumerated()), id: \.offset) { index, section in
                        HStack(spacing: 14) {
                            Text("\(numbers[index]) \(section.category.title)")
                                .font(.subheadline)
                                .fontWeight(.medium)
                                .foregroundColor(.primary)

                            Spacer()

                            if let dDay = section.earliestDDay {
                                Text("\(section.totalCount)건·D-\(dDay)")
                                    .font(.caption)
                                    .foregroundColor(.gray)
                            } else {
                                Text("\(section.totalCount)건")
                                    .font(.caption)
                                    .foregroundColor(.gray)
                            }
                        }
                        .padding(.horizontal, AppSpacing.cardHorizontal)
                        .padding(.vertical, AppSpacing.cardVertical)
                        .background(Color.App.cardBg)
                        .cornerRadius(AppSpacing.cardRadius)
                    }
                }
                .padding(.horizontal, AppSpacing.screenHorizontal)

                Spacer()

                // 하단 버튼 + 진행 바
                VStack(spacing: 12) {
                    Text("서비스를 상세하게 소개드릴게요")
                        .font(.subheadline)
                        .fontWeight(.medium)
                        .foregroundColor(Color.App.accentDim)
                        .frame(maxWidth: .infinity)
                        .padding(.vertical, 16)
                        .background(Color.App.accentSubtle)
                        .cornerRadius(AppSpacing.cardRadius)

                    GeometryReader { geo in
                        ZStack(alignment: .leading) {
                            Rectangle()
                                .fill(Color.primary.opacity(0.1))
                            Rectangle()
                                .fill(Color.App.accentDark)
                                .frame(width: geo.size.width * progressValue)
                        }
                        .cornerRadius(2)
                    }
                    .frame(height: 3)
                }
                .padding(.horizontal, AppSpacing.screenHorizontal)
                .padding(.bottom, AppSpacing.screenBottom)
            }
        }
        .onAppear {
            withAnimation(.linear(duration: 5.0)) { progressValue = 1.0 }
            DispatchQueue.main.asyncAfter(deadline: .now() + 5.0) { onFinish() }
        }
    }
}

#Preview {
    ChecklistSummaryView(deceasedDate: Date(), onFinish: {})
}

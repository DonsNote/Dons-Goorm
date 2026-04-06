//
//  OnboardingView.swift
//  Accompany
//

import SwiftUI

private struct OnboardingPage {
    let imageName: String
    let title: String
    let description: String
}

private let pages: [OnboardingPage] = [
    OnboardingPage(
        imageName: "checklist",
        title: "해야 할 일들을 한눈에",
        description: "사무행정, 금융, 디지털, 법원행정\n4가지 영역으로 정리된 체크리스트를\n단계별로 안내드립니다."
    ),
    OnboardingPage(
        imageName: "bubble.left.and.bubble.right",
        title: "궁금한 점은 언제든지",
        description: "어렵고 복잡한 절차들을\n가이드를 통해 편하게\n질문하고 도움받으세요."
    ),
    OnboardingPage(
        imageName: "chart.bar.doc.horizontal",
        title: "진행 현황을 한눈에",
        description: "처리된 일과 남은 일을\n보고서 형식으로 확인하며\n놓치는 것 없이 마무리하세요."
    ),
    OnboardingPage(
        imageName: "heart.circle",
        title: "홀로 남겨지지 않도록",
        description: "힘든 시간, 동행이\n곁에서 함께하겠습니다."
    ),
]

struct OnboardingView: View {
    var onFinish: () -> Void

    @State private var currentPage: Int = 0

    var body: some View {
        VStack(spacing: 0) {
            TabView(selection: $currentPage) {
                ForEach(pages.indices, id: \.self) { index in
                    OnboardingPageView(page: pages[index])
                        .tag(index)
                }
            }
            .tabViewStyle(.page(indexDisplayMode: .never))
            .animation(.easeInOut, value: currentPage)

            VStack(spacing: 24) {
                PageIndicator(total: pages.count, current: currentPage)

                Button {
                    if currentPage < pages.count - 1 {
                        currentPage += 1
                    } else {
                        onFinish()
                    }
                } label: {
                    Text(currentPage < pages.count - 1 ? "다음" : "시작하기")
                        .font(.body)
                        .fontWeight(.semibold)
                        .frame(maxWidth: .infinity)
                        .padding()
                        .background(Color.primary)
                        .foregroundColor(Color(uiColor: .systemBackground))
                        .cornerRadius(12)
                }
                .padding(.horizontal, 24)
            }
            .padding(.bottom, 52)
        }
    }
}

private struct OnboardingPageView: View {
    let page: OnboardingPage

    var body: some View {
        VStack(spacing: 32) {
            Spacer()

            Image(systemName: page.imageName)
                .font(.system(size: 80))
                .foregroundColor(.primary)

            VStack(spacing: 12) {
                Text(page.title)
                    .font(.title2)
                    .fontWeight(.bold)
                    .multilineTextAlignment(.center)

                Text(page.description)
                    .font(.subheadline)
                    .foregroundColor(.secondary)
                    .multilineTextAlignment(.center)
                    .lineSpacing(4)
            }
            .padding(.horizontal, 32)

            Spacer()
        }
    }
}

private struct PageIndicator: View {
    let total: Int
    let current: Int

    var body: some View {
        HStack(spacing: 6) {
            ForEach(0..<total, id: \.self) { index in
                Capsule()
                    .fill(index == current ? Color.primary : Color.secondary.opacity(0.3))
                    .frame(width: index == current ? 20 : 6, height: 6)
                    .animation(.easeInOut(duration: 0.25), value: current)
            }
        }
    }
}

#Preview {
    OnboardingView(onFinish: {})
}

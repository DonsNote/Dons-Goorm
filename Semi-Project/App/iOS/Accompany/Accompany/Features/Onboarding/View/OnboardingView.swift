//
//  OnboardingView.swift
//  Accompany
//

import SwiftUI

struct OnboardingView: View {
    var onFinish: () -> Void
    @State private var currentPage: Int = 0
    private let totalPages = 4

    var body: some View {
        ZStack {
            Color.App.lightBg.ignoresSafeArea()

            VStack(spacing: 0) {
                TabView(selection: $currentPage) {
                    OnboardingSlide1().tag(0)
                    OnboardingSlide2().tag(1)
                    OnboardingSlide3().tag(2)
                    OnboardingSlide4().tag(3)
                }
                .tabViewStyle(.page(indexDisplayMode: .never))
                .animation(.easeInOut, value: currentPage)

                VStack(spacing: 20) {
                    PageIndicator(total: totalPages, current: currentPage)

                    Button {
                        if currentPage < totalPages - 1 {
                            currentPage += 1
                        } else {
                            onFinish()
                        }
                    } label: {
                        Text(currentPage < totalPages - 1 ? "다음" : "지금 시작하기 →")
                            .font(.body)
                            .fontWeight(.semibold)
                            .frame(maxWidth: .infinity)
                            .padding(.vertical, 18)
                            .background(Color.App.accentDark)
                            .foregroundColor(.white)
                            .cornerRadius(16)
                    }
                    .padding(.horizontal, AppSpacing.screenHorizontal)
                }
                .padding(.bottom, 52)
                .padding(.top, 16)
            }
        }
    }
}

// MARK: - 슬라이드 1: 체크리스트 화면

private struct OnboardingSlide1: View {
    private let sections = ChecklistSection.placeholder(deceasedDate: Date())

    var body: some View {
        VStack(spacing: 0) {
            AppScreenPreview {
                ChecklistView(sections: .constant(sections), onProfileTap: {})
            }

            Spacer()

            slideCaption(
                title: "이제 혼자 챙기지\n않아도 됩니다",
                sub: "사망일자 하나만 입력하면 행정, 금융,\n상속까지 처리 목록이 자동으로 만들어집니다"
            )
        }
    }
}

// MARK: - 슬라이드 2: 태스크 상세 화면 (사망신고)

private struct OnboardingSlide2: View {
    private let sections = ChecklistSection.placeholder(deceasedDate: Date())

    var body: some View {
        VStack(spacing: 0) {
            AppScreenPreview {
                TaskDetailView(
                    task: sections[0].tasks[0],
                    onToggleDocument: { _ in },
                    onToggleTask: {}
                )
            }

            Spacer()

            slideCaption(
                title: "필요한 것들을\n저희가 미리 챙겨두었습니다",
                sub: "필요한 서류와 기관을 순서대로\n알려드릴게요"
            )
        }
    }
}

// MARK: - 슬라이드 3: 진행상황 화면

private struct OnboardingSlide3: View {
    private let sections = ChecklistSection.placeholder(deceasedDate: Date())

    var body: some View {
        VStack(spacing: 0) {
            AppScreenPreview {
                ProgressReportView(sections: sections, deceasedDate: Date())
            }

            Spacer()

            slideCaption(
                title: "놓치는 것 없이\n저희가 기억하고 있습니다",
                sub: "6개 영역 진행 상황을 한눈에\n확인할 수 있습니다"
            )
        }
    }
}

// MARK: - 슬라이드 4: 가이드 탭 화면

private struct OnboardingSlide4: View {
    var body: some View {
        VStack(spacing: 0) {
            AppScreenPreview {
                GuideView(onProfileTap: {})
            }

            Spacer()

            VStack(alignment: .leading, spacing: 12) {
                VStack(alignment: .leading, spacing: 8) {
                    Text("혼자 애쓰지 마세요\n저희가 밤낮없이 곁에 있을게요")
                        .font(.system(size: 24, weight: .bold))
                        .foregroundColor(.black)
                        .lineSpacing(4)

                    Text("복잡한 절차부터 사소한 질문까지\n실시간으로 답해드려요")
                        .font(.subheadline)
                        .foregroundColor(.secondary)
                        .lineSpacing(3)
                }

                Text("지금 시작하면 첫 알림까지 자동 설정됩니다")
                    .font(.caption)
                    .foregroundColor(Color.App.accentDim)
                    .frame(maxWidth: .infinity, alignment: .center)
                    .padding(.vertical, 12)
                    .background(Color.App.accentSubtle)
                    .cornerRadius(10)
            }
            .padding(.horizontal, AppSpacing.screenHorizontal)
            .padding(.bottom, 8)
        }
    }
}

// MARK: - 실제 앱 화면 프리뷰 래퍼

private struct AppScreenPreview<Content: View>: View {
    @ViewBuilder var content: () -> Content

    var body: some View {
        content()
            .allowsHitTesting(false)
            .frame(height: 420)
            .clipped()
            .cornerRadius(20)
            .shadow(color: .black.opacity(0.08), radius: 12, x: 0, y: 4)
            .padding(.horizontal, AppSpacing.screenHorizontal)
            .padding(.top, 36)
    }
}

// MARK: - 공통 헬퍼

private func slideCaption(title: String, sub: String) -> some View {
    VStack(alignment: .leading, spacing: 10) {
        Text(title)
            .font(.system(size: 24, weight: .bold))
            .foregroundColor(.black)
            .lineSpacing(4)

        Text(sub)
            .font(.subheadline)
            .foregroundColor(.secondary)
            .lineSpacing(3)
    }
    .frame(maxWidth: .infinity, alignment: .leading)
    .padding(.horizontal, AppSpacing.screenHorizontal)
    .padding(.bottom, 8)
}

private struct PageIndicator: View {
    let total: Int
    let current: Int

    var body: some View {
        HStack(spacing: 6) {
            ForEach(0..<total, id: \.self) { index in
                Capsule()
                    .fill(index == current ? Color.App.accentDark : Color.App.accent.opacity(0.25))
                    .frame(width: index == current ? 20 : 6, height: 6)
                    .animation(.easeInOut(duration: 0.25), value: current)
            }
        }
    }
}

private extension View {
    func cornerRadius(_ radius: CGFloat, corners: UIRectCorner) -> some View {
        clipShape(RoundedCorner(radius: radius, corners: corners))
    }
}

private struct RoundedCorner: Shape {
    var radius: CGFloat
    var corners: UIRectCorner
    func path(in rect: CGRect) -> Path {
        Path(UIBezierPath(roundedRect: rect, byRoundingCorners: corners,
                          cornerRadii: CGSize(width: radius, height: radius)).cgPath)
    }
}

#Preview {
    OnboardingView(onFinish: {})
}

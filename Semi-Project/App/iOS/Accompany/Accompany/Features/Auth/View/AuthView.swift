import SwiftUI

struct AuthView: View {
    @EnvironmentObject private var viewModel: AuthViewModel

    var body: some View {
        ZStack {
            Color.App.lightBg.ignoresSafeArea()

            VStack(alignment: .leading, spacing: 0) {
                VStack(alignment: .leading, spacing: 16) {
                    Text("처음부터 끝까지\n함께 동행하겠습니다")
                        .font(Font.App.displayBold)
                        .foregroundColor(.black)
                        .lineSpacing(4)

                    Text("고인께서 남기신 것들\n저희가 끝까지 곁에서 정리해드리겠습니다")
                        .font(.subheadline)
                        .foregroundColor(.gray)
                        .lineSpacing(3)
                }
                .padding(.top, 72)
                .padding(.horizontal, 28)

                Spacer()

                VStack(alignment: .leading, spacing: 20) {
                    if let error = viewModel.errorMessage {
                        Text(error)
                            .font(.caption)
                            .foregroundColor(Color.App.warning)
                    }

                    Text("목록 저장과 D-day 알림\n로그인 후 시작 됩니다")
                        .font(.footnote)
                        .foregroundColor(Color.App.accentDim)
                        .lineSpacing(3)

                    VStack(spacing: 12) {
                        SocialLoginButton(
                            label: "카카오로 시작하기",
                            customIcon: "K",
                            backgroundColor: Color.App.kakao,
                            foregroundColor: Color.App.kakaoFg
                        ) {
                            viewModel.signInMock()
                        }

                        SocialLoginButton(
                            label: "Google로 시작하기",
                            customIcon: "G",
                            backgroundColor: .white,
                            foregroundColor: .black,
                            hasBorder: true
                        ) {
                            viewModel.signInMock()
                        }

                        SocialLoginButton(
                            label: "Apple로 시작하기",
                            icon: "apple.logo",
                            backgroundColor: .black,
                            foregroundColor: .white
                        ) {
                            viewModel.signInMock()
                        }
                    }
                    .disabled(viewModel.isLoading)
                    .overlay {
                        if viewModel.isLoading { ProgressView() }
                    }
                }
                .padding(.horizontal, AppSpacing.screenHorizontal)
                .padding(.bottom, AppSpacing.screenBottom + 12)
            }
        }
    }
}

private struct SocialLoginButton: View {
    let label: String
    var icon: String? = nil
    var customIcon: String? = nil
    let backgroundColor: Color
    let foregroundColor: Color
    var hasBorder: Bool = false
    let action: () -> Void

    var body: some View {
        Button(action: action) {
            HStack(spacing: 10) {
                if let icon {
                    Image(systemName: icon)
                        .font(.body)
                        .frame(width: 20)
                } else if let customIcon {
                    Text(customIcon)
                        .font(.body)
                        .fontWeight(.bold)
                        .frame(width: 20)
                }
                Text(label)
                    .font(.body)
                    .fontWeight(.medium)
                    .frame(maxWidth: .infinity)
            }
            .padding(.vertical, 14)
            .padding(.horizontal, 20)
            .background(backgroundColor)
            .foregroundColor(foregroundColor)
            .cornerRadius(AppSpacing.buttonRadius)
            .overlay {
                if hasBorder {
                    RoundedRectangle(cornerRadius: AppSpacing.buttonRadius)
                        .stroke(Color.secondary.opacity(0.3), lineWidth: 1)
                }
            }
        }
    }
}

#Preview {
    AuthView()
        .environmentObject(AuthViewModel())
}

import SwiftUI

struct AuthView: View {
    @EnvironmentObject private var viewModel: AuthViewModel

    var body: some View {
        VStack(spacing: 0) {
            Spacer()

            VStack(spacing: 12) {
                Image(systemName: "heart.circle")
                    .font(.system(size: 64))
                    .foregroundColor(.primary)

                Text("동행")
                    .font(.largeTitle)
                    .fontWeight(.bold)

                Text("홀로 남겨지지 않도록,\n함께 걷겠습니다.")
                    .font(.subheadline)
                    .foregroundColor(.secondary)
                    .multilineTextAlignment(.center)
            }

            Spacer()

            VStack(spacing: 12) {
                if let error = viewModel.errorMessage {
                    Text(error)
                        .font(.caption)
                        .foregroundColor(.red)
                        .multilineTextAlignment(.center)
                        .padding(.horizontal)
                }

                SocialLoginButton(
                    label: "Apple로 계속하기",
                    icon: "apple.logo",
                    backgroundColor: .black,
                    foregroundColor: .white
                ) {
                    viewModel.signInMock()
                }

                SocialLoginButton(
                    label: "Google로 계속하기",
                    customIcon: "G",
                    backgroundColor: .white,
                    foregroundColor: .black,
                    hasBorder: true
                ) {
                    viewModel.signInMock()
                }

                SocialLoginButton(
                    label: "카카오로 계속하기",
                    customIcon: "K",
                    backgroundColor: Color(red: 1.0, green: 0.9, blue: 0.0),
                    foregroundColor: Color(red: 0.13, green: 0.13, blue: 0.13)
                ) {
                    viewModel.signInMock()
                }


            }
            .disabled(viewModel.isLoading)
            .overlay {
                if viewModel.isLoading {
                    ProgressView()
                }
            }
            .padding(.horizontal, 24)
            .padding(.bottom, 52)
        }
        .background(Color(uiColor: .systemBackground))
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
            .cornerRadius(12)
            .overlay {
                if hasBorder {
                    RoundedRectangle(cornerRadius: 12)
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

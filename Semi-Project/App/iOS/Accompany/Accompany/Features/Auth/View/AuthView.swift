import SwiftUI

struct AuthView: View {
    @StateObject private var viewModel = AuthViewModel()
    @State private var email = ""
    @State private var password = ""
    @State private var isSignUp = false

    var body: some View {
        VStack(spacing: 20) {
            Text(isSignUp ? "회원가입" : "로그인")
                .font(.largeTitle)
                .bold()

            TextField("이메일", text: $email)
                .keyboardType(.emailAddress)
                .autocapitalization(.none)
                .padding()
                .background(Color(.systemGray6))
                .cornerRadius(10)

            SecureField("비밀번호", text: $password)
                .padding()
                .background(Color(.systemGray6))
                .cornerRadius(10)

            if let error = viewModel.errorMessage {
                Text(error)
                    .foregroundColor(.red)
                    .font(.caption)
            }

            Button {
                Task {
                    if isSignUp {
                        await viewModel.signUp(email: email, password: password)
                    } else {
                        await viewModel.signIn(email: email, password: password)
                    }
                }
            } label: {
                if viewModel.isLoading {
                    ProgressView()
                        .frame(maxWidth: .infinity)
                        .padding()
                } else {
                    Text(isSignUp ? "회원가입" : "로그인")
                        .frame(maxWidth: .infinity)
                        .padding()
                        .background(Color.blue)
                        .foregroundColor(.white)
                        .cornerRadius(10)
                }
            }

            Button(isSignUp ? "이미 계정이 있으신가요? 로그인" : "계정이 없으신가요? 회원가입") {
                isSignUp.toggle()
            }
            .font(.footnote)
        }
        .padding()
    }
}

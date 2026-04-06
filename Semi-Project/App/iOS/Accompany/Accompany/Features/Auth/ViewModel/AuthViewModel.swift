import Foundation
import Supabase
internal import Combine

@MainActor
final class AuthViewModel: ObservableObject {
    @Published var isLoggedIn: Bool = false
    @Published var isLoading: Bool = false
    @Published var errorMessage: String? = nil

    private let supabase = SupabaseManager.shared.client

    init() {
        Task { await checkSession() }
    }

    func checkSession() async {
        if let _ = try? await supabase.auth.session {
            isLoggedIn = true
        }
    }

    func signInWithApple() async {
        isLoading = true
        defer { isLoading = false }
        do {
            try await supabase.auth.signInWithOAuth(provider: .apple)
            isLoggedIn = true
        } catch {
            errorMessage = error.localizedDescription
        }
    }

    func signInWithGoogle() async {
        isLoading = true
        defer { isLoading = false }
        do {
            try await supabase.auth.signInWithOAuth(provider: .google)
            isLoggedIn = true
        } catch {
            errorMessage = error.localizedDescription
        }
    }

    func signInWithKakao() async {
        isLoading = true
        defer { isLoading = false }
        do {
            try await supabase.auth.signInWithOAuth(provider: .kakao)
            isLoggedIn = true
        } catch {
            errorMessage = error.localizedDescription
        }
    }

    // TODO: 실제 소셜 로그인 연동 후 제거
    func signInMock() {
        isLoggedIn = true
    }

    func signOut() async {
        do {
            try await supabase.auth.signOut()
            isLoggedIn = false
        } catch {
            errorMessage = error.localizedDescription
        }
    }
}

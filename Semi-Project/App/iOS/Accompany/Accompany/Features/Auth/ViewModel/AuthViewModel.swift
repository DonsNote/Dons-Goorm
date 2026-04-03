import Foundation
import Supabase
internal import Combine

@MainActor
final class AuthViewModel: ObservableObject {
    @Published var user: User? = nil
    @Published var isLoading = false
    @Published var errorMessage: String? = nil

    private let supabase = SupabaseManager.shared.client

    func signIn(email: String, password: String) async {
        isLoading = true
        defer { isLoading = false }
        do {
            let session = try await supabase.auth.signIn(email: email, password: password)
            user = session.user
        } catch {
            errorMessage = error.localizedDescription
        }
    }

    func signUp(email: String, password: String) async {
        isLoading = true
        defer { isLoading = false }
        do {
            let session = try await supabase.auth.signUp(email: email, password: password)
            user = session.user
        } catch {
            errorMessage = error.localizedDescription
        }
    }

    func signOut() async {
        do {
            try await supabase.auth.signOut()
            user = nil
        } catch {
            errorMessage = error.localizedDescription
        }
    }
}

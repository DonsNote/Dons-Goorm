//
//  ContentView.swift
//  Accompany
//
//  Created by Don on 4/2/26.
//

import SwiftUI

enum AppStep {
    case splash
    case deceasedDateInput
    case onboarding
    case login
    case main
}

struct ContentView: View {
    @State private var step: AppStep = .splash
    @State private var deceasedDate: Date = Date()
    @StateObject private var authViewModel = AuthViewModel()

    var body: some View {
        switch step {
        case .splash:
            SplashView {
                step = .deceasedDateInput
            }
        case .deceasedDateInput:
            DeceasedDateInputView { date in
                deceasedDate = date
                step = .onboarding
            }
        case .onboarding:
            OnboardingView {
                step = .login
            }
        case .login:
            AuthView()
                .environmentObject(authViewModel)
                .onChange(of: authViewModel.isLoggedIn) {
                    if authViewModel.isLoggedIn { step = .main }
                }
        case .main:
            MainTabView(deceasedDate: deceasedDate)
        }
    }
}

#Preview {
    ContentView()
}

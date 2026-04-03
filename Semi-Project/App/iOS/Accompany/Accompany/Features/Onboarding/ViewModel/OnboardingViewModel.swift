//
//  OnboardingViewModel.swift
//  Accompany
//

import Foundation
internal import Combine

class OnboardingViewModel: ObservableObject {
    @Published var deceasedDate: Date = Date()
    @Published var showConfirmAlert: Bool = false

    var formattedDate: String {
        let formatter = DateFormatter()
        formatter.dateFormat = "yyyy년 M월 d일"
        return formatter.string(from: deceasedDate)
    }

    func confirm() {
        showConfirmAlert = true
    }
}

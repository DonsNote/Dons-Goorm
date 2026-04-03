//
//  ContentView.swift
//  Accompany
//
//  Created by Don on 4/2/26.
//

import SwiftUI

struct ContentView: View {
    @State private var showSplash: Bool = true
    @State private var deceasedDate: Date? = nil

    var body: some View {
        if showSplash {
            SplashView {
                showSplash = false
            }
        } else if let date = deceasedDate {
            MainTabView(deceasedDate: date)
        } else {
            DeceasedDateInputView { date in
                deceasedDate = date
            }
        }
    }
}

#Preview {
    ContentView()
}

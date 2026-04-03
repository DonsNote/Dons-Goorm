//
//  SplashView.swift
//  Accompany
//

import SwiftUI

struct SplashView: View {
    @State private var opacity: Double = 0
    var onFinish: () -> Void

    var body: some View {
        ZStack {
            Color.black.ignoresSafeArea()

            Text("삼가 고인의 명복을 빕니다.")
                .font(.title2)
                .fontWeight(.light)
                .foregroundColor(.white)
                .opacity(opacity)
        }
        .onAppear {
            withAnimation(.easeIn(duration: 1.2)) {
                opacity = 1
            }
            DispatchQueue.main.asyncAfter(deadline: .now() + 2.8) {
                withAnimation(.easeOut(duration: 0.8)) {
                    opacity = 0
                }
            }
            DispatchQueue.main.asyncAfter(deadline: .now() + 3.6) {
                onFinish()
            }
        }
    }
}

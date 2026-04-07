//
//  SplashView.swift
//  Accompany
//

import SwiftUI

struct SplashView: View {
    @State private var opacity: Double = 0
    @State private var progress: CGFloat = 0
    var onFinish: () -> Void

    var body: some View {
        ZStack {
            Color.App.splashBg.ignoresSafeArea()

            VStack(spacing: 0) {
                Spacer()

                VStack(spacing: 0) {
                    RoundedRectangle(cornerRadius: 18)
                        .fill(Color.App.splashIconBg)
                        .frame(width: 72, height: 72)

                    Spacer().frame(height: 20)

                    Text("동행")
                        .font(Font.App.appTitle)
                        .foregroundColor(.white)
                        .padding(.bottom, 20)
                        .background(alignment: .bottom) {
                            Rectangle()
                                .fill(Color.white.opacity(0.2))
                                .frame(height: 1)
                        }

                    Spacer().frame(height: 20)

                    VStack(spacing: 10) {
                        Text("장례가 끝난 순간,\n곁에서 함께합니다")
                            .font(.body)
                            .foregroundColor(.white)
                            .multilineTextAlignment(.center)
                            .lineSpacing(4)

                        Text("복잡한 사후 행정 절차,\n처음부터 끝까지 동행합니다")
                            .font(.caption)
                            .foregroundColor(.white.opacity(0.5))
                            .multilineTextAlignment(.center)
                            .lineSpacing(3)
                    }
                }
                .opacity(opacity)

                Spacer()

                GeometryReader { geo in
                    ZStack(alignment: .leading) {
                        Rectangle()
                            .fill(Color.white.opacity(0.15))
                        Rectangle()
                            .fill(Color.white.opacity(0.55))
                            .frame(width: geo.size.width * progress)
                    }
                    .frame(height: 2)
                }
                .frame(height: 2)
                .padding(.horizontal, AppSpacing.screenHorizontal)
                .padding(.bottom, 44)
                .opacity(opacity)
            }
        }
        .onAppear {
            withAnimation(.easeIn(duration: 0.8)) { opacity = 1 }
            withAnimation(.linear(duration: 3.0)) { progress = 1 }
            DispatchQueue.main.asyncAfter(deadline: .now() + 3.2) {
                withAnimation(.easeOut(duration: 0.4)) { opacity = 0 }
            }
            DispatchQueue.main.asyncAfter(deadline: .now() + 3.6) {
                onFinish()
            }
        }
    }
}

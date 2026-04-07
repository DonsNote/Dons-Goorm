//
//  DesignSystem.swift
//  Accompany
//

import SwiftUI

// MARK: - Colors

extension Color {
    enum App {
        // Backgrounds
        /// 스플래시 어두운 그린 배경 #1B2B24
        static let splashBg      = Color(red: 0.106, green: 0.169, blue: 0.141)
        /// 스플래시 아이콘 배경 #263C32
        static let splashIconBg  = Color(red: 0.149, green: 0.231, blue: 0.196)
        /// 라이트 크림 배경 #F5F0E8 (날짜입력·요약·로그인)
        static let lightBg       = Color(red: 0.96,  green: 0.94,  blue: 0.91)
        /// 카드 반투명 흰 배경
        static let cardBg        = Color.white.opacity(0.7)

        // Accent (틸 계열)
        /// 메인 틸 #61A08D
        static let accent        = Color(red: 0.38, green: 0.62, blue: 0.55)
        /// 연한 틸 텍스트 #80A699
        static let accentDim     = Color(red: 0.50, green: 0.65, blue: 0.60)
        /// 틸 배경 서브 #D9EBDF
        static let accentSubtle  = Color(red: 0.85, green: 0.92, blue: 0.88)
        /// 짙은 그린 (진행 바) #2E4738
        static let accentDark    = Color(red: 0.18, green: 0.28, blue: 0.22)

        // Semantic
        /// 경고 빨강 텍스트
        static let warning       = Color(red: 0.85, green: 0.35, blue: 0.35)
        /// 경고 빨강 배경
        static let warningBg     = Color(red: 1.00, green: 0.88, blue: 0.88)

        // Social Login
        static let kakao         = Color(red: 1.00, green: 0.90, blue: 0.00)
        static let kakaoFg       = Color(red: 0.13, green: 0.13, blue: 0.13)
    }
}

// MARK: - Typography

extension Font {
    enum App {
        /// 화면 대표 헤드라인 (28pt Bold) - 날짜입력·로그인
        static let displayBold   = Font.system(size: 28, weight: .bold)
        /// 요약 화면 헤드라인 (22pt Medium)
        static let headlineMd    = Font.system(size: 22, weight: .medium)
        /// 강조 숫자 (36pt Bold) - 요약 건수
        static let accentNumber  = Font.system(size: 36, weight: .bold)
        /// 앱 타이틀 (28pt Semibold) - 스플래시 "동행"
        static let appTitle      = Font.system(size: 28, weight: .semibold)
    }
}

// MARK: - Spacing

enum AppSpacing {
    static let screenHorizontal: CGFloat = 24
    static let cardHorizontal: CGFloat   = 18
    static let cardVertical: CGFloat     = 14
    static let cardRadius: CGFloat       = 14
    static let buttonRadius: CGFloat     = 12
    static let screenBottom: CGFloat     = 40
}

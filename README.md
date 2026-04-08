# Dons-Goorm

DonsNote in Goorm Deep Dive

---

## 서비스 소개

**Accompany (동행)** — 사후 유족 지원 서비스

사람이 사망한 이후, 남겨진 유족이 처리해야 하는 복잡한 행정 절차(관공서 신고, 금융/보험 정리 등)와 디지털 자산 정리(SNS, 이메일, 구독 서비스 등)를 함께 도와주는 동행 서비스입니다.

**개발 순서**
1. iOS + Server API 기반 확립
2. Android
3. Web

---

## 프로젝트 구성

```
Dons-Goorm/
├── Semi-Project/
│   ├── Server/          # Express.js 백엔드 서버
│   ├── Web/             # Next.js 웹 프론트엔드
│   └── App/
│       ├── Android/     # Android 앱 (Kotlin + Jetpack Compose)
│       └── iOS/         # iOS 앱 (SwiftUI)
└── docker-compose.yml
```

---

## Semi-Project

### Server

**Node.js + Express + TypeScript** 기반 REST API 서버

| 항목 | 내용 |
|------|------|
| 런타임 | Node.js |
| 언어 | TypeScript |
| 프레임워크 | Express 4 |
| ORM | Prisma 5 |
| 데이터베이스 | PostgreSQL (Supabase) |
| 인증 | Supabase Auth (JWT Bearer Token) |
| 포트 | 4000 |

**주요 구성**

- `src/app.ts` — Express 앱 설정 (helmet, cors, morgan)
- `src/index.ts` — 서버 진입점
- `src/routes/index.ts` — API 라우터 (`/api`)
- `src/middlewares/auth.ts` — Supabase JWT 인증 미들웨어
- `src/lib/prisma.ts` — Prisma 클라이언트
- `src/lib/supabase.ts` — Supabase 클라이언트
- `prisma/schema.prisma` — DB 스키마 정의
- `GET /health` — 헬스체크 엔드포인트

**환경변수 (`.env`)**

```env
PORT=4000
SUPABASE_URL=
SUPABASE_ANON_KEY=
SUPABASE_SERVICE_ROLE_KEY=
DATABASE_URL=        # Supabase Transaction Pooler (포트 6543)
DIRECT_URL=          # Supabase Direct Connection (포트 5432)
```

**스크립트**

```bash
npm run dev          # tsx watch로 개발 서버 실행
npm run build        # TypeScript 컴파일
npm run start        # 빌드 결과 실행
npm run db:push      # DB 스키마 반영
npm run db:migrate   # 마이그레이션 실행
npm run db:studio    # Prisma Studio 실행
```

---

### Web

**Next.js 14 + TypeScript** 기반 웹 클라이언트

| 항목 | 내용 |
|------|------|
| 프레임워크 | Next.js 14 (App Router) |
| 언어 | TypeScript |
| 스타일 | Tailwind CSS |
| 상태관리 | Zustand (persist) |
| 폼 | React Hook Form + Zod |
| 인증 | Supabase SSR Auth |
| 포트 | 3000 |

**페이지 구조**

```
src/app/
├── page.tsx                          # 스플래시 (자동 라우팅)
├── login/page.tsx                    # 로그인 (카카오)
├── onboarding/
│   ├── date/page.tsx                 # 사망일 입력
│   ├── intro/page.tsx                # 서비스 소개 슬라이드
│   └── summary/page.tsx             # 체크리스트 요약
├── (main)/                           # 로그인 후 메인 (Header + TabNav)
│   ├── checklist/page.tsx            # 카테고리 목록
│   ├── checklist/[category]/page.tsx # 카테고리별 태스크
│   ├── checklist/[category]/[taskId]/page.tsx  # 태스크 상세
│   ├── guide/page.tsx                # AI 가이드 챗
│   ├── progress/page.tsx             # 전체 진행 현황
│   └── profile/page.tsx             # 프로필
├── stats/page.tsx                    # 관리자 통계 대시보드
└── api/
    ├── track/route.ts                # POST /api/track (방문/시작 이벤트 기록)
    └── stats/route.ts               # GET /api/stats (통계 조회)
```

**주요 구성**

- `src/components/layout/Header.tsx` — 상단 헤더
- `src/components/layout/TabNav.tsx` — 하단 탭 내비게이션
- `src/components/VisitTracker.tsx` — 방문 이벤트 자동 추적
- `src/lib/design.ts` — 디자인 토큰 (iOS DesignSystem과 동일한 색상값)
- `src/lib/analyticsStore.ts` — 파일 기반 analytics (`analytics-data.json`)
- `src/lib/supabase/client.ts` — 브라우저용 Supabase 클라이언트
- `src/lib/supabase/server.ts` — 서버 컴포넌트용 Supabase 클라이언트
- `src/middleware.ts` — Next.js 미들웨어 (인증 세션 처리)
- `src/store/useAuthStore.ts` — Zustand 인증 상태 스토어
- `src/store/useChecklistStore.ts` — 체크리스트 상태 스토어 (persist)
- `src/store/useOnboardingStore.ts` — 온보딩 상태 스토어 (persist)
- `analytics-data.json` — analytics 이벤트 영속 저장 파일

**체크리스트 카테고리**

| 카테고리 | ID | 주요 태스크 |
|---------|-----|------------|
| 사무행정 | `administrative` | 사망신고, 사망진단서 발급, 건강보험 자격 상실 신고 |
| 금융 | `financial` | 은행 계좌 동결, 생명보험 청구, 국민연금 사망 신고 |
| 디지털 | `digital` | SNS 계정 처리, 이메일 계정 정리, 구독 서비스 해지 |
| 법원행정 | `legal` | 상속 포기 신청, 유언장 검인 신청, 후견인 지정 |

**환경변수 (`.env`)**

```env
NEXT_PUBLIC_SUPABASE_URL=
NEXT_PUBLIC_SUPABASE_ANON_KEY=
NEXT_PUBLIC_API_URL=http://localhost:4000
```

**스크립트**

```bash
npm run dev      # 개발 서버 실행
npm run build    # 프로덕션 빌드
npm run start    # 프로덕션 서버 실행
npm run lint     # ESLint 검사
```

---

### App / Android

**Kotlin + Jetpack Compose + MVVM** 기반 Android 앱

| 항목 | 내용 |
|------|------|
| 언어 | Kotlin 2.0 |
| UI | Jetpack Compose |
| 아키텍처 | MVVM |
| DI | Hilt |
| 네트워크 | Retrofit 2 + OkHttp |
| 인증 | Supabase Auth (gotrue-kt) |
| DB | Supabase Postgrest |

**주요 라이브러리**

- Hilt — 의존성 주입
- Retrofit + Gson — REST API 통신
- Supabase Kotlin SDK (Auth, Postgrest, Storage)
- Jetpack Navigation, ViewModel, Lifecycle

**주요 구성**

```
presentation/
├── MainActivity.kt                   # 진입점
├── AppNavigation.kt                  # 전체 내비게이션 그래프
├── splash/SplashScreen.kt            # 스플래시 화면
├── auth/
│   ├── AuthScreen.kt
│   └── AuthViewModel.kt
├── onboarding/
│   ├── DeceasedDateInputScreen.kt
│   ├── OnboardingSlideScreen.kt
│   ├── ChecklistSummaryScreen.kt
│   └── OnboardingViewModel.kt
├── main/MainTabScreen.kt             # 하단 탭 메인
├── checklist/
│   ├── ChecklistScreen.kt
│   ├── ChecklistDetailScreen.kt
│   ├── TaskDetailScreen.kt
│   ├── ChecklistViewModel.kt
│   └── model/ChecklistSection.kt
├── guide/GuideScreen.kt              # AI 가이드 챗
├── progress/ProgressScreen.kt        # 진행 현황
└── ui/DesignSystem.kt                # 디자인 토큰

di/AppModule.kt                       # Hilt 모듈 (Supabase, Retrofit, ApiService)
data/remote/ApiService.kt             # Retrofit API 인터페이스
DonsGoormApplication.kt              # Hilt Application 클래스
```

---

### App / iOS

**SwiftUI + MVVM** 기반 iOS 앱 (프로젝트명: Accompany)

| 항목 | 내용 |
|------|------|
| 언어 | Swift |
| UI | SwiftUI |
| 아키텍처 | MVVM |
| 인증 | Supabase Auth |
| 네트워크 | URLSession 기반 APIService |

**주요 구성**

```
Core/
├── Config/Config.swift               # 환경 설정
├── DesignSystem.swift                # 디자인 토큰 (색상, 타이포)
├── Network/APIService.swift          # REST API 클라이언트
└── Supabase/SupabaseManager.swift    # Supabase 클라이언트 관리

Features/
├── Auth/
│   ├── View/AuthView.swift
│   └── ViewModel/AuthViewModel.swift
├── Onboarding/
│   ├── View/OnboardingView.swift
│   ├── View/DeceasedDateInputView.swift
│   ├── View/ChecklistSummaryView.swift
│   └── ViewModel/OnboardingViewModel.swift
├── Main/MainTabView.swift            # 하단 탭 메인
├── Checklist/
│   ├── Model/ChecklistSection.swift
│   ├── View/ChecklistView.swift
│   ├── View/ChecklistDetailView.swift
│   └── View/TaskDetailView.swift
├── Guide/GuideView.swift             # AI 가이드 챗
├── Progress/ProgressView.swift       # 진행 현황
├── Profile/UserProfileView.swift     # 프로필
└── Splash/SplashView.swift           # 스플래시 화면
```

---

## 기술 스택 요약

| 영역 | 기술 |
|------|------|
| 백엔드 | Node.js, Express, TypeScript, Prisma |
| 웹 | Next.js 14, React 18, Tailwind CSS, Zustand |
| Android | Kotlin, Jetpack Compose, Hilt, Retrofit, Supabase Kotlin SDK |
| iOS | Swift, SwiftUI, Supabase Swift SDK |
| DB / Auth | Supabase (PostgreSQL + Auth) |
| 인프라 | Docker, Docker Compose |
| 패키지 매니저 | npm |

---

## 실행 방법

### Docker Compose (Server + Web)

```bash
docker-compose up --build
```

- Web: http://localhost:3000
- Server: http://localhost:4000
- Health: http://localhost:4000/health
- 통계 대시보드: http://localhost:3000/stats

### 로컬 개발

```bash
# 의존성 설치 (루트에서)
npm install

# 서버 개발 서버
cd Semi-Project/Server && npm run dev

# 웹 개발 서버
cd Semi-Project/Web && npm run dev
```

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
│       ├── Android/     # Android 앱 (Kotlin)
│       └── iOS/         # iOS 앱 (SwiftUI)
├── docker-compose.yml
└── pnpm-workspace.yaml
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
pnpm dev          # tsx watch로 개발 서버 실행
pnpm build        # TypeScript 컴파일
pnpm start        # 빌드 결과 실행
pnpm db:push      # DB 스키마 반영
pnpm db:migrate   # 마이그레이션 실행
pnpm db:studio    # Prisma Studio 실행
```

---

### Web

**Next.js 14 + TypeScript** 기반 웹 클라이언트

| 항목 | 내용 |
|------|------|
| 프레임워크 | Next.js 14 (App Router) |
| 언어 | TypeScript |
| 스타일 | Tailwind CSS |
| 상태관리 | Zustand |
| 폼 | React Hook Form + Zod |
| 인증 | Supabase SSR Auth |
| 포트 | 3000 |

**주요 구성**

- `src/app/` — App Router 페이지
- `src/lib/supabase/client.ts` — 브라우저용 Supabase 클라이언트
- `src/lib/supabase/server.ts` — 서버 컴포넌트용 Supabase 클라이언트
- `src/middleware.ts` — Next.js 미들웨어 (인증 세션 처리)
- `src/store/useAuthStore.ts` — Zustand 인증 상태 스토어

**환경변수 (`.env`)**

```env
NEXT_PUBLIC_SUPABASE_URL=
NEXT_PUBLIC_SUPABASE_ANON_KEY=
NEXT_PUBLIC_API_URL=http://localhost:4000
```

**스크립트**

```bash
pnpm dev      # 개발 서버 실행
pnpm build    # 프로덕션 빌드
pnpm start    # 프로덕션 서버 실행
pnpm lint     # ESLint 검사
```

---

### App / Android

**Kotlin + MVVM** 기반 Android 앱

| 항목 | 내용 |
|------|------|
| 언어 | Kotlin 2.0 |
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

- `DonsGoormApplication.kt` — Hilt Application 클래스
- `di/AppModule.kt` — Hilt 모듈 (Supabase, Retrofit, ApiService 제공)
- `data/remote/ApiService.kt` — Retrofit API 인터페이스
- `presentation/auth/AuthViewModel.kt` — 인증 ViewModel

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

- `Core/Config/Config.swift` — 환경 설정
- `Core/Network/APIService.swift` — REST API 클라이언트
- `Core/Supabase/SupabaseManager.swift` — Supabase 클라이언트 관리
- `Features/Auth/View/AuthView.swift` — 로그인/회원가입 화면
- `Features/Auth/ViewModel/AuthViewModel.swift` — 인증 ViewModel

---

## 기술 스택 요약

| 영역 | 기술 |
|------|------|
| 백엔드 | Node.js, Express, TypeScript, Prisma |
| 웹 | Next.js 14, React 18, Tailwind CSS, Zustand |
| Android | Kotlin, Hilt, Retrofit, Supabase Kotlin SDK |
| iOS | Swift, SwiftUI, Supabase Swift SDK |
| DB / Auth | Supabase (PostgreSQL + Auth) |
| 인프라 | Docker, Docker Compose |
| 패키지 매니저 | pnpm (workspace) |

---

## 실행 방법

### Docker Compose (Server + Web)

```bash
docker-compose up --build
```

- Web: http://localhost:3000
- Server: http://localhost:4000
- Health: http://localhost:4000/health

### 로컬 개발

```bash
# 의존성 설치 (루트에서)
npm install

# 서버 개발 서버
cd Semi-Project/Server && npm run dev

# 웹 개발 서버
cd Semi-Project/Web && npm run dev
```

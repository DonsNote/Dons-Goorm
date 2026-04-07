'use client'

import { useState } from 'react'
import { useRouter } from 'next/navigation'
import { useOnboardingStore } from '@/store/useOnboardingStore'
import { colors } from '@/lib/design'

const captions = [
  {
    title: '이제 혼자 챙기지\n않아도 됩니다',
    sub: '사망일자 하나만 입력하면 행정, 금융,\n상속까지 처리 목록이 자동으로 만들어집니다',
  },
  {
    title: '필요한 것들을\n저희가 미리 챙겨두었습니다',
    sub: '필요한 서류와 기관을 순서대로\n알려드릴게요',
  },
  {
    title: '놓치는 것 없이\n저희가 기억하고 있습니다',
    sub: '6개 영역 진행 상황을 한눈에\n확인할 수 있습니다',
  },
  {
    title: '혼자 애쓰지 마세요\n저희가 밤낮없이 곁에 있을게요',
    sub: '복잡한 절차부터 사소한 질문까지\n실시간으로 답해드려요',
  },
]

export default function OnboardingIntroPage() {
  const router = useRouter()
  const { setOnboardingDone } = useOnboardingStore()
  const [current, setCurrent] = useState(0)

  function handleNext() {
    if (current < captions.length - 1) {
      setCurrent(current + 1)
    } else {
      // 지금 시작하기 클릭 카운팅
      fetch('/api/track', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ type: 'start' }),
      }).catch(() => {})
      setOnboardingDone()
      router.push('/login')
    }
  }

  const caption = captions[current]

  return (
    <div
      className="min-h-screen flex flex-col"
      style={{ backgroundColor: colors.lightBg }}
    >
      {/* 슬라이드 영역 */}
      <div className="flex-1 flex flex-col">
        {/* 앱 화면 프리뷰 */}
        <div className="px-6 pt-9">
          <div
            style={{
              height: 420,
              borderRadius: 20,
              overflow: 'hidden',
              boxShadow: '0 4px 24px rgba(0,0,0,0.08)',
              backgroundColor: colors.lightBg,
            }}
          >
            {current === 0 && <ChecklistPreview />}
            {current === 1 && <TaskDetailPreview />}
            {current === 2 && <ProgressPreview />}
            {current === 3 && <GuidePreview />}
          </div>
        </div>

        <div className="flex-1" />

        {/* 슬라이드 캡션 */}
        <div className="px-6 pb-2 flex flex-col gap-2.5">
          <p
            className="text-2xl font-bold leading-tight whitespace-pre-line"
            style={{ color: '#111', lineHeight: 1.35 }}
          >
            {caption.title}
          </p>
          <p className="text-sm text-gray-500 whitespace-pre-line leading-relaxed">
            {caption.sub}
          </p>
        </div>
      </div>

      {/* 하단 고정 영역 */}
      <div className="px-6 pb-14 pt-4 flex flex-col items-center gap-5">
        {/* 페이지 인디케이터 */}
        <div className="flex gap-1.5">
          {captions.map((_, i) => (
            <div
              key={i}
              style={{
                height: 6,
                width: i === current ? 20 : 6,
                borderRadius: 999,
                backgroundColor: i === current ? colors.accentDark : `${colors.accent}40`,
                transition: 'width 0.25s ease, background-color 0.25s ease',
              }}
            />
          ))}
        </div>

        {/* 다음 버튼 */}
        <button
          onClick={handleNext}
          className="w-full font-semibold text-white"
          style={{
            backgroundColor: colors.accentDark,
            borderRadius: 16,
            paddingTop: 18,
            paddingBottom: 18,
          }}
        >
          {current < captions.length - 1 ? '다음' : '지금 시작하기 →'}
        </button>
      </div>
    </div>
  )
}

// MARK: - 슬라이드 1: 체크리스트 화면

const checklistSections = [
  { icon: '📋', title: '사무행정', nextTask: '사망신고', completed: 0, total: 3, dDay: 7 },
  { icon: '💰', title: '금융', nextTask: '은행 계좌 동결 신청', completed: 0, total: 3, dDay: 7 },
  { icon: '💻', title: '디지털', nextTask: 'SNS 계정 처리', completed: 0, total: 3, dDay: null },
  { icon: '⚖️', title: '법원행정', nextTask: '상속 포기 신청', completed: 0, total: 3, dDay: 90 },
]

function ChecklistPreview() {
  return (
    <div className="flex flex-col h-full" style={{ backgroundColor: colors.lightBg }}>
      {/* 네비게이션 바 */}
      <div className="flex items-center justify-between px-4 py-3.5" style={{ backgroundColor: colors.lightBg }}>
        <span className="font-semibold text-base">체크리스트</span>
        <svg xmlns="http://www.w3.org/2000/svg" width="22" height="22" viewBox="0 0 24 24" fill={colors.accent}>
          <path d="M12 12c2.7 0 4.8-2.1 4.8-4.8S14.7 2.4 12 2.4 7.2 4.5 7.2 7.2 9.3 12 12 12zm0 2.4c-3.2 0-9.6 1.6-9.6 4.8v2.4h19.2v-2.4c0-3.2-6.4-4.8-9.6-4.8z" />
        </svg>
      </div>
      <div style={{ height: 1, backgroundColor: 'rgba(0,0,0,0.07)' }} />

      <div className="flex flex-col gap-2.5 px-4 pt-3">
        {checklistSections.map((s) => (
          <div
            key={s.title}
            className="flex items-center gap-2 px-3 py-3 rounded-xl"
            style={{ backgroundColor: colors.cardBg }}
          >
            <span style={{ fontSize: 16 }}>{s.icon}</span>
            <div className="flex-1 min-w-0">
              <p className="text-sm font-semibold">{s.title}</p>
              <p className="text-xs text-gray-400 truncate">{s.nextTask}</p>
            </div>
            <div className="flex items-center gap-2">
              {s.dDay !== null && (
                <span
                  className="text-xs font-semibold"
                  style={{ color: s.dDay <= 7 ? colors.warning : colors.accentDim }}
                >
                  D-{s.dDay}
                </span>
              )}
              <MiniCircle ratio={0} />
            </div>
          </div>
        ))}
      </div>
    </div>
  )
}

// MARK: - 슬라이드 2: 태스크 상세 화면 (사망신고)

const sampleDocuments = ['사망진단서 (원본)', '신고인 신분증', '가족관계증명서']

function TaskDetailPreview() {
  return (
    <div className="flex flex-col h-full" style={{ backgroundColor: colors.lightBg }}>
      {/* 네비게이션 바 */}
      <div className="flex items-center gap-2 px-2 py-2.5" style={{ backgroundColor: colors.lightBg }}>
        <svg xmlns="http://www.w3.org/2000/svg" width="22" height="22" viewBox="0 0 24 24" fill={colors.accent}>
          <path d="M20 11H7.83l5.59-5.59L12 4l-8 8 8 8 1.41-1.41L7.83 13H20v-2z" />
        </svg>
        <span className="font-semibold text-base">사망신고</span>
      </div>
      <div style={{ height: 1, backgroundColor: 'rgba(0,0,0,0.07)' }} />

      <div className="flex flex-col gap-3 p-3.5">
        {/* 완료 상태 카드 */}
        <div
          className="flex items-center gap-3 px-3.5 py-3 rounded-xl"
          style={{ backgroundColor: colors.cardBg }}
        >
          <div className="flex-1">
            <p className="text-sm font-semibold">사망신고</p>
            <p className="text-xs font-medium mt-0.5" style={{ color: colors.warning }}>D-7 마감</p>
          </div>
          <div
            className="flex items-center gap-1 px-2.5 py-1.5 rounded-full"
            style={{ backgroundColor: 'rgba(0,0,0,0.06)' }}
          >
            <svg xmlns="http://www.w3.org/2000/svg" width="13" height="13" viewBox="0 0 24 24" fill="none" stroke="#9CA3AF" strokeWidth="2">
              <circle cx="12" cy="12" r="10" />
            </svg>
            <span className="text-xs text-gray-400">미완료</span>
          </div>
        </div>

        {/* 필요 서류 카드 */}
        <div className="rounded-xl overflow-hidden" style={{ backgroundColor: colors.cardBg }}>
          <div className="flex items-center justify-between px-4 pt-3.5 pb-2">
            <span className="text-sm font-semibold">필요 서류</span>
            <span className="text-xs text-gray-400">0/{sampleDocuments.length} 준비됨</span>
          </div>
          {/* progress bar */}
          <div className="mx-4 mb-2.5 h-1 rounded-full overflow-hidden" style={{ backgroundColor: `${colors.accent}26` }}>
            <div className="h-full rounded-full" style={{ width: '0%', backgroundColor: colors.accent }} />
          </div>
          <div style={{ height: 1, backgroundColor: 'rgba(0,0,0,0.06)' }} />
          {sampleDocuments.map((doc, i) => (
            <div key={doc}>
              <div className="flex items-center gap-3 px-4 py-3">
                <svg xmlns="http://www.w3.org/2000/svg" width="19" height="19" viewBox="0 0 24 24" fill="none" stroke={`${colors.accent}4D`} strokeWidth="2">
                  <circle cx="12" cy="12" r="10" />
                </svg>
                <span className="text-sm">{doc}</span>
              </div>
              {i < sampleDocuments.length - 1 && (
                <div style={{ height: 1, backgroundColor: 'rgba(0,0,0,0.06)', marginLeft: 52 }} />
              )}
            </div>
          ))}
          <div style={{ height: 4 }} />
        </div>
      </div>
    </div>
  )
}

// MARK: - 슬라이드 3: 진행상황 화면

function ProgressPreview() {
  const sectionProgress = [
    { icon: '📋', title: '사무행정', total: 3 },
    { icon: '💰', title: '금융', total: 3 },
    { icon: '💻', title: '디지털', total: 3 },
  ]

  return (
    <div className="flex flex-col h-full" style={{ backgroundColor: colors.lightBg }}>
      <div className="px-4 py-3.5" style={{ backgroundColor: colors.lightBg }}>
        <span className="font-semibold text-base">진행상황</span>
      </div>
      <div style={{ height: 1, backgroundColor: 'rgba(0,0,0,0.07)' }} />

      <div className="flex flex-col gap-2.5 px-3.5 pt-3">
        {/* 전체 진행률 카드 - 원형 프로그레스 */}
        <div
          className="flex items-center justify-between px-4 py-3.5 rounded-xl"
          style={{ backgroundColor: colors.cardBg }}
        >
          <div className="flex flex-col gap-1">
            <p className="text-sm font-semibold">전체 진행률</p>
            <p className="text-xs text-gray-400">영면일: 2025년 1월 1일</p>
          </div>
          <MiniCircularProgress ratio={0} size={60} />
        </div>

        {/* 섹션 카드들 */}
        {sectionProgress.map((s) => (
          <div
            key={s.title}
            className="flex flex-col gap-2 px-3.5 py-3 rounded-xl"
            style={{ backgroundColor: colors.cardBg }}
          >
            <div className="flex items-center justify-between">
              <div className="flex items-center gap-1.5">
                <span style={{ fontSize: 14 }}>{s.icon}</span>
                <span className="text-sm font-medium">{s.title}</span>
              </div>
              <span className="text-xs text-gray-400">0/{s.total}</span>
            </div>
            <div className="h-1.5 rounded-full overflow-hidden" style={{ backgroundColor: `${colors.accent}26` }}>
              <div className="h-full rounded-full" style={{ width: '0%', backgroundColor: colors.accent }} />
            </div>
          </div>
        ))}
      </div>
    </div>
  )
}

// MARK: - 슬라이드 4: 가이드 화면

function GuidePreview() {
  return (
    <div className="flex flex-col h-full" style={{ backgroundColor: colors.lightBg }}>
      <div className="px-4 py-3.5" style={{ backgroundColor: colors.lightBg }}>
        <span className="font-semibold text-base">가이드</span>
      </div>
      <div style={{ height: 1, backgroundColor: 'rgba(0,0,0,0.07)' }} />

      <div className="flex flex-col gap-3 p-3.5">
        {/* AI 코디네이터 헤더 */}
        <div
          className="flex items-center gap-3 px-3.5 py-3 rounded-2xl"
          style={{ backgroundColor: colors.cardBg }}
        >
          <div
            className="w-11 h-11 rounded-full flex items-center justify-center flex-shrink-0"
            style={{ backgroundColor: colors.accentSubtle }}
          >
            <svg xmlns="http://www.w3.org/2000/svg" className="w-5 h-5" viewBox="0 0 24 24" fill={colors.accent}>
              <path d="M16 11c1.66 0 2.99-1.34 2.99-3S17.66 5 16 5c-1.66 0-3 1.34-3 3s1.34 3 3 3zm-8 0c1.66 0 2.99-1.34 2.99-3S9.66 5 8 5C6.34 5 5 6.34 5 8s1.34 3 3 3zm0 2c-2.33 0-7 1.17-7 3.5V19h14v-2.5c0-2.33-4.67-3.5-7-3.5zm8 0c-.29 0-.62.02-.97.05 1.16.84 1.97 1.97 1.97 3.45V19h6v-2.5c0-2.33-4.67-3.5-7-3.5z" />
            </svg>
          </div>
          <div className="flex flex-col gap-1">
            <p className="text-sm font-semibold text-black">AI 코디네이터</p>
            <div className="flex items-center gap-1.5">
              <div className="w-1.5 h-1.5 rounded-full bg-green-500" />
              <p className="text-xs text-gray-400">24시간 실시간 상담중</p>
            </div>
          </div>
        </div>

        {/* 메시지 버블 */}
        <div className="flex justify-start">
          <div
            className="text-sm leading-relaxed whitespace-pre-line px-3.5 py-2.5"
            style={{
              backgroundColor: colors.cardBg,
              color: '#111',
              borderRadius: '14px 14px 14px 4px',
              maxWidth: '80%',
            }}
          >
            {'안녕하세요. 어렵고 힘드신 상황에서 찾아주셨군요.\n처리해야 할 일들에 대해 궁금한 점을 편하게 물어보세요.'}
          </div>
        </div>
      </div>
    </div>
  )
}

// MARK: - 공통 컴포넌트

function MiniCircle({ ratio }: { ratio: number }) {
  const size = 26
  const strokeWidth = 2.5
  const radius = (size - strokeWidth) / 2
  const circumference = 2 * Math.PI * radius
  const offset = circumference * (1 - ratio)

  return (
    <svg width={size} height={size} style={{ transform: 'rotate(-90deg)', flexShrink: 0 }}>
      <circle cx={size / 2} cy={size / 2} r={radius} fill="none" stroke={`${colors.accent}33`} strokeWidth={strokeWidth} />
      <circle
        cx={size / 2}
        cy={size / 2}
        r={radius}
        fill="none"
        stroke={colors.accent}
        strokeWidth={strokeWidth}
        strokeLinecap="round"
        strokeDasharray={circumference}
        strokeDashoffset={offset}
      />
    </svg>
  )
}

function MiniCircularProgress({ ratio, size }: { ratio: number; size: number }) {
  const strokeWidth = 6
  const radius = (size - strokeWidth) / 2
  const circumference = 2 * Math.PI * radius
  const offset = circumference * (1 - ratio)

  return (
    <div style={{ position: 'relative', width: size, height: size, flexShrink: 0 }}>
      <svg width={size} height={size} style={{ transform: 'rotate(-90deg)' }}>
        <circle cx={size / 2} cy={size / 2} r={radius} fill="none" stroke={`${colors.accent}26`} strokeWidth={strokeWidth} />
        <circle
          cx={size / 2}
          cy={size / 2}
          r={radius}
          fill="none"
          stroke={colors.accent}
          strokeWidth={strokeWidth}
          strokeLinecap="round"
          strokeDasharray={circumference}
          strokeDashoffset={offset}
        />
      </svg>
      <div
        style={{
          position: 'absolute',
          inset: 0,
          display: 'flex',
          alignItems: 'center',
          justifyContent: 'center',
          fontSize: Math.round(size * 0.26),
          fontWeight: 700,
          color: colors.accent,
        }}
      >
        {Math.round(ratio * 100)}%
      </div>
    </div>
  )
}

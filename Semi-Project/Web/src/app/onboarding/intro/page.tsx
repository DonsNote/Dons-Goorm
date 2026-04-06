'use client'

import { useState } from 'react'
import { useRouter } from 'next/navigation'
import { useOnboardingStore } from '@/store/useOnboardingStore'

const pages = [
  {
    icon: '✅',
    title: '해야 할 일들을 한눈에',
    description: '사무행정, 금융, 디지털, 법원행정\n4가지 영역으로 정리된 체크리스트를\n단계별로 안내드립니다.',
  },
  {
    icon: '💬',
    title: '궁금한 점은 언제든지',
    description: '어렵고 복잡한 절차들을\n가이드를 통해 편하게\n질문하고 도움받으세요.',
  },
  {
    icon: '📊',
    title: '진행 현황을 한눈에',
    description: '처리된 일과 남은 일을\n보고서 형식으로 확인하며\n놓치는 것 없이 마무리하세요.',
  },
  {
    icon: '🤝',
    title: '홀로 남겨지지 않도록',
    description: '힘든 시간, 동행이\n곁에서 함께하겠습니다.',
  },
]

export default function OnboardingIntroPage() {
  const router = useRouter()
  const { setOnboardingDone } = useOnboardingStore()
  const [current, setCurrent] = useState(0)

  function handleNext() {
    if (current < pages.length - 1) {
      setCurrent(current + 1)
    } else {
      setOnboardingDone()
      router.push('/login')
    }
  }

  const page = pages[current]

  return (
    <div className="min-h-screen flex flex-col items-center justify-between px-6 py-12">
      <div className="flex-1 flex flex-col items-center justify-center gap-8 text-center">
        <span className="text-7xl">{page.icon}</span>
        <div className="flex flex-col gap-3">
          <p className="text-2xl font-bold">{page.title}</p>
          <p className="text-sm text-gray-500 whitespace-pre-line leading-relaxed">{page.description}</p>
        </div>
      </div>

      <div className="w-full flex flex-col items-center gap-6">
        <div className="flex gap-1.5">
          {pages.map((_, i) => (
            <div
              key={i}
              className={`h-1.5 rounded-full transition-all duration-300 ${
                i === current ? 'w-5 bg-gray-900' : 'w-1.5 bg-gray-200'
              }`}
            />
          ))}
        </div>

        <button
          onClick={handleNext}
          className="w-full py-4 rounded-xl bg-gray-900 text-white font-semibold"
        >
          {current < pages.length - 1 ? '다음' : '시작하기'}
        </button>
      </div>
    </div>
  )
}

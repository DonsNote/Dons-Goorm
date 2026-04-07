'use client'

import { useEffect, useState } from 'react'
import { useRouter } from 'next/navigation'
import { useOnboardingStore } from '@/store/useOnboardingStore'

export default function Home() {
  const [visible, setVisible] = useState(false)
  const router = useRouter()
  const { deceasedDate, onboardingDone } = useOnboardingStore()

  useEffect(() => {
    setVisible(true)
    const timer = setTimeout(() => {
      if (!deceasedDate) {
        router.replace('/onboarding/date')
      } else if (!onboardingDone) {
        router.replace('/onboarding/intro')
      } else {
        router.replace('/login')
      }
    }, 3600)
    return () => clearTimeout(timer)
  }, [])

  return (
    <div
      className="min-h-screen flex flex-col"
      style={{ backgroundColor: '#1B2B24' }}
    >
      {/* Center content */}
      <div className="flex-1 flex items-center justify-center">
        <div
          className={`flex flex-col items-center gap-5 transition-opacity duration-700 ${
            visible ? 'opacity-100' : 'opacity-0'
          }`}
        >
          {/* App icon */}
          <div
            className="w-[72px] h-[72px] rounded-[18px]"
            style={{ backgroundColor: '#263C32' }}
          />

          {/* Title + divider: inline-flex so divider matches title width */}
          <div className="inline-flex flex-col items-stretch gap-5">
            <p className="text-white text-2xl font-semibold text-center">동행</p>
            <div className="h-px" style={{ backgroundColor: 'rgba(255,255,255,0.2)' }} />
          </div>

          {/* Taglines */}
          <div className="flex flex-col items-center gap-2.5 text-center">
            <p className="text-white text-base leading-7 whitespace-pre-line">
              {'장례가 끝난 순간,\n곁에서 함께합니다'}
            </p>
            <p
              className="text-sm leading-6 whitespace-pre-line"
              style={{ color: 'rgba(255,255,255,0.5)' }}
            >
              {'복잡한 사후 행정 절차,\n처음부터 끝까지 동행합니다'}
            </p>
          </div>
        </div>
      </div>

      {/* Progress bar */}
      <div
        className={`mx-6 mb-11 relative h-0.5 rounded-full overflow-hidden transition-opacity duration-700 ${
          visible ? 'opacity-100' : 'opacity-0'
        }`}
        style={{ backgroundColor: 'rgba(255,255,255,0.15)' }}
      >
        <div
          className="absolute inset-y-0 left-0 rounded-full"
          style={{
            backgroundColor: 'rgba(255,255,255,0.55)',
            width: visible ? '100%' : '0%',
            transition: 'width 3s linear',
          }}
        />
      </div>
    </div>
  )
}

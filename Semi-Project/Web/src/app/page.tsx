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
    }, 3000)
    return () => clearTimeout(timer)
  }, [])

  return (
    <div className="min-h-screen bg-black flex items-center justify-center">
      <p
        className={`text-white text-lg font-light transition-opacity duration-1000 ${
          visible ? 'opacity-100' : 'opacity-0'
        }`}
      >
        삼가 고인의 명복을 빕니다.
      </p>
    </div>
  )
}

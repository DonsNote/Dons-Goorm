'use client'

import { useEffect, useState } from 'react'
import { useRouter } from 'next/navigation'
import { useChecklistStore } from '@/store/useChecklistStore'
import { useOnboardingStore } from '@/store/useOnboardingStore'
import { colors } from '@/lib/design'

const numbers = ['①', '②', '③', '④']

export default function SummaryPage() {
  const router = useRouter()
  const { sections } = useChecklistStore()
  const { deceasedDate } = useOnboardingStore()
  const [progress, setProgress] = useState(false)

  const totalCount = sections.reduce((acc, s) => acc + s.tasks.length, 0)
  const earliestDDay = sections
    .flatMap((s) => s.tasks.filter((t) => !t.isCompleted && t.dDay !== null).map((t) => t.dDay!))
    .sort((a, b) => a - b)[0] ?? null

  useEffect(() => {
    setProgress(true)
    const timer = setTimeout(() => {
      router.replace('/onboarding/intro')
    }, 5000)
    return () => clearTimeout(timer)
  }, [])

  return (
    <div className="min-h-screen flex flex-col" style={{ backgroundColor: colors.lightBg }}>
      {/* 중앙 콘텐츠 */}
      <div className="flex-1 flex flex-col items-center justify-center gap-1.5 text-center">
        <p className="text-[22px] font-medium leading-8 text-black">
          고객님이<br />처리해야 할 일이
        </p>
        <p className="text-[36px] font-bold" style={{ color: colors.accent }}>
          {totalCount}건
        </p>
        <p className="text-[22px] font-medium text-black">있습니다</p>

        {earliestDDay !== null && (
          <div
            className="mt-4 px-4 py-2 rounded-full text-xs font-medium"
            style={{ backgroundColor: colors.warningBg, color: colors.warning }}
          >
            가장 빠른 기한까지 D-{earliestDDay}일 남았습니다
          </div>
        )}

        <div className="mt-7 w-full px-5 flex flex-col gap-2.5">
          {sections.map((section, index) => {
            const dDay = section.tasks
              .filter((t) => !t.isCompleted && t.dDay !== null)
              .map((t) => t.dDay!)
              .sort((a, b) => a - b)[0] ?? null

            return (
              <div
                key={section.id}
                className="flex items-center justify-between px-[18px] py-3.5 rounded-2xl"
                style={{ backgroundColor: colors.cardBg }}
              >
                <span className="text-sm font-medium text-black">
                  {numbers[index]} {section.title}
                </span>
                <span className="text-xs text-gray-400">
                  {section.tasks.length}건{dDay !== null ? `·D-${dDay}` : ''}
                </span>
              </div>
            )
          })}
        </div>
      </div>

      {/* 하단 */}
      <div className="px-5 pb-9 flex flex-col gap-3">
        <div
          className="w-full py-4 rounded-2xl text-sm font-medium text-center"
          style={{ backgroundColor: colors.accentSubtle, color: colors.accentDim }}
        >
          서비스를 상세하게 소개드릴게요
        </div>
        <div className="relative h-[3px] rounded-full overflow-hidden" style={{ backgroundColor: 'rgba(0,0,0,0.1)' }}>
          <div
            className="absolute inset-y-0 left-0 rounded-full"
            style={{
              backgroundColor: colors.accentDark,
              width: progress ? '100%' : '0%',
              transition: 'width 5s linear',
            }}
          />
        </div>
      </div>
    </div>
  )
}

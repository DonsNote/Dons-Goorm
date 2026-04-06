'use client'

import { useChecklistStore } from '@/store/useChecklistStore'
import { useOnboardingStore } from '@/store/useOnboardingStore'

export default function ProgressPage() {
  const { sections } = useChecklistStore()
  const { deceasedDate } = useOnboardingStore()

  const totalCompleted = sections.reduce((acc, s) => acc + s.tasks.filter((t) => t.isCompleted).length, 0)
  const totalTasks = sections.reduce((acc, s) => acc + s.tasks.length, 0)
  const overallRatio = totalTasks > 0 ? totalCompleted / totalTasks : 0

  const formatted = deceasedDate
    ? new Date(deceasedDate).toLocaleDateString('ko-KR', { year: 'numeric', month: 'long', day: 'numeric' })
    : ''

  return (
    <div className="px-4 py-4 flex flex-col gap-3">
      <div className="bg-gray-50 rounded-2xl p-4 flex flex-col gap-4">
        <div className="flex items-center justify-between">
          <div className="flex flex-col gap-1">
            <p className="font-semibold">전체 진행률</p>
            {formatted && <p className="text-xs text-gray-400">사망일: {formatted}</p>}
          </div>
          <p className="text-4xl font-bold">{Math.round(overallRatio * 100)}%</p>
        </div>
        <ProgressBar ratio={overallRatio} />
        <p className="text-xs text-gray-400 text-right">{totalCompleted}개 완료 / 총 {totalTasks}개</p>
      </div>

      {sections.map((section) => {
        const completed = section.tasks.filter((t) => t.isCompleted).length
        const total = section.tasks.length
        const ratio = total > 0 ? completed / total : 0
        return (
          <div key={section.id} className="bg-gray-50 rounded-2xl p-4 flex flex-col gap-3">
            <div className="flex items-center justify-between">
              <div className="flex items-center gap-2">
                <span>{section.icon}</span>
                <span className="text-sm font-medium">{section.title}</span>
              </div>
              <span className="text-xs text-gray-400">{completed}/{total}</span>
            </div>
            <ProgressBar ratio={ratio} />
          </div>
        )
      })}
    </div>
  )
}

function ProgressBar({ ratio }: { ratio: number }) {
  return (
    <div className="w-full h-1.5 bg-gray-200 rounded-full overflow-hidden">
      <div
        className="h-full bg-gray-900 rounded-full transition-all duration-500"
        style={{ width: `${ratio * 100}%` }}
      />
    </div>
  )
}

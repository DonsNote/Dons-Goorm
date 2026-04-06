'use client'

import Link from 'next/link'
import { useChecklistStore } from '@/store/useChecklistStore'

export default function ChecklistPage() {
  const { sections } = useChecklistStore()

  return (
    <div className="px-4 py-4 flex flex-col gap-3">
      {sections.map((section) => {
        const nextTask = section.tasks
          .filter((t) => !t.isCompleted)
          .sort((a, b) => (a.dDay ?? Infinity) - (b.dDay ?? Infinity))[0]
        const earliestDDay = nextTask?.dDay ?? null
        const completed = section.tasks.filter((t) => t.isCompleted).length
        const total = section.tasks.length
        const ratio = total > 0 ? completed / total : 0

        return (
          <Link key={section.id} href={`/checklist/${section.id}`}>
            <div className="bg-gray-50 rounded-2xl p-4 flex flex-col gap-3 active:opacity-70 transition-opacity">
              <div className="flex items-center justify-between">
                <div className="flex items-center gap-2">
                  <span className="text-xl">{section.icon}</span>
                  <span className="font-semibold text-base">{section.title}</span>
                </div>
                <CircleProgress ratio={ratio} />
              </div>

              <div className="h-px bg-gray-200" />

              <div className="flex items-end justify-between">
                <div className="flex flex-col gap-1">
                  <p className={`text-sm ${nextTask ? 'text-gray-900' : 'text-gray-400'}`}>
                    {nextTask ? nextTask.title : '모든 항목 완료'}
                  </p>
                  <p className="text-xs text-gray-400">{completed}/{total} 완료</p>
                </div>
                {earliestDDay !== null && (
                  <span className={`text-sm font-semibold ${earliestDDay <= 7 ? 'text-red-500' : 'text-gray-900'}`}>
                    D-{earliestDDay}
                  </span>
                )}
              </div>
            </div>
          </Link>
        )
      })}
    </div>
  )
}

function CircleProgress({ ratio }: { ratio: number }) {
  const r = 12
  const circ = 2 * Math.PI * r
  return (
    <svg width="28" height="28" viewBox="0 0 28 28">
      <circle cx="14" cy="14" r={r} fill="none" stroke="#e5e7eb" strokeWidth="2.5" />
      <circle
        cx="14" cy="14" r={r}
        fill="none"
        stroke="#111827"
        strokeWidth="2.5"
        strokeDasharray={circ}
        strokeDashoffset={circ * (1 - ratio)}
        strokeLinecap="round"
        transform="rotate(-90 14 14)"
      />
    </svg>
  )
}

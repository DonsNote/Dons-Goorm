'use client'

import { useParams, useRouter } from 'next/navigation'
import Link from 'next/link'
import { useChecklistStore, type Category } from '@/store/useChecklistStore'
import { colors } from '@/lib/design'

export default function ChecklistDetailPage() {
  const { category } = useParams<{ category: string }>()
  const router = useRouter()
  const { sections } = useChecklistStore()
  const section = sections.find((s) => s.id === category)

  if (!section) return null

  return (
    <div>
      <button
        onClick={() => router.back()}
        className="flex items-center gap-1 px-4 py-3 text-sm"
        style={{ color: colors.accentDim }}
      >
        <svg xmlns="http://www.w3.org/2000/svg" className="w-4 h-4" fill="none" viewBox="0 0 24 24" stroke="currentColor" strokeWidth={2}>
          <path strokeLinecap="round" strokeLinejoin="round" d="M15 19l-7-7 7-7" />
        </svg>
        뒤로
      </button>

      <div className="px-4 pb-4">
        <div className="rounded-[14px] overflow-hidden" style={{ backgroundColor: colors.cardBg }}>
          {section.tasks.map((task, index) => {
            const readyCount = task.documents.filter((d) => d.isReady).length
            return (
              <div key={task.id}>
                <Link href={`/checklist/${category}/${task.id}`}>
                  <div className="flex items-center gap-4 px-5 py-4 active:opacity-60 transition-opacity">
                    <div
                      className="w-5 h-5 rounded-full border-2 flex-shrink-0 flex items-center justify-center transition-colors"
                      style={{
                        backgroundColor: task.isCompleted ? colors.accent : 'transparent',
                        borderColor: task.isCompleted ? colors.accent : `${colors.accent}4D`,
                      }}
                    >
                      {task.isCompleted && (
                        <svg className="w-3 h-3 text-white" fill="none" viewBox="0 0 24 24" stroke="currentColor" strokeWidth={3}>
                          <path strokeLinecap="round" strokeLinejoin="round" d="M5 13l4 4L19 7" />
                        </svg>
                      )}
                    </div>
                    <div className="flex flex-col gap-0.5 flex-1">
                      <span
                        className="text-sm"
                        style={{
                          textDecoration: task.isCompleted ? 'line-through' : 'none',
                          color: task.isCompleted ? '#9CA3AF' : '#111',
                        }}
                      >
                        {task.title}
                      </span>
                      <div className="flex gap-2">
                        {task.dDay !== null && (
                          <span className="text-xs" style={{ color: task.dDay <= 7 ? colors.warning : colors.accentDim }}>
                            D-{task.dDay}
                          </span>
                        )}
                        {task.documents.length > 0 && (
                          <span className="text-xs text-gray-400">
                            서류 {readyCount}/{task.documents.length}
                          </span>
                        )}
                      </div>
                    </div>
                    <svg xmlns="http://www.w3.org/2000/svg" className="w-4 h-4" fill="none" viewBox="0 0 24 24" stroke="currentColor" strokeWidth={2} style={{ color: `${colors.accent}66` }}>
                      <path strokeLinecap="round" strokeLinejoin="round" d="M9 5l7 7-7 7" />
                    </svg>
                  </div>
                </Link>
                {index < section.tasks.length - 1 && (
                  <div className="ml-14 h-px" style={{ backgroundColor: 'rgba(0,0,0,0.06)' }} />
                )}
              </div>
            )
          })}
        </div>
      </div>
    </div>
  )
}

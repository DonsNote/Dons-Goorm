'use client'

import { useParams, useRouter } from 'next/navigation'
import { useChecklistStore, type Category } from '@/store/useChecklistStore'

export default function ChecklistDetailPage() {
  const { category } = useParams<{ category: string }>()
  const router = useRouter()
  const { sections, toggleTask } = useChecklistStore()
  const section = sections.find((s) => s.id === category)

  if (!section) return null

  return (
    <div>
      <button
        onClick={() => router.back()}
        className="flex items-center gap-1 px-4 py-3 text-sm text-gray-500"
      >
        <svg xmlns="http://www.w3.org/2000/svg" className="w-4 h-4" fill="none" viewBox="0 0 24 24" stroke="currentColor" strokeWidth={2}>
          <path strokeLinecap="round" strokeLinejoin="round" d="M15 19l-7-7 7-7" />
        </svg>
        뒤로
      </button>

      <div className="flex flex-col divide-y divide-gray-100">
        {section.tasks.map((task) => (
          <button
            key={task.id}
            onClick={() => toggleTask(section.id as Category, task.id)}
            className="flex items-center gap-4 px-5 py-4 text-left active:bg-gray-50 transition-colors"
          >
            <div className={`w-5 h-5 rounded-full border-2 flex-shrink-0 flex items-center justify-center transition-colors ${
              task.isCompleted ? 'bg-gray-900 border-gray-900' : 'border-gray-300'
            }`}>
              {task.isCompleted && (
                <svg className="w-3 h-3 text-white" fill="none" viewBox="0 0 24 24" stroke="currentColor" strokeWidth={3}>
                  <path strokeLinecap="round" strokeLinejoin="round" d="M5 13l4 4L19 7" />
                </svg>
              )}
            </div>
            <div className="flex flex-col gap-0.5 flex-1">
              <span className={`text-sm ${task.isCompleted ? 'line-through text-gray-400' : 'text-gray-900'}`}>
                {task.title}
              </span>
              {task.dDay !== null && (
                <span className={`text-xs ${task.dDay <= 7 ? 'text-red-500' : 'text-gray-400'}`}>
                  D-{task.dDay}
                </span>
              )}
            </div>
          </button>
        ))}
      </div>
    </div>
  )
}

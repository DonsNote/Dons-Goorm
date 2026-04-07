'use client'

import { useParams, useRouter } from 'next/navigation'
import { useChecklistStore, type Category } from '@/store/useChecklistStore'
import { colors } from '@/lib/design'

export default function TaskDetailPage() {
  const { category, taskId } = useParams<{ category: string; taskId: string }>()
  const router = useRouter()
  const { sections, toggleTask, toggleDocument } = useChecklistStore()

  const section = sections.find((s) => s.id === category)
  const task = section?.tasks.find((t) => t.id === taskId)

  if (!section || !task) return null

  const readyCount = task.documents.filter((d) => d.isReady).length
  const allReady = task.documents.length > 0 && readyCount === task.documents.length
  const docRatio = task.documents.length > 0 ? readyCount / task.documents.length : 0

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

      <div className="px-4 pb-6 flex flex-col gap-4">
        {/* 완료 상태 카드 */}
        <div className="rounded-[14px] p-4 flex items-center gap-3" style={{ backgroundColor: colors.cardBg }}>
          <div className="flex-1 flex flex-col gap-1">
            <p className="font-semibold text-sm">{task.title}</p>
            {task.dDay !== null && (
              <p className="text-xs font-medium" style={{ color: task.dDay <= 7 ? colors.warning : colors.accentDim }}>
                D-{task.dDay} 마감
              </p>
            )}
          </div>
          <button
            onClick={() => toggleTask(category as Category, task.id)}
            className="flex items-center gap-1.5 px-3 py-2 rounded-full text-xs font-medium transition-colors"
            style={{
              backgroundColor: task.isCompleted ? colors.accentSubtle : 'rgba(0,0,0,0.06)',
              color: task.isCompleted ? colors.accent : '#6B7280',
            }}
          >
            <div
              className="w-4 h-4 rounded-full border-2 flex items-center justify-center"
              style={{
                borderColor: task.isCompleted ? colors.accent : '#9CA3AF',
                backgroundColor: task.isCompleted ? colors.accent : 'transparent',
              }}
            >
              {task.isCompleted && (
                <svg className="w-2.5 h-2.5 text-white" fill="none" viewBox="0 0 24 24" stroke="currentColor" strokeWidth={3}>
                  <path strokeLinecap="round" strokeLinejoin="round" d="M5 13l4 4L19 7" />
                </svg>
              )}
            </div>
            {task.isCompleted ? '완료' : '미완료'}
          </button>
        </div>

        {/* 필요 서류 */}
        <div className="rounded-[14px] overflow-hidden" style={{ backgroundColor: colors.cardBg }}>
          <div className="px-[18px] pt-4 pb-3 flex items-center justify-between">
            <p className="text-sm font-semibold">필요 서류</p>
            <p className="text-xs" style={{ color: allReady ? colors.accent : '#9CA3AF' }}>
              {readyCount}/{task.documents.length} 준비됨
            </p>
          </div>

          {/* 진행 바 */}
          <div className="mx-[18px] mb-3 h-[5px] rounded-full overflow-hidden" style={{ backgroundColor: `${colors.accent}26` }}>
            <div
              className="h-full rounded-full transition-all duration-300"
              style={{ width: `${docRatio * 100}%`, backgroundColor: colors.accent }}
            />
          </div>

          <div className="h-px" style={{ backgroundColor: 'rgba(0,0,0,0.06)' }} />

          {task.documents.map((doc, index) => (
            <div key={doc.id}>
              <button
                onClick={() => toggleDocument(category as Category, task.id, doc.id)}
                className="w-full flex items-center gap-4 px-5 py-4 text-left active:opacity-60 transition-opacity"
              >
                <div
                  className="w-5 h-5 rounded-full border-2 flex-shrink-0 flex items-center justify-center"
                  style={{
                    backgroundColor: doc.isReady ? colors.accent : 'transparent',
                    borderColor: doc.isReady ? colors.accent : `${colors.accent}4D`,
                  }}
                >
                  {doc.isReady && (
                    <svg className="w-3 h-3 text-white" fill="none" viewBox="0 0 24 24" stroke="currentColor" strokeWidth={3}>
                      <path strokeLinecap="round" strokeLinejoin="round" d="M5 13l4 4L19 7" />
                    </svg>
                  )}
                </div>
                <span
                  className="text-sm flex-1"
                  style={{
                    textDecoration: doc.isReady ? 'line-through' : 'none',
                    color: doc.isReady ? '#9CA3AF' : '#111',
                  }}
                >
                  {doc.title}
                </span>
              </button>
              {index < task.documents.length - 1 && (
                <div className="ml-14 h-px" style={{ backgroundColor: 'rgba(0,0,0,0.06)' }} />
              )}
            </div>
          ))}

          <div className="h-1" />
        </div>

        {/* 가까운 발급처 */}
        <div className="rounded-[14px] p-4 flex flex-col gap-3" style={{ backgroundColor: colors.cardBg }}>
          <div className="flex items-center gap-2">
            <svg xmlns="http://www.w3.org/2000/svg" className="w-5 h-5" viewBox="0 0 24 24" fill={colors.accent}>
              <path fillRule="evenodd" d="M11.54 22.351l.07.04.028.016a.76.76 0 00.723 0l.028-.015.071-.041a16.975 16.975 0 001.144-.742 19.58 19.58 0 002.683-2.282c1.944-2.079 3.218-4.402 3.218-6.853C19.5 8.574 16.315 6 12 6c-4.314 0-7.5 2.574-7.5 6.474 0 2.45 1.274 4.773 3.218 6.853a19.58 19.58 0 002.683 2.282 16.975 16.975 0 001.144.742zM12 13.5a1.5 1.5 0 100-3 1.5 1.5 0 000 3z" clipRule="evenodd" />
            </svg>
            <p className="text-sm font-semibold">가까운 발급처</p>
          </div>
          <p className="text-xs text-gray-400 leading-5">
            필요 서류를 발급받을 수 있는<br />가장 가까운 기관을 안내해 드릴게요.
          </p>
          <button
            disabled
            className="w-full py-3.5 rounded-2xl text-sm font-medium opacity-60"
            style={{ backgroundColor: colors.accentSubtle, color: colors.accentDim }}
          >
            가까운 기관 찾기
          </button>
          <p className="text-[11px]" style={{ color: 'rgba(0,0,0,0.3)' }}>* 해당 기능은 준비 중입니다.</p>
        </div>
      </div>
    </div>
  )
}

'use client'

import { useState } from 'react'
import { useRouter } from 'next/navigation'
import { useOnboardingStore } from '@/store/useOnboardingStore'
import { colors } from '@/lib/design'

export default function DateInputPage() {
  const router = useRouter()
  const { setDeceasedDate } = useOnboardingStore()
  const [date, setDate] = useState('')
  const [showConfirm, setShowConfirm] = useState(false)

  const formatted = date
    ? new Date(date).toLocaleDateString('ko-KR', { year: 'numeric', month: 'long', day: 'numeric' })
    : ''

  function handleConfirm() {
    setDeceasedDate(date)
    setShowConfirm(false)
    router.push('/onboarding/summary')
  }

  return (
    <div className="min-h-screen flex flex-col" style={{ backgroundColor: colors.lightBg }}>
      {/* 헤드라인 */}
      <div className="px-7 pt-16 flex flex-col gap-3">
        <p className="text-[26px] font-bold leading-snug text-black">
          이제 혼자<br />챙기지 않아도 됩니다
        </p>
        <p className="text-sm text-gray-500 leading-relaxed">
          고인께서 영면에 드신 날을 입력해주시면<br />처리해야 할 모든 것을 알려드릴게요
        </p>
      </div>

      <div className="flex-1 flex items-center px-5">
        {/* 날짜 선택 카드 */}
        <div className="w-full rounded-[20px] flex flex-col" style={{ backgroundColor: colors.cardBg }}>
          <p className="px-5 pt-5 pb-3 text-xs font-semibold" style={{ color: colors.accent }}>
            고인께서 영면에 드신 날
          </p>
          <input
            type="date"
            max={new Date().toISOString().split('T')[0]}
            value={date}
            onChange={(e) => setDate(e.target.value)}
            className="mx-5 mb-3 border rounded-xl px-4 py-3 text-center text-base focus:outline-none"
            style={{ borderColor: `${colors.accent}40`, color: colors.accent }}
          />
          <div className="h-px mx-5" style={{ backgroundColor: 'rgba(0,0,0,0.07)' }} />
          <p className="px-5 py-3.5 text-xs text-gray-400">법적 기한 계산에 사용됩니다</p>
        </div>
      </div>

      {/* 하단 버튼 */}
      <div className="px-5 pb-10 flex flex-col items-center gap-2.5">
        <button
          onClick={() => setShowConfirm(true)}
          disabled={!date}
          className="w-full py-[18px] rounded-2xl text-white font-semibold disabled:opacity-40 transition-opacity"
          style={{ backgroundColor: colors.accent }}
        >
          처리 목록 확인하기
        </button>
        <p className="text-xs text-gray-400">입력하신 내용은 안전하게 저장됩니다</p>
      </div>

      {showConfirm && (
        <div className="fixed inset-0 bg-black/40 flex items-center justify-center z-50 px-6">
          <div className="bg-white rounded-2xl p-6 w-full max-w-sm flex flex-col gap-5">
            <div className="flex flex-col gap-2">
              <p className="text-base font-semibold text-center">영면일자 확인</p>
              <p className="text-sm text-gray-500 text-center">{formatted}<br />영면일이 맞나요?</p>
            </div>
            <div className="flex gap-3">
              <button
                onClick={() => setShowConfirm(false)}
                className="flex-1 py-3 rounded-xl border border-gray-200 text-sm font-medium"
              >
                취소
              </button>
              <button
                onClick={handleConfirm}
                className="flex-1 py-3 rounded-xl text-white text-sm font-medium"
                style={{ backgroundColor: colors.accent }}
              >
                확인
              </button>
            </div>
          </div>
        </div>
      )}
    </div>
  )
}

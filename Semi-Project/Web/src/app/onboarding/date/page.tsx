'use client'

import { useState } from 'react'
import { useRouter } from 'next/navigation'
import { useOnboardingStore } from '@/store/useOnboardingStore'

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
    router.push('/onboarding/intro')
  }

  return (
    <div className="min-h-screen flex flex-col items-center justify-between px-6 py-12">
      <div />

      <div className="w-full flex flex-col items-center gap-10">
        <div className="text-center flex flex-col gap-3">
          <p className="text-lg font-medium">고인의 사망일자를 입력해 주세요.</p>
          <p className="text-sm text-gray-500">
            입력하신 날짜를 기준으로<br />처리해야 할 일들을 안내드립니다.
          </p>
        </div>

        <input
          type="date"
          max={new Date().toISOString().split('T')[0]}
          value={date}
          onChange={(e) => setDate(e.target.value)}
          className="w-full border border-gray-200 rounded-xl px-4 py-3 text-center text-lg focus:outline-none focus:ring-2 focus:ring-gray-300"
        />
      </div>

      <button
        onClick={() => setShowConfirm(true)}
        disabled={!date}
        className="w-full py-4 rounded-xl bg-gray-900 text-white font-semibold disabled:opacity-40 transition-opacity"
      >
        확인
      </button>

      {showConfirm && (
        <div className="fixed inset-0 bg-black/40 flex items-center justify-center z-50 px-6">
          <div className="bg-white rounded-2xl p-6 w-full max-w-sm flex flex-col gap-5">
            <div className="flex flex-col gap-2">
              <p className="text-base font-semibold text-center">사망일자 확인</p>
              <p className="text-sm text-gray-500 text-center">{formatted}이 맞으신가요?</p>
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
                className="flex-1 py-3 rounded-xl bg-gray-900 text-white text-sm font-medium"
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

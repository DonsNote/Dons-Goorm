'use client'

import { useRouter } from 'next/navigation'
import { useOnboardingStore } from '@/store/useOnboardingStore'
import { colors } from '@/lib/design'

export default function ProfilePage() {
  const router = useRouter()
  const { reset } = useOnboardingStore()

  function handleLogout() {
    reset()
    router.replace('/')
  }

  return (
    <div className="px-5 py-8 flex flex-col items-center gap-8">
      <div className="flex flex-col items-center gap-4">
        <div
          className="w-20 h-20 rounded-full flex items-center justify-center"
          style={{ backgroundColor: colors.accentSubtle }}
        >
          <svg xmlns="http://www.w3.org/2000/svg" className="w-12 h-12" viewBox="0 0 24 24" fill={colors.accent}>
            <path d="M12 12c2.7 0 4.8-2.1 4.8-4.8S14.7 2.4 12 2.4 7.2 4.5 7.2 7.2 9.3 12 12 12zm0 2.4c-3.2 0-9.6 1.6-9.6 4.8v2.4h19.2v-2.4c0-3.2-6.4-4.8-9.6-4.8z"/>
          </svg>
        </div>
        <div className="text-center flex flex-col gap-1">
          <p className="text-lg font-semibold">사용자</p>
          <p className="text-sm text-gray-400">user@example.com</p>
        </div>
      </div>

      {/* TODO: 실제 사용자 정보 연동 */}

      <button
        onClick={handleLogout}
        className="w-full py-3 rounded-xl text-sm font-medium"
        style={{ border: `1px solid rgba(0,0,0,0.1)`, color: '#6B7280' }}
      >
        로그아웃
      </button>
    </div>
  )
}

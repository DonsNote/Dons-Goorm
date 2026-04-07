'use client'

import { useRouter } from 'next/navigation'
import { colors } from '@/lib/design'

export default function LoginPage() {
  const router = useRouter()

  function handleLogin() {
    router.replace('/checklist')
  }

  return (
    <div className="min-h-screen flex flex-col" style={{ backgroundColor: colors.lightBg }}>
      {/* 헤드라인 */}
      <div className="px-7 pt-[72px] flex flex-col gap-4">
        <p className="text-[26px] font-bold leading-snug text-black">
          처음부터 끝까지<br />함께 동행하겠습니다
        </p>
        <p className="text-sm text-gray-500 leading-relaxed">
          고인께서 남기신 것들<br />저희가 끝까지 곁에서 정리해드리겠습니다
        </p>
      </div>

      <div className="flex-1" />

      {/* 하단 버튼 영역 */}
      <div className="px-6 pb-10 flex flex-col gap-5">
        <p className="text-[13px] leading-6" style={{ color: colors.accentDim }}>
          목록 저장과 D-day 알림<br />로그인 후 시작 됩니다
        </p>

        <div className="flex flex-col gap-3">
          <SocialButton
            label="카카오로 시작하기"
            leading="K"
            bg={colors.kakao}
            color={colors.kakaoFg}
            onClick={handleLogin}
          />
          <SocialButton
            label="Google로 시작하기"
            leading="G"
            bg="#ffffff"
            color="#111111"
            border
            onClick={handleLogin}
          />
          <SocialButton
            label="Apple로 시작하기"
            leading="􀣺"
            bg="#000000"
            color="#ffffff"
            onClick={handleLogin}
          />
        </div>
      </div>
    </div>
  )
}

function SocialButton({
  label, leading, bg, color, border = false, onClick,
}: {
  label: string; leading: string; bg: string; color: string; border?: boolean; onClick: () => void
}) {
  return (
    <button
      onClick={onClick}
      className="w-full py-[14px] rounded-xl font-medium flex items-center px-5"
      style={{
        backgroundColor: bg,
        color: color,
        border: border ? '1px solid rgba(0,0,0,0.12)' : 'none',
      }}
    >
      <span className="w-5 font-bold text-sm">{leading}</span>
      <span className="flex-1 text-center text-sm">{label}</span>
      <span className="w-5" />
    </button>
  )
}

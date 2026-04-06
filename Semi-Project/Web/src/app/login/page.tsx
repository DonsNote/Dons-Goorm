'use client'

import { useRouter } from 'next/navigation'

export default function LoginPage() {
  const router = useRouter()

  function handleLogin() {
    // TODO: 실제 소셜 로그인 연동 후 교체
    router.replace('/checklist')
  }

  return (
    <div className="min-h-screen flex flex-col items-center justify-between px-6 py-12">
      <div className="flex-1 flex flex-col items-center justify-center gap-5 text-center">
        <span className="text-6xl">🤝</span>
        <p className="text-3xl font-bold">동행</p>
        <p className="text-sm text-gray-500 leading-relaxed">
          홀로 남겨지지 않도록,<br />함께 걷겠습니다.
        </p>
      </div>

      <div className="w-full flex flex-col gap-3">
        <SocialButton
          label="Apple로 계속하기"
          leading="􀣺"
          bg="bg-black"
          text="text-white"
          onClick={handleLogin}
        />
        <SocialButton
          label="Google로 계속하기"
          leading="G"
          bg="bg-white"
          text="text-gray-900"
          border
          onClick={handleLogin}
        />
        <SocialButton
          label="카카오로 계속하기"
          leading="K"
          bg="bg-[#FFE600]"
          text="text-[#191919]"
          onClick={handleLogin}
        />
      </div>
    </div>
  )
}

function SocialButton({
  label,
  leading,
  bg,
  text,
  border = false,
  onClick,
}: {
  label: string
  leading: string
  bg: string
  text: string
  border?: boolean
  onClick: () => void
}) {
  return (
    <button
      onClick={onClick}
      className={`w-full py-4 rounded-xl font-medium flex items-center px-5 ${bg} ${text} ${
        border ? 'border border-gray-200' : ''
      }`}
    >
      <span className="w-5 font-bold">{leading}</span>
      <span className="flex-1 text-center">{label}</span>
      <span className="w-5" />
    </button>
  )
}

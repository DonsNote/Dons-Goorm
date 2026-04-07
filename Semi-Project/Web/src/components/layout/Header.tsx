'use client'

import Link from 'next/link'
import { usePathname } from 'next/navigation'
import { colors } from '@/lib/design'

const titles: Record<string, string> = {
  '/guide': '가이드',
  '/checklist': '체크리스트',
  '/progress': '진행상황',
}

export default function Header() {
  const pathname = usePathname()
  const title = Object.entries(titles).find(([key]) => pathname.startsWith(key))?.[1] ?? '동행'

  return (
    <header
      className="flex items-center justify-between px-5 py-4 sticky top-0 z-10"
      style={{ backgroundColor: colors.lightBg, borderBottom: `1px solid rgba(0,0,0,0.07)` }}
    >
      <h1 className="text-lg font-semibold">{title}</h1>
      <Link href="/profile">
        <div
          className="w-8 h-8 rounded-full flex items-center justify-center"
          style={{ backgroundColor: colors.accentSubtle }}
        >
          <svg xmlns="http://www.w3.org/2000/svg" className="w-5 h-5" viewBox="0 0 24 24" fill={colors.accent}>
            <path d="M12 12c2.7 0 4.8-2.1 4.8-4.8S14.7 2.4 12 2.4 7.2 4.5 7.2 7.2 9.3 12 12 12zm0 2.4c-3.2 0-9.6 1.6-9.6 4.8v2.4h19.2v-2.4c0-3.2-6.4-4.8-9.6-4.8z"/>
          </svg>
        </div>
      </Link>
    </header>
  )
}

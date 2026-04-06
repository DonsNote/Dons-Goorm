'use client'

import Link from 'next/link'
import { usePathname } from 'next/navigation'

const titles: Record<string, string> = {
  '/guide': '가이드',
  '/checklist': '체크리스트',
  '/progress': '진행상황',
}

export default function Header() {
  const pathname = usePathname()
  const title = Object.entries(titles).find(([key]) => pathname.startsWith(key))?.[1] ?? '동행'

  return (
    <header className="flex items-center justify-between px-5 py-4 border-b border-gray-100 bg-white sticky top-0 z-10">
      <h1 className="text-lg font-semibold">{title}</h1>
      <Link href="/profile">
        <div className="w-8 h-8 rounded-full bg-gray-200 flex items-center justify-center text-gray-500">
          <svg xmlns="http://www.w3.org/2000/svg" className="w-5 h-5" viewBox="0 0 24 24" fill="currentColor">
            <path d="M12 12c2.7 0 4.8-2.1 4.8-4.8S14.7 2.4 12 2.4 7.2 4.5 7.2 7.2 9.3 12 12 12zm0 2.4c-3.2 0-9.6 1.6-9.6 4.8v2.4h19.2v-2.4c0-3.2-6.4-4.8-9.6-4.8z"/>
          </svg>
        </div>
      </Link>
    </header>
  )
}

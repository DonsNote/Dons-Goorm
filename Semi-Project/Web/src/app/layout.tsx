import type { Metadata } from 'next'
import './globals.css'
import VisitTracker from '@/components/VisitTracker'

export const metadata: Metadata = {
  title: '동행',
  description: '사후 유족을 위한 행정 및 디지털 자산 정리 서비스',
}

export default function RootLayout({ children }: { children: React.ReactNode }) {
  return (
    <html lang="ko">
      <body>
        <VisitTracker />
        {children}
      </body>
    </html>
  )
}

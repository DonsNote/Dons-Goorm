'use client'

import { useEffect } from 'react'

export default function VisitTracker() {
  useEffect(() => {
    // 세션당 1회만 카운팅 (새로고침 시 중복 방지)
    if (sessionStorage.getItem('visit-tracked')) return
    sessionStorage.setItem('visit-tracked', '1')

    fetch('/api/track', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ type: 'visit' }),
    }).catch(() => {})
  }, [])

  return null
}

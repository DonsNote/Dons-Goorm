'use client'

import { useEffect, useState, useCallback } from 'react'
import { colors } from '@/lib/design'

type Event = {
  type: 'visit' | 'start'
  timestamp: string
  userAgent?: string
}

type Stats = {
  visits: number
  starts: number
  events: Event[]
}

export default function StatsPage() {
  const [stats, setStats] = useState<Stats | null>(null)
  const [lastUpdated, setLastUpdated] = useState<string>('')

  const fetchStats = useCallback(async () => {
    const res = await fetch('/api/stats')
    const data: Stats = await res.json()
    setStats(data)
    setLastUpdated(new Date().toLocaleTimeString('ko-KR'))
  }, [])

  useEffect(() => {
    fetchStats()
    const interval = setInterval(fetchStats, 5000)
    return () => clearInterval(interval)
  }, [fetchStats])

  const conversionRate =
    stats && stats.visits > 0 ? ((stats.starts / stats.visits) * 100).toFixed(1) : '0.0'

  return (
    <div className="min-h-screen p-6" style={{ backgroundColor: colors.lightBg }}>
      <div className="max-w-lg mx-auto flex flex-col gap-5">
        {/* 헤더 */}
        <div className="flex items-end justify-between">
          <div>
            <h1 className="text-2xl font-bold" style={{ color: colors.accentDark }}>
              테스트 현황
            </h1>
            <p className="text-xs text-gray-400 mt-0.5">5초마다 자동 갱신 · {lastUpdated}</p>
          </div>
          <button
            onClick={fetchStats}
            className="text-xs px-3 py-1.5 rounded-full font-medium"
            style={{ backgroundColor: colors.accentSubtle, color: colors.accentDim }}
          >
            새로고침
          </button>
        </div>

        {/* 주요 지표 카드 */}
        <div className="grid grid-cols-3 gap-3">
          <StatCard
            label="방문자"
            value={stats?.visits ?? 0}
            icon="👥"
            color={colors.accent}
          />
          <StatCard
            label="시작하기"
            value={stats?.starts ?? 0}
            icon="🚀"
            color={colors.accent}
          />
          <StatCard
            label="전환율"
            value={`${conversionRate}%`}
            icon="📊"
            color={Number(conversionRate) >= 50 ? '#4CAF50' : colors.accentDim}
          />
        </div>

        {/* 전환율 바 */}
        {stats && stats.visits > 0 && (
          <div className="rounded-2xl p-4" style={{ backgroundColor: colors.cardBg }}>
            <div className="flex justify-between text-sm mb-2">
              <span className="font-medium">온보딩 → 시작하기 전환율</span>
              <span className="font-bold" style={{ color: colors.accent }}>{conversionRate}%</span>
            </div>
            <div className="h-2 rounded-full overflow-hidden" style={{ backgroundColor: `${colors.accent}26` }}>
              <div
                className="h-full rounded-full transition-all duration-700"
                style={{
                  width: `${Math.min(Number(conversionRate), 100)}%`,
                  backgroundColor: colors.accent,
                }}
              />
            </div>
            <p className="text-xs text-gray-400 mt-1.5">
              {stats.visits}명 방문 중 {stats.starts}명이 시작하기를 눌렀습니다
            </p>
          </div>
        )}

        {/* 이벤트 로그 */}
        <div className="rounded-2xl overflow-hidden" style={{ backgroundColor: colors.cardBg }}>
          <div className="px-4 py-3 border-b" style={{ borderColor: 'rgba(0,0,0,0.06)' }}>
            <span className="font-semibold text-sm">이벤트 로그</span>
            <span className="text-xs text-gray-400 ml-2">최근 {Math.min(stats?.events.length ?? 0, 50)}건</span>
          </div>

          {!stats || stats.events.length === 0 ? (
            <div className="px-4 py-8 text-center text-sm text-gray-400">
              아직 이벤트가 없습니다
            </div>
          ) : (
            <div className="divide-y" style={{ borderColor: 'rgba(0,0,0,0.05)' }}>
              {[...stats.events].reverse().slice(0, 50).map((event, i) => (
                <div key={i} className="flex items-center gap-3 px-4 py-2.5">
                  <span className="text-base">{event.type === 'visit' ? '👤' : '🚀'}</span>
                  <div className="flex-1 min-w-0">
                    <p className="text-xs font-medium">
                      {event.type === 'visit' ? '방문' : '지금 시작하기 클릭'}
                    </p>
                    <p className="text-xs text-gray-400 truncate" title={event.userAgent}>
                      {formatUA(event.userAgent)}
                    </p>
                  </div>
                  <span className="text-xs text-gray-400 whitespace-nowrap">
                    {formatTime(event.timestamp)}
                  </span>
                </div>
              ))}
            </div>
          )}
        </div>
      </div>
    </div>
  )
}

function StatCard({ label, value, icon, color }: { label: string; value: number | string; icon: string; color: string }) {
  return (
    <div className="rounded-2xl p-4 flex flex-col gap-2" style={{ backgroundColor: colors.cardBg }}>
      <span className="text-xl">{icon}</span>
      <p className="text-2xl font-bold leading-none" style={{ color }}>{value}</p>
      <p className="text-xs text-gray-400">{label}</p>
    </div>
  )
}

function formatTime(iso: string): string {
  return new Date(iso).toLocaleTimeString('ko-KR', { hour: '2-digit', minute: '2-digit', second: '2-digit' })
}

function formatUA(ua?: string): string {
  if (!ua) return '알 수 없음'
  if (ua.includes('iPhone') || ua.includes('iPad')) return 'iOS'
  if (ua.includes('Android')) return 'Android'
  if (ua.includes('Mac')) return 'Mac'
  if (ua.includes('Windows')) return 'Windows'
  return ua.slice(0, 40)
}

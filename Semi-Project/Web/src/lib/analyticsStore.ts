import fs from 'fs'
import path from 'path'

const DATA_FILE = path.join(process.cwd(), 'analytics-data.json')

export type AnalyticsEvent = {
  type: 'visit' | 'start'
  timestamp: string
  userAgent?: string
}

export type AnalyticsStats = {
  visits: number
  starts: number
  events: AnalyticsEvent[]
}

function readStats(): AnalyticsStats {
  try {
    if (fs.existsSync(DATA_FILE)) {
      return JSON.parse(fs.readFileSync(DATA_FILE, 'utf-8'))
    }
  } catch {
    // 파일 읽기 실패 시 초기값 반환
  }
  return { visits: 0, starts: 0, events: [] }
}

function writeStats(stats: AnalyticsStats): void {
  try {
    fs.writeFileSync(DATA_FILE, JSON.stringify(stats, null, 2))
  } catch {
    // 파일 쓰기 실패 시 무시
  }
}

export function trackEvent(type: 'visit' | 'start', userAgent?: string): AnalyticsStats {
  const stats = readStats()
  if (type === 'visit') stats.visits += 1
  if (type === 'start') stats.starts += 1
  stats.events.push({ type, timestamp: new Date().toISOString(), userAgent })
  writeStats(stats)
  return stats
}

export function getStats(): AnalyticsStats {
  return readStats()
}

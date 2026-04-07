import { NextRequest, NextResponse } from 'next/server'
import { trackEvent } from '@/lib/analyticsStore'

export async function POST(req: NextRequest) {
  const body = await req.json().catch(() => ({}))
  const type = body.type as 'visit' | 'start'

  if (type !== 'visit' && type !== 'start') {
    return NextResponse.json({ error: 'invalid type' }, { status: 400 })
  }

  const userAgent = req.headers.get('user-agent') ?? undefined
  const stats = trackEvent(type, userAgent)

  return NextResponse.json({ ok: true, stats })
}

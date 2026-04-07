'use client'

import { useState, useRef, useEffect } from 'react'
import { colors } from '@/lib/design'

interface Message {
  id: string
  content: string
  isUser: boolean
}

export default function GuidePage() {
  const [messages, setMessages] = useState<Message[]>([
    {
      id: '0',
      content: '안녕하세요. 어렵고 힘드신 상황에서 찾아주셨군요.\n처리해야 할 일들에 대해 궁금한 점을 편하게 물어보세요.',
      isUser: false,
    },
  ])
  const [input, setInput] = useState('')
  const bottomRef = useRef<HTMLDivElement>(null)

  useEffect(() => {
    bottomRef.current?.scrollIntoView({ behavior: 'smooth' })
  }, [messages])

  function handleSend() {
    const text = input.trim()
    if (!text) return
    setMessages((prev) => [...prev, { id: Date.now().toString(), content: text, isUser: true }])
    setInput('')
    // TODO: 서버 API 연동
  }

  return (
    <div className="flex flex-col h-[calc(100vh-120px)]">
      <div className="flex-1 overflow-y-auto px-4 py-4 flex flex-col gap-3">

        {/* AI 코디네이터 헤더 카드 */}
        <div
          className="flex items-center gap-3.5 px-4 py-3.5 rounded-2xl"
          style={{ backgroundColor: colors.cardBg }}
        >
          <div
            className="w-12 h-12 rounded-full flex items-center justify-center flex-shrink-0"
            style={{ backgroundColor: colors.accentSubtle }}
          >
            <svg xmlns="http://www.w3.org/2000/svg" className="w-6 h-6" viewBox="0 0 24 24" fill={colors.accent}>
              <path d="M16 11c1.66 0 2.99-1.34 2.99-3S17.66 5 16 5c-1.66 0-3 1.34-3 3s1.34 3 3 3zm-8 0c1.66 0 2.99-1.34 2.99-3S9.66 5 8 5C6.34 5 5 6.34 5 8s1.34 3 3 3zm0 2c-2.33 0-7 1.17-7 3.5V19h14v-2.5c0-2.33-4.67-3.5-7-3.5zm8 0c-.29 0-.62.02-.97.05 1.16.84 1.97 1.97 1.97 3.45V19h6v-2.5c0-2.33-4.67-3.5-7-3.5z"/>
            </svg>
          </div>
          <div className="flex flex-col gap-1">
            <p className="text-sm font-semibold text-black">AI 코디네이터</p>
            <div className="flex items-center gap-1.5">
              <div className="w-1.5 h-1.5 rounded-full bg-green-500" />
              <p className="text-xs text-gray-400">24시간 실시간 상담중</p>
            </div>
          </div>
        </div>

        {/* 메시지 목록 */}
        {messages.map((msg) => (
          <div key={msg.id} className={`flex ${msg.isUser ? 'justify-end' : 'justify-start'}`}>
            <div
              className="max-w-[75%] px-4 py-2.5 text-sm whitespace-pre-line leading-relaxed"
              style={{
                backgroundColor: msg.isUser ? colors.accent : colors.cardBg,
                color: msg.isUser ? '#fff' : '#111',
                borderRadius: msg.isUser ? '16px 16px 4px 16px' : '16px 16px 16px 4px',
              }}
            >
              {msg.content}
            </div>
          </div>
        ))}
        <div ref={bottomRef} />
      </div>

      <div
        className="px-4 py-3 flex gap-2 items-end"
        style={{ borderTop: '1px solid rgba(0,0,0,0.06)', backgroundColor: colors.lightBg }}
      >
        <textarea
          value={input}
          onChange={(e) => setInput(e.target.value)}
          onKeyDown={(e) => {
            if (e.key === 'Enter' && !e.shiftKey) {
              e.preventDefault()
              handleSend()
            }
          }}
          placeholder="궁금한 점을 물어보세요..."
          rows={1}
          className="flex-1 resize-none rounded-2xl px-4 py-2.5 text-sm focus:outline-none max-h-28"
          style={{ backgroundColor: colors.cardBg }}
        />
        <button
          onClick={handleSend}
          disabled={!input.trim()}
          className="w-9 h-9 rounded-full flex items-center justify-center flex-shrink-0 transition-opacity"
          style={{
            backgroundColor: colors.accent,
            opacity: input.trim() ? 1 : 0.3,
          }}
        >
          <svg xmlns="http://www.w3.org/2000/svg" className="w-4 h-4 text-white" viewBox="0 0 24 24" fill="currentColor">
            <path d="M12 4l8 16-8-4-8 4 8-16z" />
          </svg>
        </button>
      </div>
    </div>
  )
}

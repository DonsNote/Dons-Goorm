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
          placeholder="질문을 입력해 주세요."
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

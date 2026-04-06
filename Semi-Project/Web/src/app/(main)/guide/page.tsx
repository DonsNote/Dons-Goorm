'use client'

import { useState, useRef, useEffect } from 'react'

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
              className={`max-w-[75%] px-4 py-2.5 rounded-2xl text-sm whitespace-pre-line leading-relaxed ${
                msg.isUser
                  ? 'bg-gray-900 text-white rounded-br-sm'
                  : 'bg-gray-100 text-gray-900 rounded-bl-sm'
              }`}
            >
              {msg.content}
            </div>
          </div>
        ))}
        <div ref={bottomRef} />
      </div>

      <div className="border-t border-gray-100 px-4 py-3 flex gap-2 items-end bg-white">
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
          className="flex-1 resize-none bg-gray-100 rounded-2xl px-4 py-2.5 text-sm focus:outline-none max-h-28"
        />
        <button
          onClick={handleSend}
          disabled={!input.trim()}
          className="w-9 h-9 rounded-full bg-gray-900 text-white flex items-center justify-center disabled:opacity-30 flex-shrink-0"
        >
          <svg xmlns="http://www.w3.org/2000/svg" className="w-4 h-4" viewBox="0 0 24 24" fill="currentColor">
            <path d="M12 4l8 16-8-4-8 4 8-16z" />
          </svg>
        </button>
      </div>
    </div>
  )
}

'use client'

import { create } from 'zustand'
import { persist } from 'zustand/middleware'

export type Category = 'administrative' | 'financial' | 'digital' | 'legal'

export interface ChecklistTask {
  id: string
  title: string
  dDay: number | null
  isCompleted: boolean
}

export interface ChecklistSection {
  id: Category
  title: string
  icon: string
  tasks: ChecklistTask[]
}

const initialSections: ChecklistSection[] = [
  {
    id: 'administrative',
    title: '사무행정',
    icon: '📋',
    tasks: [
      { id: 'a1', title: '사망신고', dDay: 7, isCompleted: false },
      { id: 'a2', title: '사망진단서 발급', dDay: 3, isCompleted: false },
      { id: 'a3', title: '건강보험 자격 상실 신고', dDay: 14, isCompleted: false },
    ],
  },
  {
    id: 'financial',
    title: '금융',
    icon: '💳',
    tasks: [
      { id: 'f1', title: '은행 계좌 동결 신청', dDay: 7, isCompleted: false },
      { id: 'f2', title: '생명보험 청구', dDay: 30, isCompleted: false },
      { id: 'f3', title: '국민연금 사망 신고', dDay: 14, isCompleted: false },
    ],
  },
  {
    id: 'digital',
    title: '디지털',
    icon: '💻',
    tasks: [
      { id: 'd1', title: 'SNS 계정 처리', dDay: null, isCompleted: false },
      { id: 'd2', title: '이메일 계정 정리', dDay: null, isCompleted: false },
      { id: 'd3', title: '구독 서비스 해지', dDay: 30, isCompleted: false },
    ],
  },
  {
    id: 'legal',
    title: '법원행정',
    icon: '⚖️',
    tasks: [
      { id: 'l1', title: '상속 포기 신청', dDay: 90, isCompleted: false },
      { id: 'l2', title: '유언장 검인 신청', dDay: null, isCompleted: false },
      { id: 'l3', title: '후견인 지정', dDay: null, isCompleted: false },
    ],
  },
]

interface ChecklistState {
  sections: ChecklistSection[]
  toggleTask: (sectionId: Category, taskId: string) => void
}

export const useChecklistStore = create<ChecklistState>()(
  persist(
    (set) => ({
      sections: initialSections,
      toggleTask: (sectionId, taskId) =>
        set((state) => ({
          sections: state.sections.map((section) =>
            section.id !== sectionId
              ? section
              : {
                  ...section,
                  tasks: section.tasks.map((task) =>
                    task.id !== taskId ? task : { ...task, isCompleted: !task.isCompleted }
                  ),
                }
          ),
        })),
    }),
    { name: 'checklist' }
  )
)

'use client'

import { create } from 'zustand'
import { persist } from 'zustand/middleware'

export type Category = 'administrative' | 'financial' | 'digital' | 'legal'

export interface RequiredDocument {
  id: string
  title: string
  isReady: boolean
}

export interface ChecklistTask {
  id: string
  title: string
  dDay: number | null
  isCompleted: boolean
  documents: RequiredDocument[]
}

export interface ChecklistSection {
  id: Category
  title: string
  icon: string
  tasks: ChecklistTask[]
}

const initialSections: ChecklistSection[] = [
  {
    id: 'administrative', title: '사무행정', icon: '📋',
    tasks: [
      { id: 'a1', title: '사망신고', dDay: 7, isCompleted: false, documents: [
        { id: 'a1d1', title: '사망진단서 (원본)', isReady: false },
        { id: 'a1d2', title: '신고인 신분증', isReady: false },
        { id: 'a1d3', title: '가족관계증명서', isReady: false },
      ]},
      { id: 'a2', title: '사망진단서 발급', dDay: 3, isCompleted: false, documents: [
        { id: 'a2d1', title: '신청인 신분증', isReady: false },
        { id: 'a2d2', title: '진료비 영수증', isReady: false },
        { id: 'a2d3', title: '가족관계증명서', isReady: false },
      ]},
      { id: 'a3', title: '건강보험 자격 상실 신고', dDay: 14, isCompleted: false, documents: [
        { id: 'a3d1', title: '사망진단서', isReady: false },
        { id: 'a3d2', title: '건강보험증', isReady: false },
        { id: 'a3d3', title: '신청인 신분증', isReady: false },
      ]},
    ],
  },
  {
    id: 'financial', title: '금융', icon: '💳',
    tasks: [
      { id: 'f1', title: '은행 계좌 동결 신청', dDay: 7, isCompleted: false, documents: [
        { id: 'f1d1', title: '사망진단서', isReady: false },
        { id: 'f1d2', title: '가족관계증명서', isReady: false },
        { id: 'f1d3', title: '신청인 신분증', isReady: false },
        { id: 'f1d4', title: '인감증명서', isReady: false },
      ]},
      { id: 'f2', title: '생명보험 청구', dDay: 30, isCompleted: false, documents: [
        { id: 'f2d1', title: '사망진단서', isReady: false },
        { id: 'f2d2', title: '보험증권 또는 증권번호', isReady: false },
        { id: 'f2d3', title: '가족관계증명서', isReady: false },
        { id: 'f2d4', title: '수익자 신분증', isReady: false },
      ]},
      { id: 'f3', title: '국민연금 사망 신고', dDay: 14, isCompleted: false, documents: [
        { id: 'f3d1', title: '사망진단서', isReady: false },
        { id: 'f3d2', title: '가족관계증명서', isReady: false },
        { id: 'f3d3', title: '신청인 통장 사본', isReady: false },
      ]},
    ],
  },
  {
    id: 'digital', title: '디지털', icon: '💻',
    tasks: [
      { id: 'd1', title: 'SNS 계정 처리', dDay: null, isCompleted: false, documents: [
        { id: 'd1d1', title: '사망확인서류 사본', isReady: false },
        { id: 'd1d2', title: '신청인 신분증 사본', isReady: false },
        { id: 'd1d3', title: '고인과의 관계 증명서류', isReady: false },
      ]},
      { id: 'd2', title: '이메일 계정 정리', dDay: null, isCompleted: false, documents: [
        { id: 'd2d1', title: '사망확인서류', isReady: false },
        { id: 'd2d2', title: '신청인 신분증', isReady: false },
      ]},
      { id: 'd3', title: '구독 서비스 해지', dDay: 30, isCompleted: false, documents: [
        { id: 'd3d1', title: '사망진단서 또는 사망확인서', isReady: false },
        { id: 'd3d2', title: '신청인 신분증', isReady: false },
      ]},
    ],
  },
  {
    id: 'legal', title: '법원행정', icon: '⚖️',
    tasks: [
      { id: 'l1', title: '상속 포기 신청', dDay: 90, isCompleted: false, documents: [
        { id: 'l1d1', title: '사망진단서', isReady: false },
        { id: 'l1d2', title: '가족관계증명서 (전부 사항)', isReady: false },
        { id: 'l1d3', title: '인감증명서', isReady: false },
        { id: 'l1d4', title: '인감도장', isReady: false },
        { id: 'l1d5', title: '신청인 신분증', isReady: false },
      ]},
      { id: 'l2', title: '유언장 검인 신청', dDay: null, isCompleted: false, documents: [
        { id: 'l2d1', title: '유언장 원본', isReady: false },
        { id: 'l2d2', title: '사망진단서', isReady: false },
        { id: 'l2d3', title: '가족관계증명서', isReady: false },
        { id: 'l2d4', title: '신청인 신분증', isReady: false },
      ]},
      { id: 'l3', title: '후견인 지정', dDay: null, isCompleted: false, documents: [
        { id: 'l3d1', title: '가족관계증명서', isReady: false },
        { id: 'l3d2', title: '신청인 신분증', isReady: false },
        { id: 'l3d3', title: '인감증명서', isReady: false },
      ]},
    ],
  },
]

interface ChecklistState {
  sections: ChecklistSection[]
  toggleTask: (sectionId: Category, taskId: string) => void
  toggleDocument: (sectionId: Category, taskId: string, docId: string) => void
}

export const useChecklistStore = create<ChecklistState>()(
  persist(
    (set) => ({
      sections: initialSections,
      toggleTask: (sectionId, taskId) =>
        set((state) => ({
          sections: state.sections.map((s) =>
            s.id !== sectionId ? s : {
              ...s,
              tasks: s.tasks.map((t) =>
                t.id !== taskId ? t : { ...t, isCompleted: !t.isCompleted }
              ),
            }
          ),
        })),
      toggleDocument: (sectionId, taskId, docId) =>
        set((state) => ({
          sections: state.sections.map((s) =>
            s.id !== sectionId ? s : {
              ...s,
              tasks: s.tasks.map((t) =>
                t.id !== taskId ? t : {
                  ...t,
                  documents: t.documents.map((d) =>
                    d.id !== docId ? d : { ...d, isReady: !d.isReady }
                  ),
                }
              ),
            }
          ),
        })),
    }),
    { name: 'checklist-v2' }
  )
)

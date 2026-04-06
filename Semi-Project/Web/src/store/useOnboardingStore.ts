'use client'

import { create } from 'zustand'
import { persist } from 'zustand/middleware'

interface OnboardingState {
  deceasedDate: string | null
  onboardingDone: boolean
  setDeceasedDate: (date: string) => void
  setOnboardingDone: () => void
  reset: () => void
}

export const useOnboardingStore = create<OnboardingState>()(
  persist(
    (set) => ({
      deceasedDate: null,
      onboardingDone: false,
      setDeceasedDate: (date) => set({ deceasedDate: date }),
      setOnboardingDone: () => set({ onboardingDone: true }),
      reset: () => set({ deceasedDate: null, onboardingDone: false }),
    }),
    { name: 'onboarding' }
  )
)

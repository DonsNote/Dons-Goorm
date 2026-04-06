import Header from '@/components/layout/Header'
import TabNav from '@/components/layout/TabNav'

export default function MainLayout({ children }: { children: React.ReactNode }) {
  return (
    <div className="max-w-lg mx-auto min-h-screen flex flex-col">
      <Header />
      <main className="flex-1 pb-20">{children}</main>
      <TabNav />
    </div>
  )
}

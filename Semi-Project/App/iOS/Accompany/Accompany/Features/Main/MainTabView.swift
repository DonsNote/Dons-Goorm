//
//  MainTabView.swift
//  Accompany
//

import SwiftUI

struct MainTabView: View {
    let deceasedDate: Date
    @State private var sections: [ChecklistSection]
    @State private var selectedTab: Int = 1
    @State private var showUserProfile: Bool = false

    init(deceasedDate: Date) {
        self.deceasedDate = deceasedDate
        _sections = State(initialValue: ChecklistSection.placeholder(deceasedDate: deceasedDate))
    }

    var body: some View {
        TabView(selection: $selectedTab) {
            GuideView(onProfileTap: { showUserProfile = true })
                .tabItem {
                    Label("가이드", systemImage: "bubble.left.and.bubble.right")
                }
                .tag(0)

            ChecklistView(sections: $sections, onProfileTap: { showUserProfile = true })
                .tabItem {
                    Label("체크리스트", systemImage: "checklist")
                }
                .tag(1)

            ProgressReportView(sections: sections, deceasedDate: deceasedDate, onProfileTap: { showUserProfile = true })
                .tabItem {
                    Label("진행상황", systemImage: "chart.bar.doc.horizontal")
                }
                .tag(2)
        }
        .tint(Color.App.accent)
        .onChange(of: selectedTab) {
            UIApplication.shared.sendAction(#selector(UIResponder.resignFirstResponder), to: nil, from: nil, for: nil)
        }
        .sheet(isPresented: $showUserProfile) {
            UserProfileView()
        }
    }
}

#Preview {
    MainTabView(deceasedDate: Date())
}

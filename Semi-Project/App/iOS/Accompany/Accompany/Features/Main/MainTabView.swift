//
//  MainTabView.swift
//  Accompany
//

import SwiftUI

struct MainTabView: View {
    let deceasedDate: Date
    @State private var sections: [ChecklistSection]
    @State private var selectedTab: Int = 1

    init(deceasedDate: Date) {
        self.deceasedDate = deceasedDate
        _sections = State(initialValue: ChecklistSection.placeholder(deceasedDate: deceasedDate))
    }

    var body: some View {
        TabView(selection: $selectedTab) {
            GuideView()
                .tabItem {
                    Label("가이드", systemImage: "bubble.left.and.bubble.right")
                }
                .tag(0)

            ChecklistView(sections: $sections)
                .tabItem {
                    Label("체크리스트", systemImage: "checklist")
                }
                .tag(1)

            ProgressReportView(sections: sections, deceasedDate: deceasedDate)
                .tabItem {
                    Label("진행상황", systemImage: "chart.bar.doc.horizontal")
                }
                .tag(2)
        }
    }
}

#Preview {
    MainTabView(deceasedDate: Date())
}

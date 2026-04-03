//
//  UserProfileView.swift
//  Accompany
//

import SwiftUI

struct UserProfileView: View {
    @Environment(\.dismiss) private var dismiss

    var body: some View {
        NavigationStack {
            VStack(spacing: 32) {
                Spacer()

                VStack(spacing: 16) {
                    Image(systemName: "person.circle.fill")
                        .font(.system(size: 80))
                        .foregroundColor(.secondary)

                    VStack(spacing: 4) {
                        Text("사용자")
                            .font(.title2)
                            .fontWeight(.semibold)

                        Text("user@example.com")
                            .font(.subheadline)
                            .foregroundColor(.secondary)
                    }
                }

                Spacer()

                // TODO: 실제 사용자 정보 연동
            }
            .navigationTitle("내 정보")
            .navigationBarTitleDisplayMode(.inline)
            .toolbar {
                ToolbarItem(placement: .navigationBarTrailing) {
                    Button("닫기") { dismiss() }
                }
            }
        }
    }
}

#Preview {
    UserProfileView()
}

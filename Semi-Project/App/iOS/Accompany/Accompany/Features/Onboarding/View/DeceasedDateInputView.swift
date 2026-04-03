//
//  DeceasedDateInputView.swift
//  Accompany
//

import SwiftUI

struct DeceasedDateInputView: View {
    @StateObject private var viewModel = OnboardingViewModel()
    var onComplete: (Date) -> Void

    var body: some View {
        VStack(spacing: 40) {
            Spacer()

            VStack(spacing: 12) {
                Text("고인의 사망일자를 입력해 주세요.")
                    .font(.title3)
                    .fontWeight(.medium)

                Text("입력하신 날짜를 기준으로\n처리해야 할 일들을 안내드립니다.")
                    .font(.subheadline)
                    .foregroundColor(.secondary)
                    .multilineTextAlignment(.center)
            }

            DatePicker(
                "",
                selection: $viewModel.deceasedDate,
                in: ...Date(),
                displayedComponents: .date
            )
            .datePickerStyle(.wheel)
            .labelsHidden()
            .environment(\.locale, Locale(identifier: "ko_KR"))

            Spacer()

            Button {
                viewModel.confirm()
            } label: {
                Text("확인")
                    .font(.body)
                    .fontWeight(.semibold)
                    .frame(maxWidth: .infinity)
                    .padding()
                    .background(Color.primary)
                    .foregroundColor(Color(uiColor: .systemBackground))
                    .cornerRadius(12)
            }
            .padding(.horizontal)
            .padding(.bottom, 24)
        }
        .alert("사망일자 확인", isPresented: $viewModel.showConfirmAlert) {
            Button("취소", role: .cancel) {}
            Button("확인") {
                onComplete(viewModel.deceasedDate)
            }
        } message: {
            Text("\(viewModel.formattedDate)이 맞으신가요?")
        }
    }
}

#Preview {
    DeceasedDateInputView(onComplete: { _ in })
}

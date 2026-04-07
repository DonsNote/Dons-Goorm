//
//  DeceasedDateInputView.swift
//  Accompany
//

import SwiftUI

struct DeceasedDateInputView: View {
    @StateObject private var viewModel = OnboardingViewModel()
    var onComplete: (Date) -> Void

    var body: some View {
        ZStack {
            Color.App.lightBg.ignoresSafeArea()

            VStack(alignment: .leading, spacing: 0) {
                VStack(alignment: .leading, spacing: 12) {
                    Text("이제 혼자\n챙기지 않아도 됩니다")
                        .font(Font.App.displayBold)
                        .foregroundColor(.black)
                        .lineSpacing(4)

                    Text("고인께서 영면에 드신 날을 입력해주시면\n처리해야 할 모든 것을 알려드릴게요")
                        .font(.subheadline)
                        .foregroundColor(.gray)
                        .lineSpacing(3)
                }
                .padding(.top, 60)
                .padding(.horizontal, 28)

                Spacer()

                VStack(alignment: .leading, spacing: 0) {
                    Text("고인께서 영면에 드신 날")
                        .font(.footnote)
                        .fontWeight(.semibold)
                        .foregroundColor(Color.App.accent)
                        .padding(.horizontal, AppSpacing.cardHorizontal)
                        .padding(.top, 20)
                        .padding(.bottom, 12)

                    DatePicker(
                        "",
                        selection: $viewModel.deceasedDate,
                        in: ...Date(),
                        displayedComponents: .date
                    )
                    .datePickerStyle(.wheel)
                    .labelsHidden()
                    .environment(\.locale, Locale(identifier: "ko_KR"))
                    .colorScheme(.light)
                    .frame(maxWidth: .infinity)

                    Divider()
                        .padding(.horizontal, AppSpacing.cardHorizontal)

                    Text("법적 기한 계산에 사용됩니다")
                        .font(.caption)
                        .foregroundColor(.gray)
                        .padding(.horizontal, AppSpacing.cardHorizontal)
                        .padding(.vertical, 14)
                }
                .background(Color.App.cardBg)
                .cornerRadius(20)
                .padding(.horizontal, AppSpacing.screenHorizontal)

                Spacer()

                VStack(spacing: 10) {
                    Button {
                        viewModel.confirm()
                    } label: {
                        Text("처리 목록 확인하기")
                            .font(.body)
                            .fontWeight(.semibold)
                            .foregroundColor(.white)
                            .frame(maxWidth: .infinity)
                            .padding(.vertical, 18)
                            .background(Color.App.accent)
                            .cornerRadius(AppSpacing.buttonRadius)
                    }

                    Text("입력하신 내용은 안전하게 저장됩니다")
                        .font(.caption)
                        .foregroundColor(.gray)
                }
                .padding(.horizontal, AppSpacing.screenHorizontal)
                .padding(.bottom, AppSpacing.screenBottom)
            }
        }
        .alert("영면일자 확인", isPresented: $viewModel.showConfirmAlert) {
            Button("취소", role: .cancel) {}
            Button("확인") {
                onComplete(viewModel.deceasedDate)
            }
        } message: {
            Text("\(viewModel.formattedDate)\n영면일이 맞나요?")
        }
    }
}

#Preview {
    DeceasedDateInputView(onComplete: { _ in })
}

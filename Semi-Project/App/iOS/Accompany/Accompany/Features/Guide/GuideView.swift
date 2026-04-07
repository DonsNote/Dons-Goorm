//
//  GuideView.swift
//  Accompany
//

import SwiftUI

struct GuideMessage: Identifiable {
    let id = UUID()
    let content: String
    let isUser: Bool
}

struct GuideView: View {
    var onProfileTap: () -> Void = {}

    @State private var messages: [GuideMessage] = [
        GuideMessage(content: "안녕하세요. 어렵고 힘드신 상황에서 찾아주셨군요.\n처리해야 할 일들에 대해 궁금한 점을 편하게 물어보세요.", isUser: false)
    ]
    @State private var inputText: String = ""
    @FocusState private var isInputFocused: Bool

    var body: some View {
        NavigationStack {
            ScrollViewReader { proxy in
                ScrollView {
                    LazyVStack(spacing: 12) {
                        ForEach(messages) { message in
                            MessageBubble(message: message)
                                .id(message.id)
                        }
                    }
                    .padding()
                }
                .background(Color.App.lightBg)
                .scrollDismissesKeyboard(.immediately)
                .onTapGesture { isInputFocused = false }
                .onChange(of: messages.count) {
                    if let last = messages.last {
                        withAnimation { proxy.scrollTo(last.id, anchor: .bottom) }
                    }
                }
                .safeAreaInset(edge: .bottom) {
                    VStack(spacing: 0) {
                        Divider()
                        HStack(spacing: 12) {
                            TextField("질문을 입력해 주세요.", text: $inputText, axis: .vertical)
                                .lineLimit(1...4)
                                .focused($isInputFocused)
                                .padding(.horizontal, 14)
                                .padding(.vertical, 10)
                                .background(Color.App.cardBg)
                                .cornerRadius(20)

                            Button {
                                sendMessage()
                            } label: {
                                Image(systemName: "arrow.up.circle.fill")
                                    .font(.title2)
                                    .foregroundColor(
                                        inputText.trimmingCharacters(in: .whitespaces).isEmpty
                                            ? Color.App.accent.opacity(0.3)
                                            : Color.App.accent
                                    )
                            }
                            .disabled(inputText.trimmingCharacters(in: .whitespaces).isEmpty)
                        }
                        .padding(.horizontal)
                        .padding(.vertical, 10)
                        .background(Color.App.lightBg)
                    }
                }
            }
            .navigationTitle("가이드")
            .navigationBarTitleDisplayMode(.inline)
            .toolbar {
                ToolbarItem(placement: .navigationBarTrailing) {
                    Button { onProfileTap() } label: {
                        Image(systemName: "person.circle")
                            .font(.title3)
                            .foregroundColor(Color.App.accent)
                    }
                }
            }
        }
    }

    private func sendMessage() {
        let text = inputText.trimmingCharacters(in: .whitespaces)
        guard !text.isEmpty else { return }
        messages.append(GuideMessage(content: text, isUser: true))
        inputText = ""
        // TODO: 서버 API 연동
    }
}

private struct MessageBubble: View {
    let message: GuideMessage

    var body: some View {
        HStack {
            if message.isUser { Spacer(minLength: 60) }
            Text(message.content)
                .font(.subheadline)
                .padding(.horizontal, 14)
                .padding(.vertical, 10)
                .background(message.isUser ? Color.App.accent : Color.App.cardBg)
                .foregroundColor(message.isUser ? .white : .primary)
                .cornerRadius(16)
            if !message.isUser { Spacer(minLength: 60) }
        }
    }
}

#Preview {
    GuideView()
}

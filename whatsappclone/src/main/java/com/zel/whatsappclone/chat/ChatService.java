package com.zel.whatsappclone.chat;

import com.zel.whatsappclone.exception.UserNotFoundException;
import com.zel.whatsappclone.user.User;
import com.zel.whatsappclone.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final ChatRepository chatRepository;
    private final UserRepository userRepository;
    private final ChatMapper mapper;

    @Transactional(readOnly = true)
    public List<ChatResponse> getChatsByReceiverId(Authentication loggedUser){
        final String userId = loggedUser.getName();
        return chatRepository.findChatsBySenderId(userId)
                .stream()
                .map(chat -> mapper.toChatResponse(chat, userId)).toList();
    }

    public String createChat(String senderId, String receiverId){
        Optional<Chat> existingChat = chatRepository.findChatBySenderIdAndReceiverId(senderId, receiverId);
        if (existingChat.isPresent()){
            return existingChat.get().getId();
        }

        User sender = getUser(senderId);
        User receiver = getUser(receiverId);

        return createNewChat(sender, receiver).getId();
    }

    private User getUser(String userId) {
        return userRepository.findByPublicId(userId)
                .orElseThrow(() -> new UserNotFoundException("User with ID: " + userId + " not found"));
    }

    private Chat createNewChat(User sender, User receiver){
        Chat chat = new Chat();
        chat.setSender(sender);
        chat.setRecipient(receiver);
        return chatRepository.save(chat);
    }
}

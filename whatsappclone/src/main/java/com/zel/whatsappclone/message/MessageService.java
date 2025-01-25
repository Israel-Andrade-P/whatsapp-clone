package com.zel.whatsappclone.message;

import com.zel.whatsappclone.chat.Chat;
import com.zel.whatsappclone.chat.ChatService;
import com.zel.whatsappclone.file.FileService;
import com.zel.whatsappclone.notification.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.zel.whatsappclone.message.MessageUtils.createImageMessage;
import static com.zel.whatsappclone.message.MessageUtils.toMessage;
import static com.zel.whatsappclone.notification.NotificationUtils.*;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepository messageRepository;
    private final ChatService chatService;
    private final FileService fileService;
    private final NotificationService notificationService;


    public void saveMessage(MessageRequest messageRequest){
        Chat chat = chatService.findByChatId(messageRequest.getChatId());
        messageRepository.save(toMessage(messageRequest, chat));

        notificationService.sendNotification(messageRequest.getReceiverId(), buildNotificationText(chat, messageRequest));
    }

    public List<MessageResponse> findChatMessages(String chatId){
        return messageRepository.findMessagesByChatId(chatId).stream().map(MessageUtils::fromMessage).toList();
    }
    @Transactional
    public void setMessagesToSeen(String chatId, Authentication authentication){
        Chat chat = chatService.findByChatId(chatId);
        final String recipientId = getRecipientId(chat, authentication);

        messageRepository.setMessagesToSeenByChat(chatId, MessageState.SEEN);

        notificationService.sendNotification(recipientId, buildNotificationMsgSeen(chat, getSenderId(chat, authentication), recipientId));
    }

    public void uploadMediaMessage(String chatId, MultipartFile file, Authentication authentication){
        Chat chat = chatService.findByChatId(chatId);
        final String senderId = getSenderId(chat, authentication);
        final String recipientId = getRecipientId(chat, authentication);

        final String filePath = fileService.saveFile(file, senderId);
        messageRepository.save(createImageMessage(chat, senderId, recipientId, filePath));

        notificationService.sendNotification(recipientId, buildNotificationImage(chat, senderId, recipientId, filePath));
    }

    private String getSenderId(Chat chat, Authentication authentication) {
        if (chat.getSender().getId().equals(authentication.getName())){
            return chat.getSender().getId();
        }
        return chat.getRecipient().getId();
    }

    private String getRecipientId(Chat chat, Authentication authentication) {
        if (chat.getSender().getId().equals(authentication.getName())){
            return chat.getRecipient().getId();
        }
        return chat.getSender().getId();
    }
}

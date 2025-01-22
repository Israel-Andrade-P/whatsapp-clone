package com.zel.whatsappclone.notification;

import com.zel.whatsappclone.chat.Chat;
import com.zel.whatsappclone.file.FileUtils;
import com.zel.whatsappclone.message.MessageRequest;
import com.zel.whatsappclone.message.MessageType;

public class NotificationUtils {

    private NotificationUtils(){}

    public static Notification buildNotificationText(Chat chat, MessageRequest messageRequest){
        return Notification.builder()
                .chatId(chat.getId())
                .messageType(messageRequest.getType())
                .content(messageRequest.getContent())
                .senderId(messageRequest.getSenderId())
                .receiverId(messageRequest.getReceiverId())
                .notificationType(NotificationType.MESSAGE)
                .chatName(chat.getChatName(messageRequest.getSenderId()))
                .build();
    }

    public static Notification buildNotificationMsgSeen(Chat chat, String senderId, String recipientId){
        return Notification.builder()
                .chatId(chat.getId())
                .notificationType(NotificationType.SEEN)
                .receiverId(recipientId)
                .senderId(senderId)
                .build();
    }

    public static Notification buildNotificationImage(Chat chat, String senderId, String recipientId, String filePath){
        return Notification.builder()
                .chatId(chat.getId())
                .messageType(MessageType.IMAGE)
                .notificationType(NotificationType.IMAGE)
                .receiverId(recipientId)
                .senderId(senderId)
                .media(FileUtils.readFileFromLocation(filePath))
                .build();
    }
}

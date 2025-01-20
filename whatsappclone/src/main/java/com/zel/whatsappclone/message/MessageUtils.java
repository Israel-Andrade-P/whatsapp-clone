package com.zel.whatsappclone.message;

import com.zel.whatsappclone.chat.Chat;

public class MessageUtils {

    public static Message toMessage(MessageRequest request, Chat chat){
        return Message.builder()
                .content(request.getContent())
                .type(request.getType())
                .senderId(request.getSenderId())
                .receiverId(request.getReceiverId())
                .state(MessageState.SENT)
                .chat(chat)
                .build();
    }

    public static MessageResponse fromMessage(Message message){
        return MessageResponse.builder()
                .id(message.getId())
                .content(message.getContent())
                .senderId(message.getSenderId())
                .receiverId(message.getReceiverId())
                .type(message.getType())
                .state(message.getState())
                .createdAt(message.getCreatedAt())
                //todo read the media file
                .build();
    }

    public static Message createImageMessage(Chat chat, String senderId, String recipientId, String filePath){
        return Message.builder()
                .chat(chat)
                .senderId(senderId)
                .receiverId(recipientId)
                .type(MessageType.IMAGE)
                .state(MessageState.SENT)
                .mediaFilePath(filePath)
                .build();
    }
}

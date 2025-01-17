package com.zel.whatsappclone.chat;

import com.zel.whatsappclone.common.StringResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/chats")
@RequiredArgsConstructor
public class ChatController {
    private final ChatService chatService;

    @PostMapping
    public ResponseEntity<StringResponse> createChat(@RequestParam(name = "sender-id") String senderId,
                                                     @RequestParam(name = "receiver-id") String receiverId){
        final String chatId = chatService.createChat(senderId, receiverId);
        return ResponseEntity.ok(StringResponse.builder().response(chatId).build());
    }

    
}

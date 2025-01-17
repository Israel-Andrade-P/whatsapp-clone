package com.zel.whatsappclone.chat;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

import static com.zel.whatsappclone.constants.Constants.FIND_CHAT_BY_SENDER_ID;
import static com.zel.whatsappclone.constants.Constants.FIND_CHAT_BY_SENDER_ID_AND_RECEIVER_ID;

public interface ChatRepository extends JpaRepository<Chat, String> {
    @Query(name = FIND_CHAT_BY_SENDER_ID)
    List<Chat> findChatsBySenderId(@Param("senderId") String userId);

    @Query(name = FIND_CHAT_BY_SENDER_ID_AND_RECEIVER_ID)
    Optional<Chat> findChatBySenderIdAndReceiverId(@Param("senderId") String senderId, @Param("recipientId") String receiverId);
}

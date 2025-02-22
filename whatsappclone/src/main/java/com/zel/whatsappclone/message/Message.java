package com.zel.whatsappclone.message;

import com.zel.whatsappclone.chat.Chat;
import com.zel.whatsappclone.common.BaseAuditingEntity;
import com.zel.whatsappclone.constants.Constants;
import jakarta.persistence.*;
import lombok.*;

import static com.zel.whatsappclone.constants.Constants.*;

@Entity
@Table(name = "messages")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@NamedQuery(name = FIND_MESSAGES_BY_CHAT_ID,
            query = "SELECT m FROM Message m WHERE m.chat.id = :chatId ORDER BY m.createdAt")
@NamedQuery(name = SET_MESSAGES_TO_SEEN_BY_CHAT,
            query = "UPDATE Message SET state = :newState WHERE chat.id = :chatId")
public class Message extends BaseAuditingEntity {
    @Id
    @SequenceGenerator(name = "msg_seq", sequenceName = "msg_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "msg_seq")
    private Long id;
    @Column(columnDefinition = "TEXT")
    private String content;
    @Enumerated(EnumType.STRING)
    private MessageState state;
    @Enumerated(EnumType.STRING)
    private MessageType type;
    @ManyToOne
    @JoinColumn(name = "chat_id")
    private Chat chat;
    @Column(name = "sender_id", nullable = false)
    private String senderId;
    @Column(name = "receiver_id", nullable = false)
    private String receiverId;
    private String mediaFilePath;
}

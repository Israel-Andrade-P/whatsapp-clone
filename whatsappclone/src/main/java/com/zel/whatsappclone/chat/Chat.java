package com.zel.whatsappclone.chat;

import com.zel.whatsappclone.common.BaseAuditingEntity;
import com.zel.whatsappclone.message.Message;
import com.zel.whatsappclone.message.MessageState;
import com.zel.whatsappclone.message.MessageType;
import com.zel.whatsappclone.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

import static com.zel.whatsappclone.constants.Constants.FIND_CHAT_BY_SENDER_ID;
import static com.zel.whatsappclone.constants.Constants.FIND_CHAT_BY_SENDER_ID_AND_RECEIVER_ID;
import static jakarta.persistence.GenerationType.UUID;

@Entity
@Table(name = "chats")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@NamedQuery(name = FIND_CHAT_BY_SENDER_ID,
            query = "SELECT DISTINCT c FROM Chat c WHERE c.sender.id = :senderId OR c.recipient.id = :senderId ORDER BY createdAt DESC")
@NamedQuery(name = FIND_CHAT_BY_SENDER_ID_AND_RECEIVER_ID,
            query = "SELECT DISTINCT c FROM Chat c WHERE (c.sender.id = :senderId AND c.recipient.id = :recipientId) OR  (c.sender.id = :recipientId AND c.recipient.id = :senderId)")
public class Chat extends BaseAuditingEntity {
    @Id
    @GeneratedValue(strategy = UUID)
    private String id;
    @ManyToOne
    @JoinColumn(name = "sender_id")
    private User sender;
    @ManyToOne
    @JoinColumn(name = "recipient_id")
    private User recipient;
    @OneToMany(mappedBy = "chat", fetch = FetchType.EAGER)
    @OrderBy("createdAt DESC")
    private List<Message> messages;

    @Transient
    public String getChatName(final String senderId){
        if (recipient.getId().equals(senderId)){
            return sender.getFirstName() + " " + sender.getLastName();
        }
        return recipient.getFirstName() + " " + recipient.getLastName();
    }

    @Transient
    public long getUnreadMessages(final String senderId){
        return messages.stream()
                .filter(m -> m.getReceiverId().equals(senderId))
                .filter(m -> m.getState() == MessageState.SENT)
                .count();
    }

    @Transient
    public String getLastMessage(){
        if (messages != null && !messages.isEmpty()){
            if (messages.get(0).getType() != MessageType.TEXT){
                return "Attachment";
            }
            return messages.get(0).getContent();
        }
        return null;
    }

    @Transient
    public LocalDateTime getLastMessageTime(){
        if (messages != null && !messages.isEmpty()){
            return messages.get(0).getCreatedAt();
        }
        return null;
    }
}

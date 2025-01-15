package com.zel.whatsappclone.user;

import com.zel.whatsappclone.chat.Chat;
import com.zel.whatsappclone.common.BaseAuditingEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

import static com.zel.whatsappclone.constants.Constants.LAST_ACTIVE_INTERVAL;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User extends BaseAuditingEntity {
    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private LocalDateTime lastSeen;
    @OneToMany(mappedBy = "sender")
    private List<Chat> chatsAsSender;
    @OneToMany(mappedBy = "recipient")
    private List<Chat> chatsAsRecipient;

    @Transient
    public boolean isUserOnline(){
        //lastSeen = 10:05
        //now 10:09 --> online
        //now 10:12 --> offline
        return lastSeen != null && lastSeen.isAfter(LocalDateTime.now().minusMinutes(LAST_ACTIVE_INTERVAL));
    }
}

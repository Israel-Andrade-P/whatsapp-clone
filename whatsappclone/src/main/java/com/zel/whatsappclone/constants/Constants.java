package com.zel.whatsappclone.constants;

public class Constants {
    public static final String[] WHITE_LIST = {
            "/auth/**",
            "/v2/api-docs",
            "/v3/api-docs",
            "/v3/api-docs/**",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui/**",
            "/webjars/**",
            "/swagger-ui.html",
            "/ws/**"
    };
    public static final int LAST_ACTIVE_INTERVAL = 5;
    public static final String FIND_USER_BY_EMAIL = "Users.findUserByEmail";
    public static final String FIND_ALL_USERS_EXCEPT_SELF = "Users.findAllUsersExceptSelf";
    public static final String FIND_USER_BY_PUBLIC_ID = "Users.findUserByPublicId";
    public static final String FIND_MESSAGES_BY_CHAT_ID = "Messages.findMessagesByChatId";
    public static final String SET_MESSAGES_TO_SEEN_BY_CHAT = "Messages.setMessagesToSeenByChat";
    public static final String FIND_CHAT_BY_SENDER_ID = "Chat.findChatBySenderId";
    public static final String FIND_CHAT_BY_SENDER_ID_AND_RECEIVER_ID = "Chat.findChatBySenderIdAndReceiverId";

    private Constants(){}
}

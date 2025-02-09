package com.finpoints.bss.common.requester;

public interface CurrentRequesterService {

    Requester currentRequester();

    default String currentAppId() {
        return currentRequester().getAppId();
    }
    
    default String currentUserId() {
        return currentRequester().getUserId();
    }

    default String currentUserRole() {
        return currentRequester().getRole();
    }

    default String currentRequesterIp() {
        return currentRequester().getIp();
    }

}

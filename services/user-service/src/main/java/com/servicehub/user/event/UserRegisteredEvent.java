package com.servicehub.user.event;

public class UserRegisteredEvent {

    private Long userId;
    private String email;

    public UserRegisteredEvent(Long userId, String email) {
        this.userId = userId;
        this.email = email;
    }

    public Long getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }
}
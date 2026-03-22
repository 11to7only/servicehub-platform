package com.servicehub.booking.event;

public class UserRegisteredEvent {

    private Long userId;
    private String email;

    public Long getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }
}
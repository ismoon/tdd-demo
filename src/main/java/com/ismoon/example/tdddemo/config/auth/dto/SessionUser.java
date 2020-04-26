package com.ismoon.example.tdddemo.config.auth.dto;

import com.ismoon.example.tdddemo.domain.user.User;
import lombok.Getter;

@Getter
public class SessionUser {
    private String name;

    private String email;

    private String picture;

    public SessionUser(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.picture = user.getPicture();
    }
}

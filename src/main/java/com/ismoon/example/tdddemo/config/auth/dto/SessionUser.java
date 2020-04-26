package com.ismoon.example.tdddemo.config.auth.dto;

import com.ismoon.example.tdddemo.domain.user.User;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class SessionUser implements Serializable {

    private static final long serialVersionUID = -7146547511083676317L;

    private String name;

    private String email;

    private String picture;

    public SessionUser(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.picture = user.getPicture();
    }
}

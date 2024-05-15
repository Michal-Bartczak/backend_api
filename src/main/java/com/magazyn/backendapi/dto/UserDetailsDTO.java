package com.magazyn.backendapi.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Collection;

@Getter
@Setter
public class UserDetailsDTO {
    private String username;
    private Collection<String> roles;

    public UserDetailsDTO(String username, Collection<String> roles) {
        this.username = username;
        this.roles = roles;
    }
}

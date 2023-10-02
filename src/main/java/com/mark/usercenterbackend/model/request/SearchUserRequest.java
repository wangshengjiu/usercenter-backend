package com.mark.usercenterbackend.model.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class SearchUserRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    private String username;
}

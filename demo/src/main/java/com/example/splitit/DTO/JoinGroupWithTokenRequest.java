package com.example.splitit.DTO;

import lombok.Data;

@Data
public class JoinGroupWithTokenRequest {

    private long userid;
    private String joinToken;
    private String role;
}

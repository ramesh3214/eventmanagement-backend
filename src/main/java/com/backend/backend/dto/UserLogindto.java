package com.backend.backend.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserLogindto {

    private String token;
    private UUID id;
    private String name;
    private String email;
    private String role;
    private Integer createdat;
}

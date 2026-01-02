package com.backend.backend.dto;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Userpasswordchange {
  private String email;
  @NotBlank(message="new password required")
  private String newpass;
  
}

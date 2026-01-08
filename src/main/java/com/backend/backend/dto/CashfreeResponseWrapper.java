package com.backend.backend.dto;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CashfreeResponseWrapper {
  private String status;
  private String message;
  private Map<String, Object> data;
}

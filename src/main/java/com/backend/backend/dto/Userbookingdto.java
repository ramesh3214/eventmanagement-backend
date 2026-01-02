package com.backend.backend.dto;

import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Userbookingdto {
  
  private String eventName;
  private Long eventPrice;
  @JsonProperty("eventimage") // ✅ map lowercase JSON
  private String eventImage;
  
  private Instant bookingDate; // ISO-8601 compatible
  private String status;

  
}

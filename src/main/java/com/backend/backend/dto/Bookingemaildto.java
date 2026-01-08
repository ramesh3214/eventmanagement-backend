package com.backend.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Bookingemaildto {
  private String email;
  private String date;
  private String name;
  private String eventname;
  
}

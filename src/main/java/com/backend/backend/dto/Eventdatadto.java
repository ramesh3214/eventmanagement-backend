package com.backend.backend.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Eventdatadto {

  

  private long id;

  private String name;
  private String category;
  private String imagelink;

  private String deadline;

  private long price;

  private String description;

  private String openingtime;
  private String closingtime;

  private long seat;

  private List<String> highlights;
}

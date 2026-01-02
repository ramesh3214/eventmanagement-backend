package com.backend.backend.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventaddDto {

  @NotBlank(message = "name must not be empty")
  private String name;

  @NotBlank(message = "category must not be empty")
  private String category;

  @NotBlank(message = "image link must not be empty")
  private String imagelink;

  @NotBlank(message = "deadline must not be empty")
  private String deadline;

  @NotNull(message = "price must not be null")
  @Min(value = 1, message = "price must be greater than 0")
  private long price;

  @NotBlank(message = "description must not be empty")
  private String description;

  @NotBlank(message = "opening time must not be empty")
  private String openingtime;

  @NotBlank(message = "closing time must not be empty")
  private String closingtime;

  @Min(value = 1, message = "seat must be greater than 0")
  private long seat;

  @Size(min = 1, message = "at least one highlight is required")
  private String[] highligts;
}

package com.backend.backend.entity;

import java.time.Instant;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "bookingdata")
public class Bookingentity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  private UUID userId; // ✅ UUID type (correct)

  private Long eventId;

  private String userName;
  private String userEmail;

  private String eventName;
  private Long eventPrice;
  private String eventImage;

  private Instant bookingDate; // ISO-8601 compatible
  private String status;

}

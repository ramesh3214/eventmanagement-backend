package com.backend.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "eventtable")
public class Eventdata {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  private String name;
  private String category;
  private String imagelink;
  private String deadline;
  private long price;

  @Column(length = 1000)
  private String description;

  private String openingtime;
  private String closingtime;
  private long seat;

  @ElementCollection
  @CollectionTable(
    name = "event_highlights",
    joinColumns = @JoinColumn(name = "event_id")
  )
  @Column(name = "highlight")
  private List<String> highligts;
}

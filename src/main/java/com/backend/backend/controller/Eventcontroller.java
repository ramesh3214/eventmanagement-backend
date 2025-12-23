package com.backend.backend.controller;

import org.springframework.web.bind.annotation.*;

import com.backend.backend.dto.Adduserdto;
import com.backend.backend.dto.Userdto;
import com.backend.backend.service.Userservice;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController

@RequiredArgsConstructor
public class Eventcontroller {

  private final Userservice userservice;

  @PostMapping("/signup")
  public ResponseEntity<Userdto> signup(
      @Valid @RequestBody Adduserdto addUserDto) {

    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(userservice.createnewuser(addUserDto));
  }

  @GetMapping("/health")
  public String health() {
    return "API is working";
  }
}

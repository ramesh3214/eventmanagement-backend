package com.backend.backend.controller;

import org.springframework.web.bind.annotation.*;

import com.backend.backend.dto.Adduserdto;
import com.backend.backend.dto.Bookingdto;
import com.backend.backend.dto.EventaddDto;
import com.backend.backend.dto.Eventdatadto;
import com.backend.backend.dto.Googlelogindto;
import com.backend.backend.dto.Updateuser;
import com.backend.backend.dto.UserLogindto;
import com.backend.backend.dto.Userbookingdto;
import com.backend.backend.dto.Userdto;
import com.backend.backend.dto.Userlogindata;
import com.backend.backend.dto.Userpasswordchange;
import com.backend.backend.repository.Eventrepo;
import com.backend.backend.service.Bookingservice;
import com.backend.backend.service.Eventservice;
import com.backend.backend.service.Userservice;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.jspecify.annotations.Nullable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

@RestController

@RequiredArgsConstructor
public class Eventcontroller {

  private final Userservice userservice;
  private final Eventservice eventservice;
  private final Bookingservice bookingservice;

  @PostMapping("/signup")
  public ResponseEntity<Userdto> signup(
      @Valid @RequestBody Adduserdto addUserDto) {

    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(userservice.createnewuser(addUserDto));
  }

  @PostMapping("/signin")

  public ResponseEntity<UserLogindto> signin(@RequestBody @Valid Userlogindata userlogindata) {
    return ResponseEntity.status(HttpStatus.OK).body(userservice.isvalid(userlogindata));

  }

  @PutMapping("/passwordchange")
  public ResponseEntity<String> updatePassword(
      @RequestBody Userpasswordchange dto

  ) {
    String email = dto.getEmail(); // comes from JWT
    userservice.passwordchange(email, dto.getNewpass());
    return ResponseEntity.ok("Password updated successfully");
  }

  @PutMapping("/user/{id}")
  public ResponseEntity<Userdto> updateuser(
      @RequestBody Updateuser userdto,
      @PathVariable UUID id) {

    return ResponseEntity.ok(userservice.updateuserdetail(userdto, id));
  }

  @CrossOrigin(origins = "http://localhost:5173")
  @PostMapping("/auth/google")
  public ResponseEntity<UserLogindto> googleLogin(@RequestBody Googlelogindto request) {

    return ResponseEntity.ok(userservice.googleLogin(request));
  }

  @GetMapping("/admin/user")
  public ResponseEntity<List<Userdto>> getallstudent() {
    return ResponseEntity.ok(userservice.getstudentall());
  }

  @GetMapping("/event")

  public ResponseEntity<List<Eventdatadto>> getAllEvent(
      @RequestParam(required = false) String category,
      @RequestParam(required = false) String sort) {

    return ResponseEntity.ok(
        eventservice.getevent(category, sort));
  }

  @PostMapping("/event")
  public ResponseEntity<List<Eventdatadto>> addevent(@Valid @RequestBody List<EventaddDto> eventadd) {
    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(eventservice.addevent(eventadd));

  }

  @GetMapping("/event/{id}")
  public ResponseEntity<Eventdatadto> geteventbyid(@PathVariable Long id) {
    return ResponseEntity.ok(eventservice.geteventbyId(id));
  }

  @PutMapping("/event/{id}")
  public ResponseEntity<Void> updateSeat(@PathVariable Long id) {
    eventservice.eventseatupdate(id);
    return ResponseEntity.noContent().build();
  }

  @PostMapping("/booking")
  public ResponseEntity<Bookingdto> createbooking(@Valid @RequestBody Bookingdto bookingdto) {
    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(bookingservice.createnewuser(bookingdto));

  }

  @GetMapping("/booking")
  public ResponseEntity<List<Userbookingdto>> getbookingbyid(
      @RequestParam UUID id) {

    return ResponseEntity.ok(bookingservice.getbooking(id));
  }

  @GetMapping("/health")
  public String health() {
    return "API is working";
  }
}

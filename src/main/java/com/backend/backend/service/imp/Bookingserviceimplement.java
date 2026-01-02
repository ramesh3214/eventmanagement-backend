package com.backend.backend.service.imp;

import java.time.Instant;
import java.util.UUID;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.backend.backend.dto.Bookingdto;
import com.backend.backend.dto.Userbookingdto;
import com.backend.backend.entity.Bookingentity;
import com.backend.backend.repository.Bookingrepo;
import com.backend.backend.service.Bookingservice;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class Bookingserviceimplement implements Bookingservice {

  private final Bookingrepo bookingrepo;

  // ✅ CREATE BOOKING
  @Override
  public Bookingdto createnewuser(Bookingdto bookingdto) {

    Bookingentity newbooking = new Bookingentity();

    // USER (UUID)
    newbooking.setUserId(bookingdto.getUserId());

    // EVENT
    newbooking.setEventId(bookingdto.getEventId());
    newbooking.setEventName(bookingdto.getEventName());
    newbooking.setEventPrice(bookingdto.getEventPrice());
    newbooking.setEventImage(bookingdto.getEventImage());

    // USER INFO
    newbooking.setUserName(bookingdto.getUserName());
    newbooking.setUserEmail(bookingdto.getUserEmail());
    

    // META
    newbooking.setBookingDate(
        bookingdto.getBookingDate() != null
            ? bookingdto.getBookingDate()
            : Instant.now());
    newbooking.setStatus(bookingdto.getStatus());
   

    Bookingentity savedBooking = bookingrepo.save(newbooking);


    return new Bookingdto(
        savedBooking.getUserId(),
        savedBooking.getEventId(),
        savedBooking.getUserName(),
        savedBooking.getUserEmail(),
        savedBooking.getEventName(),
        savedBooking.getEventPrice(),
        savedBooking.getEventImage(),
        savedBooking.getBookingDate(),
        savedBooking.getStatus());
  }

  // ✅ GET BOOKING BY USER UUID
  @Override
  public List<Userbookingdto> getbooking(UUID id) {

    List<Bookingentity> bookings = bookingrepo.findAllByUserId(id);

    if (bookings.isEmpty()) {
      throw new ResponseStatusException(
          HttpStatus.NOT_FOUND,
          "No bookings found for this user");
    }

    return bookings.stream()
        .map(b -> new Userbookingdto(
            b.getEventName(),
            b.getEventPrice(),
            b.getEventImage(),
            b.getBookingDate(),
            b.getStatus()))
        .toList();
  }

}

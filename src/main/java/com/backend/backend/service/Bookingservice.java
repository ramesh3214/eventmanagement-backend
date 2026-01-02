package com.backend.backend.service;

import java.util.List;
import java.util.UUID;

import org.jspecify.annotations.Nullable;

import com.backend.backend.dto.Bookingdto;
import com.backend.backend.dto.Userbookingdto;

import jakarta.validation.Valid;

public interface Bookingservice {

  @Nullable
  Bookingdto createnewuser(Bookingdto bookingdto);

  @Nullable
 List<Userbookingdto> getbooking(UUID id);
  
}

package com.backend.backend.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.backend.dto.Userbookingdto;
import com.backend.backend.entity.Bookingentity;

public interface Bookingrepo extends JpaRepository<Bookingentity,Long> {

 

 List<Bookingentity> findAllByUserId(UUID userId);


  
}

package com.backend.backend.repository;

import java.util.List;

import org.jspecify.annotations.Nullable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.backend.entity.Eventdata;
import com.backend.backend.entity.User;

public interface Eventrepo extends  JpaRepository<Eventdata,Long> {

  List<Eventdata> findByCategoryIgnoreCase(String category);

  

  
  
}

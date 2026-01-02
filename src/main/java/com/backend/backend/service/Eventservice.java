package com.backend.backend.service;

import java.util.List;

import org.jspecify.annotations.Nullable;

import com.backend.backend.dto.EventaddDto;
import com.backend.backend.dto.Eventdatadto;

public interface Eventservice {
  List<Eventdatadto> getevent(String category,String sort);

  @Nullable
  List<Eventdatadto> addevent(List<EventaddDto> eventadd);

  @Nullable
  Eventdatadto geteventbyId(Long id);

  
  void eventseatupdate(Long id);
  
}

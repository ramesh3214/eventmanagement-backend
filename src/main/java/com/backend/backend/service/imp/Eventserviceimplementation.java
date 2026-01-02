package com.backend.backend.service.imp;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Service;

import com.backend.backend.dto.EventaddDto;
import com.backend.backend.dto.Eventdatadto;
import com.backend.backend.entity.Eventdata;
import com.backend.backend.repository.Bookingrepo;
import com.backend.backend.repository.Eventrepo;
import com.backend.backend.service.Eventservice;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class Eventserviceimplementation implements Eventservice {

    private final Eventrepo eventrepo;
    private final Bookingrepo bookingrepo;

    // ✅ GET EVENTS (FILTER + SORT)
    @Override
    public List<Eventdatadto> getevent(String category, String sort) {

        List<Eventdata> events;

        // 🔹 FILTER
        if (category == null || category.trim().isEmpty() || category.equalsIgnoreCase("all")) {
            events = eventrepo.findAll();
        } else {
            events = eventrepo.findByCategoryIgnoreCase(category);
        }

        // 🔹 SORT
        if (sort != null && !sort.trim().isEmpty()) {
            switch (sort.toLowerCase()) {
                case "low" ->
                    events.sort(Comparator.comparing(Eventdata::getPrice));
                case "high" ->
                    events.sort(Comparator.comparing(Eventdata::getPrice).reversed());
                case "date" ->
                    events.sort(Comparator.comparing(Eventdata::getDeadline));
            }
        }

        // 🔹 MAP TO DTO
        return events.stream()
            .map(this::mapToDto)
            .toList();
    }

    // ✅ ADD EVENTS
    @Override
    public List<Eventdatadto> addevent(List<EventaddDto> eventaddList) {

        List<Eventdata> events = eventaddList.stream().map(dto -> {
            Eventdata event = new Eventdata();

            event.setName(dto.getName());
            event.setCategory(dto.getCategory());
            event.setImagelink(dto.getImagelink());
            event.setDeadline(dto.getDeadline());
            event.setPrice(dto.getPrice());
            event.setDescription(dto.getDescription());
            event.setOpeningtime(dto.getOpeningtime());
            event.setClosingtime(dto.getClosingtime());
            event.setSeat(dto.getSeat());
            event.setHighligts(List.of(dto.getHighligts()));

            return event;
        }).toList();

        return eventrepo.saveAll(events)
            .stream()
            .map(this::mapToDto)
            .toList();
    }

    // ✅ GET EVENT BY ID
    @Override
    public Eventdatadto geteventbyId(Long id) {

        Eventdata event = eventrepo.findById(id)
            .orElseThrow(() -> new RuntimeException("Event not found with id: " + id));

        return mapToDto(event);
    }

    // 🔁 COMMON DTO MAPPER (BEST PRACTICE)
    private Eventdatadto mapToDto(Eventdata e) {
        return new Eventdatadto(
            e.getId(),
            e.getName(),
            e.getCategory(),
            e.getImagelink(),
            e.getDeadline(),
            e.getPrice(),
            e.getDescription(),
            e.getOpeningtime(),
            e.getClosingtime(),
            e.getSeat(),
            e.getHighligts()
        );
    }

    public  void eventseatupdate(Long id){

        Eventdata event=eventrepo.findById(id) .orElseThrow(() -> new RuntimeException("Event not found with id: " + id));

        if(event.getSeat()<=0){
             throw new RuntimeException("No seats available");
        }

        event.setSeat(event.getSeat()-1);
        eventrepo.save(event);



    }
}

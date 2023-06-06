package dk.logb.spring.boot.swagger.demo.event.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dk.logb.spring.boot.swagger.demo.event.model.Event;

@Service
public class EventService {
    //a stub event service class

    static int nextId = 0;
    static Map<Integer, Event> events = new HashMap<Integer, Event>();

    //add some events to the map
    static {
        events.put(1, new Event(nextId++, "Copenhagen Arena", "Bryan Adams", java.time.LocalDate.of(2021, 9, 1)));
        events.put(2, new Event(nextId++, "Forum", "Rammstein", java.time.LocalDate.of(2022, 6, 15)));
        events.put(3, new Event(nextId++, "Globen", "Rammstein", java.time.LocalDate.of(2022, 6, 16)));
        events.put(4, new Event(nextId++, "Globen", "Rammstein", java.time.LocalDate.of(2022, 6, 18)));
        events.put(5, new Event(nextId++, "Globen", "Rammstein", java.time.LocalDate.of(2022, 6, 19)));
        events.put(6, new Event(nextId++, "Globen", "Rammstein", java.time.LocalDate.of(2022, 6, 21)));
        events.put(7, new Event(nextId++, "Globen", "Rammstein", java.time.LocalDate.of(2022, 6, 22)));
        events.put(8, new Event(nextId++, "Globen", "Rammstein", java.time.LocalDate.of(2022, 6, 24)));
        events.put(9, new Event(nextId++, "Globen", "Rammstein", java.time.LocalDate.of(2022, 6, 25)));
        events.put(10, new Event(nextId++, "Globen", "Rammstein", java.time.LocalDate.of(2022, 6, 27)));
        events.put(11, new Event(nextId++, "Globen", "Rammstein", java.time.LocalDate.of(2022, 6, 28)));
        events.put(12, new Event(nextId++, "Globen", "Rammstein", java.time.LocalDate.of(2022, 6, 30)));
        events.put(13, new Event(nextId++, "Globen", "Rammstein", java.time.LocalDate.of(2022, 7, 1)));
        events.put(14, new Event(nextId++, "Globen", "Rammstein", java.time.LocalDate.of(2022, 7, 3)));
    }


    public List<Event> getAllEvents() {
        List<Event> events = new ArrayList<Event>();
        events.addAll(this.events.values());
        return events;
    }

    public Event getEvent(Integer id) {
        return (Event) events.get(id);
    }

    public void addEvent(Event event) {
        event.setId(nextId);
        events.put(nextId++, event);
    }

    public void updateEvent(Integer id, Event event) {
        event.setId(id);
        events.put(id, event);
    }

    //remove
    public void deleteEvent(Integer id) {
        events.remove(id);
    }


}

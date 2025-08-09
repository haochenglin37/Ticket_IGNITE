package com.example.ticketing.repository;

import com.example.ticketing.model.Event;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.springframework.stereotype.Repository;

@Repository
public class EventRepository {

    private final IgniteCache<Long, Event> cache;

    public EventRepository(Ignite ignite) {
        this.cache = ignite.getOrCreateCache("events");
    }

    public void save(Event event) {
        cache.put(event.getId(), event);
    }

    public Event findById(Long id) {
        return cache.get(id);
    }
}

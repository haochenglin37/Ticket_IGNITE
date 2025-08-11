package com.example.ticketing.repository;

import com.example.ticketing.model.Event;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteAtomicSequence;
import org.apache.ignite.cache.query.ScanQuery;
import javax.cache.Cache;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Repository;

@Repository
public class EventRepository {

    private final IgniteCache<Long, Event> cache;
    private final IgniteAtomicSequence idSeq;

    public EventRepository(Ignite ignite) {
        this.cache = ignite.getOrCreateCache("events");
        this.idSeq = ignite.atomicSequence("eventIds", 0, true);
    }

    public void save(Event event) {
        cache.put(event.getId(), event);
    }

    public Event findById(Long id) {
        return cache.get(id);
    }

    public long nextId() {
        return idSeq.incrementAndGet();
    }

    public List<Event> findAll() {
        return cache.query(new ScanQuery<Long, Event>()).getAll().stream()
                .map(Cache.Entry::getValue)
                .collect(Collectors.toList());
    }
}

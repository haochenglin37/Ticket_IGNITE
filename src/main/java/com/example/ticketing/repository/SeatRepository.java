package com.example.ticketing.repository;

import com.example.ticketing.model.Seat;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.springframework.stereotype.Repository;

import javax.cache.Cache;
import java.util.ArrayList;
import java.util.List;

@Repository
public class SeatRepository {

    private final IgniteCache<Long, Seat> cache;

    public SeatRepository(Ignite ignite) {
        this.cache = ignite.getOrCreateCache("seats");
    }

    public void save(Seat seat) {
        cache.put(seat.getId(), seat);
    }

    public Seat findById(Long id) {
        return cache.get(id);
    }

    public List<Seat> findByEventId(Long eventId) {
        List<Seat> seats = new ArrayList<>();
        for (Cache.Entry<Long, Seat> entry : cache) {
            Seat seat = entry.getValue();
            if (seat.getEventId().equals(eventId)) {
                seats.add(seat);
            }
        }
        return seats;
    }
}

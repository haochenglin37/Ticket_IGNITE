package com.example.ticketing.repository;

import com.example.ticketing.model.Seat;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.springframework.stereotype.Repository;

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
}

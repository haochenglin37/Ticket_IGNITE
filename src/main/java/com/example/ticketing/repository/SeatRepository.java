package com.example.ticketing.repository;

import com.example.ticketing.model.Seat;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.atomic.IgniteAtomicSequence;
import org.apache.ignite.cache.query.ScanQuery;
import javax.cache.Cache;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Repository;

@Repository
public class SeatRepository {

    private final IgniteCache<Long, Seat> cache;
    private final IgniteAtomicSequence idSeq;

    public SeatRepository(Ignite ignite) {
        this.cache = ignite.getOrCreateCache("seats");
        this.idSeq = ignite.atomicSequence("seatIds", 0, true);
    }

    public void save(Seat seat) {
        cache.put(seat.getId(), seat);
    }

    public Seat findById(Long id) {
        return cache.get(id);
    }

    public long nextId() {
        return idSeq.incrementAndGet();
    }

    public List<Seat> findByEventId(Long eventId) {
        return cache.query(new ScanQuery<Long, Seat>((k, v) -> v.getEventId().equals(eventId)))
                .getAll().stream()
                .map(Cache.Entry::getValue)
                .collect(Collectors.toList());
    }
}

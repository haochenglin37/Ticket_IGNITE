package com.example.ticketing.repository;

import com.example.ticketing.model.Ticket;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.springframework.stereotype.Repository;

@Repository
public class TicketRepository {

    private final IgniteCache<Long, Ticket> cache;

    public TicketRepository(Ignite ignite) {
        this.cache = ignite.getOrCreateCache("tickets");
    }

    public void save(Ticket ticket) {
        cache.put(ticket.getId(), ticket);
    }

    public Ticket findById(Long id) {
        return cache.get(id);
    }
}

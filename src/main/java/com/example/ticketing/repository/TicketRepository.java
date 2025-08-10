package com.example.ticketing.repository;

import com.example.ticketing.model.Ticket;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.springframework.stereotype.Repository;

import javax.cache.Cache;
import java.util.ArrayList;
import java.util.List;


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

    public void delete(Long id) {
        cache.remove(id);
    }

    public Ticket findByEventIdAndSeatId(Long eventId, Long seatId) {
        for (Cache.Entry<Long, Ticket> entry : cache) {
            Ticket ticket = entry.getValue();
            if (ticket.getEventId().equals(eventId) && ticket.getSeatId().equals(seatId)) {
                return ticket;
            }
        }
        return null;
    }

    public List<Ticket> findByCustomer(String customer) {
        List<Ticket> tickets = new ArrayList<>();
        for (Cache.Entry<Long, Ticket> entry : cache) {
            Ticket ticket = entry.getValue();
            if (customer.equals(ticket.getCustomer())) {
                tickets.add(ticket);
            }
        }
        return tickets;
    }
}

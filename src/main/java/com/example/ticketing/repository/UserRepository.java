package com.example.ticketing.repository;

import com.example.ticketing.model.User;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.atomic.IgniteAtomicSequence;
import org.apache.ignite.cache.query.ScanQuery;
import org.springframework.stereotype.Repository;

import javax.cache.Cache;
import java.util.List;

@Repository
public class UserRepository {

    private final IgniteCache<Long, User> cache;
    private final IgniteAtomicSequence idSeq;

    public UserRepository(Ignite ignite) {
        this.cache = ignite.getOrCreateCache("users");
        this.idSeq = ignite.atomicSequence("userIds", 0, true);
    }

    public long nextId() {
        return idSeq.incrementAndGet();
    }

    public void save(User user) {
        cache.put(user.getId(), user);
    }

    public User findByUsername(String username) {
        List<Cache.Entry<Long, User>> entries =
                cache.query(new ScanQuery<Long, User>((k, v) -> username.equals(v.getUsername()))).getAll();
        return entries.isEmpty() ? null : entries.get(0).getValue();
    }
}

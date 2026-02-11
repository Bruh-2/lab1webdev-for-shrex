package org.example.repository;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class InMemoryRateRepository {

    private final Map<String, Double> storage = new HashMap<>();

    public Map<String, Double> getStorage() {
        return storage;
    }
}

package org.example.service;

import org.example.dto.RateDto;
import org.example.exception.DuplicateRateException;
import org.example.exception.ResourceNotFoundException;
import org.example.repository.InMemoryRateRepository;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

@Service
public class ExchangeService {

    private final InMemoryRateRepository repository;
    private final Random random = new Random();

    public ExchangeService(InMemoryRateRepository repository) {
        this.repository = repository;
    }

    public Map<String, Double> findAll(String filter) {

        Map<String, Double> allRates = repository.getStorage();

        if (filter == null || filter.isBlank()) {
            return allRates;
        }

        Map<String, Double> filtered = new LinkedHashMap<>();

        allRates.forEach((key, value) -> {
            if (key.startsWith(filter.toUpperCase())) {
                filtered.put(key, value);
            }
        });

        return filtered;
    }

    public Double findByCode(String code) {

        Double value = repository.getStorage().get(code);

        if (value == null) {
            throw new ResourceNotFoundException("Currency not found: " + code);
        }

        return value;
    }

    public void create(RateDto dto) {

        if (repository.getStorage().containsKey(dto.getCurrency())) {
            throw new DuplicateRateException("Currency already exists");
        }

        repository.getStorage().put(dto.getCurrency(), dto.getRate());
    }

    public void update(String code, Double newRate) {

        if (!repository.getStorage().containsKey(code)) {
            throw new ResourceNotFoundException("Currency not found: " + code);
        }

        repository.getStorage().put(code, newRate);
    }

    public void delete(String code) {

        if (!repository.getStorage().containsKey(code)) {
            throw new ResourceNotFoundException("Currency not found: " + code);
        }

        repository.getStorage().remove(code);
    }

    public boolean shouldFail() {
        return random.nextBoolean();
    }
}

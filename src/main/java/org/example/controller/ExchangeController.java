package org.example.controller;

import jakarta.validation.Valid;
import org.example.dto.ApiResult;
import org.example.dto.RateDto;
import org.example.exception.RandomFailureException;
import org.example.service.ExchangeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/rates")
public class ExchangeController {

    private final ExchangeService exchangeService;

    public ExchangeController(ExchangeService exchangeService) {
        this.exchangeService = exchangeService;
    }

    @GetMapping
    public ResponseEntity<ApiResult<Map<String, Double>>> listRates(
            @RequestParam(required = false) String filter,
            @RequestHeader(value = "User-Agent", required = false) String agent
    ) {
        Map<String, Double> result = exchangeService.findAll(filter);
        return ResponseEntity.ok(new ApiResult<>(result));
    }

    @GetMapping("/{code}")
    public ResponseEntity<ApiResult<Double>> getSingleRate(@PathVariable String code) {
        Double value = exchangeService.findByCode(code);
        return ResponseEntity.ok(new ApiResult<>(value));
    }

    @PostMapping
    public ResponseEntity<String> createRate(@Valid @RequestBody RateDto dto) {
        exchangeService.create(dto);
        return ResponseEntity.ok("Created successfully");
    }

    @PutMapping("/{code}")
    public ResponseEntity<String> modifyRate(
            @PathVariable String code,
            @Valid @RequestBody RateDto dto
    ) {
        exchangeService.update(code, dto.getRate());
        return ResponseEntity.ok("Updated successfully");
    }

    @DeleteMapping("/{code}")
    public ResponseEntity<String> removeRate(@PathVariable String code) {

        if (exchangeService.shouldFail()) {
            throw new RandomFailureException("Random delete failure occurred");
        }

        exchangeService.delete(code);
        return ResponseEntity.ok("Deleted successfully");
    }

    @ExceptionHandler(RandomFailureException.class)
    public ResponseEntity<String> handleRandom(RandomFailureException ex) {
        return ResponseEntity.status(500).body(ex.getMessage());
    }
}

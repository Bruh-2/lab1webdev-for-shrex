package org.example.dto;

import java.time.LocalDateTime;
import java.util.Map;

public class ApiResult<T> {

    private T data;
    private int count;
    private LocalDateTime timestamp;

    public ApiResult(T data) {
        this.data = data;
        this.timestamp = LocalDateTime.now();

        if (data instanceof Map) {
            this.count = ((Map<?, ?>) data).size();
        } else {
            this.count = 1;
        }
    }

    public T getData() {
        return data;
    }

    public int getCount() {
        return count;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}

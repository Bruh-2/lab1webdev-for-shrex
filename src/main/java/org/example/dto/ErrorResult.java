package org.example.dto;

import java.time.LocalDateTime;

public class ErrorResult {

    private String error;
    private int status;
    private LocalDateTime time;

    public ErrorResult(String error, int status) {
        this.error = error;
        this.status = status;
        this.time = LocalDateTime.now();
    }

    public String getError() { return error; }
    public int getStatus() { return status; }
    public LocalDateTime getTime() { return time; }
}

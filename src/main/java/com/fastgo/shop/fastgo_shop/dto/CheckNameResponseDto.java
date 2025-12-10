package com.fastgo.shop.fastgo_shop.dto;

public class CheckNameResponseDto {
    private boolean available;
    private String message;

    public CheckNameResponseDto(boolean available, String message) {
        this.available = available;
        this.message = message;
    }

    // Getters e Setters
    public boolean isAvailable() { return available; }
    public void setAvailable(boolean available) { this.available = available; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}
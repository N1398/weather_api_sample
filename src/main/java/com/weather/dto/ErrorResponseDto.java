package com.weather.dto;

public class ErrorResponseDto {
    private Error error;

    public static class Error {
        private int code;
        private String message;

        // Геттеры и сеттеры
        public int getCode() { return code; }
        public void setCode(int code) { this.code = code; }
        public String getMessage() { return message; }
        public void setMessage(String message) { this.message = message; }
    }

    // Геттеры и сеттеры
    public Error getError() { return error; }
    public void setError(Error error) { this.error = error; }
}
package com.weather.dto;

public class CurrentWeatherDto {
    private Location location;
    private Current current;

    public static class Location {
        private String name;
        private String country;
        private double lat;
        private double lon;

        // Геттеры и сеттеры
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public String getCountry() { return country; }
        public void setCountry(String country) { this.country = country; }
        public double getLat() { return lat; }
        public void setLat(double lat) { this.lat = lat; }
        public double getLon() { return lon; }
        public void setLon(double lon) { this.lon = lon; }
    }

    public static class Current {
        private double temp_c;
        private double temp_f;
        private Condition condition;

        // Геттеры и сеттеры
        public double getTemp_c() { return temp_c; }
        public void setTemp_c(double temp_c) { this.temp_c = temp_c; }
        public double getTemp_f() { return temp_f; }
        public void setTemp_f(double temp_f) { this.temp_f = temp_f; }
        public Condition getCondition() { return condition; }
        public void setCondition(Condition condition) { this.condition = condition; }
    }

    public static class Condition {
        private String text;
        private int code;

        // Геттеры и сеттеры
        public String getText() { return text; }
        public void setText(String text) { this.text = text; }
        public int getCode() { return code; }
        public void setCode(int code) { this.code = code; }
    }

    // Геттеры и сеттеры для основного класса
    public Location getLocation() { return location; }
    public void setLocation(Location location) { this.location = location; }
    public Current getCurrent() { return current; }
    public void setCurrent(Current current) { this.current = current; }
}
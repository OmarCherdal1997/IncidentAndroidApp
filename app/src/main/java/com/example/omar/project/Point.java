package com.example.omar.project;

public class Point {
    private Double latitude;
    private Double longitude;
    public Point(Double lat,Double longitude){
        this.latitude=lat;
        this.longitude=longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }
}

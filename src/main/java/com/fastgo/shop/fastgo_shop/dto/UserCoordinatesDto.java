package com.fastgo.shop.fastgo_shop.dto;

public class UserCoordinatesDto {

    private double latitude;
    private double longitude;

    private double rangeInKm; 

   

    public UserCoordinatesDto() {
    }

    public double getLatitude() {
        return latitude;
    }
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
    public double getLongitude() {
        return longitude;
    }
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
    public double getRangeInKm() {
        
        if (rangeInKm <= 0) {
            return 10.0; 
        }
        return rangeInKm;
    }
    public void setRangeInKm(double rangeInKm) {
        this.rangeInKm = rangeInKm;
    }
}
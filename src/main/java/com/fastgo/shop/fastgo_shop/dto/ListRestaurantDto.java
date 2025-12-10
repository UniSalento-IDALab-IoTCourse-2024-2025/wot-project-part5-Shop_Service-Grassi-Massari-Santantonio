package com.fastgo.shop.fastgo_shop.dto;

import java.util.List;

public class ListRestaurantDto {
    
    private List<RestaurantDto> restaurants;

    public List<RestaurantDto> getRestaurants() {
        return restaurants;
    }
    public void setRestaurants(List<RestaurantDto> restaurants) {
        this.restaurants = restaurants;
    }
}

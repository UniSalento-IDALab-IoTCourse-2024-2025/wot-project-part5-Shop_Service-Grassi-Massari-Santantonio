package com.fastgo.shop.fastgo_shop.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.geo.Distance;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.fastgo.shop.fastgo_shop.domain.Restaurant;

import org.springframework.data.geo.Point;

@Repository
public interface RestaurantRepository extends MongoRepository<Restaurant, String> {
    
    Optional<Restaurant> findByShopkeeperId(String shopkeeperId);
    List<Restaurant> findByLocationNear(Point location, Distance distance);
}
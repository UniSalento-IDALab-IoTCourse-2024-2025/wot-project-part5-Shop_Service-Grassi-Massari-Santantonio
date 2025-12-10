package com.fastgo.shop.fastgo_shop.repositories;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.fastgo.shop.fastgo_shop.domain.Menu;

@Repository
public interface MenuRepository extends MongoRepository<Menu, String> {
    
    Optional<Menu> findByShopId(String shopId);
}
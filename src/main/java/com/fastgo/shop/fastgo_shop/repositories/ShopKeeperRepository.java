package com.fastgo.shop.fastgo_shop.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.fastgo.shop.fastgo_shop.domain.Shopkeeper;

@Repository
public interface ShopKeeperRepository extends MongoRepository<Shopkeeper, String> {
    
}

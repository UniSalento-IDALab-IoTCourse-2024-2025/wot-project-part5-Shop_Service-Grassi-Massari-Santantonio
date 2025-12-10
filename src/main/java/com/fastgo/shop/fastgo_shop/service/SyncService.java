package com.fastgo.shop.fastgo_shop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fastgo.shop.fastgo_shop.dto.ShopKeeperDto;
import com.fastgo.shop.fastgo_shop.security.JwtUtilities;

@Service
public class SyncService {
    

    @Autowired
    private JwtUtilities jwtUtilities;

    @Autowired
    private ShopKeeperService shopKeeperService;

    @Autowired
    private RestaurantService restaurantService;
    

    public boolean isTokenValidForShopkeeper(String token) {
        return jwtUtilities.hasRoleShop(token);
    }

    
    public String saveShopKeeper(ShopKeeperDto shopKeeperDto) {
        
        if(shopKeeperService.shopkeeperExists(shopKeeperDto.getId())) {
              return " Shopkeeper already exists";
            }
        
        if(shopKeeperService.saveShopKeeper(shopKeeperDto) && restaurantService.saveRestaurant(shopKeeperDto)) {
            return "OK";

        }
        
        return "Error saving shopkeeper or restaurant";
    }
}

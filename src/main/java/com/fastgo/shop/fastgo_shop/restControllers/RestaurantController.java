package com.fastgo.shop.fastgo_shop.restControllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fastgo.shop.fastgo_shop.dto.RestaurantDto;
import com.fastgo.shop.fastgo_shop.dto.UserCoordinatesDto;
import com.fastgo.shop.fastgo_shop.service.RestaurantService;
import com.fastgo.shop.fastgo_shop.service.ShopKeeperService;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/restaurants")
public class RestaurantController {
    
    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private ShopKeeperService shopKeeperService;


    @PutMapping(value ="update", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> putMethodName(@RequestHeader("Authorization") String token, @RequestBody RestaurantDto restaurantDto) {
        

        token = token.replace("Bearer ", "");
        if(!shopKeeperService.isTokenValidForShopkeeper(token)){
            return ResponseEntity.status(401).body("Invalid token for shopkeeper");
        }

        String idShopKeeper = shopKeeperService.getShopkeeperIdFromToken(token);
        ResponseEntity<?> entity = restaurantService.updateRestaurant(restaurantDto, idShopKeeper);
        
        return entity;
    }

    @PostMapping(value ="/nearby", consumes = "application/json", produces = "application/json")
    public ResponseEntity<List<RestaurantDto>> findNearbyRestaurants(
            @RequestBody UserCoordinatesDto coordsDto) {
                System.out.println("Received coordinates: Lat " + coordsDto.getLatitude() + ", Lon " + coordsDto.getLongitude() + ", Range " + coordsDto.getRangeInKm() + " km");
        List<RestaurantDto> nearbyRestaurants = restaurantService.findNearbyRestaurants(coordsDto);
        
        return ResponseEntity.ok(nearbyRestaurants);
    }

    @GetMapping(value ="/{id}", produces = "application/json")
public ResponseEntity<RestaurantDto> getRestaurantById(@PathVariable String id) {
    
    
    RestaurantDto restaurant = restaurantService.findRestaurantById(id);
    
    if (restaurant != null) {
        return ResponseEntity.ok(restaurant);
    } else {
        return ResponseEntity.notFound().build(); 
    }
}

@GetMapping(value ="/my-restaurant", produces = "application/json")
public ResponseEntity<?> getMyRestaurant(@RequestHeader("Authorization") String token) {
    
    token = token.replace("Bearer ", "");
    if (!shopKeeperService.isTokenValidForShopkeeper(token)) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token for shopkeeper");
    }

    String idShopKeeper = shopKeeperService.getShopkeeperIdFromToken(token);

    RestaurantDto restaurant = restaurantService.findRestaurantByShopkeeperId(idShopKeeper);
    
    if (restaurant != null) {
        return ResponseEntity.ok(restaurant);
    } else {
        return ResponseEntity.notFound().build(); 
    }
}

}
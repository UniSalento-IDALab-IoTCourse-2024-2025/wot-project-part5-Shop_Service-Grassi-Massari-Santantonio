package com.fastgo.shop.fastgo_shop.restControllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fastgo.shop.fastgo_shop.dto.ShopKeeperInfoDto;
import com.fastgo.shop.fastgo_shop.service.ShopKeeperService;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/shopkeeper")
public class ShopKeeperProfileController {

    @Autowired
    private ShopKeeperService shopKeeperService;

    private String extractToken(String bearerToken) {
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        throw new IllegalArgumentException("Token Bearer not valid");
    }

    @GetMapping("/profile")
    public ResponseEntity<?> getMyProfile(@RequestHeader("Authorization") String bearerToken) {
        try {
            String token = extractToken(bearerToken);

            if (!shopKeeperService.isTokenValidForShopkeeper(token)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token not valid");
            }

            String shopkeeperId = shopKeeperService.getShopkeeperIdFromToken(token);

            ShopKeeperInfoDto profile = shopKeeperService.getShopkeeperProfile(shopkeeperId);
            return ResponseEntity.ok(profile);

        } catch (IllegalArgumentException e) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (RuntimeException e) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/profile")
    public ResponseEntity<?> updateMyProfile(
            @RequestHeader("Authorization") String bearerToken,
            @RequestBody ShopKeeperInfoDto profileDto) {

        try {
            String token = extractToken(bearerToken);

            if (!shopKeeperService.isTokenValidForShopkeeper(token)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token not valid");
            }

            String shopkeeperIdFromToken = shopKeeperService.getShopkeeperIdFromToken(token);

            ShopKeeperInfoDto updatedProfile = shopKeeperService.updateShopkeeper(shopkeeperIdFromToken, profileDto);
            return ResponseEntity.ok(updatedProfile);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }


     @GetMapping("/picture")
    public ResponseEntity<?> getMyProfilePicture(@RequestHeader("Authorization") String bearerToken) {
        try {
            String token = extractToken(bearerToken);

            if (!shopKeeperService.isTokenValidForShopkeeper(token)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token not valid");
            }

            String shopkeeperId = shopKeeperService.getShopkeeperIdFromToken(token);

            return ResponseEntity.ok(shopKeeperService.getShopkeeperProfilePicure(shopkeeperId));

        } catch (IllegalArgumentException e) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (RuntimeException e) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

}
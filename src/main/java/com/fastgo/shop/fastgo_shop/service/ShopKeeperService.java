package com.fastgo.shop.fastgo_shop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fastgo.shop.fastgo_shop.domain.Shopkeeper;
import com.fastgo.shop.fastgo_shop.dto.ProfilePictureDto;
import com.fastgo.shop.fastgo_shop.dto.ShopKeeperDto;
import com.fastgo.shop.fastgo_shop.dto.ShopKeeperInfoDto;
import com.fastgo.shop.fastgo_shop.repositories.ShopKeeperRepository;
import com.fastgo.shop.fastgo_shop.security.JwtUtilities;

@Service
public class ShopKeeperService {
    

    @Autowired
    private ShopKeeperRepository shopKeeperRepository;

    @Autowired
    private JwtUtilities jwtUtilities;

    public boolean shopkeeperExists(String shopkeeperId) {
        return shopKeeperRepository.existsById(shopkeeperId);
    }

    public boolean saveShopKeeper(ShopKeeperDto shopKeeperDto) {
        
        try{
            Shopkeeper shopkeeper = new Shopkeeper();
            
            shopkeeper.setId(shopKeeperDto.getId());
            shopkeeper.setName(shopKeeperDto.getName());
            shopkeeper.setLastName(shopKeeperDto.getLastName());
            shopkeeper.setUsername(shopKeeperDto.getUsername());
            shopkeeper.setPassword(shopKeeperDto.getPassword());
            shopkeeper.setEmail(shopKeeperDto.getEmail());
            shopkeeper.setStatus(shopKeeperDto.getStatus());

            shopKeeperRepository.save(shopkeeper);

            return true;

        }catch(Exception e){
            return false;
        }
       
    }

    public boolean isTokenValidForShopkeeper(String token) {
        return jwtUtilities.hasRoleShop(token);
    }

    public String getShopkeeperIdFromToken(String token) {
        return jwtUtilities.extractUserId(token);
    }

    public ShopKeeperInfoDto updateShopkeeper(String shopkeeperIdFromToken, ShopKeeperInfoDto dtoToUpdate) {
    
    
    if (!shopkeeperIdFromToken.equals(dtoToUpdate.getId())) {
        throw new SecurityException("Unathorized.");
    }

    
    Shopkeeper existingShopkeeper = shopKeeperRepository.findById(shopkeeperIdFromToken)
            .orElseThrow(() -> new RuntimeException("Shopkeeper not found with id: " + shopkeeperIdFromToken));

    
    existingShopkeeper.setName(dtoToUpdate.getName());
    existingShopkeeper.setLastName(dtoToUpdate.getLastName());
    existingShopkeeper.setEmail(dtoToUpdate.getEmail());
    

    Shopkeeper savedShopkeeper = shopKeeperRepository.save(existingShopkeeper);
    
    return toDto(savedShopkeeper);
}

    public ShopKeeperInfoDto getShopkeeperProfile(String shopkeeperId) {
    Shopkeeper shopkeeper = shopKeeperRepository.findById(shopkeeperId)
            .orElseThrow(() -> new RuntimeException("Shopkeeper not found with id: " + shopkeeperId));
    
    return toDto(shopkeeper);
}

    public ProfilePictureDto getShopkeeperProfilePicure(String shopkeeperId) {
    Shopkeeper shopkeeper = shopKeeperRepository.findById(shopkeeperId)
            .orElseThrow(() -> new RuntimeException("Shopkeeper not found with id: " + shopkeeperId));
    ProfilePictureDto dto = new ProfilePictureDto();
    dto.setProfilePicture(shopkeeper.getProfilePicture());
    return dto;
}

private ShopKeeperInfoDto toDto(Shopkeeper entity) {
    ShopKeeperInfoDto dto = new ShopKeeperInfoDto();
    dto.setId(entity.getId());
    dto.setName(entity.getName());
    dto.setLastName(entity.getLastName());
    dto.setUsername(entity.getUsername());
    dto.setEmail(entity.getEmail());
    dto.setStatus(entity.getStatus());
    
    return dto;
}
}
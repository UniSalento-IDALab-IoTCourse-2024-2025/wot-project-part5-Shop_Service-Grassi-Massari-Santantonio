package com.fastgo.shop.fastgo_shop.dto;

public class SyncShopDto {
    
    private String token;
    private ShopKeeperDto shop;

    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }
    public ShopKeeperDto getShop() {
        return shop;
    }
    public void setShop(ShopKeeperDto shop) {
        this.shop = shop;
    }
}

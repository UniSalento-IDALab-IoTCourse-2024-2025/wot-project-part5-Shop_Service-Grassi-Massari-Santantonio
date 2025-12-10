package com.fastgo.shop.fastgo_shop.dto;

import java.util.List;


public class MenuDto {
    

    private String id;
    private String shopId;
    private List<MenuItemDto> items;

    public MenuDto() {
    }

    // --- Getters e Setters ---

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public List<MenuItemDto> getItems() {
        return items;
    }

    public void setItems(List<MenuItemDto> items) {
        this.items = items;
    }
}
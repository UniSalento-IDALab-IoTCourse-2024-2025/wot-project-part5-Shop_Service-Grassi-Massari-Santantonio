package com.fastgo.shop.fastgo_shop.domain;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("menu")
public class Menu {
    
    @Id
    private String id;
    private String shopId;
    private List<MenuItem> items;

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
    public List<MenuItem> getItems() {
        return items;
    }
    public void setItems(List<MenuItem> items) {
        this.items = items;
    }
}

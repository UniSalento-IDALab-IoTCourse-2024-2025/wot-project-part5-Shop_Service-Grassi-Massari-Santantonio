package com.fastgo.shop.fastgo_shop.dto;

public class CheckItemNameRequestDto {
    private String menuId;
    private String itemName;

    // Getters e Setters
    public String getMenuId() { return menuId; }
    public void setMenuId(String menuId) { this.menuId = menuId; }
    public String getItemName() { return itemName; }
    public void setItemName(String itemName) { this.itemName = itemName; }
}
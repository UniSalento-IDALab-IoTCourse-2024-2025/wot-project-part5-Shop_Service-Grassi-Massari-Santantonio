package com.fastgo.shop.fastgo_shop.dto;

public class UpdateMenuItemRequestDto {

    private String menuId;      
    private MenuItemDto item;   

    
    public UpdateMenuItemRequestDto() {
    }

    
    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public MenuItemDto getItem() {
        return item;
    }

    public void setItem(MenuItemDto item) {
        this.item = item;
    }
}
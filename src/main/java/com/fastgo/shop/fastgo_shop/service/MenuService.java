package com.fastgo.shop.fastgo_shop.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fastgo.shop.fastgo_shop.domain.Menu;
import com.fastgo.shop.fastgo_shop.domain.MenuItem;
import com.fastgo.shop.fastgo_shop.dto.MenuDto;
import com.fastgo.shop.fastgo_shop.dto.MenuItemDto;
import com.fastgo.shop.fastgo_shop.exception.ItemNameConflictException;
import com.fastgo.shop.fastgo_shop.repositories.MenuRepository;

@Service
public class MenuService {

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private ShopKeeperService shopKeeperService;

    public boolean tokenCheck(String token, MenuDto menuDto) {

        String idShopKeeper = restaurantService.getIdShopKeeper(menuDto.getShopId());

        return (idShopKeeper.equals(shopKeeperService.getShopkeeperIdFromToken(token))
                && shopKeeperService.isTokenValidForShopkeeper(token));
    }

    public MenuItemDto toDto(MenuItem entity) {
        if (entity == null) {
            return null;
        }

        MenuItemDto dto = new MenuItemDto();
        dto.setName(entity.getName());
        dto.setPrice(entity.getPrice());
        dto.setDescription(entity.getDescription());
        dto.setCategory(entity.getCategory());
        dto.setImageUrl(entity.getImageUrl());
        return dto;
    }

    public MenuDto toDto(Menu entity) {

        if (entity == null) {
            return null;
        }

        MenuDto dto = new MenuDto();
        dto.setId(entity.getId());
        dto.setShopId(entity.getShopId());

        if (entity.getItems() != null) {
            List<MenuItemDto> itemDtos = entity.getItems().stream()
                    .map(this::toDto)
                    .collect(Collectors.toList());
            dto.setItems(itemDtos);
        }

        return dto;
    }

    public MenuItem toEntity(MenuItemDto dto) {
        if (dto == null) {
            return null;
        }

        MenuItem entity = new MenuItem();
        entity.setName(dto.getName());
        entity.setPrice(dto.getPrice());
        entity.setDescription(dto.getDescription());
        entity.setCategory(dto.getCategory());
        entity.setImageUrl(dto.getImageUrl());
        return entity;
    }

    public Menu toEntity(MenuDto dto) {
        if (dto == null) {
            return null;
        }

        Menu entity = new Menu();
        entity.setId(dto.getId());
        entity.setShopId(dto.getShopId());

        if (dto.getItems() != null) {
            List<MenuItem> items = dto.getItems().stream()
                    .map(this::toEntity)
                    .collect(Collectors.toList());
            entity.setItems(items);
        }

        return entity;
    }

    public Menu findMenuById(String id) {
        return menuRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Menu with id: " + id + " not found"));
    }

    public MenuDto updateMenu( MenuDto menuDto) {
        Menu existingMenu = findMenuById(menuDto.getId());

        if (menuDto.getItems() != null) {
            List<MenuItem> newItems = menuDto.getItems().stream()
                    .map(this::toEntity)
                    .collect(Collectors.toList());

            existingMenu.setItems(newItems);
        } else {
            existingMenu.getItems().clear();
        }

        Menu savedMenu = menuRepository.save(existingMenu);

        return toDto(savedMenu);
    }

    public MenuDto updateMenuItem(String menuId, MenuItemDto itemDto) {

        Menu existingMenu = findMenuById(menuId);

        String itemName = itemDto.getName();
        if (itemName == null) {
            throw new IllegalArgumentException("Item name cannot be null for an update");
        }

        MenuItem itemToUpdate = existingMenu.getItems().stream()
                .filter(item -> itemName.equals(item.getName()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Item '" + itemName + "' not found " + menuId));

        itemToUpdate.setPrice(itemDto.getPrice());
        itemToUpdate.setDescription(itemDto.getDescription());
        itemToUpdate.setCategory(itemDto.getCategory());
        itemToUpdate.setImageUrl(itemDto.getImageUrl());

        Menu savedMenu = menuRepository.save(existingMenu);

        return toDto(savedMenu);
    }


    public MenuDto createMenu(MenuDto menuDto) {
    Menu menu = toEntity(menuDto);

    menu.setId(null); 
    
    if (menu.getItems() == null) {
        menu.setItems(new java.util.ArrayList<>());
    }

    Menu savedMenu = menuRepository.save(menu);
    return toDto(savedMenu);
}

public MenuDto addItemToMenu(String menuId, MenuItemDto itemDto) {

    Menu menu = findMenuById(menuId); 

    String newItemName = itemDto.getName();
    if (newItemName == null || newItemName.isBlank()) {
        throw new IllegalArgumentException("Item name cannot be empty");
    }

    boolean nameExists = menu.getItems().stream()
            .anyMatch(item -> newItemName.equals(item.getName()));

    if (nameExists) {
        throw new ItemNameConflictException("Item name '" + newItemName + "' exist yet.");
    }

    MenuItem newItem = toEntity(itemDto);
    menu.getItems().add(newItem);

    Menu savedMenu = menuRepository.save(menu);
    return toDto(savedMenu);
}

public MenuDto getMenuByShopId(String shopId) {
        Menu menu = menuRepository.findByShopId(shopId)
                .orElseThrow(() -> new RuntimeException("Menu not found for shopId: " + shopId));
        
        return toDto(menu);
    }

public MenuDto deleteMenuItem(String menuId, String itemName) {

        Menu menu = findMenuById(menuId);

        MenuItem itemToRemove = menu.getItems().stream()
                .filter(item -> itemName.equals(item.getName()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Item '" + itemName + "' not found in menu " + menuId));

        menu.getItems().remove(itemToRemove);

        Menu savedMenu = menuRepository.save(menu);
        return toDto(savedMenu);
    }
}

package com.fastgo.shop.fastgo_shop.restControllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fastgo.shop.fastgo_shop.domain.Menu;
import com.fastgo.shop.fastgo_shop.dto.CheckItemNameRequestDto;
import com.fastgo.shop.fastgo_shop.dto.CheckNameResponseDto;
import com.fastgo.shop.fastgo_shop.dto.DeleteItemRequestDto;
import com.fastgo.shop.fastgo_shop.dto.MenuDto;
import com.fastgo.shop.fastgo_shop.dto.MenuItemDto;
import com.fastgo.shop.fastgo_shop.dto.UpdateMenuItemRequestDto;
import com.fastgo.shop.fastgo_shop.exception.ItemNameConflictException;
import com.fastgo.shop.fastgo_shop.service.MenuService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/menu")
public class MenuRestController {

    @Autowired
    private MenuService menuService;

    private String extractToken(String bearerToken) {
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        throw new IllegalArgumentException("Token Bearer not valid");
    }

    @PatchMapping("/update/item")
    public ResponseEntity<?> updateMenuItem(
            @RequestHeader("Authorization") String bearerToken,
            @RequestBody UpdateMenuItemRequestDto requestDto) {

        try {
            String token = extractToken(bearerToken);

            String menuId = requestDto.getMenuId();
            MenuItemDto itemDto = requestDto.getItem();

            if (menuId == null || itemDto == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("menuId e item cannot be null");
            }

            Menu existingMenu = menuService.findMenuById(menuId);
            MenuDto menuForCheck = menuService.toDto(existingMenu);

            if (!menuService.tokenCheck(token, menuForCheck)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authorization failed");
            }

            MenuDto updatedMenu = menuService.updateMenuItem(menuId, itemDto);
            return ResponseEntity.ok(updatedMenu);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/update/menu")
    public ResponseEntity<?> updateFullMenu(
            @RequestHeader("Authorization") String bearerToken,
            @RequestBody MenuDto menuDto) {

        try {
            String token = extractToken(bearerToken);

            if (!menuService.tokenCheck(token, menuDto)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Authorization failed");
            }

            MenuDto updatedMenu = menuService.updateMenu(menuDto);
            return ResponseEntity.ok(updatedMenu);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping("/create")
    public ResponseEntity<?> createNewMenu(
            @RequestHeader("Authorization") String bearerToken,
            @RequestBody MenuDto menuDto) {

        try {
            String token = extractToken(bearerToken);

            if (!menuService.tokenCheck(token, menuDto)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authorization failed");
            }

            MenuDto createdMenu = menuService.createMenu(menuDto);

            return ResponseEntity.status(HttpStatus.CREATED).body(createdMenu);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (RuntimeException e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/item/add")
    public ResponseEntity<?> addNewMenuItem(
            @RequestHeader("Authorization") String bearerToken,
            @RequestBody UpdateMenuItemRequestDto requestDto) {

        try {
            String token = extractToken(bearerToken);

            String menuId = requestDto.getMenuId();
            MenuItemDto itemDto = requestDto.getItem();

            if (menuId == null || itemDto == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("menuId e item cannot be null");
            }

            Menu existingMenu = menuService.findMenuById(menuId);
            MenuDto menuForCheck = menuService.toDto(existingMenu);

            if (!menuService.tokenCheck(token, menuForCheck)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authorization failed");
            }

            MenuDto updatedMenu = menuService.addItemToMenu(menuId, itemDto);
            return ResponseEntity.ok(updatedMenu);

        } catch (ItemNameConflictException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping("/item/check-name")
    public ResponseEntity<?> checkItemNameAvailability(
            @RequestHeader("Authorization") String bearerToken,
            @RequestBody CheckItemNameRequestDto requestDto) {

        try {
            String token = extractToken(bearerToken);
            String menuId = requestDto.getMenuId();
            String itemName = requestDto.getItemName();

            if (menuId == null || itemName == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("menuId e itemName cannot be null");
            }

            Menu existingMenu = menuService.findMenuById(menuId);
            MenuDto menuForCheck = menuService.toDto(existingMenu);

            if (!menuService.tokenCheck(token, menuForCheck)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authorization failed");
            }

            boolean nameExists = existingMenu.getItems().stream()
                    .anyMatch(item -> itemName.equals(item.getName()));

            if (nameExists) {
                return ResponseEntity.ok(new CheckNameResponseDto(false, "Name already used"));
            } else {
                return ResponseEntity.ok(new CheckNameResponseDto(true, "Name available"));
            }

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/by-shop/{shopId}")
    public ResponseEntity<?> getMenuForCustomer(@PathVariable String shopId) {
        try {
            MenuDto menu = menuService.getMenuByShopId(shopId);
            return ResponseEntity.ok(menu);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/item/delete")
    public ResponseEntity<?> deleteMenuItem(
            @RequestHeader("Authorization") String bearerToken,
            @RequestBody DeleteItemRequestDto requestDto) {
        
        try {
            String token = extractToken(bearerToken);
            String menuId = requestDto.getMenuId();
            String itemName = requestDto.getItemName();

            if (menuId == null || itemName == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                     .body("menuId e itemName cannot be null");
            }

            
            Menu existingMenu = menuService.findMenuById(menuId);
            MenuDto menuForCheck = menuService.toDto(existingMenu);
            
            if (!menuService.tokenCheck(token, menuForCheck)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authorization failed");
            }

            MenuDto updatedMenu = menuService.deleteMenuItem(menuId, itemName);
            return ResponseEntity.ok(updatedMenu);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (RuntimeException e) { 
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e
            .getMessage());
        }
    }
}

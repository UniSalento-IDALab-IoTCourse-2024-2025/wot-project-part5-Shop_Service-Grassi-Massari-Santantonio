package com.fastgo.shop.fastgo_shop.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT) 
public class ItemNameConflictException extends RuntimeException {
    public ItemNameConflictException(String message) {
        super(message);
    }
}
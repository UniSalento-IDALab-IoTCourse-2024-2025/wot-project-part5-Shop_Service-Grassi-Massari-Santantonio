package com.fastgo.shop.fastgo_shop.dto;


import com.fastgo.shop.fastgo_shop.domain.Role;
import com.fastgo.shop.fastgo_shop.domain.State;


public class ShopKeeperInfoDto {
    private String id;
    private String name;
    private String lastName;
    private String username;
    private String email;
    private Role role = Role.SHOPKEEPER;
    private State status;
    

    


    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public Role getRole() {
        return role;
    }
   
    public State getStatus() {
        return status;
    }
    public void setStatus(State status) {
        this.status = status;
    }

    

}
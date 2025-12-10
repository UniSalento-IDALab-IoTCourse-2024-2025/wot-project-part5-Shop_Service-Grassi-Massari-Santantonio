package com.fastgo.shop.fastgo_shop.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("shopkeepers")
public class Shopkeeper {
    
    @Id
    private String id;
    private String name;
    private String lastName;
    private String username;
    private String password;
    private String email;
    private Role role = Role.SHOPKEEPER;
    private State status;
    private String profilePicture;

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

    public String getPassword(){
        return password;
    }
    public void setPassword(String password){
        this.password = password;
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
    public String getProfilePicture() {
        return profilePicture;
    }
    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }
}

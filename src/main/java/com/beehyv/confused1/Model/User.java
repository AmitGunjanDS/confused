package com.beehyv.confused1.Model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;


@Entity
public class User {


    @Id
    private String username;
    private String name;
    private String password;
    private String email;
    private String phone;
    @Column(length = 1, nullable = false)
    private boolean active = true;
    private byte[] image;


    @Column(nullable = false, columnDefinition = "varchar(10) default 'USER'")
    private String role = "USER";


    public User(String name, String username, String password, String email, String phone) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.email = email;
        this.phone = phone;
    }


    public User() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getRole() {
        return role;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void assignAdmin() {
        this.role = "ADMIN";
    }
}

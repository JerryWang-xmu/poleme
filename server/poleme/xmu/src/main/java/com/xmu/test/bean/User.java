package com.xmu.test.bean;

public class User {
    private String id;
    private String username;
    private String password;
    private String email;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    private String phone;
    private String role;
    private boolean state;


    public User() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean setState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }



    public User(String username, String password,String phone, String email, String role, boolean state) {
        this.username = username;
        this.password = password;
        this.phone=phone;
        this.email = email;
        this.role = role;
        this.state = state;
    }


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", role='" + role + '\'' +
                ", state=" + state +
                '}';
    }
}



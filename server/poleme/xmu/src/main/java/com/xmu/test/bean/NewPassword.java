package com.xmu.test.bean;

public class NewPassword {
    int id;
    String password;
    String newpassword;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNewpassword() {
        return newpassword;
    }

    public void setNewpassword(String newpassword) {
        this.newpassword = newpassword;
    }

    @Override
    public String toString() {
        return "NewPassword{" +
                "id=" + id +
                ", password='" + password + '\'' +
                ", newpassword='" + newpassword + '\'' +
                '}';
    }
}

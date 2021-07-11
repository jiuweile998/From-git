package com.test;

public class User {

    private  String userName;
    private  String password;
    private String userphone;
    private int userShelf;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserphone() {
        return userphone;
    }

    public void setUserphone(String userphone) {
        this.userphone = userphone;
    }

    public int getUserShelf() {
        return userShelf;
    }

    public void setUserShelf(int userShelf) {
        this.userShelf = userShelf;
    }

    public User() {
    }

    public User(String userName, String password, String userphone, int userShelf) {
        this.userName = userName;
        this.password = password;
        this.userphone = userphone;
        this.userShelf = userShelf;
    }

    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", userphone='" + userphone + '\'' +
                ", userShelf=" + userShelf +
                '}';
    }
}

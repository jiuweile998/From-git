package com.example.springbootdemo.model;

/**
 * @author wlg
 * @create_time 2021-07-02 上午 10:16
 * @function
 */
public class Admin_book {

    private String username;
    private String password;

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

    public Admin_book() {
    }

    public Admin_book(String username, String password) {
        this.username = username;
        this.password = password;
    }
}

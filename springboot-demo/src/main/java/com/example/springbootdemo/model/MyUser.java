package com.example.springbootdemo.model;

/**
 * @author wlg
 * @create_time 2021-06-21 下午 18:30
 * @function
 */

public class MyUser {
    public String userName;
    public String userPassword;
    //private Integer userShelf;
    public String userPhone;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public MyUser() {
    }

    public MyUser(String userName, String userPassword, String userPhone) {
        this.userName = userName;
        this.userPassword = userPassword;
        this.userPhone = userPhone;
    }
}

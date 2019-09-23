package com.luo.entity;

import java.util.List;

public class User {

    private int userId;

    private String userName;

    private List<User> stringList;
    public User(){}

    public User(int userId, String userName) {
        this.userId = userId;
        this.userName = userName;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<User> getStringList() {
        return stringList;
    }

    public void setStringList(List<User> stringList) {
        this.stringList = stringList;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                '}';
    }
}

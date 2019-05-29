package com.cluo.mybatis.pojo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

public class User implements Serializable{
    private Integer userId;
    private String userName;
    private Integer age;
    private String phone;
    private String desc;

    @Setter
    @Getter
    private String str;
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", age=" + age +
                ", phone='" + phone + '\'' +
                ", desc='" + desc + '\'' +
                ", str='" + str + '\'' +
                '}';
    }
}

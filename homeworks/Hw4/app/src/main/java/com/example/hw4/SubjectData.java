package com.example.hw4;

import java.io.Serializable;

// 序列化才可以在兩個Activity傳遞
class SubjectData implements Serializable {
    String name;
    String birthday;
    String constellation;
    Integer image;
    public SubjectData(String name, String birthday, String constellation, Integer image) {
        this.name = name;
        this.birthday = birthday;
        this.constellation = constellation;
        this.image = image;
    }
}
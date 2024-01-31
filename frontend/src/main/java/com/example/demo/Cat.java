package com.example.demo;

public class Cat {

    private int id;
    private int age;
    private String name;

    public Cat() {
        this.age = 0;
        this.name = "";
    }

    public Object getId() {
        return id;
    }

    public int getAge() {
        return age;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAge(int age) {
        if (age < 0) {
            throw new IllegalArgumentException("Age cannot be less than 0");
        }
        this.age = age;
    }

    public void setName(String name) {
        this.name = name;
    }
}
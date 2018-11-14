package com.boot.zjy.user.entity;

public class UserEntityKey {
    private Long id;

    private String name;


    private int zone;

    private int totalZone;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public int getZone() {
        return zone;
    }

    public void setZone(int zone) {
        this.zone = zone;
    }

    public int getTotalZone() {
        return totalZone;
    }

    public void setTotalZone(int totalZone) {
        this.totalZone = totalZone;
    }
}
package com.boot.zjy.user.entity;

import java.io.Serializable;
import java.util.Date;

public class UserEntity extends UserEntityKey implements Serializable{
    private Date createTime;

    private Integer age;

    public UserEntity(int zone,int totalZone){
        super.setZone(zone);
        super.setTotalZone(totalZone);

    }

    public UserEntity(){
        this.createTime=new Date();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
package com.boot.zjy.controller;
import com.boot.zjy.master.mapper.UserEntityMapper;
import com.boot.zjy.user.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by jiayizhang on 2018/10/29.
 */
@RestController
public class UserController {
    @Autowired
    private UserEntityMapper userEntityMapper;

    @RequestMapping(value="/addUser.do",method = RequestMethod.POST)
    public int addUser(UserEntity user){
        int i=0;
        if(user!=null){

            i= userEntityMapper.insert(user);
        }

        return i;
    }

    @RequestMapping(value="/hello.do",method = RequestMethod.GET)
    public String hello(UserEntity user){


        return "hellp";
    }
}

package com.xmu.test.controller;

import com.xmu.test.bean.User;
import com.xmu.test.dao.UserDao;
import com.xmu.test.util.Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegisterController {
    @Autowired
    UserDao userDao;

    @CrossOrigin
    @RequestMapping("/registername")
    public String userRegistercheck(@RequestBody User user) {
        String str = "error";
        int count = userDao.getUserNumByUsername(user.getUsername());
        if (count == 0) {
            str = "ok";
        }
        return str;
    }

    @CrossOrigin
    @RequestMapping("/register")
    public String userRegister(@RequestBody User user) {
        String str = "error";
        int count = userDao.getUserNumByUsername(user.getUsername());
        if (count == 0) {
            int nextId = userDao.getUserMaxId() + 1;
            user.setState(true);
            user.setPassword(Hash.sha1(user.getPassword()));
            user.setId(nextId + "");
            user.setRole("用户");
            int i = userDao.addUser(user);
            str = i > 0 ? "success" : "fail";
        }
        return str;
    }

}


package com.xmu.test.controller;

import com.alibaba.fastjson.JSON;
import com.xmu.test.bean.NewPassword;
import com.xmu.test.bean.User;
import com.xmu.test.dao.UserDao;
import com.xmu.test.util.Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

@RestController
public class InfoController {
    @Autowired
    UserDao userDao;

    @CrossOrigin
    @RequestMapping("/info")
    public String userInformation(@RequestBody User user) {

        String str = "error";
        User us = new User();
        int count = userDao.getUserCount(user.getId());
        HashMap<String, Object> resMap = new HashMap<>();
        List<User> userList = new LinkedList<>();
        if (count > 0) {
            str = "ok";
            us = userDao.getUserInformation(user.getId());
            userList.add(us);
        }
        resMap.put("str", str);
        resMap.put("user", userList);

        String res = JSON.toJSONString(resMap);
        return res;
    }

    @CrossOrigin
    @RequestMapping("/change")
    public String changeUserInformation(@RequestBody User user) {
        String str = "error";
        User us = new User();
        int count = userDao.getUserCount(user.getId());
        int flag = 0;
        if (count > 0) {
            flag = userDao.changeUserInfor(user);
            if (flag > 0)
                str = "ok";
        }
        return str;
    }

    @CrossOrigin
    @RequestMapping("/secret")
    public String changePassword(@RequestBody NewPassword npw) {
        String str = "error";
        User us = new User();
        npw.setPassword(Hash.sha1(npw.getPassword()));
        npw.setNewpassword(Hash.sha1(npw.getNewpassword()));
        int count = userDao.getSecretCount(npw.getId(), npw.getPassword());
        int flag = 0;
        if (count > 0) {
            flag = userDao.changeSecret(npw);
            if (flag > 0)
                str = "ok";
        }
        return str;
    }

}

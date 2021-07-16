package com.xmu.test.controller;

import com.alibaba.fastjson.JSON;
import com.xmu.test.bean.User;
import com.xmu.test.dao.UserDao;
import com.xmu.test.util.Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;


@RestController
public class LoginController {
    @Autowired
    UserDao userDao;

    @CrossOrigin
    @RequestMapping("/login")
    public String userLogin(@RequestBody User user) {//RequestBody注解去掉头，取数值部分
        int id = 0;               //初始化查询id
        int count = userDao.getUserNumByMassage(user.getUsername(), Hash.sha1(user.getPassword()), user.getRole());  //数据库中符合条件的记录条数
        HashMap<String, String> resMap = new HashMap<>();  //创造键值对，向后端返回signal和用户id
        if (count > 0) {        //当数据库中符合前端发回的查询条件的记录大于0条时
            id = userDao.getUserByMassage(user.getUsername(), Hash.sha1(user.getPassword()));  //必须先做if判断，否则数据库中该内容为空会引发异常
        } else {
            resMap.put("ERROR", "0");
            String res = JSON.toJSONString(resMap);
            return res;     //向前端返回键值对
        }
        if (user.getRole().equals("管理员")) {
            resMap.put("str", "MNOK");
            resMap.put("count", id + "");
            String res = JSON.toJSONString(resMap);
            return res;     //向前端返回键值对
        }
        resMap.put("str", "USOK");
        resMap.put("count", id + "");
        String res = JSON.toJSONString(resMap);
        return res;     //向前端返回键值对
    }
}
package com.xmu.test.controller;

import com.alibaba.fastjson.JSON;
import com.xmu.test.bean.QueryInfo;
import com.xmu.test.bean.User;
import com.xmu.test.dao.UserDao;
import com.xmu.test.util.Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
public class UserListController {

    @Autowired
    private UserDao udao;

    @RequestMapping("/alluser")
    public String getUserList(@RequestBody QueryInfo queryInfo) {
        HashMap<String, Object> resMap = new HashMap<>();
        //获取最大列表数和当前编号
        if (queryInfo.getCurrentId().equals("null")) {
            resMap.put("users", "-1");
            resMap.put("data", "-1");
            String resStr = JSON.toJSONString(resMap);
            return resStr;
        } else if (!udao.isManager(Integer.parseInt(queryInfo.getCurrentId())).equals("管理员")) {
            resMap.put("users", "-1");
            resMap.put("data", "-1");
            String resStr = JSON.toJSONString(resMap);
            return resStr;
        }
        int numbers;
        List<User> users;
        int pageStart = (queryInfo.getPageNum() - 1) * queryInfo.getPageSize();
        if (queryInfo.getQuery().equals("")) {
            numbers = udao.getUserCounts();
            users = udao.getAllUser(pageStart, queryInfo.getPageSize());
        } else {
            numbers = udao.getUserSelectCount(queryInfo.getQuery());
            users = udao.getSelectedUsers(queryInfo.getQuery(), pageStart, queryInfo.getPageSize());
        }

        resMap.put("users", numbers);
        resMap.put("data", users);
        String res_string = JSON.toJSONString(resMap);


        return res_string;

    }

    @RequestMapping("/addUser")
    public String addUser(@RequestBody User user) {
        int nextId = udao.getUserMaxId() + 1;
        user.setPassword(Hash.sha1(user.getPassword()));
        user.setState(true);
        user.setId(nextId + "");
        int i = udao.addUser(user);
        String str = i > 0 ? "success" : "error";
        return str;
    }

    @RequestMapping("/deleteUser")
    public String deleteUser(int id) {
        String id_get = Integer.toString(id);

        int i = udao.deleteUser(id_get);
        String str = i > 0 ? "success" : "error";

        return str;
    }

    @RequestMapping("/revokeUser")
    public String revokeUser(int id) {
        int i = udao.revokeUser(id);
        String str = i > 0 ? "success" : "error";
        return str;
    }

    @RequestMapping("/editUser")
    public String editUser(@RequestBody User user) {
        user.setPassword(Hash.sha1(user.getPassword()));
        int i = udao.editUser(user);
        String str = i > 0 ? "success" : "error";
        return str;
    }

    @RequestMapping("/getUpdate")
    public String getUpdateUser(int id) {
        User updateUser = udao.getUpdateUser(id);
        String identity = updateUser.getId();
        String users_json = JSON.toJSONString(identity);
        return users_json;
    }

}

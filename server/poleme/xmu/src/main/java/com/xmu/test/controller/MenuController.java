package com.xmu.test.controller;

import com.alibaba.fastjson.JSON;
import com.xmu.test.bean.MainMenu;
import com.xmu.test.bean.QueryInfo;
import com.xmu.test.bean.User;
import com.xmu.test.dao.MenuDao;
import com.xmu.test.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
public class MenuController {
    @Autowired
    MenuDao menuDao;
    @Autowired
    UserDao userDao;

    @CrossOrigin
    @RequestMapping("/menus")
    public String getAllMenus(@RequestBody QueryInfo queryInfo) {
        HashMap<String, Object> data = new HashMap<>();
        User user = userDao.getUserInformation(queryInfo.getCurrentId());
        try {
            if (queryInfo.getCurrentId().equals("null")) {
                List<MainMenu> mainMenus = menuDao.getMainMenus();
                data.put("data", mainMenus);
            } else {

                if (user.getRole().equals("管理员")) {
                    List<MainMenu> mainMenus = menuDao.getManagerMainMenus();
                    data.put("data", mainMenus);
                }
                if (user.getRole().equals("用户")) {
                    List<MainMenu> mainMenus = menuDao.getMainMenus();
                    data.put("data", mainMenus);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        data.put("status", 200);
        String data_json = JSON.toJSONString(data);
        return data_json;               //键值对
    }
}
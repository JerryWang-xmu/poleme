package com.xmu.test.controller;

import com.alibaba.fastjson.JSON;
import com.xmu.test.bean.MainMenu;
import com.xmu.test.bean.Menu;
import com.xmu.test.bean.QueryInfo;
import com.xmu.test.bean.User;
import com.xmu.test.dao.MenusDao;
import com.xmu.test.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

@RestController
public class MenusController {
    @Autowired
    MenusDao menusdao;

    @Autowired
    private UserDao udao;

    @CrossOrigin
    @RequestMapping("/allMenu")
    public String getAllMenu(@RequestBody QueryInfo queryInfo){
        HashMap<String, Object> data = new HashMap<>();
        if(queryInfo.getCurrentId().equals("null"))
        {
            data.put("users","-1");
            data.put("data","-1");
            String res_string= JSON.toJSONString(data);
            return res_string;
        }else if(!udao.isManager(Integer.parseInt(queryInfo.getCurrentId())).equals("管理员")){
            data.put("users","-1");
            data.put("data","-1");
            String res_string= JSON.toJSONString(data);
            return res_string;
        }

        int pageStart=(queryInfo.getPageNum()-1)*queryInfo.getPageSize();
        List<Menu> Menus =new LinkedList<Menu>();
        if(queryInfo.getQuery().equals("")){
            Menus = menusdao.getAllMenus(pageStart,queryInfo.getPageSize());
            data.put("data", Menus);
            data.put("total", menusdao.getAllMenuCount());
            System.out.println("all");
        }
        else{
            int count=menusdao.getMenuNameCount(queryInfo.getQuery());
            if(count>0){
                Menus =menusdao.getSelectedMenu(queryInfo.getQuery(), pageStart,queryInfo.getPageSize());
            }
            else Menus=null;
            data.put("data", Menus);
            data.put("total", count);
        }

        String data_json = JSON.toJSONString(data);
        return data_json;
    }

    @CrossOrigin
    @RequestMapping("/addMenu")
    public String addMenu(@RequestBody Menu menu) {
        String str = "error";
        menu.setId(menusdao.getMenuIDCount() + 1);
        int flag = menusdao.addMenus(menu);
        if (flag > 0) {
            str = "ok";
        }

        return str;
    }

    @CrossOrigin
    @RequestMapping("/editMenu")
    public String editMenu(@RequestBody Menu menu) {
        String str = "error";
        int count = menusdao.getMenuCount(menu.getId());
        if (count > 0) {
            int flag = menusdao.editMenu(menu);
            if (flag > 0) {
                str = "ok";
            }
        }

        return str;
    }

    @CrossOrigin
    @RequestMapping("/deleteMenu")
    public String deleteMenu(int id) {
        String str = "error";
        int count = menusdao.getMenuCount(id);
        if (count > 0) {
            int flag = menusdao.deleteMenu(id + "");
            if (flag > 0) {
                str = "ok";
            }
        }
        return str;
    }
    //此段代码已经整合到allMenu中，考虑以后可能有其他用处，故保留
    //0703改为进行另一重搜索
    @RequestMapping("/selectMenu")
    public String selectMenu(@RequestBody QueryInfo queryInfo){
        String str="error";
        HashMap<String, Object> data = new HashMap<>();
        int count=menusdao.getMenuNameCount(queryInfo.getQuery());
        if(count>0){
            List<Menu> selectedList=menusdao.getSelectedMenuWithoutPage(queryInfo.getQuery());
            data.put("flag","ok");
            data.put("data",selectedList);
        }
        String data_json = JSON.toJSONString(data);
        return data_json;
    }

    @CrossOrigin
    @RequestMapping("/foodmenu")
    public String path2menu(@RequestBody QueryInfo queryInfo) {
        String str = "error";
        HashMap<String, Object> data = new HashMap<>();
        List<Menu> list = menusdao.getMenuByPath(queryInfo.getCurrentId());
        if (list.size() > 0) str = "ok";
        data.put("flag", str);
        data.put("data", list);
        String data_json = JSON.toJSONString(data);
        return data_json;
    }

    @CrossOrigin
    @RequestMapping("/id2menu")
    public String id2menu(@RequestBody QueryInfo queryInfo){
        String str="error";
        HashMap<String, Object> data = new HashMap<>();
        List<Menu> list=menusdao.getMenuById(queryInfo.getCurrentMenuId());
        if(list.size()>0)str="ok";
        data.put("flag",str);
        data.put("data",list.get(0));
        String data_json = JSON.toJSONString(data);
        return data_json;
    }

}

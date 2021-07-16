package com.xmu.test.controller;

import com.alibaba.fastjson.JSON;
import com.xmu.test.bean.QueryInfo;
import com.xmu.test.bean.Rank;
import com.xmu.test.dao.MenusDao;
import com.xmu.test.dao.RankDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
public class RankController {
    @Autowired
    RankDao rankDao;
    @Autowired
    MenusDao menusDao;

    @CrossOrigin
    @RequestMapping("/toprank")
    public String getTopRank(){
        HashMap<String,Object> res=new HashMap<>();
        res.put("flag","error");
        List<Rank> data=rankDao.getAllMenuItemForRank().subList(0,3);
        getorder(data);
        if(data.size()>0){
            res.put("data",data);
            res.put("total",menusDao.getAllMenuCount());
            res.replace("flag","ok");
        }
        System.out.println(res);

        return JSON.toJSONString(res);
    }

    @CrossOrigin
    @RequestMapping("/allrank")
    public String getAllRank(@RequestBody QueryInfo queryInfo){
        System.out.println(queryInfo);
        int pageStart=(queryInfo.getPageNum()-1)*queryInfo.getPageSize();
        HashMap<String,Object> res=new HashMap<>();
        res.put("flag","error");
        List<Rank> data=rankDao.getRankMenuItemWithPage(pageStart,queryInfo.getPageSize());
        getorder(data);
        if(data.size()>0){
            res.put("data",data);
            res.put("total",menusDao.getAllMenuCount());
            res.replace("flag","ok");
        }
        //System.out.println(res.get("total"));

        return JSON.toJSONString(res);
    }

    private void getorder(List<Rank> list){
        int i=1;
        for(Rank rank:list){
            rank.setOrder(i++);
        }
    }
}

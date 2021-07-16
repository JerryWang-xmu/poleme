package com.xmu.test.controller;

import com.alibaba.fastjson.JSON;
import com.xmu.test.bean.Menu;
import com.xmu.test.bean.OrderDetails;
import com.xmu.test.bean.Orders;
import com.xmu.test.bean.QueryInfo;
import com.xmu.test.dao.OrdersDao;
import com.xmu.test.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

@RestController
public class OrdersController {
    @Autowired
    OrdersDao ordersDao;
    @Autowired
    UserDao userDao;

    @CrossOrigin
    @RequestMapping("/allOrder")
    public String getAllOrders(@RequestBody QueryInfo queryInfo){
        //System.out.println(Integer.parseInt(queryInfo.getCurrentId()));
        HashMap<String,Object> data=new HashMap<>();
        List<Orders> order=new LinkedList<>();
        int pageStart=(queryInfo.getPageNum()-1)*queryInfo.getPageSize();
        int count=0;
        String aFlag="";
        data.put("data",-1);
        data.put("total",-1);
        if(queryInfo.getCurrentId().equals("null")){
            return JSON.toJSONString(data);
        }
        else aFlag=userDao.isManager(Integer.parseInt(queryInfo.getCurrentId()));
        if(aFlag.equals("管理员")){
            if(queryInfo.getQuery().equals("")){
                count=ordersDao.getOrdersNum();
                if(count>0) {
                    order = ordersDao.getAllOrder(pageStart,queryInfo.getPageSize());
                    data.replace("data",order);
                    data.replace("total",count);
                }
            }
            else{
                count=ordersDao.getOrderSearchNum(Integer.parseInt(queryInfo.getQuery()));
                if(count>0){
                    order=ordersDao.getSearchOrder(Integer.parseInt(queryInfo.getQuery()),pageStart,queryInfo.getPageSize());
                }
                data.replace("data",order);
                data.replace("total",count);
            }
        }
        //System.out.println(data);

        return JSON.toJSONString(data);
    }

    @CrossOrigin
    @RequestMapping("/orderDetails")
    public String getOrderDetails(@RequestBody OrderDetails od){
        //System.out.println(od.getOrderid());
        List<OrderDetails> order=new LinkedList<>();
        HashMap<String,Object> data=new HashMap<>();
        int count=ordersDao.isAvailable(od.getOrderid());
        if(count>0){
            order=ordersDao.getOrderDetails(od.getOrderid());
            data.put("data",order);
        }
        else data.put("data",-1);
        //System.out.println(order);
        return JSON.toJSONString(data);
    }

    @CrossOrigin
    @RequestMapping("/userOrder")
    public String getUserOrders(@RequestBody QueryInfo queryInfo){
        System.out.println(queryInfo);
        HashMap<String,Object> data=new HashMap<>();
        int count=ordersDao.getOrderSearchNum(Integer.parseInt(queryInfo.getCurrentId()));
        if(count>0){
            int pageStart=(queryInfo.getPageNum()-1)*queryInfo.getPageSize();
            List<Orders> orderList=ordersDao.getUserOrder(Integer.parseInt(queryInfo.getCurrentId()),pageStart,queryInfo.getPageSize());
            /*List<Orders> resultList=new LinkedList<>();
            if(orderList.size()>5){
                if(pageStart<orderList.size()&&pageStart+queryInfo.getPageSize()<orderList.size()){
                    resultList=orderList.subList(pageStart,pageStart+queryInfo.getPageSize());
                }
                else if(pageStart<orderList.size()&&pageStart+queryInfo.getPageSize()>orderList.size()){
                    resultList=orderList.subList(pageStart,orderList.size()-1);
                }
                else{
                    resultList=null;
                }
            }
            else{
                resultList=orderList;
            }*/
            data.put("status","ok");
            data.put("orderList",orderList);
            data.put("total",count);
        }
        else {
            data.put("status","error");
        }
        return JSON.toJSONString(data);
    }

    @CrossOrigin
    @RequestMapping("/paymenu")
    public String getOrderImmediately(@RequestBody Orders order){
        System.out.println(order);
        String str="error";
        if(order.getUserid()==0){
            return str;
        }
        int count=ordersDao.getOrdersCount();
        order.setOrderid(count+1);
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        order.setDate(df.format(new Date()));
        int flag=ordersDao.changeMenuImmediately(order);
        if(flag>0){
            int stock=ordersDao.getMenuItemStock(order.getMenuid());
            if(stock>=0){
                flag=ordersDao.insertOrderImmediately(order);
                if(flag>0){
                    str="success";
                }
            }
            else{
                ordersDao.changeStockToZero(order.getMenuid());
            }
        }
        return str;
    }

}

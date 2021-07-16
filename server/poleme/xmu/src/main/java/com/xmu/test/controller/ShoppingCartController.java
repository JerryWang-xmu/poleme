package com.xmu.test.controller;

import com.alibaba.fastjson.JSON;
import com.sun.org.apache.xpath.internal.operations.Or;
import com.xmu.test.bean.Menu;
import com.xmu.test.bean.Orders;
import com.xmu.test.bean.QueryInfo;
import com.xmu.test.bean.ShoppingCart;
import com.xmu.test.dao.MenusDao;
import com.xmu.test.dao.OrdersDao;
import com.xmu.test.dao.ShoppingCartDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@RestController
public class ShoppingCartController {

    @Autowired
    ShoppingCartDao cartDao;

    @Autowired
    MenusDao menusDao;

    @Autowired
    OrdersDao ordersDao;

    class temp_ShoppingCart{
        private String name;
        private String url;
        private double price;
        private int stock;
        private int id;
        private  String selected;

        private int num;

        public temp_ShoppingCart(){}

        public temp_ShoppingCart(String name, String url, double price, int stock, int id, String selected, int num, double totalPriceForEach) {
            this.name = name;
            this.url = url;
            this.price = price;
            this.stock = stock;
            this.id = id;
            this.selected = selected;
            this.num = num;
            this.totalPriceForEach = totalPriceForEach;
        }

        public String getSelected() {
            return selected;
        }

        public void setSelected(String selected) {
            this.selected = selected;
        }

        public double getTotalPriceForEach() {
            return totalPriceForEach;
        }

        public void setTotalPriceForEach(double totalPriceForEach) {
            this.totalPriceForEach = totalPriceForEach;
        }

        private double totalPriceForEach;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public int getStock() {
            return stock;
        }

        public void setStock(int stock) {
            this.stock = stock;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        @Override
        public String toString() {
            return "temp_ShoppingCart{" +
                    "name='" + name + '\'' +
                    ", url='" + url + '\'' +
                    ", price=" + price +
                    ", stock=" + stock +
                    ", id=" + id +
                    ", selected='" + selected + '\'' +
                    ", num=" + num +
                    ", totalPriceForEach=" + totalPriceForEach +
                    '}';
        }
    }


    @CrossOrigin
    @RequestMapping("cartMessage")
    String getShoppingMessage(@RequestBody QueryInfo queryInfo)
    {

        List<ShoppingCart> carts=cartDao.getMessageByUid(queryInfo.getCurrentId());
        HashMap<String,Object> res=new HashMap<>();
        res.put("size",carts.size());
        ArrayList<temp_ShoppingCart> data_list=new ArrayList<>();
        double total=0;
        for (ShoppingCart cart:carts
        ) {
            List<Menu> temp=menusDao.getMenuById(Integer.toString(cart.getMenuid()));
            temp_ShoppingCart temp_cart= new temp_ShoppingCart();
            temp_cart.setId(temp.get(0).getId());
            temp_cart.setName(temp.get(0).getName());
            temp_cart.setUrl(temp.get(0).getImgurl());
            temp_cart.setNum(cart.getNum());
            temp_cart.setPrice(temp.get(0).getPrice());
            temp_cart.setStock(temp.get(0).getStock());
            temp_cart.setTotalPriceForEach(temp.get(0).getPrice()*cart.getNum());
            temp_cart.setSelected(cart.getSelected());
            if(cart.getSelected().equals("true")) total+=temp.get(0).getPrice()*cart.getNum();
            data_list.add(temp_cart);


        }

        res.put("data",data_list);
        res.put("total",total);
        String jsonString= JSON.toJSONString(res);

        return jsonString;

    }

    @CrossOrigin
    @RequestMapping("/shoppingcart")
    public String updateOrder(@RequestBody QueryInfo order)
    {
        String str="error";
        if(order.getCurrentId().equals("null")){
            return str;
        }


        int count=cartDao.getOrderNumByMassage(order.getCurrentId(), order.getCurrentMenuId(), order.getUnitnum());
        if(count>0)
        {
            int i=cartDao.updateOrder(order.getCurrentId(), order.getCurrentMenuId(), order.getUnitnum());
            str = i >0?"success":"error";
        }
        else{
            int i=cartDao.insertOrder(order.getCurrentId(), order.getCurrentMenuId(), order.getUnitnum());
            str = i >0?"success":"error";
        }

        return str;
    }

    @CrossOrigin
    @RequestMapping("/AddOrSubCarts")
    public String add_sub_carts(@RequestBody QueryInfo queryInfo)
    {
        List<ShoppingCart> temp_list=cartDao.getMessageByUid(queryInfo.getCurrentId());
        String str="success";
        for(ShoppingCart temp_cart:temp_list)
        {
            if(temp_cart.getMenuid()==Integer.parseInt(queryInfo.getCurrentMenuId()))
            {
                int temp_num=queryInfo.getUnitnum()-temp_cart.getNum();
                int i= cartDao.updateOrder(queryInfo.getCurrentId(),queryInfo.getCurrentMenuId(),temp_num);
                str = i >0?"success":"error";
                break;
            }
        }
        return str;
    }

    @CrossOrigin
    @RequestMapping("/deleteCarts")
    public String deleteCarts(@RequestBody QueryInfo queryInfo)
    {
        String str;
        int i= cartDao.deleteOrder(queryInfo.getCurrentId(),queryInfo.getCurrentMenuId());
        str = i >0?"success":"error";
        return str;
    }

    @CrossOrigin
    @RequestMapping("/updateSelected")
    public String updateSelected(@RequestBody QueryInfo queryInfo)
    {
        String str;
        int i= cartDao.updateOrder_selected(queryInfo.getCurrentId(),queryInfo.getCurrentMenuId(),queryInfo.getQuery());
        str = i >0?"success":"error";
        return str;
    }

    @CrossOrigin
    @RequestMapping("/clearAll")
    public String clearAllMessage(@RequestBody QueryInfo queryInfo)
    {
        String str;
        int i= cartDao.clearUserALL(queryInfo.getCurrentId());
        str = i >0?"success":"error";
        return str;
    }

    @CrossOrigin
    @RequestMapping("/submmit")
    public String checkOut(@RequestBody QueryInfo queryInfo)
    {
        String str="error";
        List<ShoppingCart> mysc=cartDao.getMessageByUid(queryInfo.getCurrentId());
        int orderid=ordersDao.getOrdersCount()+1;
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date=df.format(new Date());
        int flag=0;
        Orders neworder=new Orders();
        for(ShoppingCart sc:mysc)
        {
            if(sc.getSelected().equals("false")){
                continue;
            }
            float price=cartDao.getMenuPrice(sc.getMenuid());
            flag=cartDao.insertOrderByShoppingCart(sc.getUserid(),sc.getMenuid(),sc.getNum(),date,price,orderid);
            if(flag<=0) return str;
            flag=cartDao.deleteOrder(sc.getUserid()+"",sc.getMenuid()+"");
            if(flag<=0) return str;
            neworder.setMenuid(sc.getMenuid());
            neworder.setUnitprice(price);
            neworder.setUnitnum(sc.getNum());
            ordersDao.changeMenuImmediately(neworder);
        }
        str="success";
        System.out.println(str);
        return str;
    }

}

package com.xmu.test.dao;

import com.xmu.test.bean.ShoppingCart;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShoppingCartDao {
    List<ShoppingCart> getMessageByUid(String userid);
    public int getOrderNumByMassage(String userid,String menuid,int num);
    public int insertOrder(String userid,String menuid,int num);
    public int updateOrder(String userid,String menuid,int num);
    public int deleteOrder(String userid,String menuid);
    public int updateOrder_selected(String userid,String menuid,String selected);
    public int clearUserALL(String userid);
    public float getMenuPrice(int menuid);
    public int insertOrderByShoppingCart(int userid,int menuid,int num,String date,float price,int orderid);
}

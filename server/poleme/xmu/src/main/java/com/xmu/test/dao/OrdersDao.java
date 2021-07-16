package com.xmu.test.dao;

import com.xmu.test.bean.Menu;
import com.xmu.test.bean.OrderDetails;
import com.xmu.test.bean.Orders;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdersDao {
    public List<Orders> getAllOrder(@Param("pageStart") int pageStart, @Param("pageSize") int pageSize);
    public int getOrdersNum();
    public List<OrderDetails> getOrderDetails(int id);
    public int isAvailable(int id);
    public int getOrderSearchNum(int id);
    public List<Orders> getSearchOrder(int id,@Param("pageStart") int pageStart, @Param("pageSize") int pageSize);
    public List<Orders> getUserOrder(int id,@Param("pageStart") int pageStart, @Param("pageSize") int pageSize);
    public int changeMenuImmediately(Orders order);
    public int getMenuItemStock(int id);
    public int changeStockToZero(int id);
    public int getOrdersCount();
    public int insertOrderImmediately(Orders order);
}

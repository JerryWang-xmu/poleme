package com.xmu.test.dao;

import com.xmu.test.bean.Menu;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenusDao {
    public List<Menu> getAllMenus(@Param("pageStart") int pageStart, @Param("pageSize") int pageSize);

    public int getAllMenuCount();

    public int addMenus(Menu menu);

    public int getMenuIDCount();

    public int getMenuCount(@Param("id") int id);

    public int editMenu(Menu menu);

    public int deleteMenu(String id);

    public int getMenuNameCount(String name);

    public List<Menu> getSelectedMenu(String name, @Param("pageStart") int pageStart, @Param("pageSize")int pageSize);

    public List<Menu> getMenuByPath(String path);

    public List<Menu> getSelectedMenuWithoutPage(String name);

    public List<Menu> getMenuById(String id);
}

package com.xmu.test.dao;

import com.xmu.test.bean.MainMenu;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuDao {
    public List<MainMenu> getMainMenus();

    public List<MainMenu> getManagerMainMenus();
}

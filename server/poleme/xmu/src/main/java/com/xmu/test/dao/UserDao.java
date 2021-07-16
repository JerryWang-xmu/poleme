package com.xmu.test.dao;


import com.xmu.test.bean.NewPassword;
import com.xmu.test.bean.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserDao {
    public int getUserByMassage(@Param("username") String username, @Param("password") String password);

    public int getUserNumByUsername(@Param("username") String username);

    public int getUserNumByMassage(@Param("username") String username, @Param("password") String password, @Param("role") String role);

    public int getUserCount(@Param("id") String id);

    public User getUserInformation(@Param("id") String id);

    public int changeUserInfor(User user);

    public int getSecretCount(@Param("id") int id, @Param("password") String password);

    public int changeSecret(NewPassword nps);

    public List<User> getAllUser(@Param("pageStart") int pageStart, @Param("pageSize") int pageSize);

    public int getUserCounts();

    public int getUserMaxId();

    public int addUser(User user);

    public int deleteUser(String id);

    public int editUser(User user);

    public User getUpdateUser(int id);

    public String isManager(int id);

    public int revokeUser(int id);

    public int getUserSelectCount(String name);

    public List<User> getSelectedUsers(String name, @Param("pageStart") int pageStart, @Param("pageSize")int pageSize);

}

package com.xmu.test.dao;

import com.xmu.test.bean.Rank;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RankDao {
    public List<Rank> getAllMenuItemForRank();
    public List<Rank> getRankMenuItemWithPage(@Param("pageStart") int pageStart, @Param("pageSize")int pageSize);
}

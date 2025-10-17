package com.peanut.service;

import com.peanut.pojo.Good;

import java.sql.SQLException;
import java.util.List;

public interface GoodService {
    public int insertGood(Good good);
    public int updateGood(Good good);
    public int deleteGood(List<String> ids) throws SQLException;
    public Good getGoodById(String id);
    public List<Good> getAllGood(int pageNum, int pageSize);
    public List<Good> getGoodByName(String goodName ,int pageNum, int pageSize);
}

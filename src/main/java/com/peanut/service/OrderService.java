package com.peanut.service;


import com.peanut.expection.BaseExpection;
import com.peanut.pojo.Order;
import com.peanut.pojo.OrderGoodRequest;
import com.peanut.pojo.OrderVO;

import java.sql.SQLException;
import java.util.List;

public interface OrderService {
    public int insert(List<OrderGoodRequest> goodRequests) throws BaseExpection, SQLException;
    public int delete(List<String> id) throws SQLException;
    public int update(Order order);
    List<OrderVO> selectAll(int pageNum, int pageSize);
    OrderVO selectById(String id);
    List<OrderVO> selectAllByPriceASC(int pageNum, int pageSize);
    List<OrderVO> selectAllByTimeASC(int pageNum, int pageSize);
}

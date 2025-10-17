package com.peanut.service.ServiceImp;

import com.peanut.constant.DatebaseConstant;
import com.peanut.expection.BaseExpection;
import com.peanut.mapper.GoodMapper;
import com.peanut.mapper.OrderMapper;
import com.peanut.pojo.Good;
import com.peanut.pojo.Order;
import com.peanut.pojo.OrderVO;
import com.peanut.service.OrderService;
import com.peanut.tool.JdbcUtils;
import com.peanut.tool.TransactionUtils;


import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class OrderServiceImp implements OrderService {
    private GoodMapper goodMapper = new GoodMapper();

    private OrderMapper orderMapper = new OrderMapper();


    public int insert(List<Order> orders) throws BaseExpection, SQLException {
        try {
            LocalDateTime time = LocalDateTime.now();
            TransactionUtils.beginTransaction();
            for (Order order : orders) {
                UUID uuid = UUID.randomUUID();
                order.setId(uuid.toString());
                order.setTime(time);
                check(order);
                if (orderMapper.insertOrder(order) == 0) {
                    throw new BaseExpection(DatebaseConstant.ORDER_INSERT_ERROR);
                } else {
                    System.out.println(order.getId() + " " + DatebaseConstant.ORDER_INSERT_SUCCESS);
                }
            }
            TransactionUtils.commitTransaction();
        } catch (Exception e) {
            TransactionUtils.rollbackTransaction();
            throw new RuntimeException(DatebaseConstant.COMMIT_FAILURE + e);
        } finally {
            TransactionUtils.closeConnection();
        }
        return 1;
    }

    public int delete(List<String> ids) throws SQLException {
        try {
            TransactionUtils.beginTransaction();
            for (int i = 0; i < ids.size(); i++) {
                String id = ids.get(i);
                Order order = orderMapper.selectById(id);
                if (order == null) {
                    throw new BaseExpection(DatebaseConstant.ORDERD_NOT_EXIST);
                }
                int i1 = orderMapper.deleteOrder(ids.get(i));
                if (i1 == 0) {
                    throw new BaseExpection(DatebaseConstant.DELETE_FAILURE);
                }
            }
            TransactionUtils.commitTransaction();
        } catch (Exception e) {
            TransactionUtils.rollbackTransaction();
            throw new RuntimeException(e);
        }
        return 1;
    }

    public int update(Order order) {
        order.setTime(LocalDateTime.now());
        check(order);
        return orderMapper.updateOrder(order);
    }

    private void check(Order order) {
        if (JdbcUtils.isPriceIllegal(order.getPrice())) {
            throw new BaseExpection(DatebaseConstant.PRICE_ERROR);
        }
        if (order.getId() == null || order.getId().equals("")) {
            throw new BaseExpection(DatebaseConstant.ORDER_ID_NOT_EXIST);
        }

        String goodId = order.getGood_id();

        if (goodId == null || goodId.equals("")) {
            throw new BaseExpection(DatebaseConstant.GOODID_NOT_EXIST);
        }

        Good good = goodMapper.selectById(goodId);

        if (good == null) {
            throw new BaseExpection(DatebaseConstant.GOOD_NOT_EXIST);
        }
    }

    @Override
    public OrderVO selectById(String id) {
        Order order = orderMapper.selectById(id);
        if (order == null) {
            return null;
        }
        String goodId = order.getGood_id();
        Good good = goodMapper.selectById(goodId);
        OrderVO orderVO = new OrderVO(order.getId(), order.getGood_id(), order.getTime(), order.getPrice(), good.getName(), good.getPrice());
        return orderVO;
    }

    @Override
    public List<OrderVO> selectAll(int pageNum, int pageSize) {
        pageNum = JdbcUtils.updatePageNum(pageNum, pageSize);
        List<Order> orders = orderMapper.selectAll(pageNum, pageSize);
        return getOrderVO(orders);
    }

    @Override
    public List<OrderVO> selectAllByPriceASC(int pageNum, int pageSize) {
        pageNum = JdbcUtils.updatePageNum(pageNum, pageSize);
        List<Order> orders = orderMapper.selectAllByPriceASC(pageNum, pageSize);
        return getOrderVO(orders);
    }

    @Override
    public List<OrderVO> selectAllByTimeASC(int pageNum, int pageSize) {
        pageNum = JdbcUtils.updatePageNum(pageNum, pageSize);
        List<Order> orders = orderMapper.selectAllByTimeASC(pageNum, pageSize);
        return getOrderVO(orders);
    }

    private List<OrderVO> getOrderVO(List<Order> orders) {
        List<OrderVO> orderVOS = new ArrayList<OrderVO>();
        for (Order order : orders) {
            String goodId = order.getGood_id();
            Good good = goodMapper.selectById(goodId);
            OrderVO orderVO = new OrderVO(order.getId(), order.getGood_id(), order.getTime(), order.getPrice(), good.getName(), good.getPrice());
            orderVOS.add(orderVO);
        }
        return orderVOS;
    }
}

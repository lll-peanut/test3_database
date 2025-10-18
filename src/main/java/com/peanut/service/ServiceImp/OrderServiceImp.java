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


    /**
     * 批量新增订单（支持事务）
     *
     * @param orders 待新增的订单列表
     * @return 1（表示批量新增逻辑执行完成，成功时返回）
     * @throws BaseExpection 自定义业务异常（如参数校验失败、插入失败）
     * @throws SQLException  SQL执行异常
     */
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

    /**
     * 批量删除订单（支持事务，逻辑删除）
     *
     * @param ids 待删除的订单ID列表
     * @return 1（表示批量删除逻辑执行完成，成功时返回）
     * @throws SQLException SQL执行异常
     */
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

    /**
     * 更新订单信息
     *
     * @param order 待更新的订单对象（需包含订单ID及更新后的信息）
     * @return 影响的行数（1表示更新成功，0表示更新失败）
     */
    public int update(Order order) {
        order.setTime(LocalDateTime.now());
        check(order);
        return orderMapper.updateOrder(order);
    }

    /**
     * 订单参数合法性校验（私有工具方法）
     * 校验价格、订单ID、商品ID及商品存在性，不合法则抛出自定义异常
     *
     * @param order 待校验的订单对象
     */
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

    /**
     * 根据订单ID查询订单详情（返回VO对象，包含商品关联信息）
     *
     * @param id 订单ID
     * @return OrderVO（订单+商品组合信息）；null（订单不存在时）
     */
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

    /**
     * 分页查询所有订单（无排序，返回VO列表）
     *
     * @param pageNum  页数
     * @param pageSize 每页记录数
     * @return 订单VO列表（包含商品关联信息，分页结果）
     */
    @Override
    public List<OrderVO> selectAll(int pageNum, int pageSize) {
        pageNum = JdbcUtils.updatePageNum(pageNum, pageSize);
        List<Order> orders = orderMapper.selectAll(pageNum, pageSize);
        return getOrderVO(orders);
    }

    /**
     * 按订单价格升序分页查询（返回VO列表）
     *
     * @param pageNum  页数
     * @param pageSize 每页记录数
     * @return 按价格升序排列的订单VO列表（包含商品关联信息，分页结果）
     */
    @Override
    public List<OrderVO> selectAllByPriceASC(int pageNum, int pageSize) {
        pageNum = JdbcUtils.updatePageNum(pageNum, pageSize);
        List<Order> orders = orderMapper.selectAllByPriceASC(pageNum, pageSize);
        return getOrderVO(orders);
    }

    /**
     * 按订单时间升序分页查询（返回VO列表）
     *
     * @param pageNum  页数
     * @param pageSize 每页记录数
     * @return 按时间升序排列的订单VO列表（包含商品关联信息，分页结果）
     */
    @Override
    public List<OrderVO> selectAllByTimeASC(int pageNum, int pageSize) {
        pageNum = JdbcUtils.updatePageNum(pageNum, pageSize);
        List<Order> orders = orderMapper.selectAllByTimeASC(pageNum, pageSize);
        return getOrderVO(orders);
    }

    /**
     * 订单列表转VO列表（私有工具方法）
     * 将Order列表转换为OrderVO列表，关联查询商品信息并整合
     *
     * @param orders 原始订单列表
     * @return 整合商品信息的OrderVO列表
     */
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
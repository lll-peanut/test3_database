package com.peanut.mapper;

import com.peanut.pojo.Order;
import com.peanut.tool.JdbcUtils;
import com.peanut.tool.TransactionUtils;

import java.util.List;

public class OrderMapper {

    private static final String UPDATE_SQL = "update `order` set `good_id`=?, `time`=?, price = ? where `id` = ?";

    private static final String INSERT_SQL = "insert into `order` (`id`, `good_id`, `time`, `price`) values (?, ?, ?, ?)";

    private static final String DELETE_SQL = "delete from `order` where `id` = ?";

    private static final String SELECT_SQL = "select `id`, `good_id`, `time`, `price` from `order` limit ?,?";

    private static final String SELECT_SQL_PRICE_ASC = "select `id`, `good_id`, `time`, `price` from `order` ORDER BY price ASC limit ?,?";

    private static final String SELECT_ID_SQL = "select `id`, `good_id`, `time`, `price` from `order` where `id` = ?";

    private static final String SELECT_SQL_TIME_ASC = "select `id`, `good_id`, `time`, `price` from `order` ORDER BY `time` ASC limit ?,?";

    /**
     * 分页查询所有订单（无排序）
     *
     * @param PageNum  页码
     * @param PageSize 每页记录数
     * @return 订单列表（分页结果）
     */
    public List<Order> selectAll(int PageNum, int PageSize) {
        return JdbcUtils.orderQuery(SELECT_SQL, PageNum, PageSize);
    }

    /**
     * 按价格升序分页查询所有订单
     *
     * @param PageNum  页码
     * @param PageSize 每页记录数
     * @return 按价格升序排列的订单列表（分页结果）
     */
    public List<Order> selectAllByPriceASC(int PageNum, int PageSize) {
        return JdbcUtils.orderQuery(SELECT_SQL_PRICE_ASC, PageNum, PageSize);
    }

    /**
     * 按时间升序分页查询所有订单
     *
     * @param PageNum  页码
     * @param PageSize 每页记录数
     * @return 按时间升序排列的订单列表（分页结果）
     */
    public List<Order> selectAllByTimeASC(int PageNum, int PageSize) {
        return JdbcUtils.orderQuery(SELECT_SQL_TIME_ASC, PageNum, PageSize);
    }

    /**
     * 根据ID查询订单详情
     *
     * @param id 订单ID
     * @return 订单对象（若存在）；null（若不存在）
     */
    public Order selectById(String id) {
        // 执行查询，返回包含单个订单的列表
        List list = JdbcUtils.orderQuery(SELECT_ID_SQL, new Object[]{id});
        // 若列表非空且有元素，则返回第一个元素（订单对象）；否则返回null
        if (list != null && list.size() > 0) {
            return (Order) list.get(0);
        } else {
            return null;
        }
    }

    /**
     * 插入新订单（依赖事务管理）
     *
     * @param order 订单对象（包含ID、商品ID、时间、价格等信息）
     * @return 影响的行数（1表示成功，0表示失败）
     */
    public int insertOrder(Order order) {
        // 使用事务工具类执行更新，确保插入操作在事务中执行
        return TransactionUtils.update(INSERT_SQL, order.getId(), order.getGood_id(), order.getTime(), order.getPrice());
    }

    /**
     * 根据ID删除订单（依赖事务管理）
     *
     * @param id 订单ID
     * @return 影响的行数（1表示成功，0表示失败）
     */
    public int deleteOrder(String id) {
        // 使用事务工具类执行删除，确保操作在事务中执行
        return TransactionUtils.update(DELETE_SQL, id);
    }

    /**
     * 更新订单信息（不依赖事务管理，直接通过JDBC工具类执行）
     *
     * @param order 订单对象（包含更新后的商品ID、时间、价格及目标ID）
     * @return 影响的行数（1表示成功，0表示失败）
     */
    public int updateOrder(Order order) {
        return JdbcUtils.update(UPDATE_SQL, order.getGood_id(), order.getTime(), order.getPrice(), order.getId());
    }
}
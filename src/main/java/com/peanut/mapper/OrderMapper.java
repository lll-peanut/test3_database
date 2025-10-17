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

    public List<Order> selectAll(int PageNum, int PageSize) {
        return JdbcUtils.orderQuery(SELECT_SQL, PageNum, PageSize);
    }

    public List<Order> selectAllByPriceASC(int PageNum, int PageSize) {
        return JdbcUtils.orderQuery(SELECT_SQL_PRICE_ASC, PageNum, PageSize);
    }

    public List<Order> selectAllByTimeASC(int PageNum, int PageSize) {
        return JdbcUtils.orderQuery(SELECT_SQL_TIME_ASC, PageNum, PageSize);
    }

    public Order selectById(String id) {
        List list = JdbcUtils.orderQuery(SELECT_ID_SQL, new Object[]{id});
        if (list != null && list.size() > 0) {
            return (Order) list.get(0);
        } else {
            return null;
        }
    }


    public int insertOrder(Order order) {
        return TransactionUtils.update(INSERT_SQL, order.getId(), order.getGood_id(), order.getTime(), order.getPrice());
    }

    public int deleteOrder(String id) {
        return TransactionUtils.update(DELETE_SQL, id);
    }

    public int updateOrder(Order order) {
        return JdbcUtils.update(UPDATE_SQL, order.getGood_id(), order.getTime(), order.getPrice(),order.getId());
    }
}

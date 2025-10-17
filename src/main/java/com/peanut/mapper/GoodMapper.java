package com.peanut.mapper;

import com.peanut.pojo.Good;
import com.peanut.tool.JdbcUtils;
import com.peanut.tool.TransactionUtils;
import java.util.List;

public class GoodMapper {
    private static final String SELECT_ALL_SQL = "select `id`, `name`, `price`, `is_deleted` from good where `is_deleted` = 0 limit ?, ?";

    private static final String SELECT_ID_SQL = "select `id`, `name`, `price`, `is_deleted` from good where `id` = ? ";

    private static final String SELECT_LIKE_SQL = "select `id`, `name`, `price`, `is_deleted` from good where `name` like ?  ESCAPE '\\\\' and `is_deleted` = 0 limit ?, ?";

    private static final String INSERT_SQL = "insert into `good` (`id`, `name`, `price`) values (?,?,?)";

    private static final String DELETE_SQL = "update `good` set `is_deleted` = 1 where `id` = ?";

    private static final String UPDATE_SQL = "update `good` set `name` = ?, `price` = ? where `id` = ?";

    private static final String SELECT_NAME_SQL = "select  `id`, `name`, `price`, `is_deleted` from good where `name` = ?";

    public List<Good> selectAll(int pageNum, int pageSize) {
        return JdbcUtils.goodQuery(SELECT_ALL_SQL, pageNum, pageSize);
    }

    public int insert(Good good) {
        return JdbcUtils.update(INSERT_SQL, good.getId(), good.getName(), good.getPrice());
    }

    public Good selectById(String id) {
        List list = JdbcUtils.goodQuery(SELECT_ID_SQL, id);
        if (list != null && list.size() > 0) {
            return (Good) list.get(0);
        } else {
            return null;
        }
    }

    public List<Good> selectByName(String name, int pageNum, int pageSize) {
        return JdbcUtils.goodQuery(SELECT_LIKE_SQL, name, pageNum, pageSize);
    }

    public boolean isNameExist(String name) {
        List list = JdbcUtils.goodQuery(SELECT_NAME_SQL, name);
        if (list != null && list.size() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public int delete(String id) {
        return TransactionUtils.update(DELETE_SQL, id);
    }

    public int update(Good good) {
        return JdbcUtils.update(UPDATE_SQL, good.getName(), good.getPrice(), good.getId());
    }
}

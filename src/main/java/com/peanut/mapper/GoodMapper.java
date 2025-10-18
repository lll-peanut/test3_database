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

    /**
     * 分页查询所有未删除的商品
     * @param pageNum 页数
     * @param pageSize 每页显示的记录数
     * @return 商品列表（分页结果，仅包含is_deleted=0的商品）
     */
    public List<Good> selectAll(int pageNum, int pageSize) {
        return JdbcUtils.goodQuery(SELECT_ALL_SQL, pageNum, pageSize);
    }

    /**
     * 新增商品
     * @param good 商品对象（需包含id、name、price字段）
     * @return 影响的行数（1表示成功，0表示失败）
     */
    public int insert(Good good) {
        return JdbcUtils.update(INSERT_SQL, good.getId(), good.getName(), good.getPrice());
    }

    /**
     * 根据ID查询商品（忽略删除状态）
     * @param id 商品ID
     * @return 商品对象（若存在）；null（若不存在）
     */
    public Good selectById(String id) {
        List list = JdbcUtils.goodQuery(SELECT_ID_SQL, id);
        // 若查询结果非空且有数据，返回第一个商品对象
        if (list != null && list.size() > 0) {
            return (Good) list.get(0);
        } else {
            return null;
        }
    }

    /**
     * 按名称模糊查询未删除商品（分页）
     * @param name 模糊匹配的商品名称（已处理特殊字符转义）
     * @param pageNum 页数
     * @param pageSize 每页记录数
     * @return 符合条件的商品列表（分页结果，仅包含未删除商品）
     */
    public List<Good> selectByName(String name, int pageNum, int pageSize) {
        return JdbcUtils.goodQuery(SELECT_LIKE_SQL, name, pageNum, pageSize);
    }

    /**
     * 检查商品名称是否已存在（用于避免名称重复）
     * @param name 待检查的商品名称
     * @return true（名称已存在）；false（名称不存在）
     */
    public boolean isNameExist(String name) {
        List list = JdbcUtils.goodQuery(SELECT_NAME_SQL, name);
        // 若查询到结果，说明名称已存在
        if (list != null && list.size() > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 逻辑删除商品（通过事务管理）
     * @param id 商品ID
     * @return 影响的行数（1表示成功，0表示失败）
     */
    public int delete(String id) {
        // 使用事务工具类执行更新，确保删除操作在事务中进行
        return TransactionUtils.update(DELETE_SQL, id);
    }

    /**
     * 更新商品信息（名称和价格）
     * @param good 商品对象（需包含id、更新后的name和price）
     * @return 影响的行数（1表示成功，0表示失败）
     */
    public int update(Good good) {
        return JdbcUtils.update(UPDATE_SQL, good.getName(), good.getPrice(), good.getId());
    }
}
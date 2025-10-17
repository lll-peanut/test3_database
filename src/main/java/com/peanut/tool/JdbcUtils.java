package com.peanut.tool;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.peanut.constant.DatebaseConstant;
import com.peanut.pojo.Good;
import com.peanut.pojo.Order;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class JdbcUtils {
    private static DataSource dataSource;

    private static final String PROPERTIES_FILE_NAME = "druid.properties";

    static {
        try (InputStream resourceAsStream = JdbcUtils.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE_NAME);) {
            Properties properties = new Properties();
            properties.load(resourceAsStream);
            dataSource = DruidDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(DatebaseConstant.CONNECT_DATABASE_FAILURE, e);
        }
    }

    public static void getClose(ResultSet rs, PreparedStatement stmt, Connection conn) {
        try {
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static <T> List<T> executeQuery(String sql, RowMapper<T> rowMapper, Object... params) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<T> list = new ArrayList<>();

        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(sql);
            for (int i = 0; i < params.length; i++) {
                preparedStatement.setObject(i + 1, params[i]);
            }
            resultSet = preparedStatement.executeQuery();

            // 在资源关闭前，完成结果集处理
            while (resultSet.next()) {
                list.add(rowMapper.mapRow(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(DatebaseConstant.SELECT_FAILURE + sql, e);
        } finally {
            // 此时结果集已处理完毕，安全关闭所有资源
            getClose(resultSet, preparedStatement, connection);
        }
        return list;
    }


    @FunctionalInterface
    private interface RowMapper<T> {
        T mapRow(ResultSet rs) throws SQLException;
    }

    public static List<Good> goodQuery(String sql, Object... params) {

        return executeQuery(sql, rs -> new Good(
                rs.getString("id"),
                rs.getString("name"),
                rs.getDouble("price"),
                rs.getInt("is_deleted")
        ), params);
    }

    public static List<Order> orderQuery(String sql, Object... params) {

        return executeQuery(sql, rs -> new Order(
                rs.getString("id"),
                rs.getString("good_id"),
                rs.getTimestamp("time").toLocalDateTime(),
                rs.getDouble("price")
        ), params);
    }

    public static int update(String sql, Object... params) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = getConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(sql);
            for (int i = 0; i < params.length; i++) {
                preparedStatement.setObject(i + 1, params[i]);
            }

            int affectedRows = preparedStatement.executeUpdate();
            System.out.println("受影响的行数为: " + affectedRows);
            connection.commit();
            return affectedRows;

        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                System.out.println("执行回滚失败" + ex.getMessage());
                throw new RuntimeException(ex);
            }
            throw new RuntimeException("执行更新SQL失败（SQL: " + sql + "）", e);
        } finally {
            getClose(null, preparedStatement, connection);
        }
    }

    public static boolean isPriceIllegal(double price) {
        return price <= DatebaseConstant.MIN_PRICE || price > DatebaseConstant.MAX_PRICE;
    }

    public static int updatePageNum(int pageNum, int pageSize) {
        if (pageNum < 1) {
            pageNum = 1;
        }
        if (pageSize < 1) {
            pageSize = 10;
        }
        return (pageNum - 1) * pageSize;
    }


}

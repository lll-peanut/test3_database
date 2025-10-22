package com.peanut.tool;

import com.peanut.expection.BaseExpection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TransactionUtils {
    // 存储线程本地的数据库连接对象
    private static final ThreadLocal<Connection> connectionHolder = new ThreadLocal<>();

    /**
     * 获取数据库连接
     *
     * @return 数据库连接对象
     * @throws SQLException SQL 异常
     */
    public static Connection getConnection() throws SQLException {
        Connection connection = connectionHolder.get();
        if (connection == null || connection.isClosed()) {
            // 获取新连接
            connection = JdbcUtils.getConnection();
            connectionHolder.set(connection);
        }
        return connection;
    }

    /**
     * 开启事务
     *
     * @throws SQLException SQL 异常
     */
    public static void beginTransaction() throws SQLException {
        Connection connection = getConnection();
        if (connection != null) {
            connection.setAutoCommit(false);  // 禁用自动提交
            System.out.println("事务已开启");
        }
    }

    /**
     * 提交事务
     *
     * @throws SQLException SQL 异常
     */
    public static void commitTransaction() throws SQLException {
        Connection connection = connectionHolder.get();
        if (connection != null && !connection.isClosed()) {
            connection.commit();
            System.out.println("事务已提交");
        }
    }

    /**
     * 回滚事务
     *
     * @throws SQLException SQL 异常
     */
    public static void rollbackTransaction() throws SQLException {
        Connection connection = connectionHolder.get();
        if (connection != null && !connection.isClosed()) {
            connection.rollback();
            System.out.println("事务已回滚");
        }
    }

    /**
     * 关闭连接
     */
    public static void closeConnection() {
        Connection connection = connectionHolder.get();
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("数据库连接已关闭");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionHolder.remove();  // 移除ThreadLocal中的连接
        }
    }

    public static int update(String sql, Object... params) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(sql);
            for (int i = 0; i < params.length; i++) {
                preparedStatement.setObject(i + 1, params[i]);
            }

            int affectedRows = preparedStatement.executeUpdate();
            return affectedRows;

        } catch (SQLException e) {
            throw new RuntimeException("执行更新SQL失败（SQL: " + sql + "）", e);
        } finally {
            getClose(null, preparedStatement, null);
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
}

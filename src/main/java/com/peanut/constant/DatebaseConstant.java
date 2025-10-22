package com.peanut.constant;

public class DatebaseConstant {

    public final static double MIN_PRICE = 0;
    public final static double MAX_PRICE = 100000;

    public final static double MIN_SALE = 1;
    public final static double MAX_SALE = 100;

    public final static String GOODID_NOT_EXIST = "没找到订单的商品id";
    public final static String PRICE_ERROR = "价格不合理,应在(0,100000]的区间";
    public final static String GOOD_NOT_EXIST = "商品不存在";
    public final static String ORDER_INSERT_ERROR = "订单插入失败";
    public final static String ORDERD_NOT_EXIST = "订单不存在";
    public final static String ORDER_INSERT_SUCCESS = "订单插入成功";
    public final static String CONNECT_DATABASE_FAILURE = "连接数据库失败";
    public final static String SELECT_FAILURE = "执行查询失败: ";
    public final static String GOOD_NAME_EXIST = "商品名称重复";
    public final static String GOOD_NAME_NOT_INIT = "商品名称未初始化";
    public final static String GOOD_ID_NOT_EXIST = "商品无id";
    public final static String ORDER_ID_NOT_EXIST = "订单无id";
    public final static String DELETE_FAILURE = "删除失败";
    public final static String COMMIT_FAILURE = "事务提交失败";
    public final static String GOOD_NUM_ILLEGAL = "商品数量不合法，区间[1,100]";
}

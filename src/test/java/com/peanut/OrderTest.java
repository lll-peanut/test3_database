package com.peanut;

import com.peanut.mapper.GoodMapper;
import com.peanut.mapper.OrderMapper;
import com.peanut.pojo.Order;
import com.peanut.pojo.OrderVO;
import com.peanut.service.OrderService;
import com.peanut.service.ServiceImp.OrderServiceImp;
import org.junit.Test;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OrderTest {

    OrderMapper orderMapper = new OrderMapper();

    GoodMapper goodMapper = new GoodMapper();

    OrderServiceImp orderService = new OrderServiceImp();

    @Test
    public void insertTest1() throws SQLException {
        Order order = new Order();
        order.setPrice(40.674);
        order.setGood_id("123456");
        ArrayList<Order> orders = new ArrayList<>();
        orders.add(order);
        Order order1 = new Order("id", "343", null, 454.3);
        orders.add(order1);
        int i = orderService.insert(orders);
        if (i > 0) {
            System.out.println("插入成功");
        }
    }

    @Test
    public void insertTest2() throws SQLException {
        Order order = new Order();
        order.setPrice(40.674);
        order.setGood_id("123456");
        ArrayList<Order> orders = new ArrayList<>();
        orders.add(order);
        Order order1 = new Order("id", "123456", null, 454.3);
        orders.add(order1);
        int i = orderService.insert(orders);
        if (i > 0) {
            System.out.println("插入成功");
        }
    }

    @Test
    public void updateTest1() {
        Order order = new Order();
        order.setId("2ea13ec8-a08f-4f3c-9f2a-9a77677fc77c");
        order.setPrice(40.4);
        order.setGood_id("123456");
        int update = orderService.update(order);
    }

    @Test
    public void deleteTest1() throws SQLException {
        ArrayList<String> list = new ArrayList<>();
        list.add("123456");
        orderService.delete(list);
    }

    @Test
    public void selectTest1() {
        OrderVO orderVO = orderService.selectById("0796bdf2-4ae8-49bc-a777-d81815bbc18d");
        System.out.println(orderVO);
        OrderVO orderVO1 = orderService.selectById("123456");
        System.out.println(orderVO1);
    }

    @Test
    public void selectTest2() {
        List<OrderVO> orderVOS = orderService.selectAll(1, 5);
        for (OrderVO orderVO : orderVOS) {
            System.out.println(orderVO);
        }
    }

    @Test
    public void selectTest3() {
        List<OrderVO> orderVOS = orderService.selectAllByPriceASC(1, 5);
        for (OrderVO orderVO : orderVOS) {
            System.out.println(orderVO);
        }
    }

    @Test
    public void selectTest4() {
        List<OrderVO> orderVOS = orderService.selectAllByTimeASC(1, 5);
        for (OrderVO orderVO : orderVOS) {
            System.out.println(orderVO);
        }
    }
}

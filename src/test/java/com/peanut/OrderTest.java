package com.peanut;

import com.peanut.mapper.GoodMapper;
import com.peanut.mapper.OrderMapper;
import com.peanut.pojo.GoodInfo;
import com.peanut.pojo.Order;
import com.peanut.pojo.OrderGoodRequest;
import com.peanut.pojo.OrderVO;
import com.peanut.service.OrderService;
import com.peanut.service.ServiceImp.OrderServiceImp;
import org.junit.Test;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class OrderTest {

    OrderMapper orderMapper = new OrderMapper();

    GoodMapper goodMapper = new GoodMapper();

    OrderServiceImp orderService = new OrderServiceImp();

    @Test
    public void insertTest1() throws SQLException {
        OrderGoodRequest orderGoodRequest1 = new OrderGoodRequest("123456", 54);
        OrderGoodRequest orderGoodRequest = new OrderGoodRequest("1256", 22);
        ArrayList<OrderGoodRequest> orderGoodRequests = new ArrayList<>();
        orderGoodRequests.add(orderGoodRequest);
        orderGoodRequests.add(orderGoodRequest1);
        orderGoodRequests.add(orderGoodRequest);
        int i = orderService.insert(orderGoodRequests);
        if (i > 0) {
            System.out.println("插入成功");
        }
    }

    @Test
    public void insertTest2() throws SQLException {
        OrderGoodRequest orderGoodRequest1 = new OrderGoodRequest("123456", 54);
        OrderGoodRequest orderGoodRequest = new OrderGoodRequest("1256", 22);
        OrderGoodRequest orderGoodRequest2 = new OrderGoodRequest("14343256", 22);
        ArrayList<OrderGoodRequest> orderGoodRequests = new ArrayList<>();
        orderGoodRequests.add(orderGoodRequest);
        orderGoodRequests.add(orderGoodRequest1);
        orderGoodRequests.add(orderGoodRequest2);
        int i = orderService.insert(orderGoodRequests);
        if (i > 0) {
            System.out.println("插入成功");
        }
    }

    @Test
    public void insertTest3() throws SQLException {
        OrderGoodRequest orderGoodRequest1 = new OrderGoodRequest("123456", 54);
        OrderGoodRequest orderGoodRequest = new OrderGoodRequest("1256", -22);
        OrderGoodRequest orderGoodRequest2 = new OrderGoodRequest("14343256", 22);
        ArrayList<OrderGoodRequest> orderGoodRequests = new ArrayList<>();
        orderGoodRequests.add(orderGoodRequest);
        orderGoodRequests.add(orderGoodRequest1);
        orderGoodRequests.add(orderGoodRequest2);
        int i = orderService.insert(orderGoodRequests);
        if (i > 0) {
            System.out.println("插入成功");
        }
    }

    @Test
    public void updateTest1() {
        Order order = new Order();
        order.setId("2ea13ec8-a08f-4f3c-9f2a-9a77677fc77c");
        order.setPrice(40.4);
        int update = orderService.update(order);
    }

    @Test
    public void deleteTest1() throws SQLException {
        ArrayList<String> list = new ArrayList<>();
        list.add("9e79c818-a7f4-47f5-b7a5-809ee0944ee5");
        list.add("48ecb875-270d-47b0-b21f-5afef81cf3eb");
        list.add("a31878de-0cbe-4bfc-8458-6f1df2d8e03a");
        orderService.delete(list);
    }

    @Test
    public void selectByIdTest1() {
        OrderVO orderVO = orderService.selectById("2d752369-ac52-48e2-9128-5486b4d951aa");
        System.out.println(orderVO);
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
        List<OrderVO> orderVOS = orderService.selectAllByPriceASC(1, 100);
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

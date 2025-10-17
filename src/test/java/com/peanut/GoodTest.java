package com.peanut;

import com.peanut.mapper.GoodMapper;
import com.peanut.pojo.Good;
import com.peanut.service.GoodService;
import com.peanut.service.ServiceImp.GoodServiceImp;
import org.junit.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GoodTest {

    GoodServiceImp goodService = new GoodServiceImp();

    String s1 = "8";

    @Test
    public void insertTest1() {
        Good good = new Good("小龙虾12" + s1, 56);
        int insert = goodService.insertGood(good);
    }

    @Test
    public void insertTest2() {
        Good good = new Good("龙虾" + s1, -1);
        int insert = goodService.insertGood(good);
    }

    @Test
    public void insertTest3() {
        Good good = new Good("小猪" + s1, 1000000000);
        int insert = goodService.insertGood(good);
    }

    @Test
    public void insertTest4() {
        Good good = new Good("小鸡", 44);
        good.setDeleted(1);
        int insert = goodService.insertGood(good);
    }

    @Test
    public void insertTest5() {
        Good good = new Good();
        good.setName("小鸡1");
        int insert = goodService.insertGood(good);
    }

    @Test
    public void updateTest1() {
        Good good = new Good("4f36e153-cdfe-45c8-bc15-2c0f0a132da3", "小鱼" + s1, -443);
        int update = goodService.updateGood(good);
    }

    @Test
    public void updateTest2() {
        Good good = new Good("43435", "小鸡" + s1, 54);
        int update = goodService.updateGood(good);
    }

    @Test
    public void updateTest3() {
        Good good = new Good("4f36e153-cdfe-45c8-bc15-2c0f0a132da3", "小鱼" + s1, 443);
        int update = goodService.updateGood(good);
    }

    @Test
    public void deleteTest1() throws SQLException {
        ArrayList<String> list = new ArrayList<>();
        list.add("4f36e153-cdfe-45c8-bc15-2c0f0a132da3");
        list.add("43435");
        goodService.deleteGood(list);
    }

    @Test
    public void deleteTest2() throws SQLException {
        ArrayList<String> list = new ArrayList<>();
        list.add("77f1a9b7-3b0f-4477-8fab-ce3fda11af3f");
        goodService.deleteGood(list);
    }

    @Test
    public void selectTest1() {
        Good good = goodService.getGoodById("4f36e153-cdfe-45c8-bc15-2c0f0a132da3");
        System.out.println(good);
        Good good1 = goodService.getGoodById("123456");
        System.out.println(good1);
    }

    @Test
    public void selectTest2() {
        List<Good> allGood = goodService.getAllGood(1, 5);
        for (Good good : allGood) {
            System.out.println(good);
        }
        System.out.println("-------------------");
        List<Good> allGood1 = goodService.getAllGood(-1, 10);
        for (Good good : allGood1) {
            System.out.println(good);
        }
    }

    @Test
    public void selectTest3() {
        List<Good> good = goodService.getGoodByName("小", 1, 10);
        for (Good good1 : good) {
            System.out.println(good1);
        }
    }
}

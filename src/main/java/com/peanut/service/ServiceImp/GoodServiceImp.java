package com.peanut.service.ServiceImp;

import com.peanut.constant.DatebaseConstant;
import com.peanut.expection.BaseExpection;
import com.peanut.mapper.GoodMapper;
import com.peanut.pojo.Good;
import com.peanut.service.GoodService;
import com.peanut.tool.JdbcUtils;
import com.peanut.tool.TransactionUtils;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public class GoodServiceImp implements GoodService {

    GoodMapper goodMapper = new GoodMapper();

    public int insertGood(Good good) {
        UUID uuid = UUID.randomUUID();
        good.setId(uuid.toString());
        check(good);
        return goodMapper.insert(good);
    }

    @Override
    public int updateGood(Good good) {
        check(good);
        return goodMapper.update(good);
    }

    @Override
    public int deleteGood(List<String> ids) throws SQLException {
        TransactionUtils.beginTransaction();
        try {
            for (String id : ids) {
                int delete = goodMapper.delete(id);
                if (delete == 0) {
                    throw new BaseExpection(id + DatebaseConstant.DELETE_FAILURE);
                }
            }
            TransactionUtils.commitTransaction();
        } catch (Exception e) {
            TransactionUtils.rollbackTransaction();
            e.printStackTrace();
        } finally {
            TransactionUtils.closeConnection();
        }
        return 1;
    }

    @Override
    public Good getGoodById(String id) {
        return goodMapper.selectById(id);
    }

    @Override
    public List<Good> getGoodByName(String goodName, int pageNum, int pageSize) {
        pageNum = JdbcUtils.updatePageNum(pageNum, pageSize);
        goodName = goodName.replace("\\", "\\\\")
                .replace("%", "\\%")
                .replace("_", "\\_") + "%";
        return goodMapper.selectByName(goodName, pageNum, pageSize);
    }

    @Override
    public List<Good> getAllGood(int pageNum, int pageSize) {
        pageNum = JdbcUtils.updatePageNum(pageNum, pageSize);
        return goodMapper.selectAll(pageNum, pageSize);
    }

    private void check(Good good) {
        if (JdbcUtils.isPriceIllegal(good.getPrice())) {
            throw new BaseExpection(DatebaseConstant.PRICE_ERROR);
        }
        if (good.getId() == null || good.getId().equals("")) {
            throw new BaseExpection(DatebaseConstant.GOOD_ID_NOT_EXIST);
        }
        if (goodMapper.selectById(good.getId()) == null) {
            throw new BaseExpection(DatebaseConstant.GOOD_NOT_EXIST);
        }
        if (good.getName() == null || good.getName().equals("")) {
            throw new BaseExpection(DatebaseConstant.GOOD_NAME_NOT_INIT);
        }
        if (goodMapper.isNameExist(good.getName())) {
            throw new BaseExpection(DatebaseConstant.GOOD_NAME_EXIST);
        }
    }
}

package cn.roidlin.bookkeepingbook.data.repository;

import cn.roidlin.bookkeepingbook.data.AccountBean;
import cn.roidlin.bookkeepingbook.data.DBManger;
import cn.roidlin.bookkeepingbook.data.TypeBean;

import java.util.List;

public class LocalBookkeepingRepository implements BookkeepingRepository {
    @Override
    public List<TypeBean> getTypeList(int kind) {
        return DBManger.getTypeList(kind);
    }

    @Override
    public void insertAccount(AccountBean bean) {
        DBManger.insertItemToAccounttb(bean);
    }

    @Override
    public List<AccountBean> getAccountsByDay(int year, int month, int day) {
        return DBManger.getAccountListOneDayFromAccounttb(year, month, day);
    }

    @Override
    public List<AccountBean> getAccountsByMonth(int year, int month) {
        return DBManger.getAccountListOnemonthFromAccounttb(year, month);
    }

    @Override
    public float getSumMoneyOneday(int year, int month, int day, int kind) {
        return DBManger.getSumMoneyOneday(year, month, day, kind);
    }

    @Override
    public float getSumMoneyMonth(int year, int month, int kind) {
        return DBManger.getSumMoneyMonth(year, month, kind);
    }

    @Override
    public int deleteAccountById(int id) {
        return DBManger.deleteItemFromAccounttbById(id);
    }

    @Override
    public List<AccountBean> searchAccountsByRemark(String remark) {
        return DBManger.getAccountListListByRemarkFromAccounttb(remark);
    }

    @Override
    public List<Integer> getYearList() {
        return DBManger.getYearListFromAccounttb();
    }
}

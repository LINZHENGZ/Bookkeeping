package cn.roidlin.bookkeepingbook.data.repository;

import cn.roidlin.bookkeepingbook.data.AccountBean;
import cn.roidlin.bookkeepingbook.data.TypeBean;

import java.util.List;

public interface BookkeepingRepository {
    List<TypeBean> getTypeList(int kind);

    void insertAccount(AccountBean bean);

    List<AccountBean> getAccountsByDay(int year, int month, int day);

    List<AccountBean> getAccountsByMonth(int year, int month);

    float getSumMoneyOneday(int year, int month, int day, int kind);

    float getSumMoneyMonth(int year, int month, int kind);

    int deleteAccountById(int id);

    List<AccountBean> searchAccountsByRemark(String remark);

    List<Integer> getYearList();
}

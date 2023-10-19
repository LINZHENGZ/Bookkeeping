package frag_recode;

import cn.roidlin.bookkeepingbook.R;
import db.DBManger;
import db.TypeBean;

import java.util.List;

public class IncomeFragment extends BaseRecordFragment {

    @Override
    public void loadDataToGv() {
        super.loadDataToGv();
        List<TypeBean> inlist = DBManger.getTypeList(1);
        typeList.addAll(inlist);
        adapter.notifyDataSetInvalidated();
        typeTv.setText("其他");
        typeIv.setImageResource(R.mipmap.qita3);

    }

    @Override
    public void saveAccountToDB() {
        accountBean.setKind(1);
        DBManger.insertItemToAccounttb(accountBean);
    }
}
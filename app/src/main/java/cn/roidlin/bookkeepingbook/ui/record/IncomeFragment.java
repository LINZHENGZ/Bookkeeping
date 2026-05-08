package cn.roidlin.bookkeepingbook.ui.record;

import cn.roidlin.bookkeepingbook.R;
import cn.roidlin.bookkeepingbook.UnitAPP;
import cn.roidlin.bookkeepingbook.data.TypeBean;

import java.util.List;

public class IncomeFragment extends BaseRecordFragment {

    @Override
    public void loadDataToGv() {
        super.loadDataToGv();
        List<TypeBean> inlist = UnitAPP.getRepository().getTypeList(1);
        typeList.addAll(inlist);
        adapter.notifyDataSetChanged();
        typeTv.setText("其他");
        typeIv.setImageResource(R.mipmap.qita3);

    }

    @Override
    public void saveAccountToDB() {
        accountBean.setKind(1);
        UnitAPP.getRepository().insertAccount(accountBean);
    }
}

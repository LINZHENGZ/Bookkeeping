package cn.roidlin.bookkeepingbook.ui.record;

import cn.roidlin.bookkeepingbook.R;
import cn.roidlin.bookkeepingbook.UnitAPP;
import cn.roidlin.bookkeepingbook.data.TypeBean;
import java.util.List;


public class OutcomeFragment extends BaseRecordFragment {
    @Override
    public void loadDataToGv() {
        super.loadDataToGv();
        List<TypeBean> outlist = UnitAPP.getRepository().getTypeList(0);
        typeList.addAll(outlist);
        adapter.notifyDataSetChanged();
        typeTv.setText("其他");
        typeIv.setImageResource(R.mipmap.qita);

    }

    @Override
    public void saveAccountToDB() {
        accountBean.setKind(0);
        UnitAPP.getRepository().insertAccount(accountBean);

    }
}

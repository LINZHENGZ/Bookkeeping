package frag_recode;

import cn.roidlin.bookkeepingbook.R;
import db.DBManger;
import db.TypeBean;
import java.util.List;


public class OutcomeFragment extends BaseRecordFragment {
    @Override
    public void loadDataToGv() {
        super.loadDataToGv();
        List<TypeBean> outlist = DBManger.getTypeList(0);
        typeList.addAll(outlist);
        adapter.notifyDataSetChanged();
        typeTv.setText("其他");
        typeIv.setImageResource(R.mipmap.qita);

    }

    @Override
    public void saveAccountToDB() {
        accountBean.setKind(0);
        DBManger.insertItemToAccounttb(accountBean);

    }
}
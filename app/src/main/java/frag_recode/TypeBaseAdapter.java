package frag_recode;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import cn.roidlin.bookkeepingbook.R;
import db.TypeBean;

import java.util.List;

/**
 * @author LINZHENGZ
 * @version 1.0
 * Create by 20222022/12/27 23:11
 */

public class TypeBaseAdapter extends BaseAdapter {
    Context context;
    List<TypeBean> mDatas;
    int selectPos = 0 ;

    public TypeBaseAdapter(Context context, List<TypeBean> mDatas) {
        this.context = context;
        this.mDatas = mDatas;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.item_recordfrag_gv,parent,false);
        //查找布局中的控件
        ImageView iv= convertView.findViewById(R.id.item_recordfrag_iv);
        TextView tv = convertView.findViewById(R.id.item_recordfrag_tv);
        //获取指定位置的数据源
        TypeBean typeBean = mDatas.get(position);
        tv.setText(typeBean.getTypename());

        //旁段当前位置是否为选中位置
        if (selectPos == position){
            iv.setImageResource(typeBean.getSimageId());
        }else {
            iv.setImageResource(typeBean.getImageId());
        }

        return convertView;
    }
}

package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import cn.roidlin.bookkeepingbook.R;
import db.AccountBean;

import java.util.Calendar;
import java.util.List;

/**
 * @author LINZHENGZ
 * @version 1.0
 * Create by 20232023/1/6 13:14
 */

public class AccountAdapter extends BaseAdapter {
    Context context;
    List<AccountBean> mDatas;
    LayoutInflater inflater;
    int year,month,day;


    public AccountAdapter(Context context, List<AccountBean> mDatas) {
        this.context = context;
        this.mDatas = mDatas;
        inflater = LayoutInflater.from(context);
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH)+1;
        day = calendar.get(Calendar.DAY_OF_MONTH);
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
        ViewHolder holder = null;
        if (convertView == null){
            convertView = inflater.inflate(R.layout.mian_item_lv,parent,false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        AccountBean bean = mDatas.get(position);
        holder.typelv.setImageResource(bean.getsImageId());
        holder.typeTv.setText(bean.getTypename());
        holder.beizhuTv.setText(bean.getBeizhu());
        holder.moneyTv.setText("RMB："+bean.getMoney());

        //判断是否是当前时间
        if (bean.getYear()==year&&bean.getMonth()==month&&bean.getDay()==day){
            String time = bean.getTime().split(" ")[1];
            holder.timeTv.setText("今天  "+time);
        }else {
            holder.timeTv.setText(bean.getTime());
        }
        return convertView;
    }
    class ViewHolder{
            ImageView typelv;
            TextView typeTv,beizhuTv,timeTv,moneyTv;
            public  ViewHolder(View view){
                typelv = view.findViewById(R.id.item_lv_iv);
                typeTv = view.findViewById(R.id.item_lv_tv_title);
                timeTv = view.findViewById(R.id.item_lv_tv_time);
                beizhuTv = view.findViewById(R.id.item_lv_tv_beizhu);
                moneyTv = view.findViewById(R.id.item_lv_tv_money);
            }
    }
}

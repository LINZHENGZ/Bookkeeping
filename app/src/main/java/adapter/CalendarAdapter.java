package adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import cn.roidlin.bookkeepingbook.R;

import java.util.List;

public class CalendarAdapter extends BaseAdapter {

    Context context;
    List<String>mDatas;
    public int year;
    public int selPos = -1;


    public void setYear(int year) {
        this.year = year;
        mDatas.clear();
        loadDatas(year);


    }

    private void loadDatas(int year) {

        for (int i = 1; i < 13; i++) {
            String data = year+"/"+i;
            mDatas.add(data);
        }

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
    public View getView(int i, View converView, ViewGroup viewGroup) {

        converView = LayoutInflater.from(context).inflate(R.layout.item_dialogcal_gv,viewGroup,false);
        TextView tv = converView.findViewById(R.id.item_dialogcal_gv_tv);
        tv.setText(mDatas.get(i));
        tv.setBackgroundResource(R.color.yellow_F3D805);
        tv.setTextColor(Color.BLACK);

        if ( i == selPos ){

            tv.setBackgroundResource(R.color.green_929455);
            tv.setTextColor(Color.WHITE);

        }

        return converView;

    }
}

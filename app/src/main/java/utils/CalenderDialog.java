package utils;

import adapter.CalendarAdapter;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.*;
import android.widget.*;
import androidx.annotation.NonNull;
import cn.roidlin.bookkeepingbook.R;
import db.DBManger;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CalenderDialog extends Dialog implements View.OnClickListener {

    ImageView errorIv;
    GridView gv;
    LinearLayout hsvLayout;

    List<TextView> hsvViewList;
    List<Integer> yearList;

    int selectPos = -1 ;    //表示正在被点击的年份的位置
    private CalendarAdapter adapter;
    int selectMonth = -1;

    OnRefreshListener onRefreshListener;
    public interface OnRefreshListener{
        public void onRefresh(int selPos,int year,int month);
    }

    public void setOnRefreshListener(OnRefreshListener onRefreshListener) {
        this.onRefreshListener = onRefreshListener;
    }

    public CalenderDialog(@NonNull Context context,int selectPos,int selectMonth) {

        super(context);
        this.selectPos = selectPos;
        this.selectMonth = selectMonth;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_calendar);

        gv = findViewById(R.id.dialog_calendar_gv);
        errorIv = findViewById(R.id.dialog_calendar_iv);
        hsvLayout = findViewById(R.id.dialog_calendar_layout);

        errorIv.setOnClickListener(this);
        //向横向的ScrollView 当中添加View的方法
        addViewToLayount();
        initGridView();

        //设置GridView当中每一个item的点击事件
        setGVListener();

    }

    private void setGVListener() {

        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                adapter.selPos = i;
                adapter.notifyDataSetInvalidated();

                int month = i + 1;
                int year = adapter.year;

                //获取到被选中的年份和月份
                onRefreshListener.onRefresh(selectPos,year,month);
                cancel();

            }
        });

    }

    private void initGridView() {

        int selYear = yearList.get(selectPos);
        adapter = new CalendarAdapter(getContext(),selYear);

        if (selectMonth == -1){
            int month = Calendar.getInstance().get(Calendar.MONTH);
            adapter.selPos = month;
        }else {
            adapter.selPos = selectMonth - 1;
        }

        gv.setAdapter(adapter);

    }

    private void addViewToLayount() {

        //将添加进入线性布局当中的TextView进行统一管理的集合
        hsvViewList = new ArrayList<>();
        //获取数据库当中存储了多少年份
        yearList = DBManger.getYearListFromAccounttb();
        //如果数据库当中没有记录，就添加今年的记录
        if (yearList.size() == 0){
            int year = Calendar.getInstance().get(Calendar.YEAR);
            yearList.add(year);
        }

        //遍历年份，有几年，就向ScrollView当中添加几个view
        for (int i = 0; i<yearList.size(); i++){

            int year = yearList.get(i);
            View view = getLayoutInflater().inflate(R.layout.item_dialogcal_hsv,null);
            //将view添加到布局当中
            hsvLayout.addView(view);
            TextView hsvTv = view.findViewById(R.id.item_dialogcal_hsv_tv);
            hsvTv.setText(year+"");
            hsvViewList.add(hsvTv);

        }

        if (selectPos == -1){
            //设置当前被选中的是最近的年份
            selectPos = hsvViewList.size() - 1;
        }

        changeTvbg(selectPos);
        setHSVClickListener();

    }

    private void setHSVClickListener() {

        for (int i = 0;i<hsvViewList.size();i++){

            TextView view = hsvViewList.get(i);
            final int pos = i;
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    changeTvbg(pos);
                    selectPos = pos;
                    //获取被选中的年份，然后下面显示
                    int year = yearList.get(selectPos);
                    adapter.setYear(year);


                }
            });


        }

    }

    //传入被选中的位置，改变此位置上的背景和文字颜色
    private void changeTvbg(int selectPos) {

        for (int i = 0; i<hsvViewList.size();i++){
            TextView tv = hsvViewList.get(i);
            tv.setBackgroundResource(R.drawable.dialog_btn_bg);
            tv.setTextColor(Color.BLACK);
        }

        TextView selView = hsvViewList.get(selectPos);
        selView.setBackgroundResource(R.drawable.main_recordbtn_bg);
        selView.setTextColor(Color.WHITE);


    }

    @Override
    public void onClick(View view) {


    }


    public void setDialogSize(){
        //获取当前窗口对象
        Window window = getWindow();
        //获取窗口对象的参数
        WindowManager.LayoutParams wlp = window.getAttributes();
        //获取屏幕宽度
        Display d = window.getWindowManager().getDefaultDisplay();
        wlp.width = (int)d.getWidth();
        wlp.gravity = Gravity.TOP;
        window.setBackgroundDrawableResource(android.R.color.transparent);
        window.setAttributes(wlp);
    }




}

package cn.roidlin.bookkeepingbook.ui.common;

import cn.roidlin.bookkeepingbook.ui.adapter.CalendarAdapter;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.*;
import android.widget.*;
import androidx.annotation.NonNull;
import cn.roidlin.bookkeepingbook.R;
import cn.roidlin.bookkeepingbook.UnitAPP;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CalendarDialog extends Dialog implements View.OnClickListener {

    ImageView errorIv;
    GridView gv;
    LinearLayout hsvLayout;

    List<TextView> hsvViewList;
    List<Integer> yearList;

    int selectPos = -1 ;    // 表示当前选中的年份位置
    private CalendarAdapter adapter;
    int selectMonth = -1;

    OnRefreshListener onRefreshListener;
    public interface OnRefreshListener{
        public void onRefresh(int selPos,int year,int month);
    }

    public void setOnRefreshListener(OnRefreshListener onRefreshListener) {
        this.onRefreshListener = onRefreshListener;
    }

    public CalendarDialog(@NonNull Context context,int selectPos,int selectMonth) {

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
        // 向横向 ScrollView 中添加 View
        addViewToLayout();
        initGridView();

        // 设置 GridView 每个 item 的点击事件
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

                // 获取选中的年份和月份
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

    private void addViewToLayout() {

        // 统一管理添加到线性布局中的 TextView
        hsvViewList = new ArrayList<>();
        // 获取数据库中存了多少年份
        yearList = UnitAPP.getRepository().getYearList();
        // 如果数据库中没有记录，就补上今年
        if (yearList.size() == 0){
            int year = Calendar.getInstance().get(Calendar.YEAR);
            yearList.add(year);
        }

        // 遍历年份，有几个年份就向 ScrollView 中添加几个 View
        for (int i = 0; i<yearList.size(); i++){

            int year = yearList.get(i);
            View view = getLayoutInflater().inflate(R.layout.item_dialogcal_hsv,null);
            // 将 View 添加到布局中
            hsvLayout.addView(view);
            TextView hsvTv = view.findViewById(R.id.item_dialogcal_hsv_tv);
            hsvTv.setText(year+"");
            hsvViewList.add(hsvTv);

        }

        if (selectPos == -1){
            // 默认选中最近的年份
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
                    // 获取选中的年份并刷新下方月份
                    int year = yearList.get(selectPos);
                    adapter.setYear(year);


                }
            });


        }

    }

    // 根据选中位置更新背景和文字颜色
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
        // 顶部日历弹窗也走统一适配逻辑。
        ScreenAdapter.applyTopDialog(this, 0.94f, 420f);
    }




}

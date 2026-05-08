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

    int selectPos = -1 ;    //琛ㄧず姝ｅ湪琚偣鍑荤殑骞翠唤鐨勪綅缃?
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
        //鍚戞í鍚戠殑ScrollView 褰撲腑娣诲姞View鐨勬柟娉?
        addViewToLayout();
        initGridView();

        //璁剧疆GridView褰撲腑姣忎竴涓猧tem鐨勭偣鍑讳簨浠?
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

                //鑾峰彇鍒拌閫変腑鐨勫勾浠藉拰鏈堜唤
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

        //灏嗘坊鍔犺繘鍏ョ嚎鎬у竷灞€褰撲腑鐨凾extView杩涜缁熶竴绠＄悊鐨勯泦鍚?
        hsvViewList = new ArrayList<>();
        //鑾峰彇鏁版嵁搴撳綋涓瓨鍌ㄤ簡澶氬皯骞翠唤
        yearList = UnitAPP.getRepository().getYearList();
        //濡傛灉鏁版嵁搴撳綋涓病鏈夎褰曪紝灏辨坊鍔犱粖骞寸殑璁板綍
        if (yearList.size() == 0){
            int year = Calendar.getInstance().get(Calendar.YEAR);
            yearList.add(year);
        }

        //閬嶅巻骞翠唤锛屾湁鍑犲勾锛屽氨鍚慡crollView褰撲腑娣诲姞鍑犱釜view
        for (int i = 0; i<yearList.size(); i++){

            int year = yearList.get(i);
            View view = getLayoutInflater().inflate(R.layout.item_dialogcal_hsv,null);
            //灏唙iew娣诲姞鍒板竷灞€褰撲腑
            hsvLayout.addView(view);
            TextView hsvTv = view.findViewById(R.id.item_dialogcal_hsv_tv);
            hsvTv.setText(year+"");
            hsvViewList.add(hsvTv);

        }

        if (selectPos == -1){
            //璁剧疆褰撳墠琚€変腑鐨勬槸鏈€杩戠殑骞翠唤
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
                    //鑾峰彇琚€変腑鐨勫勾浠斤紝鐒跺悗涓嬮潰鏄剧ず
                    int year = yearList.get(selectPos);
                    adapter.setYear(year);


                }
            });


        }

    }

    //浼犲叆琚€変腑鐨勪綅缃紝鏀瑰彉姝や綅缃笂鐨勮儗鏅拰鏂囧瓧棰滆壊
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
        //鑾峰彇褰撳墠绐楀彛瀵硅薄
        Window window = getWindow();
        //鑾峰彇绐楀彛瀵硅薄鐨勫弬鏁?
        WindowManager.LayoutParams wlp = window.getAttributes();
        //鑾峰彇灞忓箷瀹藉害
        Display d = window.getWindowManager().getDefaultDisplay();
        wlp.width = (int)d.getWidth();
        wlp.gravity = Gravity.TOP;
        window.setBackgroundDrawableResource(android.R.color.transparent);
        window.setAttributes(wlp);
    }




}


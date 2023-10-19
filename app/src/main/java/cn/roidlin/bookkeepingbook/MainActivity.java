package cn.roidlin.bookkeepingbook;

import adapter.AccountAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import db.AccountBean;
import db.DBManger;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    ListView todayLv;       //展示今日收支情况
    List<AccountBean> mDatas;
    AccountAdapter adapter;
    int year,month,day;
    View headerView;
    TextView topOutTv,topInTv,topbudgetTv,topConTv;
    ImageView topShowIv,searchTv,moreBtn;
    Button editBtn,moreButton;

    //获取当前时间
    private void initTime(){
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH)+1;
        day = calendar.get(Calendar.DAY_OF_MONTH);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initTime();
        initView();
        mDatas = new ArrayList<>();

        //设置适配器: 加载每一行数据到列表中
        adapter = new AccountAdapter(this,mDatas);
        todayLv.setAdapter(adapter);

        //添加头布局
        addLVHeaderView();

        Button add = findViewById(R.id.main_btn_add);
        add.setOnClickListener(this);

    }

    //初始化自带的View的方法
    private void initView() {
        todayLv = findViewById(R.id.mian_lv);
        editBtn = findViewById(R.id.main_btn_add);
        moreBtn = findViewById(R.id.main_btn_more);
        searchTv = findViewById(R.id.main_iv_search);

        editBtn.setOnClickListener(this);
        moreBtn.setOnClickListener(this);
        searchTv.setOnClickListener(this);

    }

    private void addLVHeaderView() {
        //将布局转换成View对象
        headerView = getLayoutInflater().inflate(R.layout.mian_top_tab, null);
        todayLv.addHeaderView(headerView);  //添加头布局
        //查找头布局可用控件
        topOutTv =headerView.findViewById(R.id.main_top_tab_out);
        topInTv = headerView.findViewById(R.id.item_mainlv_top_tv_in);
        topbudgetTv = headerView.findViewById(R.id.item_mainlv_top_tv_budget);
        topConTv = headerView.findViewById(R.id.item_mainlv_top_tv_day);
        topShowIv = headerView.findViewById(R.id.main_top_tab_hide);

        topbudgetTv.setOnClickListener(this);
        headerView.setOnClickListener(this);
        topbudgetTv.setOnClickListener(this);


    }

    @Override
    protected void onResume() {
        super.onResume();
        loadDBData();

    }

    private void loadDBData() {
        List<AccountBean> list = DBManger.getAccountListOneDayFromAccounttb(year,month,day);
        mDatas.clear();
        mDatas.addAll(list);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            //加一笔
            case R.id.main_btn_add:
                Intent Main_Record = new Intent(MainActivity.this,RecordActivity.class);  //跳转界面
                startActivity(Main_Record);
                break;
            //更多
            case R.id.main_btn_more:
                break;
            case R.id.item_mainlv_top_tv_budget:
                break;
            case R.id.main_top_tab_hide:
                break;
        }


    }
}
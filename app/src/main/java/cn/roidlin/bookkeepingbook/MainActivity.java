package cn.roidlin.bookkeepingbook;

import adapter.AccountAdapter;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import db.AccountBean;
import db.DBManger;
import utils.BudgetDialog;
import utils.MoreDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    ListView todayLv;       //展示今日收支情况
    List<AccountBean> mDatas;
    AccountAdapter adapter;
    int year,month,day;
    View headerView;
    TextView topOutTv,topInTv,topbudgetTv,topConTv,sy;
    ImageView topShowIv,searchTv,moreBtn;
    Button editBtn,moreButton;

    SharedPreferences preferences;


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

        preferences = getSharedPreferences("budget", Context.MODE_PRIVATE);


        mDatas = new ArrayList<>();

        //设置适配器: 加载每一行数据到列表中
        adapter = new AccountAdapter(this,mDatas);
        todayLv.setAdapter(adapter);

        //添加头布局
        addLVHeaderView();

        Button add = findViewById(R.id.main_btn_add);
        add.setOnClickListener(this);

        //长按删除
        setLvLongClickListener();

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


    //设置ListView长按事件
    private void setLvLongClickListener(){
        todayLv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                if (position == 0){
                    return false;
                }

                int pos = position - 1 ;
                AccountBean clickBean = mDatas.get(pos);        //获取正在被点击的这条信息

                //弹出提示用户是否删除的方法
                showDelectItemDialog(clickBean);


                return false;
            }
        });

    }



    private void addLVHeaderView() {
        //将布局转换成View对象
        headerView = getLayoutInflater().inflate(R.layout.mian_top_tab, null);
        todayLv.addHeaderView(headerView);  //添加头布局
        //查找头布局可用控件
        topOutTv =headerView.findViewById(R.id.main_top_tab_out);
        topInTv = headerView.findViewById(R.id.main_top_tab_tv2);
        topbudgetTv = headerView.findViewById(R.id.item_mainlv_top_tv_in);
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
        setTopTvshow();

    }

    private void loadDBData() {
        List<AccountBean> list = DBManger.getAccountListOneDayFromAccounttb(year,month,day);
        mDatas.clear();
        mDatas.addAll(list);
        adapter.notifyDataSetChanged();
    }

    private void setTopTvshow(){

        //获取今日支出的收入总金额，显示在view当中
        float incomeOneday = DBManger.getSumMoneyOneday(year,month,day,1);
        float outcomeOneday = DBManger.getSumMoneyOneday(year,month,day,0);

        String inforOneday = "今日支出 ￥"+outcomeOneday+"收入 ￥" + incomeOneday;
        topConTv.setText(inforOneday);

        //获取本月收入和支出总金额
        float incomeMonth = DBManger.getSumMoneyMonth(year,month,1);
        float outcomeMonth = DBManger.getSumMoneyMonth(year,month,0);
        topInTv.setText("本月收入￥"+incomeMonth);
        topOutTv.setText("￥" + outcomeMonth);

        //设置显示预算剩余
        float bmoney = preferences.getFloat("bmoney",0);   //预算
        if (bmoney == 0){

            topbudgetTv.setText("￥ 0 ");

        }else {

            float syMonet = bmoney - outcomeOneday;
            topbudgetTv.setText("￥"+syMonet);

        }






    }

    //显示运算设置对话框
    private void showBudgetDialog(){

        BudgetDialog dialog = new BudgetDialog(this);
        dialog.setDialogSize();
        dialog.show();

        dialog.setOnEnsureListener(new BudgetDialog.OnEnsureListener(){


            @Override
            public void onEnsure(float money) {

                SharedPreferences.Editor editor = preferences.edit();
                editor.putFloat("bmoney",money);
                editor.commit();

                //计算剩余金额
                float outComeOneMorth = DBManger.getSumMoneyMonth(year,month,0);
                float syMoney = money - outComeOneMorth; //预算剩余 = 预算 - 输出
                topbudgetTv.setText("￥"+syMoney);



            }
        });



    }

    private void showDelectItemDialog(AccountBean clickBean){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示信息")
               .setMessage("您确定要删除这条记录吗?")
                .setNegativeButton("取消",null)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        int click_id = clickBean.getId();

                        //执行删除操作
                        DBManger.deleteItemFromAccounttbById(click_id);
                        mDatas.remove(clickBean);       //实时刷新
                        adapter.notifyDataSetChanged();     //提示适配器更新数据
                        setTopTvshow();                     //改变头布局的信息


                    }
                });


            builder.create().show();

    }




    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.main_iv_search:

                Intent it1 = new Intent(this,SearchActivity.class);
                startActivity(it1);

            break;

            //加一笔
            case R.id.main_btn_add:
                Intent Main_Record = new Intent(MainActivity.this,RecordActivity.class);  //跳转界面
                startActivity(Main_Record);
                break;
            //更多
            case R.id.main_btn_more:
                MoreDialog moreDialog = new MoreDialog(this);
                moreDialog.show();
                moreDialog.setDialogSize();
                break;
            case R.id.item_mainlv_top_tv_in:

                showBudgetDialog();
                break;

            case R.id.main_top_tab_hide:
                //切换TextView明文或者密文
                toggleShow();
                break;
        }

        if (v == headerView){

        }
    }


    boolean isShow = true;
    private void toggleShow(){

        if (isShow){      //明文→密文

            PasswordTransformationMethod passwordTransformationMethod = PasswordTransformationMethod.getInstance();
            //设置隐藏
            topInTv.setTransformationMethod(passwordTransformationMethod);
            topOutTv.setTransformationMethod(passwordTransformationMethod);
            topbudgetTv.setTransformationMethod(passwordTransformationMethod);
            topShowIv.setImageResource(R.mipmap.yincang);

            isShow = false; //设置标志位为隐藏状态

        }else {

            HideReturnsTransformationMethod hideReturnsTransformationMethod = HideReturnsTransformationMethod.getInstance();
            //设置显示
            topInTv.setTransformationMethod(hideReturnsTransformationMethod);
            topOutTv.setTransformationMethod(hideReturnsTransformationMethod);
            topbudgetTv.setTransformationMethod(hideReturnsTransformationMethod);
            topShowIv.setImageResource(R.mipmap.xianshi1);

            isShow = true; //设置标志位为隐藏状态

        }


    }



}
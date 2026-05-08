package cn.roidlin.bookkeepingbook;

import cn.roidlin.bookkeepingbook.ui.adapter.AccountAdapter;
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
import cn.roidlin.bookkeepingbook.data.AccountBean;
import cn.roidlin.bookkeepingbook.ui.common.BudgetDialog;
import cn.roidlin.bookkeepingbook.ui.common.MoreDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    ListView todayLv;       //灞曠ず浠婃棩鏀舵敮鎯呭喌
    List<AccountBean> mDatas;
    AccountAdapter adapter;
    int year,month,day;
    View headerView;
    TextView topOutTv,topInTv,topbudgetTv,topConTv,sy;
    ImageView topShowIv,searchTv,moreBtn;
    Button editBtn,moreButton;

    SharedPreferences preferences;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initTime();
        initView();

        preferences = getSharedPreferences("budget", Context.MODE_PRIVATE);


        mDatas = new ArrayList<>();

        //璁剧疆閫傞厤鍣? 鍔犺浇姣忎竴琛屾暟鎹埌鍒楄〃涓?
        adapter = new AccountAdapter(this,mDatas);
        todayLv.setAdapter(adapter);

        //娣诲姞澶村竷灞€
        addLVHeaderView();

        Button add = findViewById(R.id.main_btn_add);
        add.setOnClickListener(this);



    }

    //鍒濆鍖栬嚜甯︾殑View鐨勬柟娉?
    private void initView() {
        todayLv = findViewById(R.id.mian_lv);
        editBtn = findViewById(R.id.main_btn_add);
        moreBtn = findViewById(R.id.main_btn_more);
        searchTv = findViewById(R.id.main_iv_search);

        editBtn.setOnClickListener(this);
        moreBtn.setOnClickListener(this);
        searchTv.setOnClickListener(this);


        //闀挎寜鍒犻櫎
        setLvLongClickListener();

    }

    //鑾峰彇褰撳墠鏃堕棿
    private void initTime(){
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH)+1;
        day = calendar.get(Calendar.DAY_OF_MONTH);
    }

    //璁剧疆ListView闀挎寜浜嬩欢
    private void setLvLongClickListener(){
        todayLv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                if (position == 0){
                    return false;
                }

                int pos = position - 1 ;

                //鑾峰彇姝ｅ湪琚偣鍑荤殑杩欐潯淇℃伅
                AccountBean clickBean = mDatas.get(pos);

                //寮瑰嚭鎻愮ず鐢ㄦ埛鏄惁鍒犻櫎鐨勬柟娉?
                showDelectItemDialog(clickBean);

                return false;

            }
        });

    }



    private void addLVHeaderView() {

        //灏嗗竷灞€杞崲鎴怴iew瀵硅薄
        headerView = getLayoutInflater().inflate(R.layout.mian_top_tab, null);
        todayLv.addHeaderView(headerView);  //娣诲姞澶村竷灞€

        //鏌ユ壘澶村竷灞€鍙敤鎺т欢
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
        List<AccountBean> list = UnitAPP.getRepository().getAccountsByDay(year, month, day);
        mDatas.clear();
        mDatas.addAll(list);
        adapter.notifyDataSetChanged();
    }

    private void setTopTvshow(){

        //鑾峰彇浠婃棩鏀嚭鐨勬敹鍏ユ€婚噾棰濓紝鏄剧ず鍦╲iew褰撲腑
        float incomeOneday = UnitAPP.getRepository().getSumMoneyOneday(year, month, day, 1);
        float outcomeOneday = UnitAPP.getRepository().getSumMoneyOneday(year, month, day, 0);

        String inforOneday = "今日支出: " + outcomeOneday + " 收入: " + incomeOneday;
        topConTv.setText(inforOneday);

        //鑾峰彇鏈湀鏀跺叆鍜屾敮鍑烘€婚噾棰?
        float incomeMonth = UnitAPP.getRepository().getSumMoneyMonth(year, month, 1);
        float outcomeMonth = UnitAPP.getRepository().getSumMoneyMonth(year, month, 0);
        topInTv.setText("本月收入: " + incomeMonth);
        topOutTv.setText("本月支出: " + outcomeMonth);

        //璁剧疆鏄剧ず棰勭畻鍓╀綑
        float bmoney = preferences.getFloat("bmoney",0);   //棰勭畻
        if (bmoney == 0){

            topbudgetTv.setText("预算余额: 0");

        }else {

            float syMonet = bmoney - outcomeOneday;
                topbudgetTv.setText("预算余额: " + syMonet);

        }

    }

    //鏄剧ず杩愮畻璁剧疆瀵硅瘽妗?
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

                //璁＄畻鍓╀綑閲戦
                float outComeOneMorth = UnitAPP.getRepository().getSumMoneyMonth(year, month, 0);
                //棰勭畻鍓╀綑 = 棰勭畻 - 杈撳嚭
                float syMoney = money - outComeOneMorth;
                topbudgetTv.setText("预算余额: " + syMoney);


            }
        });


    }

    //鍒犻櫎璁板綍鐨勫璇濇
    private void showDelectItemDialog(final AccountBean clickBean){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示信息")
               .setMessage("确定要删除这条记录吗？")
               .setNegativeButton("取消",null)
               .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        int click_id = clickBean.getId();

                        //鎵ц鍒犻櫎鎿嶄綔
                        UnitAPP.getRepository().deleteAccountById(click_id);
                        mDatas.remove(clickBean);       //瀹炴椂鍒锋柊
                        adapter.notifyDataSetChanged();     //鎻愮ず閫傞厤鍣ㄦ洿鏂版暟鎹?
                        setTopTvshow();                     //鏀瑰彉澶村竷灞€鐨勪俊鎭?

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

            //鍔犱竴绗?
            case R.id.main_btn_add:
                Intent Main_Record = new Intent(MainActivity.this,RecordActivity.class);  //璺宠浆鐣岄潰
                startActivity(Main_Record);
                break;
            //鏇村
            case R.id.main_btn_more:
                MoreDialog moreDialog = new MoreDialog(this);
                moreDialog.show();
                moreDialog.setDialogSize();
                break;
            case R.id.item_mainlv_top_tv_in:
                showBudgetDialog();
                break;

            case R.id.main_top_tab_hide:
                //鍒囨崲TextView鏄庢枃鎴栬€呭瘑鏂?
                toggleShow();
                break;

        }

        if (v == headerView){

        }
    }


    boolean isShow = true;
    private void toggleShow(){

        if (isShow){

            //鏄庢枃鈫掑瘑鏂?
            PasswordTransformationMethod passwordTransformationMethod = PasswordTransformationMethod.getInstance();
            //璁剧疆闅愯棌
            topInTv.setTransformationMethod(passwordTransformationMethod);
            topOutTv.setTransformationMethod(passwordTransformationMethod);
            topbudgetTv.setTransformationMethod(passwordTransformationMethod);
            topShowIv.setImageResource(R.mipmap.yincang);

            isShow = false; //璁剧疆鏍囧織浣嶄负闅愯棌鐘舵€?

        }else {

            HideReturnsTransformationMethod hideReturnsTransformationMethod = HideReturnsTransformationMethod.getInstance();
            //璁剧疆鏄剧ず
            topInTv.setTransformationMethod(hideReturnsTransformationMethod);
            topOutTv.setTransformationMethod(hideReturnsTransformationMethod);
            topbudgetTv.setTransformationMethod(hideReturnsTransformationMethod);
            topShowIv.setImageResource(R.mipmap.xianshi1);

            isShow = true; //璁剧疆鏍囧織浣嶄负闅愯棌鐘舵€?

        }


    }



}

package cn.roidlin.bookkeepingbook;

import adapter.AccountAdapter;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import db.AccountBean;
import db.DBManger;
import utils.CalenderDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class HistoryActivity extends AppCompatActivity implements View.OnClickListener{

    ListView historyLv;
    TextView timeTv;

    List<AccountBean> mDatas;
    AccountAdapter adapter;

    int year,month;

    int dialogSelPos = -1;
    int dialogSelMonth = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        historyLv = findViewById(R.id.history_lv);
        timeTv = findViewById(R.id.history_tv_time);


        //设置适配器
        mDatas = new ArrayList<>();
        adapter = new AccountAdapter(this,mDatas);
        historyLv.setAdapter(adapter);
        initTime();
        timeTv.setText(year + "年" + month + "月");
        loadData(year,month);

        setLVClickListener();

    }

    private void setLVClickListener() {

        historyLv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {

                AccountBean accountBean = mDatas.get(position);
                deleteItem(accountBean);

                return false;
            }
        });


    }

    private void deleteItem(final AccountBean accountBean) {

        final int delId = accountBean.getId();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示信息").setMessage("您确定要删除这条记录么？")
                .setNegativeButton("取消",null)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {

                        DBManger.deleteItemFromAccounttbById(delId);
                        mDatas.remove(accountBean); //实时刷新，从数据源删除
                        adapter.notifyDataSetChanged();


                    }
                });

        builder.create().show();

    }


    //获取指定年份月份收支情况的列表
    private void loadData(int year,int month) {

            List<AccountBean> list = DBManger.getAccountListOnemonthFromAccounttb(year,month);
            mDatas.clear();
            mDatas.addAll(list);
            adapter.notifyDataSetChanged();

    }

    private void initTime() {

        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH) + 1;


    }

    public void onClick(View view) {


        switch (view.getId()){

            case R.id.history_iv_back:
                finish();
                break;

            case R.id.history_iv_rili:

                CalenderDialog dialog = new CalenderDialog(this,dialogSelPos,dialogSelMonth);
                dialog.show();
                dialog.setDialogSize();
                dialog.setOnRefreshListener(new CalenderDialog.OnRefreshListener() {
                    @Override
                    public void onRefresh(int selPos, int year, int month) {

                        timeTv.setText(year + "年" + month+"月");
                        loadData(year,month);
                        dialogSelPos = selPos;
                        dialogSelMonth = month;

                    }
                });


                break;

        }




    }
}
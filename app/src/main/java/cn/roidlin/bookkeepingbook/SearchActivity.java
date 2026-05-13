package cn.roidlin.bookkeepingbook;

import cn.roidlin.bookkeepingbook.ui.adapter.AccountAdapter;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import cn.roidlin.bookkeepingbook.UnitAPP;
import cn.roidlin.bookkeepingbook.data.AccountBean;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener{


    ListView searchLv;
    EditText searchEt;
    TextView emptyTv;
    List<AccountBean> mDatas;   // 数据源
    AccountAdapter adapter;     // 适配器对象


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        initView();

        mDatas = new ArrayList<>();
        adapter = new AccountAdapter(this,mDatas);
        searchLv.setAdapter(adapter);
        searchLv.setEmptyView(emptyTv);     // 设置空布局

    }

    private void initView(){

        searchEt = findViewById(R.id.search_et);
        searchLv = findViewById(R.id.search_lv);
        emptyTv = findViewById(R.id.search_tv_empty);

    }

    public void onClick(View view){
        switch (view.getId()){

            case R.id.search_iv_back:

                finish();
                break;

            case R.id.search_iv_sh:
                String msg = searchEt.getText().toString().trim();
                // 输入为空时，提示后直接返回
                if (TextUtils.isEmpty(msg)){
                    Toast.makeText(this,"输入内容不能为空",Toast.LENGTH_SHORT).show();
                    return;
                }

                // 开始搜索
                List<AccountBean> list = UnitAPP.getRepository().searchAccountsByRemark(msg);

                mDatas.clear();
                mDatas.addAll(list);
                adapter.notifyDataSetChanged();

                break;

        }



    }


}

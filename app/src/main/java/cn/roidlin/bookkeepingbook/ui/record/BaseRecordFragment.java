package cn.roidlin.bookkeepingbook.ui.record;

import android.inputmethodservice.KeyboardView;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import cn.roidlin.bookkeepingbook.R;
import cn.roidlin.bookkeepingbook.data.AccountBean;
import cn.roidlin.bookkeepingbook.data.TypeBean;
import cn.roidlin.bookkeepingbook.ui.common.KeyBoardUtils;
import cn.roidlin.bookkeepingbook.ui.common.SelectTimeDialog;
import cn.roidlin.bookkeepingbook.ui.common.BeizhuDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public abstract class BaseRecordFragment extends Fragment implements View.OnClickListener {

        // 初始化控件
        KeyboardView keyboardView;
        EditText moneyEt;
        ImageView typeIv;
        TextView typeTv,beizhutv,timeTv;
        GridView typeGv;
        List<TypeBean> typeList;
        TypeBaseAdapter adapter;
    // 记录当前待保存的账目数据
     AccountBean accountBean;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        accountBean = new AccountBean(); // 创建对象
        accountBean.setTypename("其他");
        accountBean.setsImageId(R.mipmap.qita);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_outcome, container, false);
        initView(view);
        loadDataToGv();
        // 设置每一项的点击事件
        setGVListener();
        setInitTime();
        return view;

    }
    /*
    * 获取当前时间并显示到 timeTv
    * */
    private void setInitTime() {

        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
        String time = sdf.format(date);
        timeTv.setText(time);
        accountBean.setTime(time);

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH)+1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        accountBean.setYear(year);
        accountBean.setMonth(month);
        accountBean.setDay(day);

    }

    private void setGVListener(){
                typeGv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        // 这里不能重新 new 适配器，否则会丢失选中状态

                        adapter.selectPos = position;

                        // 通知重绘
                        adapter.notifyDataSetChanged();
                        TypeBean typeBean = typeList.get(position);
                        String typename = typeBean.getTypename();
                        typeTv.setText(typename);
                        accountBean.setTypename(typename);

                        int simageId = typeBean.getSimageId();
                        typeIv.setImageResource(simageId);
                        accountBean.setsImageId(simageId);

                    }
                });
    }
    /*
    * GridView 填充数据
    * */
        public void loadDataToGv(){
            typeList = new ArrayList<>();
            adapter = new TypeBaseAdapter(getContext(), typeList);
            typeGv.setAdapter(adapter);
        }

    private void initView(View view) {

        keyboardView = view.findViewById(R.id.frag_record_keyboard);
        moneyEt = view.findViewById(R.id.frag_record_money);

        typeIv = view.findViewById(R.id.frag_record_rl_lv);
        typeGv = view.findViewById(R.id.frag_record_gv);
        typeTv = view.findViewById(R.id.frag_record_tv_type);
        beizhutv = view.findViewById(R.id.frag_record_tv_beizu);
        timeTv = view.findViewById(R.id.frag_record_tv_time);

        beizhutv.setOnClickListener(this);
        timeTv.setOnClickListener(this);

        // 显示自定义软键盘
        KeyBoardUtils boardUtils = new KeyBoardUtils(keyboardView,moneyEt);
        boardUtils.showKeyboard();
        // 设置确认监听
        boardUtils.setOnEnsureListener(new KeyBoardUtils.OnEnsureListener() {
            @Override
            public void onEnsure() {
                // 点击了确认按钮
                String moneyStr = moneyEt.getText().toString();
                if (TextUtils.isEmpty(moneyStr) || moneyStr.equals("0")){
                    getActivity().finish();
                    return;
                }
                float money = Float.parseFloat(moneyStr);
                accountBean.setMoney(money);
                // 保存记录到数据库
                saveAccountToDB();
                // 返回上一页
                getActivity().finish();
            }
        });

    }

    // 子类必须实现该方法
    public abstract void saveAccountToDB();

    @Override
    public void onClick(View v) {

            switch (v.getId()){

                case R.id.frag_record_tv_time:
                    showTimedialog();
                    break;
                case R.id.frag_record_tv_beizu:
                    showBZdialog();
                    break;

            }

    }

    private void showTimedialog() {
            SelectTimeDialog dialog = new SelectTimeDialog(getContext());
            dialog.show();

            dialog.setOnEnsureLister(new SelectTimeDialog.OnEnsureLister() {
                @Override
                public void onEnsure(String time, int year, int month, int day) {
                    timeTv.setText(time);
                    accountBean.setTime(time);
                    accountBean.setYear(year);
                    accountBean.setMonth(month);
                    accountBean.setDay(day);
                }
            });
    }

    public void showBZdialog(){
            BeizhuDialog dialog = new BeizhuDialog(getContext());
            dialog.show();
            dialog.setDialogSize();
            dialog.setOnEnsureListener(new BeizhuDialog.OnEnsureListener() {
                @Override
                public void onEnsure() {
                    String msg = dialog.getEditText();
                    if (!TextUtils.isEmpty(msg)){
                        beizhutv.setText(msg);
                        accountBean.setBeizhu(msg);
                    }
                    dialog.cancel();
                }
            });
    }
}

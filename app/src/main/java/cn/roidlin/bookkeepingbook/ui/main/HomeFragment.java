package cn.roidlin.bookkeepingbook.ui.main;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import cn.roidlin.bookkeepingbook.R;
import cn.roidlin.bookkeepingbook.RecordActivity;
import cn.roidlin.bookkeepingbook.SearchActivity;
import cn.roidlin.bookkeepingbook.UnitAPP;
import cn.roidlin.bookkeepingbook.data.AccountBean;
import cn.roidlin.bookkeepingbook.ui.adapter.AccountAdapter;
import cn.roidlin.bookkeepingbook.ui.common.BudgetDialog;
import cn.roidlin.bookkeepingbook.ui.common.MoreDialog;

public class HomeFragment extends Fragment implements View.OnClickListener {
    private ListView todayLv;       // 展示今日收支情况
    private List<AccountBean> mDatas;
    private AccountAdapter adapter;
    private int year;
    private int month;
    private int day;
    private View headerView;
    private TextView topOutTv;
    private TextView topInTv;
    private TextView topbudgetTv;
    private TextView topConTv;
    private ImageView topShowIv;
    private ImageView searchTv;
    private ImageView moreBtn;
    private Button editBtn;

    private SharedPreferences preferences;
    private boolean isShow = true;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initTime();
        initView(view);

        preferences = requireContext().getSharedPreferences("budget", Context.MODE_PRIVATE);
        mDatas = new ArrayList<>();

        // 首页列表仍然复用原有适配器和头布局逻辑。
        adapter = new AccountAdapter(requireContext(), mDatas);
        todayLv.setAdapter(adapter);
        addLVHeaderView();
    }

    @Override
    public void onResume() {
        super.onResume();
        loadDBData();
        setTopTvShow();
    }

    // 初始化界面控件并挂载点击事件。
    private void initView(View rootView) {
        todayLv = rootView.findViewById(R.id.mian_lv);
        editBtn = rootView.findViewById(R.id.main_btn_add);
        moreBtn = rootView.findViewById(R.id.main_btn_more);
        searchTv = rootView.findViewById(R.id.main_iv_search);

        editBtn.setOnClickListener(this);
        moreBtn.setOnClickListener(this);
        searchTv.setOnClickListener(this);

        setLvLongClickListener();
    }

    private void initTime() {
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH) + 1;
        day = calendar.get(Calendar.DAY_OF_MONTH);
    }

    private void setLvLongClickListener() {
        todayLv.setOnItemLongClickListener((AdapterView<?> parent, View view, int position, long id) -> {
            if (position == 0) {
                return false;
            }

            int pos = position - 1;
            AccountBean clickBean = mDatas.get(pos);
            showDeleteItemDialog(clickBean);
            return false;
        });
    }

    private void addLVHeaderView() {
        headerView = LayoutInflater.from(requireContext()).inflate(R.layout.mian_top_tab, todayLv, false);
        todayLv.addHeaderView(headerView);

        topOutTv = headerView.findViewById(R.id.main_top_tab_out);
        topInTv = headerView.findViewById(R.id.main_top_tab_tv2);
        topbudgetTv = headerView.findViewById(R.id.item_mainlv_top_tv_in);
        topConTv = headerView.findViewById(R.id.item_mainlv_top_tv_day);
        topShowIv = headerView.findViewById(R.id.main_top_tab_hide);

        topbudgetTv.setOnClickListener(this);
        topShowIv.setOnClickListener(this);
    }

    private void loadDBData() {
        List<AccountBean> list = UnitAPP.getRepository().getAccountsByDay(year, month, day);
        mDatas.clear();
        mDatas.addAll(list);
        adapter.notifyDataSetChanged();
    }

    private void setTopTvShow() {
        float incomeOneday = UnitAPP.getRepository().getSumMoneyOneday(year, month, day, 1);
        float outcomeOneday = UnitAPP.getRepository().getSumMoneyOneday(year, month, day, 0);
        topConTv.setText("今日支出: " + outcomeOneday + " 收入: " + incomeOneday);

        float incomeMonth = UnitAPP.getRepository().getSumMoneyMonth(year, month, 1);
        float outcomeMonth = UnitAPP.getRepository().getSumMoneyMonth(year, month, 0);
        topInTv.setText("本月收入: " + incomeMonth);
        topOutTv.setText("本月支出: " + outcomeMonth);

        float budgetMoney = preferences.getFloat("bmoney", 0);
        if (budgetMoney == 0) {
            topbudgetTv.setText("预算余额: 0");
        } else {
            float remainMoney = budgetMoney - outcomeOneday;
            topbudgetTv.setText("预算余额: " + remainMoney);
        }
    }

    private void showBudgetDialog() {
        BudgetDialog dialog = new BudgetDialog(requireContext());
        dialog.setDialogSize();
        dialog.show();
        dialog.setOnEnsureListener(money -> {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putFloat("bmoney", money);
            editor.commit();

            float outcomeMonth = UnitAPP.getRepository().getSumMoneyMonth(year, month, 0);
            float remainMoney = money - outcomeMonth;
            topbudgetTv.setText("预算余额: " + remainMoney);
        });
    }

    private void showDeleteItemDialog(final AccountBean clickBean) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("提示信息")
                .setMessage("确定要删除这条记录吗？")
                .setNegativeButton("取消", null)
                .setPositiveButton("确定", (DialogInterface dialog, int which) -> {
                    int clickId = clickBean.getId();
                    UnitAPP.getRepository().deleteAccountById(clickId);
                    mDatas.remove(clickBean);
                    adapter.notifyDataSetChanged();
                    setTopTvShow();
                });
        builder.create().show();
    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        if (viewId == R.id.main_iv_search) {
            startActivity(new Intent(requireContext(), SearchActivity.class));
        } else if (viewId == R.id.main_btn_add) {
            startActivity(new Intent(requireContext(), RecordActivity.class));
        } else if (viewId == R.id.main_btn_more) {
            MoreDialog moreDialog = new MoreDialog(requireContext());
            moreDialog.show();
            moreDialog.setDialogSize();
        } else if (viewId == R.id.item_mainlv_top_tv_in) {
            showBudgetDialog();
        } else if (viewId == R.id.main_top_tab_hide) {
            toggleShow();
        }
    }

    private void toggleShow() {
        if (isShow) {
            PasswordTransformationMethod passwordTransformationMethod =
                    PasswordTransformationMethod.getInstance();
            topInTv.setTransformationMethod(passwordTransformationMethod);
            topOutTv.setTransformationMethod(passwordTransformationMethod);
            topbudgetTv.setTransformationMethod(passwordTransformationMethod);
            topShowIv.setImageResource(R.mipmap.yincang);
            isShow = false;
        } else {
            HideReturnsTransformationMethod hideReturnsTransformationMethod =
                    HideReturnsTransformationMethod.getInstance();
            topInTv.setTransformationMethod(hideReturnsTransformationMethod);
            topOutTv.setTransformationMethod(hideReturnsTransformationMethod);
            topbudgetTv.setTransformationMethod(hideReturnsTransformationMethod);
            topShowIv.setImageResource(R.mipmap.xianshi1);
            isShow = true;
        }
    }
}

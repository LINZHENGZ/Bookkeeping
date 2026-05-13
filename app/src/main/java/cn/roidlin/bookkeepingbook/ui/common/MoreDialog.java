package cn.roidlin.bookkeepingbook.ui.common;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.*;
import android.widget.Button;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import cn.roidlin.bookkeepingbook.HistoryActivity;
import cn.roidlin.bookkeepingbook.R;


import cn.roidlin.bookkeepingbook.AboutActivity;

/**
 * @author LINZHENGZ
 * @version 1.0
 * Create by 20232023/12/26 13:55
 */

public class MoreDialog extends Dialog implements View.OnClickListener {


    Button aboutBtn,settingBtn,historyBtn,infoBtn;
    ImageView errorlv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_more);

        aboutBtn = findViewById(R.id.dialog_more_btn_about);
        settingBtn = findViewById(R.id.dialog_more_btn_setting);
        historyBtn = findViewById(R.id.dialog_more_btn_record);
        infoBtn = findViewById(R.id.dialog_more_btn_info);
        errorlv = findViewById(R.id.dialog_more_iv);


        aboutBtn.setOnClickListener(this);
        settingBtn.setOnClickListener(this);
        historyBtn.setOnClickListener(this);
        infoBtn.setOnClickListener(this);
        errorlv.setOnClickListener(this);


    }

    public MoreDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    public void onClick(View v) {

        Intent intent = new Intent();

        switch (v.getId()){

            case R.id.dialog_more_btn_about:

                // 关于
                intent.setClass(getContext(),AboutActivity.class);
                getContext().startActivity(intent);

                break;

            case R.id.dialog_more_btn_setting:

                break;
            case R.id.dialog_more_btn_record:

                intent.setClass(getContext(), HistoryActivity.class);
                getContext().startActivity(intent);

                break;
            case R.id.dialog_more_btn_info:

                break;
            case R.id.dialog_more_iv:

                break;



        }

        cancel();

    }

    public void setDialogSize(){
        // 底部弹窗宽度按屏幕百分比控制，并限制在一个合理最大宽度内。
        ScreenAdapter.applyBottomDialog(this, 0.94f, 420f);
    }
}

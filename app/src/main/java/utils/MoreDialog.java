package utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.*;
import android.widget.Button;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import cn.roidlin.bookkeepingbook.R;


import cn.roidlin.bookkeepingbook.aboutActivity;

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
        aboutBtn = findViewById(R.id.dialog_more_btn_about);
        errorlv = findViewById(R.id.dialog_more_iv);


        aboutBtn.setOnClickListener(this);
        settingBtn.setOnClickListener(this);
        historyBtn.setOnClickListener(this);
        aboutBtn.setOnClickListener(this);
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

                intent.setClass(getContext(),aboutActivity.class);
                getContext().startActivity(intent);

                break;
            case R.id.dialog_more_btn_setting:
                break;
            case R.id.dialog_more_btn_record:
                break;
            case R.id.dialog_more_btn_info:
                break;
            case R.id.dialog_more_iv:
                break;



        }

        cancel();

    }

    public void setDialogSize(){
        //获取当前窗口对象
        Window window = getWindow();
        //获取窗口对象的参数
        WindowManager.LayoutParams wlp = window.getAttributes();
        //获取屏幕宽度
        Display d = window.getWindowManager().getDefaultDisplay();
        wlp.width = (int)d.getWidth();
        wlp.gravity = Gravity.BOTTOM;
        window.setBackgroundDrawableResource(android.R.color.transparent);
        window.setAttributes(wlp);
    }
}

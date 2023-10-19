package utils;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import androidx.annotation.NonNull;
import cn.roidlin.bookkeepingbook.R;

/**
 * @author LINZHENGZ
 * @version 1.0
 * Create by 20232023/1/5 15:34
 */

public class SelectTimeDialog extends Dialog implements View.OnClickListener {

    EditText hourEt,minuteEt;
    DatePicker datePicker;
    Button ensureBtn,cancelBtn;

    public interface OnEnsureLister{
        public void onEnsure(String time,int year,int month,int day);
    }
    OnEnsureLister onEnsureLister;

    public void  setOnEnsureLister(OnEnsureLister onEnsureLister){
        this.onEnsureLister = onEnsureLister;
    }


    public SelectTimeDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_time);
        hourEt = findViewById(R.id.dialog_time_et_hour);
        minuteEt = findViewById(R.id.dialog_time_et_minute);
        datePicker  = findViewById(R.id.dialog_time_datepick);
        ensureBtn = findViewById(R.id.dialog_time_btright);
        cancelBtn = findViewById(R.id.dialog_time_btleft);
        ensureBtn.setOnClickListener(this);
        cancelBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.dialog_time_btleft:
                cancel();
                break;
            case R.id.dialog_time_btright:
                int year = datePicker.getYear();
                int mouth = datePicker.getMonth()+1;
                int dayofMonth = datePicker.getDayOfMonth();
                String monthStr = String.valueOf(mouth);
                if (mouth<10){
                    monthStr = "0" + mouth;
                }
                String dayStr = String.valueOf(dayofMonth);
                if (dayofMonth<10){
                    dayStr = "0"+dayofMonth;
                }

                //获取输入的小时和分钟
                String hourStr = hourEt.getText().toString();
                String minuteStr = minuteEt.getText().toString();
                int hour = 0;
                if (!TextUtils.isEmpty(hourStr)){
                    hour = Integer.parseInt(hourStr);
                    hour=hour%24;
                }
                int minute = 0;
                if (!TextUtils.isEmpty(minuteStr)){
                    minute = Integer.parseInt(minuteStr);
                    minute = minute%60;
                }

                hourStr = String.valueOf(hour);
                minuteStr = String.valueOf(minute);

                if (hour<10){
                    hourStr="0"+hour;
                }
                if (minute<10){
                    minuteStr = "0"+minute;
                }
                String timeFormat = year+"年"+monthStr+"月"+dayStr+"日"+hourStr+":"+minuteStr;

                if (onEnsureLister != null){
                    onEnsureLister.onEnsure(timeFormat,year,mouth,dayofMonth);
                }
                cancel();
                break;

        }

    }
    private void hideDatePickerHeader(){
        ViewGroup rootView = (ViewGroup) datePicker.getChildAt(0);
        if (rootView == null){
            return;
        }
        View headerView = rootView.getChildAt(0);
        if (headerView == null){
            return;
        }
        //5.0+
        int headerId = getContext().getResources().getIdentifier("day_picker_selector_layout","id","android");
        if (headerId == headerView.getId()){
            headerView.setVisibility(View.GONE);
            ViewGroup.LayoutParams layoutParamsRoot = rootView.getLayoutParams();
            layoutParamsRoot.width = ViewGroup.LayoutParams.WRAP_CONTENT;
            rootView.setLayoutParams(layoutParamsRoot);

            ViewGroup animator = (ViewGroup) rootView.getChildAt(1);
            ViewGroup.LayoutParams layoutParamsAnimator = animator.getLayoutParams();
            layoutParamsAnimator.width = ViewGroup.LayoutParams.WRAP_CONTENT;
            animator.setLayoutParams(layoutParamsAnimator);

            View child = animator.getChildAt(0);
            ViewGroup.LayoutParams layoutParamsChild = child.getLayoutParams();
            layoutParamsChild.width = ViewGroup.LayoutParams.WRAP_CONTENT;
            child.setLayoutParams(layoutParamsChild);
            return;
        }
            //6.0+
             headerId = getContext().getResources().getIdentifier("date_picker_header","id","android");
            if (headerId == headerView.getId()){
                headerView.setVisibility(View.GONE);
            }


    }
}

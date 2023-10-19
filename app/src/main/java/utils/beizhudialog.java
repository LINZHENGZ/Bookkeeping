package utils;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.*;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.NonNull;
import cn.roidlin.bookkeepingbook.R;

/**
 * @author LINZHENGZ
 * @version 1.0
 * Create by 20232023/1/4 16:43
 */

public class beizhudialog extends Dialog implements View.OnClickListener {
    EditText et;
    Button cancelBtn,ensureBtn;
    OnEnsureListener onEnsureListener;

    //设定回调接口的方法
    public void setOnEnsureListener(OnEnsureListener onEnsureListener) {
        this.onEnsureListener = onEnsureListener;
    }

    public beizhudialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_beizhu);
        et = findViewById(R.id.dialog_beizhu_et);
        cancelBtn = findViewById(R.id.dialog_beizhu_btleft);
        ensureBtn = findViewById(R.id.dialog_beizhu_btright);
        cancelBtn.setOnClickListener(this);
        ensureBtn.setOnClickListener(this);

    }
    public interface OnEnsureListener{
        void onEnsure();
    }

    @Override
    public void onClick(View v) {
                switch (v.getId()){
                    case R.id.dialog_beizhu_btleft:
                        cancel();
                        break;
                    case  R.id.dialog_beizhu_btright:
                        if (onEnsureListener!=null){
                            onEnsureListener.onEnsure();
                        }
                        break;
                }
    }
    //获取输入数据的方法
    public String getEditText(){
        return et.getText().toString().trim();
    }
    //设置Dialog的尺寸和屏幕
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
        handler.sendEmptyMessageDelayed(1,100);

    }
    Handler handler = new Handler(){

        @Override
        public void handleMessage(@NonNull Message msg) {
            InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.toggleSoftInput(0,inputMethodManager.HIDE_NOT_ALWAYS);
        }
    };


}

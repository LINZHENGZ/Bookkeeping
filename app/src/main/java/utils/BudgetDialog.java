package utils;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.*;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import cn.roidlin.bookkeepingbook.R;

/**
 * @author LINZHENGZ
 * @version 1.0
 * Create by 20232023/10/29 20:38
 */

public class BudgetDialog extends Dialog implements View.OnClickListener {

    ImageView cancellv;
    Button ensureBtn;
    EditText moneyEt;

    public interface OnEnsureListener{

        public void onEnsure(float money);

    }
    OnEnsureListener onEnsureListener;

    public void setOnEnsureListener(OnEnsureListener onEnsureListener) {
        this.onEnsureListener = onEnsureListener;
    }

    public BudgetDialog(@NonNull Context context) {
        super(context);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_budge);

        cancellv = findViewById(R.id.dialog_budget_iv_error);
        ensureBtn = findViewById(R.id.dialog_budget_btn_ensure);
        moneyEt = findViewById(R.id.dialog_budget_et);
        cancellv.setOnClickListener(this);
        ensureBtn.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.dialog_budget_iv_error:
                cancel();
                break;
            case R.id.dialog_budget_btn_ensure:
                String data = moneyEt.getText().toString();
                if (TextUtils.isEmpty(data)){

                    Toast.makeText(getContext(),"输入数据不能为空!",Toast.LENGTH_SHORT).show();
                    return;

                }

                float money = Float.parseFloat(data);
                if (money <= 0){

                    Toast.makeText(getContext(), "预算金额必须大于0", Toast.LENGTH_SHORT).show();
                    return;

                }

                if (onEnsureListener != null){

                    onEnsureListener.onEnsure(money);

                }

                cancel();
                break;

        }
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

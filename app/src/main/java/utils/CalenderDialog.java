package utils;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;

import java.util.Calendar;
import java.util.List;

public class CalenderDialog extends Dialog implements View.OnClickListener {

    ImageView errorIv;
    GridView gv;
    LinearLayout hsvLayout;

    List<TextView> hsvViewList;
    List<Integer> yearList;

    int selectPos = -1 ;    //表示正在被点击的年份的位置
    private Calendar calendar;





    public CalenderDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    public void onClick(View view) {





    }
}

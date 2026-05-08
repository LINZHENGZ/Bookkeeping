package cn.roidlin.bookkeepingbook;

import cn.roidlin.bookkeepingbook.ui.adapter.RecordPagerAdater;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import com.google.android.material.tabs.TabLayout;
import cn.roidlin.bookkeepingbook.ui.record.IncomeFragment;
import cn.roidlin.bookkeepingbook.ui.record.OutcomeFragment;

import java.util.ArrayList;
import java.util.List;

public class RecordActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        tabLayout = findViewById(R.id.record_tabs);
        viewPager = findViewById(R.id.record_vp);

        initPager();

    }

    private void initPager() {
        List<Fragment> fragmentList = new ArrayList<>();

        OutcomeFragment outFrag = new OutcomeFragment();        //鏀嚭

        IncomeFragment inFrag = new IncomeFragment();          //鏀跺叆

        fragmentList.add(outFrag);
        fragmentList.add(inFrag);

        RecordPagerAdater pagerAdater = new RecordPagerAdater(getSupportFragmentManager(),fragmentList);
        viewPager.setAdapter(pagerAdater);
        tabLayout.setupWithViewPager(viewPager);


    }

    public void onClick(View view) {
        switch (view.getId()){
            case R.id.record_iv_back:
                finish();
                break;
        }
    }
}

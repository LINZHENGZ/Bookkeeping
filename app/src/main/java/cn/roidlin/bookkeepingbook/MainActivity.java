package cn.roidlin.bookkeepingbook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import cn.roidlin.bookkeepingbook.ui.main.HomeFragment;
import cn.roidlin.bookkeepingbook.ui.main.PlaceholderFragment;

public class MainActivity extends AppCompatActivity {
    private static final String KEY_SELECTED_TAB = "selected_tab";
    private static final String TAG_HOME = "tab_home";
    private static final String TAG_RECORD = "tab_record";
    private static final String TAG_STATS = "tab_stats";
    private static final String TAG_PROFILE = "tab_profile";

    private BottomNavigationView bottomNavigationView;
    private Fragment currentFragment;
    private HomeFragment homeFragment;
    private PlaceholderFragment recordFragment;
    private PlaceholderFragment statsFragment;
    private PlaceholderFragment profileFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.main_bottom_nav);
        restoreFragments();

        bottomNavigationView.setOnItemSelectedListener(item -> {
            switchToTab(item.getItemId());
            return true;
        });

        int selectedTab = R.id.navigation_home;
        if (savedInstanceState != null) {
            selectedTab = savedInstanceState.getInt(KEY_SELECTED_TAB, R.id.navigation_home);
        }
        bottomNavigationView.setSelectedItemId(selectedTab);
    }

    private void restoreFragments() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment home = fragmentManager.findFragmentByTag(TAG_HOME);
        Fragment record = fragmentManager.findFragmentByTag(TAG_RECORD);
        Fragment stats = fragmentManager.findFragmentByTag(TAG_STATS);
        Fragment profile = fragmentManager.findFragmentByTag(TAG_PROFILE);

        if (home instanceof HomeFragment) {
            homeFragment = (HomeFragment) home;
        }
        if (record instanceof PlaceholderFragment) {
            recordFragment = (PlaceholderFragment) record;
        }
        if (stats instanceof PlaceholderFragment) {
            statsFragment = (PlaceholderFragment) stats;
        }
        if (profile instanceof PlaceholderFragment) {
            profileFragment = (PlaceholderFragment) profile;
        }

        // 找回当前正在显示的 Fragment，避免旋转屏幕后重复创建。
        for (Fragment fragment : fragmentManager.getFragments()) {
            if (fragment != null && !fragment.isHidden()) {
                currentFragment = fragment;
                break;
            }
        }
    }

    private void switchToTab(int itemId) {
        Fragment targetFragment;
        String tag;

        if (itemId == R.id.navigation_record) {
            if (recordFragment == null) {
                recordFragment = PlaceholderFragment.newInstance(
                        getString(R.string.tab_record),
                        getString(R.string.placeholder_record_desc)
                );
            }
            targetFragment = recordFragment;
            tag = TAG_RECORD;
        } else if (itemId == R.id.navigation_stats) {
            if (statsFragment == null) {
                statsFragment = PlaceholderFragment.newInstance(
                        getString(R.string.tab_stats),
                        getString(R.string.placeholder_stats_desc)
                );
            }
            targetFragment = statsFragment;
            tag = TAG_STATS;
        } else if (itemId == R.id.navigation_profile) {
            if (profileFragment == null) {
                profileFragment = PlaceholderFragment.newInstance(
                        getString(R.string.tab_profile),
                        getString(R.string.placeholder_profile_desc)
                );
            }
            targetFragment = profileFragment;
            tag = TAG_PROFILE;
        } else {
            if (homeFragment == null) {
                homeFragment = new HomeFragment();
            }
            targetFragment = homeFragment;
            tag = TAG_HOME;
        }

        if (targetFragment == currentFragment) {
            return;
        }

        // 使用 show/hide 保留每个 Tab 的界面状态，切换时不会反复重建。
        androidx.fragment.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (currentFragment != null) {
            transaction.hide(currentFragment);
        }
        if (targetFragment.isAdded()) {
            transaction.show(targetFragment);
        } else {
            transaction.add(R.id.main_fragment_container, targetFragment, tag);
        }
        transaction.commit();
        currentFragment = targetFragment;
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_SELECTED_TAB, bottomNavigationView.getSelectedItemId());
    }
}

package cn.roidlin.bookkeepingbook.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import cn.roidlin.bookkeepingbook.R;

public class PlaceholderFragment extends Fragment {
    private static final String ARG_TITLE = "arg_title";
    private static final String ARG_DESC = "arg_desc";

    public static PlaceholderFragment newInstance(String title, String desc) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TITLE, title);
        args.putString(ARG_DESC, desc);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tab_placeholder, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView titleTv = view.findViewById(R.id.placeholder_title_tv);
        TextView descTv = view.findViewById(R.id.placeholder_desc_tv);

        Bundle args = getArguments();
        if (args == null) {
            return;
        }

        // 占位页先只承载标题和说明，后续可以无缝替换为真实交互内容。
        titleTv.setText(args.getString(ARG_TITLE, ""));
        descTv.setText(args.getString(ARG_DESC, ""));
    }
}

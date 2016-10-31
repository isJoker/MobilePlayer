package com.atguigu.player.ui.fragment.fourth.child;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.atguigu.player.R;
import com.atguigu.player.base.BaseFragment;


/**
 * Created by YoKeyword on 16/6/6.
 */
public class AvatarFragment extends BaseFragment {

    public static AvatarFragment newInstance() {

        Bundle args = new Bundle();

        AvatarFragment fragment = new AvatarFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.zhihu_fragment_fourth_avatar, container, false);
        return view;
    }
}

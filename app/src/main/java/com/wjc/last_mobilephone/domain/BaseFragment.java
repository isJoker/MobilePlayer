package com.wjc.last_mobilephone.domain;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 *
 * 作用：基类，公共类
 * VideoFragment,AudioFragment,NetVideoFragment,NetAudioFragment继承BaseFragment
 */
public abstract class BaseFragment extends Fragment {

    /**
     * 上下文
     */
    public Context mContext;

    /**
     * 当BaseFragment被创建的时候被系统调用
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
    }


    /**
     * 当创建视图的时候调用该方法
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        return initView();
    }


    /**
     * 抽象方法，由孩子实现；达到自己特有效果
     * @return
     */
    public abstract View initView();


    /**
     * 当系统创建Activity完成的时候回调这个方法
     * 绑定数据
     * @param savedInstanceState
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }


    /**
     * 当孩子需要联网请求数据，绑定数据等重写该方法;此方法执行在initView之后
     */
    public void initData() {

    }
}

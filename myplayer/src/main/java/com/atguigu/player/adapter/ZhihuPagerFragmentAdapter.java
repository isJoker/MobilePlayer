package com.atguigu.player.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.atguigu.player.ui.fragment.second.child.childpager.FirstPagerFragment;
import com.atguigu.player.ui.fragment.second.child.childpager.ListViewFragment;
import com.atguigu.player.ui.fragment.second.child.childpager.RecyclerViewPagerFragment;


/**
 * Created by YoKeyword on 16/6/5.
 */
public class ZhihuPagerFragmentAdapter extends FragmentPagerAdapter {
    private String[] mTab = new String[]{"推荐", "类型ListView", "RecyclerView"};

    public ZhihuPagerFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return FirstPagerFragment.newInstance();
        }  else if(position == 1){
            return  ListViewFragment.newInstance(position);
        }  else {
            return RecyclerViewPagerFragment.newInstance(position);
        }
    }

    @Override
    public int getCount() {
        return mTab.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTab[position];
    }
}

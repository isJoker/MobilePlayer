package com.wjc.last_mobilephone;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.wjc.last_mobilephone.fragment.AudioFragment;
import com.wjc.last_mobilephone.fragment.BaseFragment;
import com.wjc.last_mobilephone.fragment.ListViewFragment;
import com.wjc.last_mobilephone.fragment.NetVideoFragment;
import com.wjc.last_mobilephone.fragment.RecyclerViewFragment;
import com.wjc.last_mobilephone.fragment.VideoFragment;

import java.util.ArrayList;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;

/**
 * 作用：主页面
 */
public class MainActivity extends AppCompatActivity {

    private RadioGroup rg_main;
    /**
     * 各个页面的Fragment
     */
    private ArrayList<BaseFragment> baseFragments;

    /**
     * 不同Fragment在列表中的位置
     */
    private int position = 0;
    private Fragment mContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isGrantExternalRW(this);
        initView();
        initFragment();
        setListener();
    }

    /**
     * 设置监听RadioGroup
     */
    private void setListener() {
        rg_main.setOnCheckedChangeListener(new MyOnCheckedChangeListener());
        rg_main.check(R.id.rb_local_video);
    }

    class MyOnCheckedChangeListener implements RadioGroup.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {

            switch (checkedId) {

                case R.id.rb_local_video://0
                    position = 0;
                    break;
                case R.id.rb_local_audio://1
                    position = 1;
                    break;
                case R.id.rb_net_video://2
                    position = 2;
                    break;
                case R.id.rb_net_audio://3
                    position = 3;
                    break;
                case R.id.rb_recyclerview_audio://3
                    position = 4;
                    break;
            }

            //根据位置从集合中取对应的Fragment
            Fragment toFragment = getFragment(position);

            //把对应的Fragment绑定到Activity中
            switchFragment(mContent, toFragment);

        }


    }


    /**
     * @param fromFragment 刚才显示过的Fragment -隐藏
     * @param toFragment   马上要显示的Fragment-显示，添加
     */
    private void switchFragment(Fragment fromFragment, Fragment toFragment) {
        if (mContent != toFragment) {
            mContent = toFragment;

            if (toFragment != null) {

                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                //判断toFragment是否添加
                if (!toFragment.isAdded()) {

                    //如果没有添加
                    //隐藏fromFragment
                    if (fromFragment != null) {
                        ft.hide(fromFragment);
                    }
                    //添加toFragment
                    ft.add(R.id.fl_content, toFragment).commit();
                    //提交
//                   ft.commit();

                } else {
                    //如果添加了
                    //隐藏fromFragment
                    if (fromFragment != null) {
                        ft.hide(fromFragment);
                    }
                    //显示toFragment
                    ft.show(toFragment).commit();
                    //提交
                }

            }


        }

    }



    /**
     * 根据位置取对应的Frament
     *
     * @param position
     * @return
     */
    private BaseFragment getFragment(int position) {
        if (baseFragments != null && baseFragments.size() > 0) {
            return baseFragments.get(position);//返回四个Fragment中的某一个
        }
        return null;
    }

    /**
     * 初始化Fragment
     */
    private void initFragment() {
        baseFragments = new ArrayList<>();
        baseFragments.add(new VideoFragment());//本地视频Fragment
        baseFragments.add(new AudioFragment());//本地音频Fragment
        baseFragments.add(new NetVideoFragment());//网络视频Fragment
        baseFragments.add(new ListViewFragment());//网络音频Fragment
        baseFragments.add(new RecyclerViewFragment());//网络音频Fragment
    }

    private void initView() {
        setContentView(R.layout.activity_main);
        rg_main = (RadioGroup) findViewById(R.id.rg_main);

    }


    /**
     * 解决安卓6.0以上版本不能读取外部存储权限的问题
     * @param activity
     * @return
     */
    public static boolean isGrantExternalRW(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && activity.checkSelfPermission(

                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            activity.requestPermissions(new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_PHONE_STATE,
                    Manifest.permission.ACCESS_NETWORK_STATE,
            }, 1);

            return false;
        }

        return true;
    }


    private boolean isExit = false;
    private Handler handler = new Handler() ;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            if(position != 0){
                rg_main.check(R.id.rb_local_video);
                return true;
            }else if(!isExit){
                isExit = true;
                Toast.makeText(MainActivity.this, "再点一次退出", Toast.LENGTH_SHORT).show();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        isExit = false;
                    }
                },2000);
                return  true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public void onBackPressed() {
        if (JCVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
    }



}

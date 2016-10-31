package com.wjc.last_mobilephone;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;

public class SplashActivity extends Activity {

    private Handler handler = new Handler();

    private static final String TAG = SplashActivity.class.getSimpleName();//SplashActivity

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //执行在主线程，并且在2秒后执行
                startMainActivity();
            }
        }, 2000);
    }

    private boolean isStartMain = false;

    private void startMainActivity() {
        if(!isStartMain){
            isStartMain = true;
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
            //关闭当前页面
            finish();
        }

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e(TAG,"action=="+event.getAction());
        startMainActivity();
        return super.onTouchEvent(event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        handler.removeCallbacksAndMessages(null);
    }
}

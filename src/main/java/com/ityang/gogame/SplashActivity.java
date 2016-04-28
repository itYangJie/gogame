package com.ityang.gogame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Window;

import java.lang.ref.SoftReference;

public class SplashActivity extends Activity {

    private static final int SPLASH_ACTIVITY_WHAT =0 ;
    private MyHandler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);

        handler = new MyHandler(this);
        handler.sendEmptyMessageDelayed(SPLASH_ACTIVITY_WHAT,1500);

    }

    private static class MyHandler extends Handler {
        //handler内部维护这activity软应用，防止内存泄露
        private SoftReference<SplashActivity> softReference;
        public MyHandler(SplashActivity activity) {
            softReference  = new SoftReference<SplashActivity>(activity);
        }
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (softReference.get()!=null && msg.what==SPLASH_ACTIVITY_WHAT){
                softReference.get().startActivity(new Intent(softReference.get(), MainActivity.class));
                softReference.get().finish();
            }
        }

    }
}

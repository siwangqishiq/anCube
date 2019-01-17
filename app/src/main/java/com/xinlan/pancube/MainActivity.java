package com.xinlan.pancube;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

public class MainActivity extends AppCompatActivity {
    private MainView mMainView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);   //应用运行时，保持屏幕高亮，不锁屏
        ShaderUtils.ctx = getApplication();
        mMainView = new MainView(this);
        setContentView(mMainView);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(mMainView != null){
            mMainView.onPause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(mMainView != null){
            mMainView.onResume();
        }
    }
}

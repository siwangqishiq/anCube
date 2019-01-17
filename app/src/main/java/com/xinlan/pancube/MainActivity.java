package com.xinlan.pancube;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    private MainView mMainView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

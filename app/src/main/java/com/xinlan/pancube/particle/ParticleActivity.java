package com.xinlan.pancube.particle;

import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import com.xinlan.pancube.OpenglEsUtils;

public class ParticleActivity extends AppCompatActivity {
    private GLSurfaceView mMainView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);   //应用运行时，保持屏幕高亮，不锁屏
        OpenglEsUtils.ctx = getApplication();
        mMainView = new ParticleView(this);
        setContentView(mMainView);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mMainView != null) {
            mMainView.onPause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (mMainView != null) {
            mMainView.onResume();
        }
    }
}

package com.xinlan.pancube;

import android.opengl.GLSurfaceView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.xinlan.pancube.light.CircleView;
import com.xinlan.pancube.light.RoatetCircleView;
import com.xinlan.pancube.light.SphereRenderView;

public class MainActivity extends AppCompatActivity {
    private GLSurfaceView mMainView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);   //应用运行时，保持屏幕高亮，不锁屏
        OpenglEsUtils.ctx = getApplication();
        //mMainView = new MainView(this);
        //mMainView = new RenderImageView(this);
        //mMainView = new MixImageView(this);
        //mMainView = new ShowSpriteView(this);
        //mMainView = new ShowFlipView(this);
        //mMainView = new DrawElemetsView(this);
        //mMainView = new MySurfaceView(this);
        //mMainView = new CircleView(this);
        //mMainView = new RoatetCircleView(this);
        mMainView = new SphereRenderView(this);
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

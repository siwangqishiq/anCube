package com.xinlan.pancube.sample1;

import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ToggleButton;

import com.xinlan.pancube.MatrixState;
import com.xinlan.pancube.OpenglEsUtils;
import com.xinlan.pancube.R;

public class SampleActivity extends AppCompatActivity {
    private GLSurfaceView mGLSurfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);   //应用运行时，保持屏幕高亮，不锁屏
        OpenglEsUtils.ctx = getApplication();
        setContentView(R.layout.activity_sample1);
        //初始化GLSurfaceView
        mGLSurfaceView = new MySurfaceView(this);
        mGLSurfaceView.requestFocus();//获取焦点
        mGLSurfaceView.setFocusableInTouchMode(true);//设置为可触控
        //将自定义的GLSurfaceView添加到外层LinearLayout中
        LinearLayout ll = (LinearLayout) findViewById(R.id.main_liner);
        ll.addView(mGLSurfaceView);
        //控制是否打开背面剪裁的ToggleButton
        ToggleButton tb = (ToggleButton) this.findViewById(R.id.ToggleButton01);
        tb.setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            //视角不合适导致变形的情况
                            //调用此方法计算产生透视投影矩阵
                            MatrixState.setProjectFrustum(-MySurfaceView.ratio * 0.7f, MySurfaceView.ratio * 0.7f, -0.7f, 0.7f, 1, 10);
                            //调用此方法产生摄像机观察矩阵
                            MatrixState.setCamera(0, 0.5f, 4, 0f, 0f, 0f, 0f, 1.0f, 0.0f);
                        } else {
                            //视角合适不变形的情况
                            //调用此方法计算产生透视投影矩阵
                            MatrixState.setProjectFrustum(-MySurfaceView.ratio, MySurfaceView.ratio, -1, 1, 20, 100);
                            //调用此方法产生摄像机观察矩阵
                            MatrixState.setCamera(0, 8f, 45, 0f, 0f, 0f, 0f, 1.0f, 0.0f);
                        }
                    }
                }
        );
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mGLSurfaceView != null) {
            mGLSurfaceView.onPause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (mGLSurfaceView != null) {
            mGLSurfaceView.onResume();
        }
    }
}

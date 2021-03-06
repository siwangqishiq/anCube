package com.xinlan.pancube.light.view;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLES30;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

import com.xinlan.pancube.MatrixState;
import com.xinlan.pancube.OpenglEsUtils;
import com.xinlan.pancube.light.Ball;
import com.xinlan.pancube.light.Line;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class LightView extends GLSurfaceView implements GLSurfaceView.Renderer {
    private Context context;

    public LightView(Context context) {
        super(context);
        initView(context);
    }

    public LightView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(Context ctx) {
        context = ctx;

        setEGLContextClientVersion(3);
        setEGLConfigChooser(8, 8, 8, 8, 0, 0);

        setRenderer(this);
        setRenderMode(RENDERMODE_CONTINUOUSLY);
    }

    //=======================================================
    float mRatio;
    public float[] mViewMatrix = new float[4 * 4];
    public float[] mProjMatrix = new float[4 * 4];

    Line xline;
    Line yline;
    Line zline;

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        xline = new Line();
        xline.update(-100, 0, 0,
                100, 0, 0);

        yline = new Line();
        yline.update(0, -100, 0,
                0, 100, 0);

        zline = new Line();
        zline.update(0, 0, -100,
                0, 0, 100);

    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES30.glViewport(0, 0, width, height);

        MatrixState.setCamera(10, 30, 30,
                0, 0, 0,
                0, 1, 0);

        mRatio = (float) width / height;
        //Matrix.perspectiveM(mProjMatrix, 0, 90, mRatio, 1f, 1000);
        MatrixState.setProjectFrustum(-mRatio, mRatio, -1, 1,
                20, 100);

        //初始化变换矩阵
        MatrixState.setInitStack();
    }

    int mAngle = 0;
    @Override
    public void onDrawFrame(GL10 gl) {
        GLES30.glClearColor(1f, 1f, 1f, 1f);
        GLES30.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);


        xline.render();
        yline.render();
        zline.render();


        OpenglEsUtils.debugFps();
    }
}//end class

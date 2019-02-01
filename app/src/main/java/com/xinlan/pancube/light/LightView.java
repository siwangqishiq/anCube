package com.xinlan.pancube.light;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLES30;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

import com.xinlan.pancube.OpenglEsUtils;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class LightView extends GLSurfaceView implements GLSurfaceView.Renderer {
    private Context context;

    public LightView(Context context) {
        super(context);
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
    Circle ball;

    float mRatio;
    public float[] mViewMatrix = new float[4 * 4];
    public float[] mProjMatrix = new float[4 * 4];

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        ball = new Circle();
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES30.glViewport(0, 0, width, height);

        Matrix.setLookAtM(mViewMatrix, 0,
                0, 0, 2,
                0, 0, 0,
                0, 1, 0);

        mRatio = (float) width / height;
//        Matrix.frustumM(mProjMatrix, 0,
//                -mRatio, mRatio,
//                -1, 1,
//                1, 10);

        Matrix.perspectiveM(mProjMatrix, 0, 90, mRatio, 1f, 1000);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        GLES30.glClearColor(1f, 1f, 1f, 1f);
        GLES30.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);

        ball.render(mViewMatrix, mProjMatrix);
        OpenglEsUtils.debugFps();
    }
}//end class

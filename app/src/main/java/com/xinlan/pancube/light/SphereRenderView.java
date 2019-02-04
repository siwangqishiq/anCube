package com.xinlan.pancube.light;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLES30;
import android.opengl.GLSurfaceView;

import com.xinlan.pancube.MatrixState;
import com.xinlan.pancube.OpenglEsUtils;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class SphereRenderView extends GLSurfaceView implements GLSurfaceView.Renderer {
    private Context context;

    public SphereRenderView(Context context) {
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
    float mRatio;
    Sphere sphere;
    RotateCircle circle;

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        sphere = new Sphere();
        //circle = new RotateCircle();
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES30.glViewport(0, 0, width, height);

        MatrixState.setCamera(0, 30, 30,
                0, 0, 0,
                0, 1, 0);

        mRatio = (float) width / height;
        MatrixState.setProjectFrustum(-mRatio, mRatio, -1, 1,
                20, 100);

        //初始化变换矩阵
        MatrixState.setInitStack();
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        GLES30.glClearColor(0f, 0f, 0f, 1f);
        GLES30.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
        sphere.render();
        //circle.render();
        OpenglEsUtils.debugFps();
    }
}//end class

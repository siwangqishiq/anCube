package com.xinlan.pancube.skybox;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLES30;
import android.opengl.GLSurfaceView;

import com.xinlan.pancube.MatrixState;
import com.xinlan.pancube.OpenglEsUtils;
import com.xinlan.pancube.light.RotateCircle;
import com.xinlan.pancube.light.Sphere;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class SkyBoxView extends GLSurfaceView implements GLSurfaceView.Renderer {
    private Context context;

    public SkyBoxView(Context context) {
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

    SkyBox mSkyBox;

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        mSkyBox = new SkyBox();
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES30.glViewport(0, 0, width, height);

        MatrixState.setCamera(0, 0, 0,
                0, 0, 1f,
                0, 1, 0);

        mRatio = (float) width / height;
        MatrixState.setProjectFrustum(-mRatio, mRatio, -1, 1, 1f, 300);

        //初始化变换矩阵
        MatrixState.setInitStack();
    }

    float angle;
    @Override
    public void onDrawFrame(GL10 gl) {
        GLES30.glClearColor(1f, 1f, 1f, 1f);
        GLES30.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);

        MatrixState.pushMatrix();
        MatrixState.rotate(angle , 0 , 1 , 0);
        angle += 0.1f;
        mSkyBox.render();
        MatrixState.popMatrix();

        OpenglEsUtils.debugFps();
    }
}//end class

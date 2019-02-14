package com.xinlan.pancube.particle;

import android.content.Context;
import android.opengl.GLES30;
import android.opengl.GLSurfaceView;

import com.xinlan.pancube.OpenglEsUtils;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class ParticleView extends GLSurfaceView implements GLSurfaceView.Renderer {
    private Context context;

    public ParticleView(Context context) {
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


    //===================================
    Particle particle;

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        particle = new Particle();
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES30.glViewport(0, 0, width, height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        GLES30.glClearColor(0f, 0f, 0f, 1f);
        GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT);

        particle.render();
        OpenglEsUtils.debugFps();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }
}//end class

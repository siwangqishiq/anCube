package com.xinlan.pancube;

import android.content.Context;
import android.opengl.GLES30;
import android.opengl.GLSurfaceView;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static android.opengl.GLES30.*;

public class MainView extends GLSurfaceView implements GLSurfaceView.Renderer {

    private int mCubeShaderProgram;

    public MainView(Context context) {
        super(context);
        initView(context);
    }

    private void initView(Context ctx) {
        setEGLContextClientVersion(3);
        setEGLConfigChooser(8, 8, 8, 8, 16, 0);

        setRenderer(this);
        setRenderMode(RENDERMODE_CONTINUOUSLY);
    }


    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        mCubeShaderProgram = ShaderUtils.buildShaderProgram(R.raw.cube_vertex, R.raw.cube_fragment);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        gl.glViewport(0, 0, width, height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        glClearColor(1, 1, 1, 1f);
        glClear(GLES30.GL_COLOR_BUFFER_BIT | GLES30.GL_DEPTH_BUFFER_BIT);

        glUseProgram(1);
    }
}//end class

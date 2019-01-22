package com.xinlan.pancube.sprite;

import android.content.Context;
import android.opengl.GLES30;
import android.opengl.GLSurfaceView;

import com.xinlan.pancube.R;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class ShowFlipView extends GLSurfaceView implements GLSurfaceView.Renderer {
    private Context context;

    public ShowFlipView(Context context) {
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
    protected ImageFlipSprite mBackground;

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        mBackground = new ImageFlipSprite(R.drawable.pokemon);
        mBackground.update(-1, -1);
        mBackground.setSize(2, 2);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES30.glViewport(0, 0, width, height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        GLES30.glClearColor(1f, 1f, 1f, 1f);
        GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT);

        mBackground.render();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mBackground.free();
    }
}//end class

package com.xinlan.pancube.sprite;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLES30;
import android.opengl.GLSurfaceView;

import com.xinlan.pancube.OpenglEsUtils;
import com.xinlan.pancube.R;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class ShowSpriteView extends GLSurfaceView implements GLSurfaceView.Renderer {
    private Context context;

    public ShowSpriteView(Context context) {
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
    protected ImageSprite mSprite;
    protected ImageSprite mBackground;

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        mBackground = new ImageSprite(R.drawable.pokemon);
        mBackground.update(-1, -1);
        mBackground.setSize(2, 2);

        mSprite = new ImageSprite(R.drawable.lena);
        mSprite.update(-1, -1);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES30.glViewport(0, 0, width, height);
    }

    double sizeAngle = 0;

    @Override
    public void onDrawFrame(GL10 gl) {
        float w = mSprite.width;
        float h = mSprite.height;

        sizeAngle += 0.01f;
        float size = (float) Math.sin(sizeAngle) + 1;

        mSprite.setSize(size, size);

        GLES30.glClearColor(1f, 1f, 1f, 1f);
        GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT);

        mBackground.render();
        mSprite.render();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mBackground.free();
        mSprite.free();
    }
}//end class

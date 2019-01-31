package com.xinlan.pancube.draw;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLES30;
import android.opengl.GLSurfaceView;

import com.xinlan.pancube.OpenglEsUtils;
import com.xinlan.pancube.R;
import com.xinlan.pancube.sprite.ImageFlipSprite;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class DrawElemetsView extends GLSurfaceView implements GLSurfaceView.Renderer {
    private Context context;

    public DrawElemetsView(Context context) {
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
    private int mPointProgram;

    float[] points =
            {
                    0.0f, 0.0f,
                    0.5f, 0.5f,
                    0.7f, 0.9f,
                    0.7f, 1.0f
            };
    float[] colors =
            {
                    1.0f, 0.0f, 0.0f, 1.0f,
                    0.0f, 1.0f, 0.0f, 1.0f,
                    0.0f, 0.0f, 1.0f, 1.0f,
                    0.0f, 0.0f, 1.0f, 1.0f
            };

    FloatBuffer mPointBuf;
    FloatBuffer mColorBuf;

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        mPointProgram = OpenglEsUtils.buildShaderProgram(R.raw.draw_point_vertex, R.raw.draw_point_frag);

        mPointBuf = OpenglEsUtils.allocateBuf(points);
        mColorBuf = OpenglEsUtils.allocateBuf(colors);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES30.glViewport(0, 0, width, height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        GLES30.glClearColor(1f, 1f, 1f, 1f);
        GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT);

        GLES30.glUseProgram(mPointProgram);
        GLES30.glVertexAttribPointer(0 , 2 , GLES30.GL_FLOAT ,false , 0 , mPointBuf);
        GLES30.glEnableVertexAttribArray(0);
        GLES30.glVertexAttribPointer(1 , 4 , GLES30.GL_FLOAT ,false , 0 , mColorBuf);
        GLES30.glEnableVertexAttribArray(1);
        GLES30.glDrawArrays(GLES30.GL_POINTS , 0 , 4);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }
}//end class

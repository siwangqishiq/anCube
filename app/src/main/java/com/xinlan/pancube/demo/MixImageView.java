package com.xinlan.pancube.demo;

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

public class MixImageView extends GLSurfaceView implements GLSurfaceView.Renderer {
    private Context context;

    public MixImageView(Context context) {
        super(context);
        initView(context);
    }

    private void initView(Context ctx) {
        context = ctx;

        setEGLContextClientVersion(3);
        setEGLConfigChooser(8, 8, 8, 8, 16, 0);

        setRenderer(this);
        setRenderMode(RENDERMODE_CONTINUOUSLY);
    }


    //===================================
    private int mRenderProgram;
    private int mImageTextureId;

    private float[] mImageVertex = {
            -1, -1,
            1, -1,
            -1, 1,
            1,  1
    };

    private float[] mTexCoords = {
            0, 1,
            1, 1,
            0, 0,
            1, 0
    };


    private FloatBuffer mVtxBuf;
    private FloatBuffer mTexCoordBuf;

    private int mImageSamplerPos;

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        mRenderProgram = OpenglEsUtils.buildShaderProgram(R.raw.render_mix_image_vertex, R.raw.render_mix_image_frg);
        mImageTextureId = OpenglEsUtils.loadTexture(context , R.drawable.pokemon);

        mImageSamplerPos = GLES30.glGetUniformLocation(mRenderProgram , "s_texture");

        mVtxBuf = ByteBuffer.allocateDirect(mImageVertex.length * Float.BYTES)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer().put(mImageVertex);
        mVtxBuf.position(0);

        mTexCoordBuf = ByteBuffer.allocateDirect(Float.BYTES * mTexCoords.length)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer().put(mTexCoords);
        mTexCoordBuf.position(0);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES30.glViewport(0, 0, width, height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        GLES30.glClearColor(1f, 1f, 1f, 1f);
        GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT | GLES30.GL_DEPTH_BUFFER_BIT);

        GLES30.glUseProgram(mRenderProgram);


        GLES30.glVertexAttribPointer(0,2 , GLES30.GL_FLOAT , false , 0 , mVtxBuf);
        GLES30.glEnableVertexAttribArray(0);

        GLES30.glVertexAttribPointer(1 , 2 , GLES20.GL_FLOAT , false , 0, mTexCoordBuf);
        GLES30.glEnableVertexAttribArray(1);

        //set texture
        GLES30.glActiveTexture(GLES30.GL_TEXTURE0);//激活纹理
        GLES30.glBindTexture(GLES30.GL_TEXTURE_2D , mImageTextureId);//绑定纹理
        GLES30.glUniform1ui(mImageSamplerPos,0);//将纹理设置到指定着色器上

        GLES30.glDrawArrays(GLES30.GL_TRIANGLE_STRIP , 0, 4);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        OpenglEsUtils.deleteTexture(mImageTextureId);
    }
}//end class

package com.xinlan.pancube.sprite;

import android.content.Context;
import android.opengl.GLES30;

import com.xinlan.pancube.OpenglEsUtils;
import com.xinlan.pancube.R;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class ImageFlipSprite {
    private Context mCtx;

    public float x, y;
    public float width;
    public float height;

    protected FloatBuffer mVertexBuf;

    protected int mRenderProgram;
    protected int mImageSamplerPos;

    private FloatBuffer mTexCoordBuf;
    private float[] mTexCoords = {
            0, 1,
            1, 1,
            1, 0,
            0, 0
    };
    private int mTextureId;


    public ImageFlipSprite(int drawable) {
        this.mCtx = OpenglEsUtils.ctx;

        x = 0;
        y = 0;
        width = 1f;
        height = 1f;
        updateVertex();

        loadShader();

        mTexCoordBuf = ByteBuffer.allocateDirect(mTexCoords.length * Float.BYTES)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer().put(mTexCoords);
        mTexCoordBuf.position(0);

        mTextureId = OpenglEsUtils.loadTexture(OpenglEsUtils.ctx , drawable);
    }

    private void loadShader() {
        mRenderProgram = OpenglEsUtils.buildShaderProgram(R.raw.image_flip_vertex, R.raw.image_flip_frg);
        mImageSamplerPos = GLES30.glGetUniformLocation(mRenderProgram , "s_texture");
    }

    private void updateVertex() {
        if (mVertexBuf == null) {
            mVertexBuf = ByteBuffer.allocateDirect(4 * 2 * Float.BYTES)
                    .order(ByteOrder.nativeOrder()).asFloatBuffer();
        }
        mVertexBuf.position(0);

        mVertexBuf.put(x);
        mVertexBuf.put(y);

        mVertexBuf.put(x + width);
        mVertexBuf.put(y);

        mVertexBuf.put(x + width);
        mVertexBuf.put(y + height);

        mVertexBuf.put(x);
        mVertexBuf.put(y + height);
    }

    public void update(float _x , float _y){
        x = _x;
        y = _y;

        updateVertex();
    }

    public void setSize(float w , float h){
        width = w;
        height = h;

        updateVertex();
    }

    public void render() {
        GLES30.glUseProgram(mRenderProgram);

        mVertexBuf.position(0);
        GLES30.glVertexAttribPointer(0,2 , GLES30.GL_FLOAT , false , 0 , mVertexBuf);
        GLES30.glEnableVertexAttribArray(0);

        mTexCoordBuf.position(0);
        GLES30.glVertexAttribPointer(1 , 2 , GLES30.GL_FLOAT , false , 0, mTexCoordBuf);
        GLES30.glEnableVertexAttribArray(1);

        //set texture
        GLES30.glActiveTexture(GLES30.GL_TEXTURE0);//激活纹理
        GLES30.glBindTexture(GLES30.GL_TEXTURE_2D , mTextureId);//绑定纹理
        GLES30.glUniform1ui(mImageSamplerPos,0);//将纹理设置到指定着色器上

        GLES30.glDrawArrays(GLES30.GL_TRIANGLE_FAN , 0, 4);
    }

    public void free() {
        OpenglEsUtils.deleteTexture(mTextureId);
    }
}//end class

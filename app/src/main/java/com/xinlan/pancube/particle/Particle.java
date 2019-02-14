package com.xinlan.pancube.particle;

import android.opengl.GLES20;
import android.opengl.GLES30;

import com.xinlan.pancube.OpenglEsUtils;
import com.xinlan.pancube.R;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class Particle {
    private int mProgram;

    private float mLifeTime = 0.0f;

    private int uTimeloc;
    private float[] mData = {};

    private int mCount = 100;

    private FloatBuffer mStartBuf;
    private FloatBuffer mEndBuf;
    private FloatBuffer mCenterBuf;


    public Particle() {
        mProgram = OpenglEsUtils.buildShaderProgram(R.raw.particle_vertex, R.raw.particle_fragment);

        uTimeloc = GLES30.glGetUniformLocation(mProgram, "uTime");

        mStartBuf = ByteBuffer.allocateDirect(mCount * 3 * Float.BYTES).order(ByteOrder.nativeOrder()).asFloatBuffer();
        mStartBuf.position(0);
        for (int i = 0; i < mCount; i++) {
            mStartBuf.put(genRnd(-1, 1));
            mStartBuf.put(genRnd(-1, 1));
            mStartBuf.put(0f);
        }
        mStartBuf.position(0);


        mEndBuf = ByteBuffer.allocateDirect(mCount * 3 * Float.BYTES).order(ByteOrder.nativeOrder()).asFloatBuffer();
        mEndBuf.position(0);
        for (int i = 0; i < mCount; i++) {
            mEndBuf.put(genRnd(-1, 1));
            mEndBuf.put(genRnd(-1, 1));
            mEndBuf.put(0f);
        }
        mEndBuf.position(0);

        mCenterBuf = ByteBuffer.allocateDirect(mCount * 3 * Float.BYTES).order(ByteOrder.nativeOrder()).asFloatBuffer();
        mCenterBuf.position(0);
        for (int i = 0; i < mCount; i++) {
            mCenterBuf.put(0f);
            mCenterBuf.put(0f);
            mCenterBuf.put(0f);
        }
        mCenterBuf.position(0);
    }

    public void render() {
        if (mLifeTime < 1.0f) {
            mLifeTime += 0.005f;
        } else {
            mLifeTime = 0.0f;
        }


        GLES30.glUseProgram(mProgram);

        GLES30.glVertexAttribPointer(0,3 , GLES20.GL_FLOAT ,false , 0 , mStartBuf);
        GLES30.glEnableVertexAttribArray(0);
        GLES30.glVertexAttribPointer(1,3 , GLES20.GL_FLOAT ,false , 0 , mEndBuf);
        GLES30.glEnableVertexAttribArray(1);
        GLES30.glVertexAttribPointer(2,3 , GLES20.GL_FLOAT ,false , 0 , mCenterBuf);
        GLES30.glEnableVertexAttribArray(2);

        GLES30.glUniform1f(uTimeloc, mLifeTime);

        GLES30.glDrawArrays(GLES30.GL_POINTS, 0, mCount);
    }

    private float genRnd(float min, float max) {
        float num = min + (float) (Math.random() * (max - min));
        return num;
    }
}//end class

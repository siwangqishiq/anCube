package com.xinlan.pancube.light;

import android.opengl.GLES20;
import android.opengl.GLES30;
import android.opengl.Matrix;

import com.xinlan.pancube.OpenglEsUtils;
import com.xinlan.pancube.R;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class Line {
    private int mProgram;
    private int mMVPMatLocation;

    float x1, y1, z1;
    float x2, y2, z2;

    private FloatBuffer mBuf;


    protected float[] mModelMat = new float[4 * 4];
    protected float[] mMVPMat = new float[4 * 4];

    public Line() {
        mProgram = OpenglEsUtils.buildShaderProgram(R.raw.render_line_vs, R.raw.render_line_frag);
        mMVPMatLocation = GLES30.glGetUniformLocation(mProgram, "u_mvp_matrix");

        mBuf = ByteBuffer.allocateDirect(6 * Float.BYTES).order(ByteOrder.nativeOrder()).asFloatBuffer();
    }

    public void update(float _x1, float _y1, float _z1,
                       float _x2, float _y2, float _z2) {
        x1 = _x1;
        y1 = _y1;
        z1 = _z1;

        x2 = _x2;
        y2 = _y2;
        z2 = _z2;

        mBuf.clear();
        mBuf.put(x1);
        mBuf.put(y1);
        mBuf.put(z1);

        mBuf.put(x2);
        mBuf.put(y2);
        mBuf.put(z2);

        mBuf.position(0);
    }

    public void render(float[] viewMat, float[] projMat) {
        MatrixState.pushMatrix();
        GLES30.glUseProgram(mProgram);

        calMvpMatrix(viewMat, projMat);
        GLES30.glUniformMatrix4fv(mMVPMatLocation, 1, false, mMVPMat, 0);

        GLES30.glVertexAttribPointer(0, 3, GLES30.GL_FLOAT, false, 0, mBuf);
        GLES30.glEnableVertexAttribArray(0);
        GLES30.glDrawArrays(GLES20.GL_LINES, 0, 2);

        MatrixState.popMatrix();
    }

    private void calMvpMatrix(float[] viewMat, float[] projMat) {
//        Matrix.setIdentityM(mMVPMat, 0);
//        Matrix.setIdentityM(mModelMat, 0);
//
//        Matrix.multiplyMM(mMVPMat, 0, mModelMat, 0, mMVPMat, 0);
//        Matrix.multiplyMM(mMVPMat, 0, viewMat, 0, mMVPMat, 0);
//        Matrix.multiplyMM(mMVPMat, 0, projMat, 0, mMVPMat, 0);

    }
}//end class

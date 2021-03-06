package com.xinlan.pancube.light;

import android.opengl.GLES20;
import android.opengl.GLES30;
import android.opengl.Matrix;

import com.xinlan.pancube.OpenglEsUtils;
import com.xinlan.pancube.R;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;

public class Circle {
    private float mRadius = 0.7f;

    private int mProgramId;
    private int mMVPMatLocation;
    private FloatBuffer mVertexBuf;
    private int mVertexCount;

    private float[] mModelMat = new float[4 * 4];
    private float[] mMVPMat = new float[4 * 4];

    public Circle() {
        initShader();
        initVertex();

        Matrix.setIdentityM(mModelMat, 0);
        Matrix.setIdentityM(mMVPMat, 0);
    }

    private void initShader() {
        mProgramId = OpenglEsUtils.buildShaderProgram(R.raw.render_ball_vs, R.raw.render_ball_frag);
        mMVPMatLocation = GLES30.glGetUniformLocation(mProgramId, "u_mvp_matrix");
    }

    private void initVertex() {
        float v[] = {
                -1.0f, -1.0f, 0.0f,
                1.0f, -1.0f, 0.0f,
                1.0f, 1.0f, 0.0f,
                -1.0f, 1.0f, 0.0f,
        };


        float delta = 10;
        ArrayList<Float> vLst = new ArrayList<Float>();
        vLst.add(0f);
        vLst.add(0f);
        vLst.add(0f);
        for (int angle = 0; angle <= 360; angle += delta) {
            float x = (float) (mRadius * Math.cos(Math.toRadians(angle)));
            float y = (float) (mRadius * Math.sin(Math.toRadians(angle)));
            float z = 0;

            vLst.add(x);
            vLst.add(y);
            vLst.add(z);
        }//end for i

        mVertexCount = vLst.size() / 3;

        mVertexBuf = ByteBuffer.allocateDirect(vLst.size() * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        for (int i = 0; i < vLst.size(); i++) {
            mVertexBuf.put(vLst.get(i));
        }//end for i
        mVertexBuf.position(0);
    }

    public void render(float[] viewMat, float[] projMat) {
        GLES30.glUseProgram(mProgramId);

        calMvpMatrix(viewMat, projMat);
        GLES30.glUniformMatrix4fv(mMVPMatLocation, 1, false, mMVPMat, 0);

        GLES30.glVertexAttribPointer(0, 3, GLES20.GL_FLOAT, false, 0, mVertexBuf);
        GLES30.glEnableVertexAttribArray(0);
        GLES30.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 0, mVertexCount);
    }

    float mAngle = 0;

    private void calMvpMatrix(float[] viewMat, float[] projMat) {
        Matrix.setIdentityM(mMVPMat, 0);
        Matrix.rotateM(mMVPMat, 0, mAngle, 0, 1, 0);
        mAngle++;

        Matrix.multiplyMM(mMVPMat, 0, mModelMat, 0, mMVPMat, 0);
        Matrix.multiplyMM(mMVPMat, 0, viewMat, 0, mMVPMat, 0);
        Matrix.multiplyMM(mMVPMat, 0, projMat, 0, mMVPMat, 0);
    }
}//end class

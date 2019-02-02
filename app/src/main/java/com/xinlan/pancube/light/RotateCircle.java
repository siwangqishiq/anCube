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

public class RotateCircle {
    private float mRadius = 1f;

    private int mProgramId;
    private int mMVPMatLocation;
    private FloatBuffer mVertexBuf;
    private int mVertexCount;

    private float[] mModelMat = new float[4 * 4];
    private float[] mMVPMat = new float[4 * 4];

    public RotateCircle() {
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

    float mAngle;

    public void render(float[] viewMat, float[] projMat) {
        GLES30.glUseProgram(mProgramId);

        calMvpMatrix(viewMat, projMat);

        MatrixState.pushMatrix();
        MatrixState.rotate(mAngle , 0 , 1, 0);
        mAngle++;
        MatrixState.translate( 0 , 0, 0);
        GLES30.glUniformMatrix4fv(mMVPMatLocation, 1, false, MatrixState.getFinalMatrix(), 0);
        MatrixState.popMatrix();

        GLES30.glVertexAttribPointer(0, 3, GLES20.GL_FLOAT, false, 0, mVertexBuf);
        GLES30.glEnableVertexAttribArray(0);
        GLES30.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 0, mVertexCount);
    }

    private void calMvpMatrix(float[] viewMat, float[] projMat) {

    }
}//end class

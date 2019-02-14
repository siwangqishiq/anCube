package com.xinlan.pancube.light.view;

import android.opengl.GLES20;
import android.opengl.GLES30;
import android.opengl.Matrix;

import com.xinlan.pancube.MatrixState;
import com.xinlan.pancube.OpenglEsUtils;
import com.xinlan.pancube.R;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;

public class DiffuseSphere {
    private float mRadius = 1f;

    private int mProgramId;
    private int mMVPMatLocation;
    private int mModelMatLocation;
    private int mLightLocation;
    private FloatBuffer mVertexBuf;
    private FloatBuffer mNormalBuf;//法线缓存
    private int mVertexCount;

    public DiffuseSphere() {
        initShader();
        initVertex();
    }

    private void initShader() {
        mProgramId = OpenglEsUtils.buildShaderProgram(R.raw.render_sphere_diffuse_vs, R.raw.render_sphere_diffuse_frag);
        mMVPMatLocation = GLES30.glGetUniformLocation(mProgramId, "u_mvp_matrix");
        mModelMatLocation = GLES30.glGetUniformLocation(mProgramId, "uModelMat");
        mLightLocation = GLES30.glGetUniformLocation(mProgramId, "uLightPos");
    }

    private void initVertex() {
        float delta = 10;
        ArrayList<Float> vLst = new ArrayList<Float>();

        for (int hAngle = 0; hAngle <= 360; hAngle += delta) {
            for (int vAngle = -90; vAngle <= 90; vAngle += delta) {
                float x0 = (float) (mRadius * Math.sin(Math.toRadians(hAngle)) * Math.cos(Math.toRadians(vAngle)));
                float y0 = (float) (mRadius * Math.sin(Math.toRadians(hAngle)) * Math.sin(Math.toRadians(vAngle)));
                float z0 = (float) (mRadius * Math.cos(Math.toRadians(hAngle)));


                float x1 = (float) (mRadius * Math.sin(Math.toRadians(hAngle)) * Math.cos(Math.toRadians(vAngle + delta)));
                float y1 = (float) (mRadius * Math.sin(Math.toRadians(hAngle)) * Math.sin(Math.toRadians(vAngle + delta)));
                float z1 = (float) (mRadius * Math.cos(Math.toRadians(hAngle)));


                float x2 = (float) (mRadius * Math.sin(Math.toRadians(hAngle + delta)) * Math.cos(Math.toRadians(vAngle + delta)));
                float y2 = (float) (mRadius * Math.sin(Math.toRadians(hAngle + delta)) * Math.sin(Math.toRadians(vAngle + delta)));
                float z2 = (float) (mRadius * Math.cos(Math.toRadians(hAngle + delta)));


                float x3 = (float) (mRadius * Math.sin(Math.toRadians(hAngle + delta)) * Math.cos(Math.toRadians(vAngle)));
                float y3 = (float) (mRadius * Math.sin(Math.toRadians(hAngle + delta)) * Math.sin(Math.toRadians(vAngle)));
                float z3 = (float) (mRadius * Math.cos(Math.toRadians(hAngle + delta)));

                //1 trigle
                vLst.add(x0);
                vLst.add(y0);
                vLst.add(z0);

                vLst.add(x1);
                vLst.add(y1);
                vLst.add(z1);

                vLst.add(x2);
                vLst.add(y2);
                vLst.add(z2);

                //2 trigle
                vLst.add(x0);
                vLst.add(y0);
                vLst.add(z0);

                vLst.add(x2);
                vLst.add(y2);
                vLst.add(z2);

                vLst.add(x3);
                vLst.add(y3);
                vLst.add(z3);
            }//end for vAngle

        }//end for hAngle

        mVertexCount = vLst.size() / 3;

        mVertexBuf = ByteBuffer.allocateDirect(vLst.size() * 4)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer();
        for (int i = 0; i < vLst.size(); i++) {
            mVertexBuf.put(vLst.get(i));
        }//end for i
        mVertexBuf.position(0);

        mNormalBuf = ByteBuffer.allocateDirect(vLst.size() * 4)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer();
        for (int i = 0; i < vLst.size(); i++) {
            mNormalBuf.put(vLst.get(i));
        }//end for i
        mNormalBuf.position(0);
    }

    public void render() {
        GLES30.glUseProgram(mProgramId);

        GLES30.glUniform3f(mModelMatLocation, MatrixState.getMMatrix()[0],
                MatrixState.getMMatrix()[1],
                MatrixState.getMMatrix()[2]);
        GLES30.glUniformMatrix4fv(mMVPMatLocation, 1, false, MatrixState.getFinalMatrix(), 0);
        GLES30.glUniform4f(mLightLocation,
                MatrixState.getDiffuseLightPos()[0],
                MatrixState.getDiffuseLightPos()[1],
                MatrixState.getDiffuseLightPos()[2], 1f);

        GLES30.glVertexAttribPointer(0, 3, GLES20.GL_FLOAT, false, 0, mVertexBuf);
        GLES30.glVertexAttribPointer(1, 3, GLES20.GL_FLOAT, false, 0, mNormalBuf);
        GLES30.glEnableVertexAttribArray(0);
        GLES30.glDrawArrays(GLES20.GL_TRIANGLES, 0, mVertexCount);
    }
}//end class

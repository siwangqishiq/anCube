package com.xinlan.pancube.skybox;

import android.opengl.GLES20;
import android.opengl.GLES30;

import com.xinlan.pancube.MatrixState;
import com.xinlan.pancube.OpenglEsUtils;
import com.xinlan.pancube.R;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

public class SkyBox {
    private int mProgram;

    private static final float UNIT = 20.0f;
    // 立方体坐标
    private static final float[] CubeCoords = new float[]{
            -UNIT, UNIT, UNIT,     // (0) Top-left near
            UNIT, UNIT, UNIT,     // (1) Top-right near
            -UNIT, -UNIT, UNIT,     // (2) Bottom-left near
            UNIT, -UNIT, UNIT,     // (3) Bottom-right near
            -UNIT, UNIT, -UNIT,     // (4) Top-left far
            UNIT, UNIT, -UNIT,     // (5) Top-right far
            -UNIT, -UNIT, -UNIT,     // (6) Bottom-left far
            UNIT, -UNIT, -UNIT      // (7) Bottom-right far
    };


    // 立方体索引
    private static final byte[] CubeIndex = new byte[]{
            // Front
            1, 3, 0,
            0, 3, 2,

            // Back
            4, 6, 5,
            5, 6, 7,

            // Left
            0, 2, 4,
            4, 2, 6,

            // Right
            5, 7, 1,
            1, 7, 3,

            // Top
            5, 1, 4,
            4, 1, 0,

            // Bottom
            6, 2, 7,
            7, 2, 3
    };


    private int mSkyBoxTexture;

    private int mMvpMatrixLoc;
    private int mCubeMapUnitLoc;
    private FloatBuffer mCubeBuf;
    private ByteBuffer mCubeIndexBuf;

    public SkyBox() {
        mCubeBuf = OpenglEsUtils.allocateBuf(CubeCoords);
        mCubeIndexBuf = OpenglEsUtils.allocateBuf(CubeIndex);

        mSkyBoxTexture = OpenglEsUtils.loadCubeMap(OpenglEsUtils.ctx, new int[]{
                R.drawable.left, R.drawable.right, R.drawable.bottom,
                R.drawable.top, R.drawable.front, R.drawable.back
        });

//        mSkyBoxTexture = OpenglEsUtils.loadCubeMap(OpenglEsUtils.ctx, new int[]{
//                R.drawable.lena, R.drawable.lena, R.drawable.lena,
//                R.drawable.lena, R.drawable.lena, R.drawable.lena
//        });

        //mSkyBoxTexture = OpenglEsUtils.loadTexture(OpenglEsUtils.ctx , R.drawable.pokemon);

        mProgram = OpenglEsUtils.buildShaderProgram(R.raw.skybox_vertex, R.raw.skybox_frag);

        mMvpMatrixLoc = GLES30.glGetUniformLocation(mProgram, "uMvpMatrix");
        mCubeMapUnitLoc = GLES30.glGetUniformLocation(mProgram, "uSkybox");
    }

    float mAngle;
    public void render() {
        //GLES30.glEnable(GLES20.GL_DEPTH_TEST);
        //GLES30.glDisable(GLES30.GL_CULL_FACE);

        GLES30.glUseProgram(mProgram);

        GLES30.glActiveTexture(GLES30.GL_TEXTURE0);
        GLES30.glBindTexture(GLES30.GL_TEXTURE_CUBE_MAP, mSkyBoxTexture);

        GLES30.glUniform1i(mCubeMapUnitLoc, 0);

        GLES30.glUniformMatrix4fv(mMvpMatrixLoc, 1, false, MatrixState.getFinalMatrix(), 0);

        GLES30.glVertexAttribPointer(0, 3, GLES30.GL_FLOAT, false, 0, mCubeBuf);
        GLES30.glEnableVertexAttribArray(0);

        GLES30.glDrawElements(GLES30.GL_TRIANGLES, 36, GLES30.GL_UNSIGNED_BYTE, mCubeIndexBuf);
    }
}//end class

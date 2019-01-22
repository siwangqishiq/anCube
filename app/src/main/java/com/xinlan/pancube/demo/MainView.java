package com.xinlan.pancube.demo;

import android.content.Context;
import android.opengl.GLES30;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

import com.xinlan.pancube.OpenglEsUtils;
import com.xinlan.pancube.R;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static android.opengl.GLES30.*;

public class MainView extends GLSurfaceView implements GLSurfaceView.Renderer {

    private int mCubeShaderProgram;

    float cubeVertices[] = {
            -1.0f, 1.0f, 1.0f,    //正面左上0
            -1.0f, -1.0f, 1.0f,   //正面左下1
            1.0f, -1.0f, 1.0f,    //正面右下2
            1.0f, 1.0f, 1.0f,     //正面右上3
            -1.0f, 1.0f, -1.0f,    //反面左上4
            -1.0f, -1.0f, -1.0f,   //反面左下5
            1.0f, -1.0f, -1.0f,    //反面右下6
            1.0f, 1.0f, -1.0f,     //反面右上7
    };

//    float color[] = {
//            0f, 1f, 0f, 1f,
//            0f, 1f, 0f, 1f,
//            0f, 1f, 0f, 1f,
//            0f, 1f, 0f, 1f,
//            1f, 1f, 0f, 1f,
//            1f, 0f, 1f, 1f,
//            1f, 0f, 0f, 1f,
//            1f, 0f, 0f, 1f,
//    };

    float color[] = {
            0f,1f,0f,1f,
            0f,1f,0f,1f,
            0f,1f,0f,1f,
            0f,1f,0f,1f,
            1f,0f,0f,1f,
            1f,0f,0f,1f,
            1f,0f,0f,1f,
            1f,0f,0f,1f,
    };

    short indices[] = {
            0, 3, 2, 0, 2, 1,    //正面
            0, 1, 5, 0, 5, 4,    //左面
            0, 7, 3, 0, 4, 7,    //上面
            6, 7, 4, 6, 4, 5,    //后面
            6, 3, 7, 6, 2, 3,    //右面
            6, 5, 1, 6, 1, 2     //下面
    };



    private float mRatio = 1f;

    public FloatBuffer mVertexPosBuf;
    public FloatBuffer mVertexColorBuf;
    public ShortBuffer mIndicesBuf;

    public static float[] mProjMatrix = new float[4 * 4];//4x4矩阵 投影用
    public static float[] mVMatrix = new float[4 * 4];//摄像机位置朝向9参数矩阵
    public static float[] mMMatrix = new float[4 * 4];//具体物体的移动旋转矩阵，旋转、平移

    public static float[] mMVPMatrix;//最后起作用的总变换矩阵
    public float xAngle = 0;//绕x轴旋转的角度

    public MainView(Context context) {
        super(context);
        initView(context);
    }

    private void initView(Context ctx) {
        setEGLContextClientVersion(3);
        setEGLConfigChooser(8, 8, 8, 8, 16, 0);

        setRenderer(this);
        setRenderMode(RENDERMODE_CONTINUOUSLY);
    }


    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        mCubeShaderProgram = OpenglEsUtils.buildShaderProgram(R.raw.cube_vertex, R.raw.cube_fragment);

        mVertexPosBuf = ByteBuffer.allocateDirect(cubeVertices.length * Float.BYTES).
                order(ByteOrder.nativeOrder())
                .asFloatBuffer();
        mVertexPosBuf.put(cubeVertices);
        mVertexPosBuf.position(0);

        mVertexColorBuf = ByteBuffer.allocateDirect(color.length * Float.BYTES)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer();
        mVertexColorBuf.put(color);
        mVertexColorBuf.position(0);

        mIndicesBuf = ByteBuffer.allocateDirect(indices.length * Short.BYTES)
                .order(ByteOrder.nativeOrder())
                .asShortBuffer();
        mIndicesBuf.put(indices);
        mIndicesBuf.position(0);

        //打开深度测试
        glEnable(GL_DEPTH_TEST);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        mRatio = (float) width / height;
        glViewport(0, 0, width, height);

        Matrix.frustumM(mProjMatrix, 0,
                -mRatio, mRatio,
                -1, 1,
                3, 20);

        Matrix.setLookAtM(mVMatrix, 0,
                0f, 5f, 10, //相机位置
                0, 0, 0,//看向位置
                0f, 1f, 0f//up位置
        );

    }

    @Override
    public void onDrawFrame(GL10 gl) {
        if(xAngle > 360){
            xAngle = -360;
        }
        xAngle++;


        glClearColor(1f, 1f, 1f, 1f);
        glClear(GLES30.GL_COLOR_BUFFER_BIT | GLES30.GL_DEPTH_BUFFER_BIT);


        glUseProgram(mCubeShaderProgram);
        //创建模型加载矩阵
//        Matrix.setRotateM(mMMatrix, 0,
//                0, 0, 1, 0);

        mMMatrix[0] = 1;
        mMMatrix[1] = 0;
        mMMatrix[2] = 0;
        mMMatrix[3] = 0;

        mMMatrix[4] = 0;
        mMMatrix[5] = 1;
        mMMatrix[6] = 0;
        mMMatrix[7] = 0;

        mMMatrix[8] =  0;
        mMMatrix[9] =  0;
        mMMatrix[10] = 1;
        mMMatrix[11] = 0;

        mMMatrix[12] = 0;
        mMMatrix[13] = 0;
        mMMatrix[14] = 0;
        mMMatrix[15] = 1;


        //位移 z轴正向位移
        Matrix.translateM(mMMatrix, 0,
                0, 0, 0);

        Matrix.rotateM(mMMatrix, 0, xAngle,
                0, 1, 0);

        glUniformMatrix4fv( 2 , 1 , false , calMVPMatrix() , 0);

        //设置数据
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, mVertexPosBuf);
        glVertexAttribPointer(1, 4, GL_FLOAT, false, 0, mVertexColorBuf);

        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);

        glDrawElements(GL_TRIANGLES, 36, GL_UNSIGNED_SHORT, mIndicesBuf);
    }

    public float[] calMVPMatrix(){
        if(mMVPMatrix == null){
            mMVPMatrix = new float[4 * 4];
        }
        mMVPMatrix[0] = 1;
        mMVPMatrix[1] = 0;
        mMVPMatrix[2] = 0;
        mMVPMatrix[3] = 0;

        mMVPMatrix[4] = 0;
        mMVPMatrix[5] = 1;
        mMVPMatrix[6] = 0;
        mMVPMatrix[7] = 0;

        mMVPMatrix[8] = 0;
        mMVPMatrix[9] = 0;
        mMVPMatrix[10] = 1;
        mMVPMatrix[11] = 0;

        mMVPMatrix[12] = 0;
        mMVPMatrix[13] = 0;
        mMVPMatrix[14] = 0;
        mMVPMatrix[15] = 1;

        //位移操作
        Matrix.multiplyMM(mMVPMatrix, 0, mVMatrix, 0, mMMatrix, 0);

        //合并投影和视口矩阵
        Matrix.multiplyMM(mMVPMatrix, 0, mProjMatrix, 0, mMVPMatrix, 0);

        return mMVPMatrix;
    }
}//end class

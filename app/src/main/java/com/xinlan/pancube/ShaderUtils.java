package com.xinlan.pancube;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.opengl.GLES30;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static android.opengl.GLES30.*;

public class ShaderUtils {
    public static Application ctx;

    public static int buildShaderProgram(int vertexShaderCodeId, int fragShaderCodeId) {
        return buildShaderProgram(
                readTextFileFromRaw(null, vertexShaderCodeId),
                readTextFileFromRaw(null, fragShaderCodeId));
    }

    /**
     * 创建shader program
     *
     * @param vertexShaderCode
     * @param fragShaderCode
     * @return
     */
    public static int buildShaderProgram(String vertexShaderCode, String fragShaderCode) {
        int program = GLES30.glCreateProgram();

        int vertexShader = loadShader(GLES30.GL_VERTEX_SHADER, vertexShaderCode);
        int fragShader = loadShader(GL_FRAGMENT_SHADER, fragShaderCode);

        glAttachShader(program, vertexShader);
        glAttachShader(program, fragShader);

        glLinkProgram(program);
        return program;
    }

    /**
     * 导入 顶点着色器 或者 片段着色器源码
     *
     * @param type
     * @param shaderCode
     * @return
     */
    public static int loadShader(int type, String shaderCode) {
        int shader = GLES30.glCreateShader(type);
        glShaderSource(shader, shaderCode);
        glCompileShader(shader);
        return shader;
    }

    /**
     * 从资源文件中读出文本文件
     *
     * @param context
     * @param resourceId
     * @return
     */
    public static String readTextFileFromRaw(Context context, int resourceId) {
        if (context == null)
            context = ctx;

        StringBuilder body = new StringBuilder();

        try {
            InputStream inputStream =
                    context.getResources().openRawResource(resourceId);
            InputStreamReader inputStreamReader =
                    new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String nextLine;
            while ((nextLine = bufferedReader.readLine()) != null) {
                body.append(nextLine);
                body.append('\n');
            }
        } catch (IOException e) {
            throw new RuntimeException(
                    "Could not open resource: " + resourceId, e);
        } catch (Resources.NotFoundException nfe) {
            throw new RuntimeException("Resource not found: " + resourceId, nfe);
        }
        return body.toString();
    }

}//end class

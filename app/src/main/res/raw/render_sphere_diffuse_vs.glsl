#version 300 es

uniform mat4 u_mvp_matrix;
uniform mat4 uModelMat;
uniform vec3 uLightPos;

layout(location = 0) in vec3 a_pos;
layout(location = 1) in vec3 aNormal;

out vec3 vPosition;
out vec4 vAmbientColor;
out vec4 vDiffuseLight;

void main(){
    //gl_PointSize = 8.0f;
    vec3 lightPosition = vec3(1.0f , 0.0f , 1.2f);
    vec4 pos = vec4(a_pos , 1);
    gl_Position = u_mvp_matrix * pos;
    vPosition = a_pos;

    vec3 tmpNormal = aNormal + a_pos;

    vec4 modelPos = normalize(uModelMat * vec4(a_pos , 1.0f));
    vec4 modelNormal = normalize(uModelMat * vec4(aNormal + a_pos , 1.0f));

    vec3 normalLightPos = normalize(uLightPos);

    vAmbientColor = vec4(0.2f , 0.2f , 0.2f , 1.0f);
    vDiffuseLight = max(dot(modelPos , modelNormal) , 0.0f) * vec4(1.0f , 1.0f , 1.0f , 1.0f);;
    //vDiffuseColor = vec4(1.0f , 0.0f , 0.0f , 1.0f);
}
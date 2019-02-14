#version 300 es

uniform float uTime;

//layout(location = 0) in vec3 aColor;
layout(location = 0) in vec3 aStartPos;
layout(location = 1) in vec3 aEndPos;
layout(location = 2) in vec3 aCenterPos;

out vec3 vColor;

void main(){
    float aLifeTime = uTime;

    aLifeTime = clamp(0.0f , 1.0f , aLifeTime);

    vec3 finalPos = (1.0f - aLifeTime) * aStartPos + aLifeTime * aEndPos;

    finalPos = finalPos + aCenterPos;
    gl_Position = vec4(finalPos.xyz , 1.0f);

    gl_PointSize =  aLifeTime * aLifeTime * 40.0f;

    vColor = vec3(0.45f , 0.23f , 0.89f);
}
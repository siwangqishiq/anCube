#version 300 es

layout(location = 0) in vec3 aPos;
uniform mat4 uMvpMatrix;

out vec3 textureCoord;

void main(){
    textureCoord = normalize(aPos);
    gl_Position = uMvpMatrix * vec4(aPos , 1.0f);
}
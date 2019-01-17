#version 300 es

layout(location = 0) in vec3 a_pos;

out vec3 v_pos;

void main(){
    gl_Position = v_pos;
}
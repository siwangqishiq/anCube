#version 300 es

layout(location = 0) in vec2 a_pos;
layout(location = 1) in vec4 a_color;

out vec4 v_color;

void main(){
    gl_Position = vec4(a_pos , 0.0f , 1.0f);
    v_color = a_color;
    gl_PointSize = 20.0f;
}
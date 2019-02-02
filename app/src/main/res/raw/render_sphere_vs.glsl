#version 300 es

uniform mat4 u_mvp_matrix;
layout(location = 0) in vec3 a_pos;

void main(){
    gl_PointSize = 8.0f;
    vec4 pos = vec4(a_pos , 1);
    gl_Position = u_mvp_matrix * pos;
}
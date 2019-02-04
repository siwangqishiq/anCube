#version 300 es

uniform mat4 u_mvp_matrix;
layout(location = 0) in vec3 a_pos;

void main(){
    vec4 pos = vec4(a_pos , 1);
    gl_Position = u_mvp_matrix * pos;
    //gl_PointSize = 5.0f;
}
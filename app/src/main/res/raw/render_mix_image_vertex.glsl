#version 300 es

precision mediump float;

layout(location = 0) in vec2 a_pos;
layout(location = 1) in vec2 a_coord;

out vec2 frg_coord;

void main(){
    gl_Position = vec4(a_pos.xy , 0 , 1);
    frg_coord = a_coord;
}
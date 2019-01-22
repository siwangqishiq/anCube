#version 300 es

precision mediump float;

in vec2 frg_coord;
out vec3 out_color;

uniform sampler2D s_texture;

void main(){
    //out_color = vec3(1,  0 , 0 );
    out_color = texture(s_texture , frg_coord).rgb;
}
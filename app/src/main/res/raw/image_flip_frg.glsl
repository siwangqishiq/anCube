#version 300 es

precision mediump float;

in vec2 frg_coord;
out vec3 out_color;

const float one = 1.0;

uniform sampler2D s_texture;

void main(){
    vec2 tex_coord = vec2(one - frg_coord.x , frg_coord.y);
    out_color = texture(s_texture , tex_coord).rgb;
}
#version 300 es

precision mediump float;

in vec4 v_color;
out vec3 frag_color;

void main(){
    frag_color = v_color.xyz;
    //frag_color = vec3(1,0,0);
}
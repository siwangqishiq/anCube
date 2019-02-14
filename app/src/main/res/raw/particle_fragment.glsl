#version 300 es

precision mediump float;

in vec3 vColor;
out vec4 fragColor;

void main(){
    if (length(gl_PointCoord - vec2(0.5f , 0.5f)) > 0.5){
        discard;
    }
    fragColor = vec4(vColor.xyz , 1.0f);
}
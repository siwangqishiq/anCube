#version 300 es

precision mediump float;

in vec3 textureCoord;
uniform samplerCube uSkybox;
//uniform sampler2D uSkybox;
out vec4 fragColor;

void main(){
    //fragColor = texture(uSkybox , normalize(textureCoord));
    //fragColor = vec4(normalize(textureCoord), 1.0f);
    fragColor = texture(uSkybox , textureCoord);
}
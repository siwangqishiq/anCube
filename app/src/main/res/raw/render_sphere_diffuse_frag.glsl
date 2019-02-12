#version 300 es

precision mediump float;

const float uR = 1.0f;

in vec3 vPosition;
in vec4 vAmbientColor;
in vec4 vDiffuseColor;

out vec4 frag_color;

void main(){
    vec3 color;
    float n = 8.0f;
    float span = 2.0f * uR/n;
    int i = int((vPosition.x + uR)/span);//当前片元位置小方块的行数
    int j = int((vPosition.y + uR)/span);
    int k = int((vPosition.z + uR)/span);

    int whichColor = int(mod(float(i+j+k),2.0));
    if(whichColor == 1) {//奇数时为红色
       	color = vec3(1.0f,0.0f,0.0f);//红色
    }else {//偶数时为白色
        color = vec3(1.0f,1.0f,1.0f);//白色
    }
    vec4 finalColor = vAmbientColor * vec4(color , 1.0);

    finalColor = finalColor + vDiffuseColor;
    frag_color = vec4(finalColor.xyz , 1.0f);
}
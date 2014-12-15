#version 330 core

#define MATERIAL_CAP 16

uniform struct Material
{
	sampler2D tex;
	sampler2D reflTex;
	vec4 color;
	float shininess;
	
} mats[MATERIAL_CAP];

in vec2 frag_tex;
in int frag_mat;

out vec4 out_color;

void main(void)
{
	/*Material mat = mats[frag_mat];
	
	out_color = mat.color;
	out_color = texture(mat.tex, frag_tex);*/
	out_color = vec4(0.0, 0.0, 0.5, 0.0);
	
}

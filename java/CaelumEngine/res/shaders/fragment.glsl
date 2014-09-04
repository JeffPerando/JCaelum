#version 330

#define MATERIAL_CAP 16

struct Material
{
	sampler2d tex;
	vec4 color;
	float shininess;
	
}

uniform Material mats[MATERIAL_CAP];

in vec2 frag_texcoord;
in int frag_mat_index;

out vec4 out_color;

void main()
{
	Material mat = mats[frag_mat_index];
	
	out_color = mat.color;
	out_color = texture(mat.tex, frag_texcoord);
	
}

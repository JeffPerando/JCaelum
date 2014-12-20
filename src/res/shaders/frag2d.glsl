#version 330 core

#define MATERIAL_CAP 8

uniform struct Material
{
	sampler2D tex;
	sampler2D glowTex;
	sampler2D renTex;
	vec4 filter;
	float shine;
	
} mats[MATERIAL_CAP];
uniform int matCount;

in vec2 frag_tex;
in int frag_mat;

out vec4 out_color;

void main(void)
{
	int m = clamp(frag_mat, 0, matCount);
	
	if (matCount > 0)
	{
		Material mat = mats[m];
		
		vec4 tex = mix(texture(mat.tex, frag_tex), texture(mat.renTex, frag_tex), mat.shine) * mat.filter;
		
		//TODO Calculate lighting
		
		vec4 glow = texture(mat.glowTex, frag_tex);
		
		if (glow.z < 1)
		{
			tex += glow;
			
		}
		
		out_color = tex;
		
	}
	else
	{
		out_color = vec4(1, 1, 1, 0);
		
	}
	
}

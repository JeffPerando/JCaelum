#version 330 core

uniform struct Material
{
	sampler2D tex;
	sampler2D renTex;
	sampler2D glowTex;
	vec4 filter;
	float shine;
	bool invert;
	
} mat;

in vec2 frag_tex;
in float frag_mat;

out vec4 out_color;

void main(void)
{
	vec4 mixed = mix(texture(mat.tex, frag_tex), texture(mat.renTex, frag_tex), shines[m]);
	
	vec4 fin = vec4((mat.filter.xyz * mat.filter.w) + (mixed.xyz * mixed.w), 0);
	
	if (invert[m])
	{
		fin.xyz = (1 - fin.xyz);
		
	}
	
	//TODO Calculate lighting
	
	vec4 glow = texture(mat.glowTex, frag_tex);
	
	if (glow.w < 1)
	{
		fin.xyz += (glow.xyz * glow.w);
		
	}
	
	out_color = fin;
	
}

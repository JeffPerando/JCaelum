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

out vec4 out_color;

void main(void)
{
	vec4 mixed = mix(texture(mat.tex, frag_tex), texture(mat.renTex, frag_tex), mat.shine);
	
	vec4 fin = vec4((mat.filter.rgb * mat.filter.a) + (mixed.rgb * mixed.a), 0);
	
	if (mat.invert)
	{
		fin.rgb = (1 - fin.rgb);
		
	}
	
	//TODO Calculate lighting
	
	vec4 glow = texture(mat.glowTex, frag_tex);
	
	if (glow.a < 1)
	{
		fin.rgb += (glow.rgb * glow.a);
		
	}
	
	out_color = fin;
	
}

#version 330 core

#define MATERIAL_CAP 8

uniform sampler2D texes[MATERIAL_CAP];
uniform sampler2D rTexes[MATERIAL_CAP];
uniform sampler2D gTexes[MATERIAL_CAP];
uniform vec3 filters[MATERIAL_CAP];
uniform float shines[MATERIAL_CAP];
uniform int matCount;

in vec2 frag_tex;
in float frag_mat;

out vec4 out_color;

void main(void)
{
	if (matCount == 0)
	{
		out_color = vec4(1, 1, 1, 0);
		return;
	}
	
	unsigned int m = int(clamp(frag_mat, 0, matCount));
	
	vec4 fin = vec4(filters[m].xyz, 0);
	
	vec4 tex = texture(texes[m], frag_tex);
	vec4 ren = texture(rTexes[m], frag_tex);
	
	vec4 mixed = mix(tex, ren, shines[m]);
	
	if (mixed.w > 0)
	{
		fin.xyz += (mixed.xyz * mixed.w);
		
	}
	
	//TODO Calculate lighting
	
	vec4 glow = texture(gTexes[m], frag_tex);
	
	if (glow.w < 1)
	{
		fin.xyz += (glow.xyz * glow.w);
		
	}
	
	out_color = fin;
	
}

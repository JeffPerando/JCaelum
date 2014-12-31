#version 330 core

#define MATERIAL_CAP 8
#define LIGHT_CAP 128

uniform struct Material
{
	sampler2D tex;
	sampler2D glowTex;
	sampler2D renTex;
	vec4 filter;
	float shine;
	
} mats[MATERIAL_CAP];
uniform int matCount;

uniform struct Light
{
	vec4 pos;
	vec3 color;
	float attenuation;
	float ambientCoefficient;
	float coneAngle;
	vec3 coneDirection;
	
} lights[LIGHT_CAP];

in vec2 frag_tex;
in vec3 frag_norm;
in unsigned int frag_mat;

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

vec3 applyLight(Light light, Material mat)
{
	return vec3(0);
}

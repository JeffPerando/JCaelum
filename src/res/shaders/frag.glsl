#version 330 core

#define LIGHT_CAP 128

uniform struct Material
{
	sampler2D tex;
	sampler2D renTex;
	sampler2D glowTex;
	vec4 filter;
	float shine;
	bool invert;
	
} mat;

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

out vec4 out_color;

void main(void)
{
	vec4 mixed = mix(texture(mat.tex, frag_tex), texture(mat.renTex, frag_tex), mat.shine);
	
	vec4 fin = vec4((mat.filter.xyz * mat.filter.w) + (mixed.xyz * mixed.w), 0);
	
	if (mat.invert)
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

vec3 applyLight(Light light, Material mat)
{
	return vec3(0);
}

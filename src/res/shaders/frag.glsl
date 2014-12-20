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
in int frag_mat;

out vec4 out_color;

void main(void)
{
	int m = clamp(frag_mat, 0, matCount);
	
	if (matCount > 0)
	{
		Material mat = mats[m];
		
		vec4 tex = tex = mix(texture(mat.tex, frag_tex), texture(mat.renTex, frag_tex), mat.shine) * mat.filter;
		
		//TODO Calculate lighting
		
		if (mat.glowTex != 0)
		{
			tex += texture(mat.glowTex, frag_tex);
			
		}
		
		out_color = tex;
		
	}
	else
	{
		out_color = vec4(1, 1, 1, 0);
		
	}
	
}

vec3 applyLight(Light light, Material mat)
{
	return vec3(0);
}

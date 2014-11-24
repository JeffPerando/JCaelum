#version 330 core

uniform int matCount;
struct Material
{
	sampler2D tex;
	sampler2D reflTex;
	vec4 color;
	float shininess;
	
} mats[matCount];

uniform int lightCount;
uniform struct Light
{
	vec4 pos;
	vec3 color;
	float attenuation;
	float ambientCoefficient;
	float coneAngle;
	vec3 coneDirection;
	
} lights[lightCount];

in vec2 frag_tex;
in vec3 frag_norm;
in int frag_mat;

out vec4 out_color;

void main(void)
{
	Material mat = mats[frag_mat];
	
	out_color = mat.color;
	out_color = texture(mat.tex, frag_tex);
	
}

vec3 applyLight(Light light, Material mat)
{
	
	
}

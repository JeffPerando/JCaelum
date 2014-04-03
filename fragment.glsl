#version 330

#define LIGHT_CAP 1024
#define MATERIAL_CAP 16

struct Light
{
	vec3 position;
	vec3 direction;
	vec4 colors;
	float brightness;
	
}

struct Material
{
	sampler2d tex;
	vec4 color;
	float shininess;
	
}

uniform vec3 camPos;

uniform int lightCount;
uniform float darknessLevel;
uniform Light lights[LIGHT_CAP];
uniform Light sun;
uniform Material mats[MATERIAL_CAP];

in vec2 frag_texcoord;
in vec3 frag_norm;

in mat4 frag_m;
in int frag_mindex;

void main()
{
	gl_FragColor = null;
	
}

vec3 calculateLighting(Light l)
{

}
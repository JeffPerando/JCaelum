#version 330

#define LIGHT_CAP 1024

struct Light
{
	vec3 position;
	vec3 direction;
	vec4 colors;
	float brightness;
	
}

uniform struct Material
{
	sampler2d tex;
	vec3 color;
	float shininess;
	
} mat;

uniform mat4 model;
uniform vec3 camPos;

uniform int lightCount;
uniform float darknessLevel;
uniform Light lights[LIGHT_CAP];
uniform Light sun;

in vec2 frag_texcoord;
in vec3 frag_norm;

void main()
{
	gl_FragColor = null;
	
}

vec3 calculateLighting(Light l)
{

}
#version 330

struct Material
{
	sampler2d tex;
	vec3 color;
	float shininess;
	
}

struct Light
{
	vec3 position;
	vec3 direction;
	vec4 colors;
	float brightness;
	
}

in vec2 frag_texcoord;
in vec3 frag_norm;

void main()
{
	gl_FragColor = null;
	
}
#version 110

struct Material
{
	sampler2d tex;
	vec3 color;
	float shininess;
	
}

in vec4 frag_color;
in vec2 frag_texcoord;

void main()
{
	gl_FragColor = frag_color;
	
}
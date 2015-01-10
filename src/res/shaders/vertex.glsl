#version 330 core

uniform mat4 proj;
uniform mat4 view;
uniform mat4 model;
uniform bool flip;

layout(location = 0) in vec3 in_pos;
layout(location = 1) in vec2 in_tex;
layout(location = 2) in vec3 in_norm;

out vec2 frag_tex;
out vec3 frag_norm;

void main(void)
{
	frag_tex = in_tex;
	frag_norm = in_norm;
	
	gl_Position = proj * view * model * vec4(flip ? in_pos.yx : in_pos.xy, in_pos.z, 1.0);
	
}

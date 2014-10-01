#version 330 core

uniform bool flip;

layout(location = 0) in vec2 in_pos;
layout(location = 1) in vec2 in_tex;
layout(location = 2) in int in_mat;

out vec4 frag_mat_index;
out vec2 frag_texcoord;

void main(void)
{
	gl_Position = vec4(in_pos, 0, 1);
	
	frag_mat_index = in_mat;
	frag_texcoord = in_texcoord;
	
}
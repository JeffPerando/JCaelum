#version 330 core

uniform bool flip;

layout(location = 0) in vec2 in_pos;
layout(location = 1) in vec2 in_tex;
layout(location = 2) in float in_mat;

out vec2 frag_tex;
out float frag_mat;

void main(void)
{
	frag_tex = in_tex;
	frag_mat = in_mat;
	
	gl_Position = vec4(flip ? in_pos.yx : in_pos.xy, 0.0, 1.0);
	
}

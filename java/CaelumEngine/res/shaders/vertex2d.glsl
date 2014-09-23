#version 330 core

layout(location = 0) in vec2 in_pos;
//layout(location = 1) in vec2 in_tex;

//out vec4 frag_color;
//out vec2 frag_texcoord;

void main(void)
{
	gl_Position = vec4(in_pos, 0, 1);
	
//	frag_color = in_color;
//	frag_texcoord = in_texcoord;
	
}
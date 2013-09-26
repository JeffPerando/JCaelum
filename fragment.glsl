#version 110

in vec4 frag_color;
in vec2 frag_texcoord;

out vec4 fin_color;
out vec2 fin_texcoord;

void main()
{
	fin_color = frag_color;
	fin_texcoord = frag_texcoord;
	
}
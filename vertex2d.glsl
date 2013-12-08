#version 330

(location = 0) in vec2 in_position;
(location = 1) in vec4 in_color;
(location = 2) in vec2 in_texcoord;

out vec4 frag_color;
out vec2 frag_texcoord;

void main()
{
	gl_Position = vec4(in_position, 0, 1);
	
	frag_color = in_color;
	frag_texcoord = in_texcoord;
	
}
#version 330

(location = 0) in vec3 in_position;
(location = 1) in vec4 in_color;

out vec4 frag_color;

void main()
{
	gl_Position = vec4(in_position, 1);
	
	frag_color = in_color;
	
}
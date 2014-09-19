#version 330

(location = 0) in vec2 in_pos;
//(location = 1) in vec2 in_tex;

//out vec4 frag_color;
//out vec2 frag_texcoord;

void main()
{
	gl_Position = vec4(in_pos, 0, 1);
	
//	frag_color = in_color;
//	frag_texcoord = in_texcoord;
	
}
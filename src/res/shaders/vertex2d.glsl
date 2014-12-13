#version 330 core

//uniform bool flip;

layout(location = 0) in vec2 in_pos;
layout(location = 1) in vec2 in_tex;
layout(location = 2) in int in_mat;

out vec2 frag_tex;
out int frag_mat;

void main(void)
{
	frag_tex = in_tex;
	frag_mat = in_mat;
	
	/*vec4 vtx = vec4(in_pos.xy, 0.0, 1.0);
	
	if (flip)
	{
		vtx.x = in_pos.y;
		vtx.y = in_pos.x;
		
	}*/
	
	gl_Position = vec4(in_pos.xy, 0.0, 1.0);
	
}

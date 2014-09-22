#version 330 core

uniform mat4 proj;
uniform mat4 view;
uniform mat4 model;
uniform boolean flip;

(location = 0) in vec3 in_pos;
(location = 1) in vec2 in_tex;
(location = 2) in vec3 in_norm;
(location = 3) in int in_mindex;

out vec2 frag_texcoord;
out vec3 frag_norm;
out int frag_mindex;

void main(void)
{
	frag_tex = in_tex;
	frag_norm = in_norm;
	
	frag_m = model;
	frag_mindex = in_mindex;
	
	vec4 vtx = vec4(in_pos.xyz, 1.0);
	
	if (flip)
	{
		vtx.x = in_pos.y;
		vtx.y = in_pos.x;
		
	}
	
	gl_Position = proj * view * model * vtx;
	
}

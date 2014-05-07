#version 330

uniform mat4 proj;
uniform mat4 view;
uniform mat4 model;
uniform boolean flip;

(location = 0) in vec3 in_pos;
(location = 1) in vec2 in_tex;
(location = 2) in vec3 in_norm;

(location = 3) in vec3 in_mat;

(location = 4) in int in_mindex;

out vec2 frag_texcoord;
out vec3 frag_norm;

out mat4 frag_m;
out int frag_mindex;

void main()
{
	frag_tex = in_tex;
	frag_norm = in_norm;
	
	frag_m = model;
	frag_mindex = in_mindex;
	
	gl_Position = proj * view * (in_mat * vec4(vec3(flip ? in_pos.y : in_pos.x, flip ? in_pos.x : in_pos.y, in_pos.z), 1.0));
	
}

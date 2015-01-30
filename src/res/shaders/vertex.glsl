#version 330 core

uniform mat4 proj;
uniform mat4 view;
uniform mat4 model;
uniform bool flip;

layout(location = 0) in vec3 in_pos;
layout(location = 1) in vec2 in_tex;
layout(location = 2) in vec3 in_norm;

layout(location = 3) in vec4 in_anim0;
layout(location = 4) in vec4 in_anim1;
layout(location = 5) in vec4 in_anim2;
layout(location = 6) in vec4 in_anim3;

out vec2 frag_tex;
out vec3 frag_norm;

void main(void)
{
	frag_tex = in_tex;
	frag_norm = in_norm;
	
	gl_Position = proj * view * model * vec4(flip ? in_pos.yx : in_pos.xy, in_pos.z, 1.0) * mat4(in_anim0.xyzw, in_anim1.xyzw, in_anim2.xyzw, in_anim3.xyzw);
	
}

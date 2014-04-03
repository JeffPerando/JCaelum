#version 330

uniform mat4 proj;
uniform mat4 view;
uniform mat4 model;
uniform boolean flip;

(location = 0) in vec3 in_pos;
(location = 1) in vec2 in_tex;
(location = 2) in vec3 in_norm;

(location = 3) in vec3 in_scale;
(location = 4) in vec3 in_rot;
(location = 5) in vec3 in_trans;
(location = 6) in int in_mindex;

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
	
	gl_Position = proj * view * (trans(in_trans) * rotate(in_rot) * scale(in_scale) * vec4(vec3(flip ? in_pos.y : in_pos.x, flip ? in_pos.x : in_pos.y, in_pos.z), 1.0));
	
}

mat4 scale(vec3 s)
{
	return mat4(s.x, 0.0, 0.0, 0.0,
				0.0, s.y, 0.0, 0.0,
				0.0, 0.0, s.z, 0.0,
				0.0, 0.0, 0.0, 1.0);
}

mat4 rotate(vec3 r)
{
	float a = cos(r.x);
	float b = sin(r.x);
	float c = cos(r.y);
	float d = sin(r.y);
	float e = cos(r.z);
	float f = sin(r.z);
	
	float ad = a * d;
	float bd = b * d;
	
	return mat4(c * e, -c * f, d, 0.0,
				bd * e + a * f, -bd * f + a * e, -b * c, 0.0,
				-ad * e + b * f, ad * f + b * e, a * c, 0.0,
				0.0, 0.0, 0.0, 1.0);
}

mat4 trans(vec3 t)
{
	return mat4(1.0, 0.0, 0.0, t.x,
				0.0, 1.0, 0.0, t.y,
				0.0, 0.0, 1.0, t.z,
				0.0, 0.0, 0.0, 1.0);
}

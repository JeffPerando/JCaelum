#version 110

uniform mat4 cam;
uniform mat4 proj;

in vec4 in_position;
in vec4 in_color;
in vec2 in_texcoord;

in vec4 in_scale;
in vec4 in_rot;
in vec4 in_trans;

out vec4 frag_color;
out vec2 frag_texcoord;

void main()
{
	frag_texcoord = in_texcoord;
	frag_color = in_color;
	
	mat4 scl = scale();
	mat4 rot = rotate();
	mat4 trans = trans();
	
	mat4 fin = trans * rot * scl * in_position;
	
	gl_Position = proj * cam * fin;
	
}

mat4 scale()
{
	return mat4(in_scale.x, 0, 0, 0,
				0, in_scale.y, 0, 0,
				0, 0, in_scale.z, 0,
				0, 0, 0, in_scale.w);
}

mat4 rotate()
{
	float a = cos(in_rot.x);
	float b = sin(in_rot.x);
	float c = cos(in_rot.y);
	float d = sin(in_rot.y);
	float e = cos(in_rot.z);
	float f = sin(in_rot.z);
	
	float ad = a * d;
	float bd = b * d;
	
	return mat4(c * e, -c * f, d, 0,
				bd * e + a * f, -bd * f + a * e, -b * c, 0,
				-ad * e + b * f, ad * f + b * e, a * c, 0,
				0, 0, 0, 1);
	
}

mat4 trans()
{
	return mat4(1, 0, 0, in_trans.x,
				0, 1, 0, in_trans.y,
				0, 0, 1, in_trans.z,
				0, 0, 0, 1);
}

#version 110

uniform mat4 cam;
uniform mat4 proj;

uniform vec3 model_pos;
uniform vec3 model_scale;
uniform vec3 model_rot;

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
	
	mat4 fin = trans(in_trans) * rotate(in_rot) * scale(in_scale) * in_position;
	
	gl_Position = proj * cam * fin;
	
}

mat4 scale(vec4 scl)
{
	return mat4(scl.x, 0.0, 0.0, 0.0,
				0.0, scl.y, 0.0, 0.0,
				0.0, 0.0, scl.z, 0.0,
				0.0, 0.0, 0.0, scl.w);
}

mat4 rotate(vec4 rot)
{
	float a = cos(rot.x);
	float b = sin(rot.x);
	float c = cos(rot.y);
	float d = sin(rot.y);
	float e = cos(rot.z);
	float f = sin(rot.z);
	
	float ad = a * d;
	float bd = b * d;
	
	return mat4(c * e, -c * f, d, 0.0,
				bd * e + a * f, -bd * f + a * e, -b * c, 0.0,
				-ad * e + b * f, ad * f + b * e, a * c, 0.0,
				0.0, 0.0, 0.0, 1.0);
}

mat4 trans(vec4 trans)
{
	return mat4(1.0, 0.0, 0.0, trans.x,
				0.0, 1.0, 0.0, trans.y,
				0.0, 0.0, 1.0, trans.z,
				0.0, 0.0, 0.0, 1.0);
}

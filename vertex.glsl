#version 440

in vec4 in_position;
in vec4 in_color;
in vec2 in_texcoord;

in vec4 in_scale;
in vec4 in_rot;
in vec4 in_trans;

void main()
{
	mat4 scl = scale();
	mat4 rot = rotate();
	mat4 trans = trans();
		
	gl_Position = trans * rot * scl * in_position;
	
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
  
}

mat4 translate()
{
  return mat4(1, 0, 0, in_trans.x,
              0, 1, 0, in_trans.y,
              0, 0, 1, in_trans.z,
              0, 0, 0, 1);
}

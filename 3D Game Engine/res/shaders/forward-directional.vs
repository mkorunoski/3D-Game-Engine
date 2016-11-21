#version 120

attribute vec3 position;
attribute vec2 texCoord;
attribute vec3 normal;

uniform mat4 T_model;
uniform mat4 T_MVP;

varying vec3 f_position;
varying vec2 f_texCoord;
varying vec3 f_normal;

void main()
{
	f_position = (T_model * vec4(position, 1)).xyz;
	f_texCoord = texCoord;
	f_normal = (T_model * vec4(normal, 0)).xyz;

	gl_Position = T_MVP * vec4(position, 1);
}
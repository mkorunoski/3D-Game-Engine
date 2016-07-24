#version 120

attribute vec3 position;
attribute vec2 texCoord;
attribute vec3 normal;

uniform mat4 model;
uniform mat4 MVP;

varying vec3 f_position;
varying vec2 f_texCoord;
varying vec3 f_normal;

void main()
{
	f_position = (model * vec4(position, 1)).xyz;
	f_texCoord = texCoord;
	f_normal = (model * vec4(normal, 0)).xyz;

	gl_Position = MVP * vec4(position, 1);
}
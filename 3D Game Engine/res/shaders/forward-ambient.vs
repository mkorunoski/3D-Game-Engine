#version 120

attribute vec3 position;
attribute vec2 texCoord;

uniform mat4 MVP;

varying vec2 f_texCoord;

void main()
{
	gl_Position = MVP * vec4(position, 1);
	f_texCoord = texCoord;
}
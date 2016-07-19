#version 330

layout (location = 0) in vec3 position;
layout (location = 1) in vec2 texCoord;

uniform mat4 transform;

out vec2 f_texCoord;

void main()
{
	gl_Position = transform * vec4(position, 1.0f);
	f_texCoord = texCoord;
}
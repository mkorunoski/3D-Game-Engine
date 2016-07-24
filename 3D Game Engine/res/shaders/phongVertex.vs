#version 330

layout (location = 0) in vec3 pos;
layout (location = 1) in vec2 texCoord;
layout (location = 2) in vec3 normal;

uniform mat4 transform;
uniform mat4 transformProjected;

out vec3 f_position;
out vec2 f_texCoord;
out vec3 f_normal;

void main()
{
	f_position = (transform * vec4(pos, 1.0)).xyz;
	f_texCoord = texCoord;
	f_normal = (transform * vec4(normal, 0.0)).xyz;

	gl_Position = transformProjected * vec4(pos, 1.0);
}
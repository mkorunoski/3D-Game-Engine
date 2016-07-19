#version 330

in vec2 f_texCoord;

uniform sampler2D sampler;
uniform vec3 color;

out vec4 fragColor;

void main()
{
	fragColor = texture2D(sampler, f_texCoord) * vec4(color, 1.0f);
}
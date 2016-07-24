#version 330

varying vec2 f_texCoord;

uniform sampler2D sampler;
uniform vec3 ambientIntensity;

void main()
{
	gl_FragColor = texture2D(sampler, f_texCoord) * vec4(ambientIntensity, 1);
}
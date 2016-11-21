#version 120
#include "utils.glh"

varying vec2 f_texCoord;

uniform sampler2D diffuse;
uniform vec3 R_ambient;

void main()
{
	vec4 texColor = texture2D(diffuse, f_texCoord);
	discardFragment(texColor);
	gl_FragColor = texColor * vec4(R_ambient, 1);
}
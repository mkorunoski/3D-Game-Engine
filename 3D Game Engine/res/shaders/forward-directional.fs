#version 120
#include "lighting.glh"
#include "utils.glh"

//	===============================
//	FS INPUT
//	===============================
varying vec3 f_position;
varying vec2 f_texCoord;
varying vec3 f_normal;

//	===============================
//	UNIFORMS
//	===============================
uniform sampler2D diffuse;
uniform DirectionalLight R_directionalLight;

//	===============================
//	===============================
//	MAIN
//	===============================
//	===============================
void main()
{
	vec4 texColor = texture2D(diffuse, f_texCoord);
	discardFragment(texColor);
	gl_FragColor = texColor * calcDirectionalLight(R_directionalLight, normalize(f_normal), f_position);
}
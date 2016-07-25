#version 120
#define POW2(x) (x * x)

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
uniform float specularIntensity;
uniform float specularExponent;
uniform vec3 eyePosition;

//	===============================
//	STRUCTS
//	===============================
struct BaseLight
{
	vec3 color;
	float intensity;
};

struct Attenuation
{
	float constant;
	float linear;
	float exponent;
};

struct PointLight
{
	BaseLight base;
	Attenuation atten;
	vec3 position;
	float range;
};
uniform PointLight pointLight;

//	===============================
//	FUNCTIONS
//	===============================
vec4 calcLight(BaseLight base, vec3 direction, vec3 normal)
{
	float diffuseFactor = dot(normal, -direction);

	vec4 diffuseColor = vec4(0, 0, 0, 0);
	vec4 specularColor = vec4(0, 0, 0, 0);

	if(diffuseFactor > 0)
	{
		diffuseColor = vec4(base.color, 1.0) * base.intensity * diffuseFactor;

		vec3 directionToEye = normalize(eyePosition - f_position);
		vec3 reflectDirection = normalize(reflect(direction, normal));

		float specularFactor = dot(directionToEye, reflectDirection);
		specularFactor = pow(specularFactor, specularExponent);

		if(specularFactor > 0)
		{
			specularColor = vec4(base.color, 1.0) * specularIntensity * specularFactor;
		}
	}

	return diffuseColor + specularColor;
}

vec4 calcPointLight(PointLight pointLight, vec3 normal)
{
	vec3 lightDirection = f_position - pointLight.position;
	float distanceToPoint = length(lightDirection);

	if(distanceToPoint > pointLight.range)
	{
		return vec4(0, 0, 0, 0);
	}

	lightDirection = normalize(lightDirection);

	vec4 color = calcLight(pointLight.base, lightDirection, normal);

	float attenuation = pointLight.atten.constant +
						pointLight.atten.linear * distanceToPoint +
						pointLight.atten.exponent * POW2(distanceToPoint) +
						0.000001;

	return color / attenuation;
}

//	===============================
//	===============================
//	MAIN
//	===============================
//	===============================
void main()
{
	gl_FragColor = texture2D(diffuse, f_texCoord) * calcPointLight(pointLight, normalize(f_normal));
}
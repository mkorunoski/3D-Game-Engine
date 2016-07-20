#version 330
#define MAX_POINT_LIGHTS 4
#define MAX_SPOT_LIGHTS 4
#define POW2(x) (x * x)

//	===============================
//	FS INPUT
//	===============================
in vec3 f_position;
in vec2 f_texCoord;
in vec3 f_normal;

//	===============================
//	UNIFORMS
//	===============================
uniform sampler2D sampler;
uniform vec3 baseColor;
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

uniform vec3 ambientLight;

struct DirectionalLight
{
	BaseLight base;
	vec3 direction;
};
uniform DirectionalLight directionalLight;

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
uniform PointLight pointLights[MAX_POINT_LIGHTS];

struct SpotLight
{
	PointLight pointLight;
	vec3 direction;
	float cutoff;
};
uniform SpotLight spotLights[MAX_SPOT_LIGHTS];

//	===============================
//	FS OUTPUT
//	===============================
out vec4 fragColor;

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

vec4 calcDirectionalLight(DirectionalLight directionalLight, vec3 normal)
{
	return calcLight(directionalLight.base, -directionalLight.direction, normal);
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

vec4 calcSpotLight(SpotLight spotLight, vec3 normal)
{
	vec3 lightDirection = normalize(f_position - spotLight.pointLight.position);
	float spotFactor = dot(lightDirection, spotLight.direction);

	vec4 color = vec4(0, 0, 0, 0);

	if(spotFactor > spotLight.cutoff)
	{
		color = calcPointLight(spotLight.pointLight, normal) * 
				(1.0 - (1.0 - spotFactor) / (1.0 - spotLight.cutoff));
	}

	return color;
}

//	===============================
//	===============================
//	MAIN
//	===============================
//	===============================
void main()
{
	vec4 totalLight = vec4(ambientLight, 1);
	vec4 color = vec4(baseColor, 1);
	vec4 textureColor = texture2D(sampler, f_texCoord);
	
	if(textureColor != vec4(0, 0, 0, 0))
		color *= textureColor;

	vec3 normal = normalize(f_normal);

	totalLight += calcDirectionalLight(directionalLight, normal);

	for(int i = 0; i < MAX_POINT_LIGHTS; ++i)
	{
		if(pointLights[i].base.intensity > 0)
		{
			totalLight += calcPointLight(pointLights[i], normal);
		}
	}

	for(int i = 0; i < MAX_SPOT_LIGHTS; ++i)
	{
		if(spotLights[i].pointLight.base.intensity > 0)
		{
			totalLight += calcSpotLight(spotLights[i], normal);
		}
	}

	fragColor = color * totalLight;
}
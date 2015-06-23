varying vec3 N;
varying vec4 pos;
varying float NdotL;
uniform vec4 lightDir;

void main()
{

	
	
	N = normalize(gl_Normal);
	
 	NdotL = dot(N,normalize(vec3(lightDir)));
 
	
	pos = gl_Vertex;
	
	gl_Position = gl_ModelViewProjectionMatrix * gl_Vertex;
}
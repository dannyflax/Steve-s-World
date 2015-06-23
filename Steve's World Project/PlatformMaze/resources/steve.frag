varying vec3 N;
varying float NdotL;
varying vec4 pos;
uniform float shadow;
uniform vec3 ppos;
void main()
{
	
	float bonus = 0.0;
	if(shadow!=0.0){
		bonus = 0.0;
	}
		
	vec4 color = vec4(1.0,0.0,0.0,1.0) * min(NdotL + bonus, 1.0);
	
	if(pow(ppos.x - pos.x,2.0) + pow(ppos.z - pos.z,2.0)<=pow(1.5,2.0)){
		color.r = color.r - shadow;
	}
	
	gl_FragColor = color;
        
}
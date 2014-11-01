varying vec3 N;
varying float NdotL;
varying vec4 pos;
uniform vec4 ppos;
uniform float godPlatform;
uniform vec4 clr;
void main()
{
	
	
	vec4 color = clr;
	
	color *= max(NdotL, 0.1);
	if(godPlatform!=1.0){
		if(pow(ppos.x - pos.x,2.0) + pow(ppos.z - pos.z,2.0)<=pow(1.7,2.0) && ppos.y>pos.y){
			color*= .25;
		}
	}
	
	gl_FragColor = color;
        
}


varying vec3 N;
varying float NdotL;
varying vec4 pos;
uniform vec4 ppos;
void main()
{
	
			
	vec4 color = vec4(0.0,0.0,1.0,1.0);
	float dis = sqrt(pow(ppos.x - pos.x,2.0) + pow(ppos.y - pos.y,2.0) + pow(ppos.z - pos.z,2.0));
	
	color = color * clamp(pow(100.0/dis,10.0) * 0.0005,0.1,0.8) * max(NdotL, 0.1) * 1.33;
	
	if(pow(ppos.x - pos.x,2.0) + pow(ppos.z - pos.z,2.0)<=pow(1.7,2.0) && ppos.y>pos.y){
		color*= .25;
	}
	
	gl_FragColor = color;
        
}
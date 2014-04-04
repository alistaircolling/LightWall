import toxi.color.TColor;
import toxi.geom.Vec2D;
import toxi.physics2d.VerletParticle2D;


public class Tadpole extends Object {

	private static final int MAX_SIZE = 10;
	public TColor color;
	public Vec2D vector;
	public int bounces;
	public Vec2D currentPos;
	public boolean removeMe;
	private float howFast = .0005f;//was .1f
	public VerletParticle2D particle;
	public int size = 3;

	public Tadpole(TColor random) {
		// TODO Auto-generated constructor stub
		color = random;
		size = (int) (Math.random()*MAX_SIZE);
		//make it slow if it is fat!
		if (size>(.75f*MAX_SIZE)){
		//	howFast *= .5f;
		}
		
		System.out.println("====Tadpole born======");
		System.out.println("size:"+size);
		
		
	}

	public void update() {
	
		//if moving left
		if (vector.x<0){
			//check if has reached left side
			if (currentPos.x<=0){
				//check if we have any bounces left
				if (bounces>0){
					//flip the vector
					vector.scale(-1);
					bounces --;
				}else{
					//no bounces left
					//flag to tell sketch to rempve from arraylist
					removeMe = true;
				}
			}
			
		}else{
			//is moving right
			//if has reached the right hand side
			if (currentPos.x>=ColorFader.GRADIENT_WIDTH){
				//check if we have bounces left
				if (bounces>0){
					//flip the vector
					vector.scale(-1);
					bounces--;
				}else{
					//no bounces left
					//flag to tell sketch to rempve from arraylist
					removeMe = true;
				}
			}
		}
		
		//add the movement vector to current position
		currentPos = currentPos.add(vector.scale(howFast));
		
		//stop if going on a flat line
//		if (vector.x < 0.1f){
//			vector.x += 0.01f;
//		}
//		if (vector.y == 0.1f){
//			vector.y += 0.01f;
//		}
		
		System.out.println(particle.getVelocity().toString());
		
	}

	

}

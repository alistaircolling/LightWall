import toxi.color.TColor;
import toxi.geom.Vec2D;
import toxi.physics2d.VerletParticle2D;


public class ColoredLine extends Object {

	public TColor color;
	public Vec2D vector;
	public int bounces;
	public Vec2D currentPos;
	public boolean removeMe;
	private float howFast = .5f;
	public VerletParticle2D particle;

	public ColoredLine(TColor random) {
		// TODO Auto-generated constructor stub
		color = random;
		
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
		
		
		
	}

	

}

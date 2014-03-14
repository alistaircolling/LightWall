package pong;

public class Human extends Paddle{
	
	private int direction;
	
	public Human(int side){
		this.setyPos(Game.height/2);
		this.setxPos(Game.width - 20);
		
		direction = 0;
		
		this.side = side; //RIGHT
		
		if(this.side == 1)
			this.setxPos(10);
		else
			this.setxPos(Game.width - 20);
	
	}

	@Override
	public void update() {
		if(this.getyPos() > Game.height - (this.getHeight()/2) && direction == 1)
			direction = 0;
		if(this.getyPos() < this.getHeight()/2 && direction == -1){
			direction = 0;
		}
		
		if(direction == 1)
			this.moveDown();
		if(direction == -1)
			this.moveUp();
	}
	
	public void keyUp(){
		direction = -1;
	}
	
	public void keyDown(){
		direction = 1;
	}
	
	public void keyRelease(){
		direction = 0;
	}

}

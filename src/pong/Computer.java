package pong;

public class Computer extends Paddle{
	
	int direction = 1;
	
	public Computer(int side){
		this.setyPos(Game.height/2);
		
		this.side = side; 
		if(this.side == 1)
			this.setxPos(10);
		else
			this.setxPos(Game.width - 20);
	}

	@Override
	public void update() {

		if(this.side == 1)
			if(Game.ball.xPos < Game.width/2){
				if(this.getyPos() - Game.ball.yPos > this.getSpeed())
					this.setyPos(this.getyPos() - this.getSpeed());
				else if(this.getyPos() - Game.ball.yPos < -this.getSpeed())
					this.setyPos(this.getyPos() + this.getSpeed());
				else
					this.setyPos(this.getyPos() - (this.getyPos() - Game.ball.yPos));
				
			}
		if(this.side == 2)
			if(Game.ball.xPos > Game.width/2){
				if(this.getyPos() - Game.ball.yPos > this.getSpeed())
					this.setyPos(this.getyPos() - this.getSpeed());
				else if(this.getyPos() - Game.ball.yPos < -this.getSpeed())
					this.setyPos(this.getyPos() + this.getSpeed());
				else
					this.setyPos(this.getyPos() - (this.getyPos() - Game.ball.yPos));
				
			}
	}

}

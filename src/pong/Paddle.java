package pong;

import java.awt.Color;
import java.awt.Graphics;

public abstract class Paddle {
	
	private double speed = .5f; //Default: 5
//	private double height = 60; //Default: 60
	private double height = 6; //Default: 60
//	private double width = 10; //Default: 10
	private double width = 1; //Default: 10
	private int score = 0;
	public int side;
	
	private double xPos, yPos;
	
	public double getxPos() {
		return xPos;
	}

	public void setxPos(double xPos) {
		this.xPos = xPos;
	}

	public double getyPos() {
		return yPos;
	}

	public void setyPos(double yPos) {
		if(yPos < height/2)
			this.yPos = height/2;
		else if (yPos > Pong.height - height/2)
			this.yPos = Pong.height - height/2;
		else
			this.yPos = yPos;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}
	
	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public abstract void update();
	
	public void draw(Graphics g){
		g.setColor(Color.WHITE);
		g.fillRect((int)xPos, (int)(yPos - (getHeight()/2.0)), (int)width, (int)getHeight());
	}
	
	public void addScore(){
		score++;
	}
	
	public void setScore(int score){
		this.score = score;
	}
	
	public int getScore(){
		return score;
	}
	
	public void moveDown(){
		yPos += speed;
	}
	
	public void moveUp(){
		yPos -= speed;
	}
	
}

package pong;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import toxi.color.ColorGradient;
import toxi.color.ColorList;
import toxi.color.TColor;

public class Ball {
	
	double diameter = 10; //Default: 10
//	double diameter = 2; //Default: 10
	
	double speed = 0;
	double angle = 0;
	
	double xPos, yPos;

	private double maxSpeed = .5f;
	private TColor ballColor;
	

	private ArrayList<TColor> ballColors;
	private ArrayList<TColor> bgColors;

	private ColorGradient bgGrad;
	private ColorGradient ballGrad;

	
	public Ball(int x, int y){
		reset();
	}
	
	
	private void createColors() {

		ballColors = new ArrayList<TColor>();
		bgColors = new ArrayList<TColor>();
		ballGrad = new ColorGradient();
		bgGrad = new ColorGradient();
		TColor newRan;
		TColor opp;
		for (int i = 0; i < 3; i++) {
			newRan = TColor.newRandom();
			opp = newRan.getInverted();
			ballColors.add(newRan);
			bgColors.add(opp);
			ballGrad.addColorAt(i * 400, newRan);
			bgGrad.addColorAt(i * 400, opp);
		}

	}

	
	public void update(){
		xPos += Math.cos(angle)*speed;
		yPos += Math.sin(angle)*speed;
		
		checkCollisions(Game.leftPaddle);
		checkCollisions(Game.rightPaddle);
		
		//Score on left side of board
		if(xPos <= 0 - diameter ){
			Game.rightPaddle.addScore();
			reset();
		}
		//Score on right side of board
		if (xPos >= Game.width){
			Game.leftPaddle.addScore();
			reset();
		}
		
		//Bounce if contacting top or bottom of window
		if(yPos <= 0 || yPos >= Game.height - (diameter/2.0)){
			angle = -angle;
		}
	}
	
	private void checkCollisions(Paddle paddle){
		
		
		
		if(paddle.side == 1) //LEFT
		
			if(xPos < paddle.getxPos() + paddle.getWidth())
				if( (yPos < paddle.getyPos() + (paddle.getHeight()/2.0) ) && 
					(yPos > paddle.getyPos() - (paddle.getHeight()/2.0) - diameter )){
					xPos = paddle.getxPos() + paddle.getWidth() + 1;
					angle = Math.PI - angle - (0.01*(yPos - paddle.getyPos()));
					addNewColor(0);
					if(speed < maxSpeed)
						speed += .05;
				}
		if(paddle.side == 2) //RIGHT
			if(xPos > paddle.getxPos())
				if( (yPos < paddle.getyPos() + (paddle.getHeight()/2.0) ) && 
					(yPos > paddle.getyPos() - (paddle.getHeight()/2.0) - diameter )){
					xPos = paddle.getxPos() -  1;
					angle = Math.PI - angle - (0.01*(yPos - paddle.getyPos()));
					addNewColor(1);
					if(speed < maxSpeed)
						speed += .05;
				}
		if (speed>maxSpeed){
			speed = maxSpeed;
		}
	}
	
	private void addNewColor(int i) {
		TColor newRand;
		TColor opp;
		ballGrad = new ColorGradient();
		if (i==0)//LEFT
		{
			//add a new color at 0
			newRand = TColor.newRandom();
			opp = newRand.getInverted();
			ballColors.remove(0);
			ballColors.add(0, newRand);
			bgColors.remove(0);
			bgColors.add(0, opp);
			
			
		}else{
			newRand = TColor.newRandom();
			opp = newRand.getInverted();
			ballColors.add(1, newRand);
			
			bgColors.add(1, opp);
			
		}
		
		for (int j = 0; j < 3; j++) {
			TColor bCol = ballColors.get(j);
			TColor bgCOL = bgColors.get(j);
			ballGrad.addColorAt(j * 400, bCol);
			bgGrad.addColorAt(j * 400, bgCOL);
		}
			
		
	}


	public float getX(){
		
		return (float) xPos;
		
	}
	
	private void reset(){
		createColors();
		xPos = Game.width/2;
		yPos = Game.height/2;
		angle = Math.random()*(Math.PI/2.0) + (3.0*Math.PI/4.0) ;
		angle += (int)(Math.random()*2)*Math.PI;
		//angle = 0;
		speed = 4; //Default: 4
	}
	
	public void draw(Graphics g){
		
		
		ColorList list = ballGrad.calcGradient(0, 400);
//background
		ballColor = list.get((int) xPos);
		
		TColor oppsCol = ballColor.getInverted();
		Color bgColz = new Color(oppsCol.toARGB());
		g.setColor(bgColz);
		g.fillRect(0, 0, 400, 250);
		
		Color col = new Color(ballColor.toARGB());
		g.setColor(col);
		g.fillRect((int)(xPos-(diameter*.5f)), (int)(yPos-(diameter*.5f)),(int) diameter, (int)diameter);
		Game.leftPaddle.setPaddleCol(ballColors.get(0));
		Game.rightPaddle.setPaddleCol(ballColors.get(1));
		//g.fillOval((int)(xPos - diameter/2.0), (int)(yPos - diameter/2.0), (int)diameter, (int)diameter);
	}

	public void setColor(TColor ballCol) {
		ballColor = ballCol;
		
	}
}

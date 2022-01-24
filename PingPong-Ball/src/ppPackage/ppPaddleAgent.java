package ppPackage;


import acm.program.GraphicsProgram;

import java.awt.Color;

import acm.graphics.GPoint;


import static ppPackage.ppSimParams.*;

public class ppPaddleAgent extends ppPaddle{
	
	private double X;					//Center of Paddle-X
	private double Y;					//Center of Paddle-Y
	private double Vx;
	private double Vy;
	ppBall myBall;
	private GraphicsProgram GProgram;
	
	/**
	 * Creates a paddle that acts as a computer opponent against the player
	 * paddle will automatically follow the height of the ball
	 * 
	 * A lag delay will determine the reaction speed of the paddle to the ball, making the game easier or harder.
	 * @param X
	 * @param Y
	 * @param myColor
	 * @param myTable
	 * @param GProgram
	 */
	
	public ppPaddleAgent(double X, double Y, Color myColor, ppTable myTable, GraphicsProgram GProgram) {
		super(X, Y, myColor, myTable, GProgram);
		this.GProgram = GProgram;
	}
	
	public void run() {
		int ballSkip = 0;
		int AgentLag = lag.getValue();
		double lastX = X;
		double lastY = Y;
		
		
		//Lag to delay the paddleAgent
		while(true) {
			
			Vx = (X-lastX)/TICK;
			Vy = (Y-lastY)/TICK;
			lastX = X;
			lastY = Y;
			
			GProgram.pause(TICK*TSCALE);
			
			if (ballSkip++ >= AgentLag) {
				// get ball Y position
				double Y = myBall.getP().getY();
				// set paddle position to that Y
				this.setP(new GPoint(this.getP().getX(), Y));
				ballSkip = 0;
			}
			
			
		}
	}
	
	//Setter method for ppBall class
	public void attachBall(ppBall myBall) {
		this.myBall = myBall;
		
	}

}

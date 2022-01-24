package ppPackage;

import acm.graphics.GRect;
import java.awt.Color;
import acm.graphics.GPoint;
import acm.program.GraphicsProgram;

import static ppPackage.ppSimParams.*;


/**
 * This class creates the paddle that can be controlled by the user to bounce back the
 * pingpong ball.
 * @author le-taoli
 *
 */
public class ppPaddle extends Thread {
	
	private double X;					//Center of Paddle-X
	private double Y;					//Center of Paddle-Y
	private double Vx;
	private double Vy;
	private GRect myPaddle;
	private ppTable myTable;			//Instance of ppTable class
	private GraphicsProgram GProgram;	//Instance of ppSims class
	private Color myColor;
	

	/**
	 * 
	 * ppPaddle class responsible for creating a paddle instance and exporting 
	 * methods for interacting with the paddle instance.
	 * @param X
	 * @param Y
	 * @param myTable
	 * @param GProgram
	 */
	public ppPaddle(double X, double Y, Color myColor, ppTable myTable, GraphicsProgram GProgram ) {
		//Code to create Grect representing paddle and add to display
		this.X=X;
		this.Y=Y;
		this.myTable = myTable;
		this.GProgram = GProgram;
		this.myColor = myColor;
		
		
		double upperLeftX = X-ppPaddleW/2;
		double upperLeftY = Y+ppPaddleH/2;
		GPoint p = myTable.W2S(new GPoint(upperLeftX, upperLeftY));
		
		
		double ScrX = p.getX();
		double ScrY = p.getY();
		this.myPaddle = new GRect(ScrX, ScrY,ppPaddleW*Xs, ppPaddleH*Ys);
		myPaddle.setColor(myColor);
		myPaddle.setFilled(true);
		GProgram.add(myPaddle);
				
	
	}
	
	public void run() {						//loop to calculate speed
		double lastX = X;
		double lastY = Y;
		while(true) {
			Vx=(X-lastX)/TICK;
			Vy=(Y-lastY)/TICK;
			lastX=X;
			lastY=Y;
			GProgram.pause(TICK*TSCALE);
		}
	}
	public GPoint getV() {					//Method to obtain values of speed at instance
		return new GPoint(Vx,Vy);
		
	}
	
	public void setP(GPoint P) {			//Method to set paddle to given point
		//update instance variables
		this.X = P.getX();
		this.Y = P.getY();
		
		
		//Move the GRect instance
		double upperLeftX = X-ppPaddleW/2;
		double upperLeftY = Y+ppPaddleH/2;
		GPoint p = myTable.W2S(new GPoint(upperLeftX, upperLeftY));
		double ScrX = p.getX();
		double ScrY = p.getY();
		this.myPaddle.setLocation(ScrX,ScrY);
		
	}
	public GPoint getP() {					//Method to obtain values of point
		return new GPoint(X,Y);
		
	}
	public double getSgnVy() {				//Method to obtain sign of 
		
		//return 1 if Vy > 0
		if(Vy >= 0) return 1;
		
		//return -1 if Vy < 0
		else return -1;
	}
	public boolean contact (double Sx, double Sy) {			//Method to determine if ball is in contact with paddle
		// called whenever X+Xo >= myPaddle.getP().getX()
		//true if ballY is in the paddle range
		//false if not
		return ((Sy >= Y - ppPaddleH/2) && (Sy <= Y + ppPaddleH/2));
	}

}

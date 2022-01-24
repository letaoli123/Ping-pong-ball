package ppPackage;

import java.awt.Color;

import javax.swing.JLabel;

import acm.graphics.GOval;
import acm.graphics.GPoint;
import acm.program.GraphicsProgram;




import static ppPackage.ppSimParams.*;

/**
 * embeds the simulation loop you created in Assignment 1 and extends the Thread class
 * 
 * @author le-taoli
 *
 */

/*
 * TODO
 * 1. Change the wall collision to be paddle collision
 * 2. Limit the ball horizontal velocity with VoxMAX
 * 3. Make tracing conditional to traceButton
 * 4. Check out of bounds for Y (> Ymax)
 * 5. BONUS: track the scores
 */

public class ppBall extends Thread {

	//Instance variables

	private double Xinit;				// Initial position of ball -X
	private double Yinit;				// INitial position of ball - Y
	private double Vo;					// Initial velocity (Magnitude)
	private double theta;				//Initial direction
	private double loss;				//Energy loss on collision
	private Color color;				// Color of ball
	ppTable myTable;
	ppPaddle myPaddle;
	ppPaddle RPaddle;
	ppPaddle LPaddle;
	double X, Xo, Y, Yo;
	double Vx, Vy;
	boolean running;



	GraphicsProgram GProgram;	// Instance of ppSim class (this)
	GOval myBall;						// Graphics object representing ball


	/*
	 * The constructor for the ppBall class copies parameters to instance variables, creates an
	 * instance of a GOval to represent the ping-pong ball, and adds it to the display. *
	 * @param Xinit - starting position of the ball X (meters)
	 * @param Yinit - starting position of the ball Y (meters)
	 * @param Vo - initial velocity (meters/second)
	 * @param theta - initial angle to the horizontal (degrees)
	 * @param loss - loss on collision ([0,1])
	 * @param color - ball color (Color)
	 * @param GProgram - a reference to the ppSim class used to manage the display 
	 */


	public ppBall(double Xinit, double Yinit, double Vo, double theta, double loss, Color color, ppTable myTable, GraphicsProgram GProgram) {
		//new GOval
		//GProgram.add(ball)

		this.Xinit = Xinit;								// Copy constructor parameters to instance variables
		this.Yinit = Yinit;
		this.Vo = Vo;
		this.theta = theta;
		this.loss = loss;
		this.color = color;
		this.myTable = myTable;
		this.GProgram = GProgram;

	}
	/**
	 * In a thread, the run method is NOT started automatically (like in Assignment 1). * Instead, a start message must be sent to each instance of the ppBall class, e.g.,
	 * ppBall myBall = new ppBall (--parameters--);
	 * myBall.start();
	 * The body of the run method is essentially the simulator code you wrote for A1. 
	 * */
	public void run() {

		//Initialize simulation parameters
		GPoint p = myTable.W2S(new GPoint(Xinit,Yinit));
		double ScrX = p.getX();			//Convert simulation to screen coordinates
		double ScrY = p.getY();
		GOval myBall = new GOval(ScrX,ScrY,2*bSize*Xs,2*bSize*Ys);
		myBall.setColor(color);
		myBall.setFilled(true);
		GProgram.add(myBall);


		//get inputs from user


		//Initialize program variables
		Xo = Xinit;				//Set initial X position
		Yo = Yinit;				//Set initial Y position

		//Initial program variables

		double time = 0;				//Time starts at 0 and counts up
		double Vt = bMass*g / (4*Pi*bSize*bSize*k);	//Terminal velocity
		double Vox = Vo*Math.cos(theta*Pi/180);		//X component of velocity
		double Voy = Vo*Math.sin(theta*Pi/180);		//Y Component of velocity


		double KEx = 0.5*bMass*Vox*Vox;
		double KEy = 0.5*bMass*Voy*Voy*(1-loss);
		double PE = bMass*Yo*g;
		/*
		 * Simulation loop. Calculate position and velocity, print, increment
		 * time. Do this until ball hits the ground.
		 */
		running = true; //Initial state == falling

		GProgram.pause(STARTDELAY);
		//Main simulation loop

		while (running) {
			X = Vox*Vt/g*(1-Math.exp(-g*time/Vt)); //Update relative position
			Y = Vt/g*(Voy+Vt)*(1-Math.exp(-g*time/Vt))-Vt*time; 
			Vx = Vox*Math.exp(-g*time/Vt); //Update velocity
			Vy = (Voy+Vt)*Math.exp(-g*time/Vt)-Vt;


			if(Yo+Y >= ppTableHgt) {									// Check for if the ball hits the ceiling (Yo+Y = YMax)
				if(Vx > 0) {
					running = false;
					PlayerPoints += 1;
					PlayerScore.setText(PlayerPoints.toString());
					
				}
				if(Vx < 0) {
					running = false;
					AgentPoints += 1;
					AgentScore.setText(AgentPoints.toString());
				}

			}

			if ( Vy< 0 && Yo+Y <= bSize) {							//Collision with ground
				KEx = 0.5*bMass*Vx*Vx*(1-loss);
				KEy = 0.5*bMass*Vy*Vy*(1-loss);
				PE = 0;


				Vox = Math.sqrt(2*KEx/bMass);
				Voy = Math.sqrt(2*KEy/bMass);

				if (Vx < 0) Vox = -Vox;

				time = 0;
				Xo += X;
				Yo = bSize;
				X = 0;
				Y = 0;
			}

			else if (Vx > 0 && Xo+X > RPaddle.getP().getX()-bSize-ppPaddleW/2) {			//Collision with RPaddle
				// possible collision

				if (RPaddle.contact(X+Xo, Y+Yo)) {
					KEx = 0.5*bMass*Vx*Vx*(1-loss);
					KEy = 0.5*bMass*Vy*Vy*(1-loss);
					PE = bMass*g*Y;


					Vox = -Math.sqrt(2*KEx/bMass);
					Voy = Math.sqrt(2*KEy/bMass);

					Vox = Vox*ppPaddleXgain;						// Scale X component of velocity
					// Check for Vox > VoxMAX
					if(Vox < -VoxMax) {
						Vox = -VoxMax;
					}
					Voy = Voy*ppPaddleYgain*RPaddle.getSgnVy();		// Scale Y + same direction as paddle


					time = 0;										// Time is reset at every collision
					Xo = RPaddle.getP().getX()-bSize-ppPaddleW/2;	// X position at paddle
					Yo += Y;	// Accumulate distance
					X = 0;		// (X,Y) is the instantaneous position along an arc
					Y = 0;		// Absolute position is (Xo+X, Yo+Y)
				}else {
					running = false;								//Increment score of Agent
					AgentPoints += 1;
					AgentScore.setText(AgentPoints.toString());
				}

			}


			else if (Vx < 0 && Xo+X<=LPaddle.getP().getX() ) {		//Collision with LPaddle
				//Possible collision
				if (LPaddle.contact(X+Xo, Y+Yo)) {
					KEx = 0.5*bMass*Vx*Vx*(1-loss);
					KEy = 0.5*bMass*Vy*Vy*(1-loss);
					PE = bMass*g*Y;


					Vox = Math.sqrt(2*KEx/bMass);
					Voy = Math.sqrt(2*KEy/bMass);

					Vox = Vox*LPaddleXgain;
					if(Vox > VoxMax) {
						Vox = VoxMax;
					}
					Voy = Voy*LPaddleYgain*LPaddle.getSgnVy();


					time = 0;
					Xo = LPaddle.getP().getX()+bSize+LPaddleW/2;
					Yo += Y;
					X = 0;
					Y = 0;	
				}
				else {
					running = false;											//Increment score of Player
					PlayerPoints += 1;
					PlayerScore.setText(PlayerPoints.toString());
				}

			}


			if ((KEx+KEy+PE)<ETHR) {					//Check total mechanical energy of the ball, if it reaches a minimum threshold, computer exits the loop
				running = false;
				Y = 0;
				Yo = bSize;
				System.out.println("Total energy has reached zero");
			}



			//Update the position of the ball. Plot a tick mark at current location.

			p = myTable.W2S(new GPoint(Xo+X-bSize,Yo+Y+bSize)); //Get current position in screen coordinates

			ScrX = p.getX();
			ScrY = p.getY();

			myBall.setLocation(ScrX,ScrY);

			// if traceButton.isSelected()
			if (traceButton.isSelected()) {
				trace(ScrX,ScrY);
			} 

			time += TICK;
			GProgram.pause(TICK*tick.getValue());		//Pause program for TICK*SCALE mS



		}

	}


	/**
	 * //Define method to plot a dot at the current location in screen coordinates
	 * @param ScrX
	 * @param ScrY
	 */
	private void trace(double ScrX, double ScrY) {		
		GOval tracePoint = new GOval (ScrX+bSize*Xs,ScrY+bSize*Ys, PD, PD); 
		tracePoint.setColor(color); 
		tracePoint.setFilled(true); 
		GProgram.add(tracePoint);
	}

	/**
	 * 	//Setter method to assign a value to RPaddle
	 * @param myPaddle
	 */
	public void setRightPaddle(ppPaddle myPaddle) {	
		this.RPaddle = myPaddle;
	}

	public void setLeftPaddle(ppPaddle myPaddle) {
		this.LPaddle = myPaddle;
	}

	public GPoint getV() {
		return new GPoint(Vx,Vy);
	}
	public GPoint getP() {
		return new GPoint(Xo+X, Yo+Y);
	}
	void kill() {
		running = false;
	}
	public void updateScore(Integer points, JLabel Score) {
		points += 1;
		Score.setText(points.toString());
	}

} 

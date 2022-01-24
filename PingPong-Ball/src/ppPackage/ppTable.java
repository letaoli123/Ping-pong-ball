package ppPackage;

import acm.graphics.GPoint;
import acm.graphics.GRect;
import acm.program.GraphicsProgram;

import static ppPackage.ppSimParams.*;

import java.awt.Color;
/**
 * This class creates the table and space in which the ping pong balls will bounce.
 *Creates the boundaries and borders for bouncing ball simulation
 *
 *Exports methods that convert world to screen coordinates and converts screen to world coordinates
 */

public class ppTable {
	
	GraphicsProgram GProgram;
	
	/**
	 * Constructor to create an instance of a ppTable
	 * @param GProgram- a reference to the ppSim class used to manage the display
	 */
	public ppTable(GraphicsProgram GProgram) {
		this.GProgram = GProgram;				//reference to the ppSim class used to manage the display
		drawGroundLine();
	}
	public void run() {
		
	}
	
	public void drawGroundLine() {				//Method to draw ground line
		GRect gPlane = new GRect(0,HEIGHT,WIDTH+OFFSET,3); //A thick line HEIGHT pixels down from the top
		gPlane.setColor(Color.black);
		gPlane.setFilled(true);
		GProgram.add(gPlane);
		
	}
	
	public GPoint W2S (GPoint P) {			//Convert world p to screen p		
		return new GPoint((P.getX()-Xmin)*Xs,ymax - (P.getY()-Ymin)*Ys);
	}
	
	public GPoint S2W (GPoint p) {			//Concert screen p to World P
		
		return new GPoint(p.getX()/Xs+Xmin,(ymax-p.getY())/Ys+Ymin);
	}
	/**
	 * Erase all objects on the display (except buttons) and draws a new ground plane.
	 */
	public void newScreen() {
		GProgram.removeAll();				//Removes all GProgram objects on display
		drawGroundLine();					//Draws ground line
		
	}

	

}

package ppPackage;


import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.JToggleButton;

import acm.graphics.GPoint;
import acm.program.GraphicsProgram;
import acm.util.RandomGenerator;

import static ppPackage.ppSimParams.*;

/**
 * Main entry point of the program. Implements method that generate the table and walls,
 * prompts user for input, creates instance for wall and starts simulation.
 * 
 * This code contains elements from the ECSE 202 A3 handout provided by professor ferrie,
 * as well as code from assignment 1 and code from tutorials given by Katrina Poulin
 * 
 * @author le-taoli
 */
public class ppSim extends GraphicsProgram {

	ppTable myTable;
	ppBall myBall;
	ppPaddle RPaddle;
	ppPaddleAgent LPaddle;

	RandomGenerator rgen;



	public static void main(String[] args) {
		new ppSim().start(args);
	}


	public void init() {
		this.resize(ppSimParams.WIDTH+OFFSET, ppSimParams.HEIGHT+OFFSET);

		//Creating buttons
		JButton startButton = new JButton("Start");
		JButton newServeButton = new JButton("New Serve");
		JButton quitButton = new JButton("Quit");
		traceButton = new JToggleButton("Trace");
		JButton clearButton = new JButton("Clear");
		JButton rlagButton = new JButton ("rlag");
		JButton rtimeButton = new JButton("rtime");
		//Creating sliders
		
		

		// add buttons
		add(startButton, SOUTH);
		add(clearButton, SOUTH);
		add(newServeButton, SOUTH);
		add(quitButton, SOUTH);
		add(traceButton, SOUTH);
		
		// Add lag Sliders with labels
		
		add(new JLabel("-lag"), SOUTH);
		lag = new JSlider(0, 15, 5);
		add(lag, SOUTH);
		add(new JLabel("+lag"),SOUTH);
		add(rlagButton, SOUTH);
		
		// Add tick sliders
		add(new JLabel("+t"), SOUTH);
		tick = new JSlider(0, 10000, 2000);
		add(tick, SOUTH);
		add(new JLabel("-t"),SOUTH);
		add(rtimeButton, SOUTH);
		
		//Add Scoreboard on top of screen
		AgentScore = new JLabel(AgentPoints.toString());
		PlayerScore = new JLabel(PlayerPoints.toString());
		add(AgentScore, NORTH);
		add(new JLabel("Agent"), NORTH);
		add(new JLabel("Player"), NORTH);
		add(PlayerScore, NORTH);
		
		addMouseListeners();
		addActionListeners();

		rgen = RandomGenerator.getInstance();
		rgen.setSeed(RSEED);

		myTable = new ppTable(this);
		myBall = newBall();

		newGame();

	}

	ppBall newBall() {
		// generate parameters for simulation
		Color iColor = Color.RED;
		double iYinit = rgen.nextDouble(YinitMIN, YinitMAX);
		double iLoss = rgen.nextDouble(EMIN,EMAX);
		double iVel = rgen.nextDouble(VoMIN,VoMAX);
		double iTheta = rgen.nextDouble(ThetaMIN,ThetaMAX);

		// Create  instance of ball
		return new ppBall(Xinit, iYinit, iVel, iTheta, iLoss , iColor, myTable, this);
	}
	public void newGame() {
		if (myBall != null) myBall.kill(); // Stop current game in play
		myTable.newScreen();
		myBall = newBall();
		RPaddle = new ppPaddle(ppPaddleXinit, ppPaddleYinit, Color.GREEN, myTable, this); 
		LPaddle = new ppPaddleAgent(LPaddleXinit, LPaddleYinit, Color.BLUE, myTable, this); 
		LPaddle.attachBall(myBall); 
		myBall.setRightPaddle(RPaddle); 
		myBall.setLeftPaddle(LPaddle); 

	}
	public void startGame() {
		pause(STARTDELAY);
		myBall.start();
		LPaddle.start();
		RPaddle.start();
	}
	


	/*
	 * Mouse Handler - a moved event moves the paddle up and down in Y
	 */

	public void mouseMoved(MouseEvent e) {
		if (myTable==null || RPaddle==null) return;
		GPoint Pm = myTable.S2W(new GPoint(e.getX(),e.getY()));
		double PaddleX = RPaddle.getP().getX();
		double PaddleY = Pm.getY();
		this.RPaddle.setP(new GPoint(PaddleX,PaddleY));
	}
	public void actionPerformed(ActionEvent e) {					//Functions of each button
		String command = e.getActionCommand();
		if(command.equals("New Serve")) {							//Creates a new screen and serve
			newGame();
			startGame();
		}
		else if (command.equals("Quit")) {							//Exits system
			System.exit(0);
		}
		else if (command.equals("Clear")) {							//Clears GraphicsProgram display
			myTable.newScreen();
		}
		else if (command.equals("Start")) {							//Starts game after first boot up
			startGame();
		}
		else if (command.equals("rlag")) {							//resets lag value
			lag.setValue(5);
		}
		else if (command.equals("rtime")) {							//Resets tscale value
			tick.setValue(2000);
		}
	}
}

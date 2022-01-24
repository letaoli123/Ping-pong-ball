package ppPackage;

import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.JToggleButton;

public class ppSimParams {
	
	//Program Parameters
		//All constants used in the program should be defined as parameters.
		//This eliminates error and makes the program more maintainable.
		
		// toggle button
		public static JToggleButton traceButton;
		
		// Score Parameters
		public static Integer AgentPoints = 0;
		public static Integer PlayerPoints = 0;
		public static JLabel AgentScore;
		public static JLabel PlayerScore;
		
		//JSliders
		public static JSlider lag;
		public static JSlider tick;
		
		//Screen Dimensions
		
		
		public static final int WIDTH = 1280; 				//n.b. screen coordinates
		public static final int HEIGHT = 600;
		public static final int OFFSET = 200;
		
		//World and Screen Parameters
		public static final double ppTableXlen = 2.74;	//Length
		public static final double ppTableHgt = 1.52;	//Ceiling
		public static final double XwallL = 0.05;		//Position of left wall
		public static final double XwallR = 2.69;		//Position of right wall
				
		
		public static final double Xmin = 0.0;			//Minimum value of X
		public static final double Xmax = ppTableXlen;		//Maximum value of X
		public static final double Ymin = 0.0;			//Minimum value of Y
		public static final double	Ymax = ppTableHgt;		//Maximum value of Y
		public static final int xmin = 0;				//Minimum value of x
		public static final int xmax = WIDTH;			//Maximum value of x
		public static final int ymin = 0;				//Minimum value of y
		public static final int ymax = HEIGHT;			//Maximum value of y
		public static final double Xs = (xmax-xmin)/(Xmax-Xmin);  	//S.F. X
		public static final double	Ys = (ymax-ymin)/(Ymax-Ymin);	//S.F. Y
		public static final double	PD = 1;				//Trace point diameter
		public static final double TSCALE = 2000;		//Scaling parameter for pause()

		
		//Simulation parameters

		public static final double g = 9.8;				//gravitational constant
		public static final double k = 0.1316;			//air friction
		public static final double Pi = 3.1416;			//Pi to 4 places
		public static final double bSize = 0.02;		//Radius of ball(m)
		public static final double bMass = 0.0027;		//Mass of ball (kg)
		public static final double Xinit = XwallL;			//Initial X position of ball
		public static final double Yinit = Ymax/2;	//Initial Y position of ball
		
		public static final double Vdef = 3.0;			//Default velocity (m/s)
		public static final double Tdef = 30.0;			//Default angle (degrees)	
		public static final int SLEEP = 10;			//Delay time in milliseconds
		public static final double TICK = SLEEP/1000.0;	//Clock increment at each iteration.
		public static final double ETHR = 0.001;			//Minimum ball energy
		
		//Paddle Parameters
		
		static final double ppPaddleH = 8*2.54/100;			//Paddle height
		static final double ppPaddleW = 0.5*2.54/100;		//Paddle width
		static final double ppPaddleXinit = XwallR-ppPaddleW/2; //Initial Paddle X
		static final double ppPaddleYinit = Yinit;				//Initial Paddle Y
		static final double ppPaddleXgain = 1.5;			//Vx gain on paddle hit
		static final double ppPaddleYgain = 1.5;			//Vy gain on paddle hit
		static final double VoxMax = 10;					//Maximum horizontal velocity of the ball
		
		
		//PaddleAgent Parameters
		static final double LPaddleH = 8*2.54/100;			//LPaddle height
		static final double LPaddleW = 0.5*2.54/100;		//LPaddle width
		static final double LPaddleXinit = XwallL - LPaddleW/2; //Initial LPaddle X
		static final double LPaddleYinit = Yinit;			//Initial LPaddle Y
		static final double LPaddleXgain = 1.5;				//Vx gain on paddle hit
		static final double LPaddleYgain = 1.5;				//Vy gain on paddle hit
		
		//Parameters used by ppSim class
		
		static final double YinitMAX = 0.75*Ymax; 			//Max initial height at 75% range
		static final double YinitMIN = 0.25*Ymax; 			//Min initial height at 25% of range
		static final double EMIN = 0.2;						//Minimum loss coefficient
		static final double EMAX = 0.2;						//Maximum loss coefficient
		static final double VoMIN = 5.0; 					//Minimum velocity
		static final double VoMAX = 5.0; 					//Maximum velocity
		static final double ThetaMIN = 0.0; 				//Minimum launch angle
		static final double ThetaMAX = 20.0; 				//Maximum launch angle
		static final long RSEED = 8976232;					//Random number gen. seed value

		
		//Miscellaneous parameters
		public static final boolean DEBUG = false;		//Debug msg. and single step if true
		public static final boolean TEST = false;		//Enable status messages on console
		public static final int STARTDELAY = 1000;		//Delay between setup and start


}

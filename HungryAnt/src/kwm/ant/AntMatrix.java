/*-------------------------------------------------------------------------------------
 *title: Homework 4 // SS 2020 
 *author: Lisa Miesenböck, 1910456020
 *created: 11.04.2020
 *description: this program draws a program called hungry ant. an animated "ant = triangle"
 *walks through a matrix and color the different matrix-field in gray or white. 
 *if the ant comes to a field which is colored white, the ant turn 90°C right, and color
 *the field gray.if the field is gray the ant turns 90°C left and color the field white
 *
 *field white --> false
 *field gray --> true
 *-----------------------------------------------------------------------------------*/

package kwm.ant;
import javax.swing.*;
import java.awt.event.*;
import java.util.Random;
import java.awt.*; 
import kwm.*; 


public class AntMatrix implements ActionListener{
	public boolean[][] matrix; 
	public int offsetX = 50; 
	public int offsetY = 50; 
	public int size = 35;
	public int height; 
	public int width; 


	//Direction for the Ant
	// up = 0; right = 1; down = 2; left = 3
	public int dir;
	public final int up = 0; 
	public final int right = 1; 
	public final int down = 2; 
	public final int left = 3;
	
	//current position of the ant
	public int i; 
	public int j; 


	public Timer timer; 
	public static int delay =1000;
	
	//Constructor
	public AntMatrix( int height, int width) {
		this.matrix = new boolean [height] [width];
		this.height = height; 
		this.width = width; 
		randomPlace(); 
		init(); 
	}

	/* At the beginning the ant get a random place and 
	 * a random direction inside the matrix */
	public void randomPlace() {
		Random r = new Random();
		int i = r.nextInt(this.width);
		int j = r.nextInt(this.height); 
		this.i = i; 
		this.j = j; 
		this.dir  = (int)Math.random()*4; 
	}

	/* at the beginning every field of the matrix get the color 
	 * white so every field have to be set false  */
	public void init() {
		for(int i = 0; i<this.height; i++) {
			for(int j = 0; j<this.width; j++) {
				this.matrix[i][j] = false; 
			}
		}
	}

	/* every new second the ant have to be updated, 
	 * if the ant have to turn right oder left */
	public void nextSecond() {
		//White fields --> gray(true); and turn right
		if(this.matrix[this.j][this.i]==false) {
			this.turnRight();
		}

		//Grey fields --> white(false); turn left
		else{
			this.turnLeft(); 
		}
		this.newCoordinates(); 
	}
	

	/*set the direction to a new number if the 
	 * ant have to turn left */
	public void turnLeft() {
		this.dir = (this.dir == 0) ? 3 : (this.dir -1);
		this.matrix[this.j][this.i]=false; 
	}

	
	/*set the direction to a new number if the 
	 * ant have to turn right */
	public void turnRight() {
		this.dir = (++this.dir)%4;  
		this.matrix[this.j][this.i]=true; 
	}
	

	/*sets new coordinate to turn the ant
	 *the right direction */
	public void newCoordinates() {
	
		if(this.dir == up) {
			this.j --; 
		}
		else if(this.dir == right) {
			this.i++;
		}
		else if(this.dir == down) {
			this.j++; 
		}
		else if(this.dir == left) {
			this.i--; 
		}
		overflow();
	}

	
	/*avoid that the ant steps out of the matrix
	 *if the ant step over on the right side it comes back on the left side
	 *if it step over on the left side it comes back on the right side
	 *if it step over on the top side it comes back on the bottom
	 *if it step over on the bottom side it comes back on the top */
	public void overflow() {
		if(this.i>this.width-1) {
			i = 0; 
		}
		else if (this.i<0) {
			i = width-1; 
		}
		if(this.j>this.height-1) {
			 j = 0; 
		}
		else if(this.j<0) {
			j = height-1; 
		}
	}

	
	/* draws the ant and the matrix
	 * and access to the method drawColorMatrix() to 
	 * color the fields in the right color */
	public void draw() {
		for(int i = 0; i<this.height; i++) {
			for(int j = 0; j<this.width; j++) {
				drawColorMatrix(i, j);
			}
		}
		drawAntDirections();
		Drawing.paint(); 
	}
	
	
	/*draws the matrix in the right color
	 * if the field is false - white; 
	 * if the field is true - gray */
	public void drawColorMatrix(int i, int j) {
		if(this.matrix[i][j]==true) {
			Drawing.graphics.setColor(Color.gray);
			Drawing.graphics.fillRect( this.offsetY+j*this.size,this.offsetX+i*this.size, this.size, this.size); 
			Drawing.graphics.setColor(Color.black);
			Drawing.graphics.drawRect(this.offsetY+j*this.size,this.offsetX+i*this.size, this.size, this.size);
		}
		else {
			Drawing.graphics.setColor(Color.white);
			Drawing.graphics.fillRect( this.offsetY+j*this.size,this.offsetX+i*this.size, this.size, this.size); 
			Drawing.graphics.setColor(Color.black);
			Drawing.graphics.drawRect(this.offsetY+j*this.size,this.offsetX+i*this.size, this.size, this.size);
		}
	}
	

	/* draws the "ant" (triangle) in the right direction 
	 * up - right - down - left */
	public void drawAntDirections() {
		int x = this.offsetX+this.i*size; 
		int y = this.offsetY+this.j*size;
		Drawing.graphics.setColor(Color.red);
		if (this.dir == 0) {
			antUp(x, y);
		}
		else if (this.dir == 1) {
			antRight(x, y);
		}
		else if (this.dir == 2) {
			antDown(x, y);
		}
		else {
			antLeft(x, y);
		}
	}

	//draws the ant which looks right
	public void antRight(int x, int y) {
		int[] xKo=  {x,x,x+size};
		int[] yKo=  {y,y+size,y+size/2};
		Drawing.graphics.fillPolygon(xKo, yKo, 3);
	}

	//draws the ant which looks left
	public void antLeft(int x, int y) {
		int[] xKo=  {x+size,x+size,x};
		int[] yKo=  {y,y+size,y+size/2};
		Drawing.graphics.fillPolygon(xKo, yKo, 3);
	}

	//draws the ant which looks up
	public void antUp(int x, int y) {
		int[] xKo=  {x+size/2,x,x+size};
		int[] yKo=  {y,y+size,y+size};
		Drawing.graphics.fillPolygon(xKo, yKo, 3);
	}
	
	//draws the ant which looks down
	public void antDown(int x, int y) {
		int[] xKo=  {x,x+size,x+size/2};
		int[] yKo=  {y,y,y+size};
		Drawing.graphics.fillPolygon(xKo, yKo, 3);;
	}


	//animation
	public void actionPerformed(ActionEvent e){
		this.draw(); 
		this.nextSecond();
	}

	//let the animation started
	public void startAnimation() {
		this.timer = new Timer(AntMatrix.delay, this); 
		this.timer.start();
		try {
			synchronized(this.timer) {this.timer.wait();}
		}
		catch(InterruptedException e) {}
		Drawing.paint(); 
	}
}




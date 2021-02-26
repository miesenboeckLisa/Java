/*******************************************************************************************
 *title: Homework 5 // SS 2020 
 *author: Lisa Miesenböck, 1910456020
 *created: 20.04.2020
 *description: this program creates a game called "Game of life, from John Conway's
 *every cell is alive or dead under certain circumstances: 
 *rule1: born a new cell, if the cell has three neighbors
 *rule2: if a cell has two or three neighbors and the cell is alive - the cell stays alive
 *rule3: all other cells die
 ******************************************************************************************/
package kwm.graphics;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

import kwm.Drawing;

public class CellMatrix implements ActionListener {

	public boolean[][] gameMatrix;
	public int height;
	public int width;
	public double p;
	public int size = 10 ;

	public Timer timer;
	public static int delay = 200;

	public CellMatrix(double p, int height, int width) {
		this.gameMatrix = new boolean[height][width];
		this.height = height;
		this.width = width;
		this.p = p;
		init();
		draw(); 
	}


	/*******************************************************
	 * generate first random generation of the Matrix 
	 * if cells stay alive(true) or dead (false) 
	 * ****************************************************/
	public void init() {
		for (int i = 0; i < this.height; i++) {
			for (int j = 0; j < this.width; j++) {
				randomValue(i, j);
			}
		}
	}

	/********************************************************
	 * every cell [i][j] get a random value 
	 * (true=alive, false=dead) 
	 * ****************************************************/
	public void randomValue(int i, int j) {
		double boundery = this.p/100;
		double randNumber = Math.random(); // create a randomNumber for each Matrix-Field
		if (randNumber < boundery) 
			gameMatrix[i][j] = true;
		else 
			gameMatrix[i][j] = false;
	}

	/********************************************************
	 * generate a new matrix every time the timer kick off
	 * moreover the old matrix is overwrite by the new one
	 * ****************************************************/
	public void generateCellGeneration() {
		boolean[][]future = new boolean[this.height][this.width]; 
		for(int i = 0; i<this.height; i++) {
			for(int j = 0; j<this.width; j++) {
				int neigh = neighbors(i, j); //how many neighbors has the cell

				//rule1: born a new cell, if the cell has three neighbors
				if(neigh==3 && gameMatrix[i][j]==false) {
					future[i][j]=true; 
				}

				//rule2: if a cell has two or three neighbors and the cell is alive - the cell stays alive
				else if(neigh==2 && gameMatrix[i][j]==true || neigh == 3 && gameMatrix[i][j]==true ) {
					future[i][j]=true;
				}

				//rule3 all other cells die
				else {
					future[i][j]=false; 
				}
			}
		}
		overWrite(this.gameMatrix, future);
	}

	/********************************************************
	 * the old matrix becomes to the new matrix 
	 * overwriting all cells from the old one with the
	 * value (true or false) from the new one. 
	 * ****************************************************/
	public void overWrite(boolean[][]gameMatrix, boolean[][]future) {
		for(int i = 0; i<this.height; i++) {
			for(int j = 0; j<this.width; j++) {
				gameMatrix[i][j] = future[i][j]; 
			}
		}
	}



	/********************************************************
	 * looks how many neighbors a cell has (max 8) 
	 * and return the amount in an integer called alive 
	 * ****************************************************/
	public int neighbors (int col, int row) {
		int alive  = 0;
		int[] xPos = {-1,0,1,1,1,0,-1,-1}; 
		int[] yPos = {-1,-1,-1,0,1,1,1,0}; 
		for(int i = 0; i<8; i++) {
			try{
				if(gameMatrix[col+xPos[i]][row+yPos[i]]==true)
					alive++; 
			}
			/*catch - ErrorHandling, when a cell has less than 8 neighbors
			 *like a cell is on the border of the matrix*/
			catch(Exception e) {
			}
		}
		return alive; 
	}

	/********************************************************
	 *draw every cell of the matrix
	 * ****************************************************/
	public void draw() {
		for(int i = 0; i<height; i++) {
			for(int j = 0; j<width; j++) {
				colorMatrix(i, j);
				Drawing.graphics.setColor(Color.black);
				Drawing.graphics.drawRect(j*this.size, i*this.size, this.size, this.size);
			}
		}
		Drawing.paint(); 
	}

	/********************************************************
	 * color the cells in the right color, if the cell is
	 * alive(color) if it is dead (white) 
	 * ****************************************************/
	public void colorMatrix(int i, int j){
		if(this.gameMatrix[i][j]==true) {
			Drawing.graphics.setColor(Color.green);
			Drawing.graphics.fillRect( j*this.size,i*this.size, this.size, this.size); 
		}
		else if(this.gameMatrix[i][j]==false) {
			Drawing.graphics.setColor(Color.black);
			Drawing.graphics.fillRect( j*this.size,i*this.size, this.size, this.size); 
		}
	}

	/********************************************************
	 * execute the animation
	 *****************************************************/
	public void actionPerformed(ActionEvent e) {
		this.generateCellGeneration();
		this.draw();
	}

	/********************************************************
	 * start the animation 
	 * ****************************************************/
	public void startAnimation() {
		this.timer = new Timer(CellMatrix.delay, this);
		this.timer.start();
		try {
			synchronized (this.timer) {
				this.timer.wait();
			}
		} catch (InterruptedException e) {
		}
	}
}

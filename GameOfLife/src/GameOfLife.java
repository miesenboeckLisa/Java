/*******************************************************************************************
 *title: Homework 5 // SS 2020 
 *author: Lisa Miesenböck, 1910456020
 *created: 20.04.2020
 *description: execute the Programm CellMatrx,
 *which creates a game called "Game of life, from John Conway's
 *every cell is alive(true) or dead(false) under certain circumstances.  
 ******************************************************************************************/
import kwm.Drawing;
import kwm.graphics.CellMatrix;
public class GameOfLife {
	public static void main (String[]args) {
		
		Drawing.begin("Game of Life", 800, 2000);
		CellMatrix c1 = new CellMatrix(65, 70, 70); 
		c1.startAnimation();
		Drawing.end(); 	
		
	}

}

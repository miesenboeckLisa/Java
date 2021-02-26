/*-------------------------------------------------------------------------------------
 *title: Homework 4 // SS 2020 
 *author: Lisa Miesenböck, 1910456020
 *created: 11.04.2020
 *description: this program execute the object ant from class AntMatrix. 
 *-----------------------------------------------------------------------------------*/

import kwm.Drawing;
import kwm.ant.AntMatrix;

public class AntMatrixTest {

	public static void main (String[]args) {

		Drawing.begin("Ant", 900,900);
		AntMatrix ant1 = new AntMatrix(15,15);
		ant1.startAnimation(); //start the animation
		Drawing.end(); 
	}

}
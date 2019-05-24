package Module_0_Checkpoint;

import java.util.Random;

public class Checkpoint {
public static void main(String[] args) {
	int[][] arr = new int[5][5];
	
	for (int row = 0; row < arr.length; row++) {
		for (int col = 0; col < arr.length; col++) {
			int rand = (int) (Math.random() * 25);
			arr[row][col] = rand;
		}
	}
	for (int rows = 0; rows < arr.length; rows++) {
		for (int cols = 0; cols < arr.length; cols++) {
			System.out.print(arr[rows][cols] + " ");
		}
		System.out.println();
	}
}
}

package _04_Maze_Maker;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

public class MazeMaker {

	private static int width;
	private static int height;

	private static Maze maze;

	private static Random randGen = new Random();
	private static Stack<Cell> uncheckedCells = new Stack<Cell>();

	public static Maze generateMaze(int w, int h) {
		width = w;
		height = h;
		maze = new Maze(width, height);

		// 4. select a random cell to start

		Cell randCell = maze.getCell(randGen.nextInt(h), randGen.nextInt(w));
		// 5. call selectNextPath method with the randomly selected cell
		selectNextPath(randCell);

		return maze;
	}

	// 6. Complete the selectNextPathMethod
	private static void selectNextPath(Cell currentCell) {
		// A. mark cell as visited
		currentCell.setBeenVisited(true);
		// B. check for unvisited neighbors using the cell
		getUnvisitedNeighbors(currentCell);
		// C. if has unvisited neighbors,
		if (!getUnvisitedNeighbors(currentCell).isEmpty()) {

			// C1. select one at random.
			int unvisitedSize = getUnvisitedNeighbors(currentCell).size();
			Cell unvisited = getUnvisitedNeighbors(currentCell).get(randGen.nextInt(unvisitedSize));
			// C2. push it to the stack
			uncheckedCells.push(unvisited);
			// C3. remove the wall between the two cells
			removeWalls(currentCell, unvisited);
			// C4. make the new cell the current cell and mark it as visited
			currentCell = unvisited;
			currentCell.setBeenVisited(true);
			// C5. call the selectNextPath method with the current cell
			selectNextPath(currentCell);
		}
		// D. if all neighbors are visited
		if (getUnvisitedNeighbors(currentCell).isEmpty()) {
			if (!uncheckedCells.isEmpty()) {

				// D1. if the stack is not empty

				// D1a. pop a cell from the stack
				Cell cellStack = uncheckedCells.pop();
				// D1b. make that the current cell
				currentCell = cellStack;
				// D1c. call the selectNextPath method with the current cell
				selectNextPath(currentCell);
			}
		}
	}

	// 7. Complete the remove walls method.
	// This method will check if c1 and c2 are adjacent.
	// If they are, the walls between them are removed.
	private static void removeWalls(Cell c1, Cell c2) {
		
		if (c2.getX() + 1 == c1.getX()) {
			c1.setWestWall(false);
			c2.setEastWall(false);

		}
		if (c2.getX() - 1 == c1.getX()) {
			c1.setEastWall(false);
			c2.setWestWall(false);

		}
		if (c2.getY() + 1 == c1.getY()) {
			c1.setNorthWall(false);
			c2.setSouthWall(false);

		}
		if (c2.getY() - 1 == c1.getY()) {
			c1.setSouthWall(false);
			c2.setNorthWall(false);

		}
	}

	// 8. Complete the getUnvisitedNeighbors method
	// Any unvisited neighbor of the passed in cell gets added
	// to the ArrayList
	private static ArrayList<Cell> getUnvisitedNeighbors(Cell c) {
		int x = c.getX();
		int y = c.getY();
		
		ArrayList<Cell> unvisitedNeighbors = new ArrayList<Cell>();
		
		for (int i = c.getX() -  1; i <= c.getX() + 1; i++) {
			for (int j = c.getY() - 1; j <= c.getY() + 1; j++) {
				
				if ((i == x - 1 && j == y - 1)||
					(i == x - 1 && j == y + 1)||
					(i == x + 1 && j == y - 1)||
					(i == x + 1 && j == y + 1)||
					(i == x && j == y)) {
					
				}
				else if (i >= 0 && i < width && j >= 0 && j < height && !maze.getCell(i, j).hasBeenVisited()) {
					//!maze.getCell(i, j).hasBeenVisited() works above because all of the &&s it has passed. If they didn't work then they would have exited the if statement.
						unvisitedNeighbors.add(maze.getCell(i, j));
					
				}
			}
		}
		return unvisitedNeighbors;
	}
}

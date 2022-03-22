import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Board {
	private final int dimension;
	private int[][] tiles;
	
	public Board (int[][] tiles) {
		dimension = tiles[0].length;
		this.tiles = tiles;
	}
	
	public String toString() {
		String result = "";
		result += dimension; 
		result += "\n";
		for (int i = 0; i < dimension; i++) {
			for(int j = 0; j < dimension; j++) {
				result += tiles[i][j];
			}
			result += "\n";
		}
		return result;
	}
	
	public int dimension() {
		return dimension;
	}
	
	/*
	 the correct position should be [n/dimension][n%dimension-1]
	 */
	public int hamming() {
		int result = 0;
		for (int i = 0; i < dimension; i++) {
			for(int j = 0; j < dimension; j++) {
				if(tiles[i][j] != 0 && !(i == tiles[i][j]/dimension && i == tiles[i][j]%dimension - 1)) {
					result++;
				}
			}
			
		}
		return result;
	}
	
	
	public int manhattan() {
		int result = 0;
		int horizontalDist;
		int verticalDist;
		for (int i = 0; i < dimension; i++) {
			for(int j = 0; j < dimension; j++) {
				if(tiles[i][j] != 0) {
					horizontalDist = tiles[i][j] % dimension - 1 - j;
					verticalDist = tiles[i][j] / dimension - i;
					result += horizontalDist;
					result += verticalDist;
					
				}
			}
		}
		return result;
	}
		
		
	
	public boolean isGoal() {
		return(hamming() == 0);
	}
	
	public boolean equals(Object y) {
		Board other = (Board) y;
		if(dimension != other.dimension()) {
			return false;
		}
		for (int i = 0; i < dimension; i++) {
			for(int j = 0; j < dimension; j++) {
				if(tiles[i][j] != other.tiles[i][j]) {
					return false;
				}
			}
			
		}
		return true;
	}
	
	
	//We can use arraylists here because they are iterable
	public Iterable<Board> neighbors(){
		ArrayList<Board>  neighbors = new ArrayList<Board>();
		int emptyRow = -1;
		int emptyCol = -1;
		
		for (int i = 0; i < dimension && emptyRow != -1; i++) {
			for (int j = 0; j < dimension; j++) {
				if(tiles[i][j] == 0) {
					emptyRow = i;
					emptyCol = j;
					break;
				}
			}
			
		}
		
		
		if (emptyRow != 0 && emptyRow != dimension - 1 && emptyCol != 0 && emptyCol != dimension - 1) {		//not on edge
			neighbors.add(swap(tiles, "right", emptyRow, emptyCol));
			neighbors.add(swap(tiles, "left", emptyRow, emptyCol));
			neighbors.add(swap(tiles, "up", emptyRow, emptyCol));
			neighbors.add(swap(tiles, "down", emptyRow, emptyCol));
			
		} else if(emptyRow == 0 && emptyCol == 0) {		//top left corner
			neighbors.add(swap(tiles, "right", emptyRow, emptyCol));
			neighbors.add(swap(tiles, "down", emptyRow, emptyCol));

		} else if(emptyCol == dimension - 1 && emptyRow == 0) {		//top right corner
			neighbors.add(swap(tiles, "left", emptyRow, emptyCol));
			neighbors.add(swap(tiles, "down", emptyRow, emptyCol));
			
		} else if(emptyCol == 0 && emptyRow == dimension - 1) {		//bottom left corner
			neighbors.add(swap(tiles, "right", emptyRow, emptyCol));
			neighbors.add(swap(tiles, "up", emptyRow, emptyCol));

		} else if(emptyRow == dimension - 1 && emptyCol == dimension - 1) {		//bottom right corner
			neighbors.add(swap(tiles, "left", emptyRow, emptyCol));
			neighbors.add(swap(tiles, "up", emptyRow, emptyCol));

		} else if(emptyCol == 0) {		//left side
			neighbors.add(swap(tiles, "right", emptyRow, emptyCol));
			neighbors.add(swap(tiles, "up", emptyRow, emptyCol));
			neighbors.add(swap(tiles, "down", emptyRow, emptyCol));

		} else if(emptyCol == dimension - 1) {		//right side
			neighbors.add(swap(tiles, "left", emptyRow, emptyCol));
			neighbors.add(swap(tiles, "up", emptyRow, emptyCol));
			neighbors.add(swap(tiles, "down", emptyRow, emptyCol));
			
		} else if(emptyRow == 0) {		//top
			neighbors.add(swap(tiles, "right", emptyRow, emptyCol));
			neighbors.add(swap(tiles, "left", emptyRow, emptyCol));
			neighbors.add(swap(tiles, "down", emptyRow, emptyCol));
			
		} else if(emptyRow == dimension - 1) {		//bottom
			neighbors.add(swap(tiles, "right", emptyRow, emptyCol));
			neighbors.add(swap(tiles, "left", emptyRow, emptyCol));
			neighbors.add(swap(tiles, "up", emptyRow, emptyCol));
		}
		
		return neighbors;
	}
	
	public Board twin() {
		int[][] newTiles = new int[dimension][dimension];
		for (int i = 0; i < dimension; i++) {
			for(int j = 0; j < dimension; j++) {
				newTiles[i][j] = tiles[i][j];
			}
		}
		
		//Pick 2 adjacent, non-zero tiles
		int firstRow = 0;
		int firstCol;
		if(newTiles[0][0] != 0) {
			firstCol = 0;
		} else {
			firstCol = 1;
		}
		int secondRow;
		int secondCol;
		if(newTiles[firstRow][firstCol+1] != 0) {
			secondRow = firstRow;
			secondCol = firstCol + 1;
		} else {
			secondRow = firstRow + 1;
			secondCol = firstCol;
		}
		
		
		//Swap them
		int temp = newTiles[firstRow][firstCol];
		newTiles[firstRow][firstCol] = newTiles[secondRow][secondCol];
		newTiles[secondRow][secondCol] = temp;
	
		
		
		return (new Board(newTiles));
		
	}
	
	private Board swap(int[][] tiles, String direction, int blankRow, int blankCol) {
		
		int temp;
		int[][] newTiles = new int[dimension][dimension];
		for(int i = 0; i < dimension; i++) {
			for(int j = 0; j < dimension; j++) {
				newTiles[i][j] = tiles[i][j];
			}
		}
		if (direction.equals("right")) {
			temp = newTiles[blankRow][blankCol + 1];
			newTiles[blankRow][blankCol] = temp;
			newTiles[blankRow][blankCol + 1] = 0;
		} else if (direction.equals("left")) {
			temp = newTiles[blankRow][blankCol - 1];
			newTiles[blankRow][blankCol] = temp;
			newTiles[blankRow][blankCol - 1] = 0;
		} else if (direction.equals("up")) {
			temp = newTiles[blankRow - 1][blankCol];
			newTiles[blankRow][blankCol] = temp;
			newTiles[blankRow - 1][blankCol] = 0;
		} else if (direction.equals("down")) {
			temp = newTiles[blankRow + 1][blankCol];
			newTiles[blankRow][blankCol] = temp;
			newTiles[blankRow + 1][blankCol] = 0;
		} else {
			throw new IllegalArgumentException("Invalid direction");
		}
		
		return (new Board(newTiles));
	}
	
	
	public static void main(String[] args) {
		int counter = 0;
		int[][] tiles = new int[3][3];
		for (int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				tiles[i][j] = counter++;
			}
		}
		
		Board board = new Board(tiles);
		System.out.println(board);
		System.out.println("The Manhattan distance is " + board.manhattan());
		System.out.println("The hamming distance is " + board.hamming());

	}
}

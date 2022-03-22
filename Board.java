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
		/*
		if (firstRow != 0 && firstRow != dimension - 1 && firstCol != 0 && firstCol != dimension - 1) {		//not on edge
			
		} else if(firstRow == 0 && firstCol == 0) {		//top left corner
			
		} else if(firstRow == dimension - 1 && firstCol == 0) {		//top right corner
			
		} else if(firstRow == 0 && firstCol == dimension - 1) {		//bottom left corner
			
		} else if(firstRow == dimension - 1 && firstCol == dimension - 1) {		//bottom right corner
			
		} else if(firstRow == 0) {		//left side
			
		} else if(firstRow == dimension - 1) {		//right side
			
		} else if(firstCol == 0) {		//top
			
		} else if(firstCol == dimension - 1) {		//bottom
			
		}
		*/
		//TODO add all the neighbors to neighbors list
		return neighbors;
	}
	
	public Board twin() {
		int[][] newTiles = new int[dimension][dimension];
		for (int i = 0; i < dimension; i++) {
			for(int j = 0; j < dimension; j++) {
				newTiles[i][j] = tiles[i][j];
			}
		}
		Random rng = new Random();
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
		//TODO swap first and second elements in the newTiles array
		
	
		
		
		return (new Board(newTiles));
		
	}
	
	public static void main(String[] args) {
		
	}
}

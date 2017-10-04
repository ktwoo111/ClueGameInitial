package experiment;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class IntBoard {
	private BoardCell[][] grid;
	private HashMap<BoardCell, HashSet<BoardCell>> adjMtx;
	private HashSet<BoardCell> visited;
	private HashSet<BoardCell> targets;
	private static final int BOARD_ROW = 4;
	private static final int BOARD_COLUMN = 4;



	public IntBoard() {
		grid = new BoardCell[BOARD_ROW][BOARD_COLUMN];
		for (int i = 0; i < BOARD_ROW; i++){ //row
			for (int j = 0; j < BOARD_COLUMN; j++){ //column
				grid[i][j] = new BoardCell(i,j);
			}
		}

		adjMtx = new HashMap<BoardCell, HashSet<BoardCell>>();
		visited = new HashSet<BoardCell>();
		targets = new HashSet<BoardCell>();
		calcAdjacencies();

	}


	public BoardCell getCell(int row, int col) {
		return grid[row][col];
	}
	public void calcAdjacencies(){
		for (int i = 0; i < BOARD_ROW; i++){ //row
			for (int j = 0; j < BOARD_COLUMN; j++){ //column
				adjMtx.put(grid[i][j], new HashSet<BoardCell>());
				if(i -1 >= 0){			
					adjMtx.get(grid[i][j]).add(grid[i-1][j]);
				}
				if(i+1 < BOARD_ROW){
					adjMtx.get(grid[i][j]).add(grid[i+1][j]);

				}
				if(j -1 >= 0){
					adjMtx.get(grid[i][j]).add(grid[i][j-1]);

				}
				if(j +1 < BOARD_COLUMN){
					adjMtx.get(grid[i][j]).add(grid[i][j+1]);					
				}			
			}

		}

	}


	
	
	public HashSet<BoardCell> getTargets() {
		return targets;
	}



	public HashSet<BoardCell> getAdjList(BoardCell cell) {
		return adjMtx.get(cell);
	}



	public void calcTargets(BoardCell startCell, int pathLength){
		visited = new HashSet<BoardCell>();
		targets = new HashSet<BoardCell>();
		findAllTargets(startCell, pathLength);
		targets.remove(startCell);
	}

	public void findAllTargets(BoardCell startCell, int pathLength){
		visited.add(startCell);

		for(BoardCell cell : adjMtx.get(startCell)){
			if(!visited.contains(cell)){
				visited.add(cell);
				if(pathLength == 1){
					targets.add(cell);
				}
				else {
					findAllTargets(cell, pathLength-1);
				}
			}
			visited.remove(cell);
		}

	}
	
	
	public static void main(String args[]) {
		IntBoard board = new IntBoard();
		BoardCell cell = board.getCell(2, 1);
		board.calcTargets(cell, 6);
		Set<BoardCell> targets = board.getTargets();
		for(BoardCell i: targets) {
			System.out.println(i);
			
		}		
	}
	
	
}

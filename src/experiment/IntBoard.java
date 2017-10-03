package experiment;

import java.util.Map;
import java.util.Set;

public class IntBoard {
	private BoardCell[][] grid;
	private Map<BoardCell, Set<BoardCell>> adjMtx;
	private Set<BoardCell> visited;
	private Set<BoardCell> targets;
	
	
	public IntBoard(BoardCell[][] grid, Map<BoardCell, Set<BoardCell>> adjMtx, Set<BoardCell> visited,
			Set<BoardCell> targets) {
		super();
		this.grid = grid;
		this.adjMtx = adjMtx;
		this.visited = visited;
		this.targets = targets;
	}



	public void calcAdjacencies(){
		for (int i = 0; i < 4; i++){
			for (int j = 0; j < 4; j++){
				
				
			}
			
		}
		
	}
	

}

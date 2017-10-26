package clueGame;

public class BoardCell {
	private int row;
	private int column;
	private char initial;
	private DoorDirection doorDirection;
	
	
	public BoardCell(int row, int column, String labels) {
		super();
		this.row = row;
		this.column = column;
		
		if(labels.length() > 1){
			this.initial = labels.charAt(0);
			if(labels.charAt(1) == 'D'){
				doorDirection = DoorDirection.DOWN;
				
			}
			else if(labels.charAt(1) == 'U'){
				doorDirection = DoorDirection.UP;
				
			}
			else if(labels.charAt(1) == 'L'){
				doorDirection = DoorDirection.LEFT;
				
			}
			else if(labels.charAt(1) == 'R'){
				doorDirection = DoorDirection.RIGHT;			
			}
			else {
				doorDirection = DoorDirection.NONE;
			}
			
		}
		else {
			this.initial = labels.charAt(0);
			doorDirection = DoorDirection.NONE;
		}
		
	}

	@Override
	public String toString() {
		return "BoardCell [row=" + row + ", column=" + column + "]";
	}
	
	public boolean isWalkway(){
		if(initial == 'W'){
			return true;
			
		}	
		else {return false;}
		
	}
	
	public boolean isRoom(){
		if(initial != 'W'){
			return true;
			
		}	
		else {return false;}	
	}
	
	public boolean isDoorway(){
		if(doorDirection != DoorDirection.NONE){
			return true;
			
		}	
		else {return false;}	
		
	}

	public DoorDirection getDoorDirection() {
		return doorDirection;
	}

	public char getInitial() {
		return initial;
	}
	
	
	

}

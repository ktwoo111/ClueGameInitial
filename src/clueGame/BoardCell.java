package clueGame;

public class BoardCell {
	private int row;
	private int column;
	private char initial;
	private DoorDirection doorDirection;
	
	
	public BoardCell(int row, int column) {
		super();
		this.row = row;
		this.column = column;
	}

	@Override
	public String toString() {
		return "BoardCell [row=" + row + ", column=" + column + "]";
	}
	
	public boolean isWalkway(){
		return true;
		
	}
	
	public boolean isRoom(){
		return true;	
	}
	
	public boolean isDoorway(){
		return true;
		
	}

	public DoorDirection getDoorDirection() {
		// TODO Auto-generated method stub
		return null;
	}

	public char getInitial() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	

}

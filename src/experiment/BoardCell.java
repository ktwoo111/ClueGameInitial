package experiment;

public class BoardCell {
	public int row;
	public int column;
	
	public BoardCell(int row, int column) {
		super();
		this.row = row;
		this.column = column;
	}

	@Override
	public String toString() {
		return "BoardCell [row=" + row + ", column=" + column + "]";
	}
	
	
	
	

}

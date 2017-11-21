package clueGame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;

import javax.swing.JLabel;

public class BoardCell {
	public static final int BOX_DIMENSION = 24;
	public static final int DOOR_DIMENSION = 4;
	private static Board board = Board.getInstance();
	private int row;
	private int column;
	private char initial;
	private boolean textField;
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
			else if(labels.charAt(1) == 'T'){
				doorDirection = DoorDirection.NONE;	
				textField = true;
			}
			else {
				doorDirection = DoorDirection.NONE;
			}

		}
		else {
			this.initial = labels.charAt(0);
			doorDirection = DoorDirection.NONE;
			textField = false;
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

	public boolean isTextField() {
		return textField;
	}
	/**
	 * 
	 * @param g graphics for drawing
	 * This function draws each square and will add labels and players where needed
	 */
	public void draw(Graphics g) {


		if (initial == 'W') {
			g.setColor(Color.YELLOW);
		}
		else {
			g.setColor(Color.GRAY);
		}

		if (doorDirection == DoorDirection.RIGHT) {
			g.fillRect(column*BOX_DIMENSION, row*BOX_DIMENSION, BOX_DIMENSION - DOOR_DIMENSION, BOX_DIMENSION);
			g.setColor(Color.BLUE);
			g.fillRect(column*BOX_DIMENSION + BOX_DIMENSION - DOOR_DIMENSION, row*BOX_DIMENSION, DOOR_DIMENSION, BOX_DIMENSION);
		}
		else if(doorDirection == DoorDirection.LEFT) {
			g.setColor(Color.BLUE);
			g.fillRect(column*BOX_DIMENSION, row*BOX_DIMENSION, DOOR_DIMENSION, BOX_DIMENSION);
			g.setColor(Color.GRAY);
			g.fillRect(column*BOX_DIMENSION + DOOR_DIMENSION, row*BOX_DIMENSION, BOX_DIMENSION - DOOR_DIMENSION, BOX_DIMENSION);
		}
		else if(doorDirection == DoorDirection.UP){
			g.setColor(Color.BLUE);
			g.fillRect(column*BOX_DIMENSION, row*BOX_DIMENSION, BOX_DIMENSION, DOOR_DIMENSION);
			g.setColor(Color.GRAY);
			g.fillRect(column*BOX_DIMENSION, row*BOX_DIMENSION + DOOR_DIMENSION, BOX_DIMENSION, BOX_DIMENSION  - DOOR_DIMENSION);
		}
		else if(doorDirection == DoorDirection.DOWN) {
			g.setColor(Color.GRAY);
			g.fillRect(column*BOX_DIMENSION, row*BOX_DIMENSION, BOX_DIMENSION, BOX_DIMENSION  - DOOR_DIMENSION);
			g.setColor(Color.BLUE);
			g.fillRect(column*BOX_DIMENSION, row*BOX_DIMENSION + BOX_DIMENSION - DOOR_DIMENSION, BOX_DIMENSION, DOOR_DIMENSION);	
		}
		else {
			g.fillRect(column*BOX_DIMENSION, row*BOX_DIMENSION, BOX_DIMENSION, BOX_DIMENSION);
			if (initial == 'W') {
				g.setColor(Color.BLACK);
				g.drawRect(column*BOX_DIMENSION, row*BOX_DIMENSION, BOX_DIMENSION, BOX_DIMENSION);
			}
		}

		if(isTextField()){
			if(initial == 'C'){
				g.setColor(Color.BLUE);
				g.drawString("CGR",column*BoardCell.BOX_DIMENSION,row*BoardCell.BOX_DIMENSION);

			}
			else{

				g.setColor(Color.BLUE);
				g.drawString(board.getLegend().get(board.getCellAt(row, column).getInitial()),column*BoardCell.BOX_DIMENSION,row*BoardCell.BOX_DIMENSION);	
			}
		}


		for (Player p : board.getPlayers()){
			if(p.getRow() == row && p.getColumn() == column){
				g.setColor(p.getColor());
				g.fillOval(column*BoardCell.BOX_DIMENSION,row*BoardCell.BOX_DIMENSION,BOX_DIMENSION,BOX_DIMENSION);
			}

		}

		if(board.getCurrentPlayer() == 0){
			for(BoardCell c : board.getTargets()){
				if(c.getRow() == row && c.getColumn() == column){
					g.setColor(Color.white);
					g.fillRect(column*BOX_DIMENSION, row*BOX_DIMENSION, BOX_DIMENSION, BOX_DIMENSION);			
				}

			}
		}
		
	}


	public int getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}







}

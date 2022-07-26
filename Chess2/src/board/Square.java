package board;

import board.figures.Figure;

public class Square {
	private Figure figureOnThis;
	
	private boolean attackedByP1;
	private boolean attackedByP2;
	
	public void resetAttackedBy() {
		attackedByP1 = false;
		attackedByP2 = false;		
	}
	
	public void setAttackedByP1(boolean b) {
		attackedByP1 = b;
	}

	public boolean getAttackedByP1() {
		return attackedByP1;
	}

	public boolean getAttackedByP2() {
		return attackedByP2;
	}
	
	public boolean attackedBy(Players p) {
		switch (p) {
		case P1:
			return getAttackedByP1();
		case P2:
			return getAttackedByP2();
		default:
			System.out.println("Unsupported argument for Square.attackedBy()");
			return false;
		}
	}
	
	public void setAttackedByP2(boolean b) {
		attackedByP2 = b;
	}
	
	@SuppressWarnings("unused")
	private Coords coordinates;

	public Figure getFigure() {
		return figureOnThis;
	}
	
	public Figure removeFigure() {
		Figure f = figureOnThis;
		figureOnThis = null;
		return f;
	}
	
	public void setFigure(Figure f) {
		figureOnThis = f;
	}
	
	
	public boolean isEmpty() {
		if (figureOnThis == null) {
			return true;
		} else {
			return false;
		}
	}
	
	public Square(Coords c) {
		coordinates = c;
	}
	
	public String toString() {
		if (isEmpty()) {
			return "EMPTY";
		} else {
			return figureOnThis.toString();
		}
	}
}

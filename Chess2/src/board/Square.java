package board;

import board.figures.Figure;

public class Square {
	private Figure figureOnThis;
	
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

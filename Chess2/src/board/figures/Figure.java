package board.figures;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import board.Board;
import board.Colour;
import board.Coords;

public abstract class Figure {
	protected int pointValue;
	protected Colour colour;
	
	// TEMP
	public char[] draw;
	
	public int getPointValue() {
		return pointValue;
	}
	
	public Colour getColour() {
		return colour;
	}
	
	public Figure(int pointValue, Colour colour) {
		this.pointValue = pointValue;
		this.colour = colour;
	}
	
	public abstract boolean isLegal(Coords from, Coords to, Board gameBoard); // For checking if move is legal
	
	public List<Coords> getLegalMoves(Coords at, Board gameBoard) {
		List<Coords> out = new ArrayList<Coords>();
		
		for (int x = 0; x < 8; x++) {
			for (int y = 0; y < 8; y++) {
				Coords to = new Coords(y, x);
				if (isLegal(at, to, gameBoard)) {
					out.add(to);
					
				}
			}
		}
		
		return out;
	};
	
	public abstract void moved(Coords from, Coords to, Board gameBoard); // For doing special stuff after moving
	
	public abstract String toString();

	public abstract void draw(Graphics2D g2, int pictureSize, Coords middle, Color borderClr, Color fillClr);
}

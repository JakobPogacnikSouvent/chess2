package board.figures;

import java.awt.Color;
import java.awt.Graphics2D;

import board.Board;
import board.Colour;
import board.Coords;

public class Rook extends Figure{
	
	public Rook(Colour colour) {
		super(5, colour);
		
		// TEMP
		draw = new char[] {'R'};
	}

	@Override
	public boolean isLegal(Coords from, Coords to, Board gameBoard) {
		if (Coords.subtract(to, from).x == 0 || Coords.subtract(to, from).y == 0) {
			if (!gameBoard.at(to).isEmpty() && gameBoard.at(to).getFigure().colour == this.colour) return false;
			
			Coords n = Coords.subtract(to, from).norm();
			Coords at = Coords.add(from, n);
			while (!at.equals(to)) {
				if (!gameBoard.at(at).isEmpty()) return false;
				at = Coords.add(at, n);
			}
			
			return true;
		}
		
		// Should never be called. Handle all cases in elifs.
		return false;	
	}

	@Override
	public String toString() {
		return "  R  ";
	}

	@Override
	public void draw(Graphics2D g2, int pictureSize, Coords middle, Color borderClr, Color fillClr) {
		// TODO Auto-generated method stub

		g2.setColor(fillClr);
		
		g2.fillRect(middle.x - (pictureSize / 2), middle.y - (pictureSize / 2), pictureSize, pictureSize);
		
		g2.setColor(borderClr);
		
		g2.drawRect(middle.x - (pictureSize / 2), middle.y - (pictureSize / 2), pictureSize, pictureSize);
		
	}

	@Override
	public void moved(Coords from, Coords to, Board gameBoard) {
		// TODO Auto-generated method stub
		
	}
}

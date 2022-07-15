package board.figures;

import java.awt.Color;
import java.awt.Graphics2D;

import board.Board;
import board.Colour;
import board.Coords;

public class Bishop extends Figure{

	public Bishop(Colour colour) {
		super(3, colour);
		
		// TEMP
		draw = new char[] {'B'};

	}

	@Override
	public boolean isLegal(Coords from, Coords to, Board gameBoard) {
		if (Math.abs(Coords.subtract(to, from).x) == Math.abs(Coords.subtract(to, from).y)) {

			if (!gameBoard.at(to).isEmpty() && gameBoard.at(to).getFigure().colour == this.colour) return false;
			
			Coords n = Coords.subtract(to, from).norm();
			Coords at = Coords.add(from, n);
			while (!at.equals(to)) {
				if (!gameBoard.at(at).isEmpty()) return false;
				at = at.add(n);
			}
				
			return true;
			
		}
		
		// Should never be called. Handle all cases in elifs.
		return false;	
	}
	
	@Override
	public String toString() {
		return "  B  ";
	}

	@Override
	public void draw(Graphics2D g2, int pictureSize, Coords middle, Color borderClr, Color fillClr) {
		// TODO Auto-generated method stub
		
		g2.setColor(fillClr);
		
		g2.fillOval(middle.x - (pictureSize / 4), middle.y - (pictureSize / 2), pictureSize / 2, pictureSize);
		
		g2.setColor(borderClr);
		
		g2.drawOval(middle.x - (pictureSize / 4), middle.y - (pictureSize / 2), pictureSize / 2, pictureSize);
		
	}

	@Override
	public void moved(Coords from, Coords to, Board gameBoard) {
		// TODO Auto-generated method stub
		
	}
}

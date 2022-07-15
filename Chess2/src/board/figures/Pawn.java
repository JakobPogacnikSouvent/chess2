package board.figures;

import java.awt.Color;
import java.awt.Graphics2D;

import board.Board;
import board.Colour;
import board.Coords;

public class Pawn extends Figure {
	
	private boolean moved;
	
	public Pawn(Colour colour) {
		super(1, colour);
		
		moved = false;
		
		// TEMP
		draw = new char[] {'P'};
	}

	@Override
	public boolean isLegal(Coords from, Coords to, Board gameBoard) {
		if (this.colour == Colour.WHITE) {
			if (Coords.subtract(to, from).equals(new Coords(0, -1)) && gameBoard.at(to).isEmpty()) return true;
			if (!moved && Coords.subtract(to, from).equals(new Coords(0, -2)) && gameBoard.at(Coords.subtract(to, new Coords(0, -1))).isEmpty() && gameBoard.at(to).isEmpty()) return true;			
			if ((Coords.subtract(to, from).equals(new Coords(1, -1)) || Coords.subtract(to, from).equals(new Coords(-1, -1))) && !gameBoard.at(to).isEmpty()) {
				if (gameBoard.at(to).getFigure().colour == this.colour) return false;
				return true;
			}
			return false;
		} else if (this.colour == Colour.BLACK) {
			if (Coords.subtract(to, from).equals(new Coords(0, 1)) && gameBoard.at(to).isEmpty()) return true;
			if (!moved && Coords.subtract(to, from).equals(new Coords(0, 2)) && gameBoard.at(Coords.subtract(to, new Coords(0, 1))).isEmpty() && gameBoard.at(to).isEmpty()) return true;
			if ((Coords.subtract(to, from).equals(new Coords(1, 1)) || Coords.subtract(to, from).equals(new Coords(-1, 1))) && !gameBoard.at(to).isEmpty()) {
				if (gameBoard.at(to).getFigure().colour == this.colour) return false;
				return true;
			}
			return false;

		}
		
		// Should never be called. Handle all cases in elifs.
		return false;
	}
	
	@Override
	public String toString() {
		return "  P  ";
	}

	@Override
	public void draw(Graphics2D g2, int pictureSize, Coords middle, Color borderClr, Color fillClr) {
		// TODO Auto-generated method stub

		g2.setColor(fillClr);
		
		g2.fillOval(middle.x - (pictureSize / 2), middle.y - (pictureSize / 2), pictureSize, pictureSize);
		
		g2.setColor(borderClr);
		
		g2.drawOval(middle.x - (pictureSize / 2), middle.y - (pictureSize / 2), pictureSize, pictureSize);
		
	}

	@Override
	public void moved(Coords from, Coords to, Board gameBoard) {
		// TODO Auto-generated method stub
		
		moved = true;
		
	}


	
}

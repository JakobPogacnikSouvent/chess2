package board.figures;

import java.awt.Color;
import java.awt.Graphics2D;

import board.Board;
import board.Colour;
import board.Coords;

public class King extends Figure {

	public King(Colour colour) {
		super(Integer.MAX_VALUE, colour);
		
		// TEMP
		draw = new char[] {'K'};

	}

	@Override
	public boolean isLegal(Coords from, Coords to, Board gameBoard) {
		if (Math.abs(Coords.subtract(to, from).x) <= 1 && Math.abs(Coords.subtract(to, from).y) <= 1) {
			if (!gameBoard.at(to).isEmpty() && gameBoard.at(to).getFigure().colour == this.colour) return false;
			return true;
		}
		
		// Should never be called. Handle all cases in elifs.
		return false;	
	}
	
	@Override
	public String toString() {
		return "  K  ";
	}

	@Override
	public void draw(Graphics2D g2, int pictureSize, Coords middle, Color borderClr, Color fillClr) {
		// TODO Auto-generated method stub

		g2.setColor(fillClr);
		
		g2.fillPolygon(new int[] {middle.x - pictureSize/2, middle.x + pictureSize/2, middle.x}, new int[] {middle.y + pictureSize/2, middle.y + pictureSize/2, middle.y - pictureSize/2}, 3);
		
		g2.setColor(borderClr);
		
		g2.drawPolyline(new int[] {middle.x - pictureSize/2, middle.x + pictureSize/2, middle.x, middle.x - pictureSize/2}, new int[] {middle.y + pictureSize/2, middle.y + pictureSize/2, middle.y - pictureSize/2, middle.y + pictureSize/2}, 4);
		
	}

	@Override
	public void moved(Coords from, Coords to, Board gameBoard) {
		// TODO Auto-generated method stub
		
	}
}

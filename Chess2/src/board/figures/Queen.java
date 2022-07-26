package board.figures;

import java.awt.Color;
import java.awt.Graphics2D;
import board.Board;
import board.Colour;
import board.Coords;
import board.Players;
import board.Square;

public class Queen extends Figure {

	public Queen(Colour colour, Players owner) {
		super(9, colour, owner);
		
		// TEMP
		draw = new char[] {'Q'};
	}

	@Override
	public boolean isLegal(Coords from, Coords to, Board gameBoard) {
		if ((Coords.subtract(to, from).x == 0 || Coords.subtract(to, from).y == 0) || (Math.abs(Coords.subtract(to, from).x) == Math.abs(Coords.subtract(to, from).y))) {
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
	public boolean isAttacked(Coords from, Coords to, Board gameBoard) {
		if ((Coords.subtract(to, from).x == 0 || Coords.subtract(to, from).y == 0) || (Math.abs(Coords.subtract(to, from).x) == Math.abs(Coords.subtract(to, from).y))) {
			
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
		return "  Q  ";
	}

	
	@Override
	public void draw(Graphics2D g2, int pictureSize, Coords middle, Color borderClr, Color fillClr) {
		// TODO Auto-generated method stub

		g2.setColor(fillClr);
		
		g2.fillPolygon(new int[] {middle.x - pictureSize/2, middle.x + pictureSize/2, middle.x}, new int[] {middle.y - pictureSize/2, middle.y - pictureSize/2, middle.y + pictureSize/2}, 3);
		
		g2.setColor(borderClr);
		
		g2.drawPolyline(new int[] {middle.x - pictureSize/2, middle.x + pictureSize/2, middle.x, middle.x - pictureSize/2}, new int[] {middle.y - pictureSize/2, middle.y - pictureSize/2, middle.y + pictureSize/2, middle.y - pictureSize/2}, 4);
		
	}

	@Override
	public void moved(Coords from, Coords to, Board gameBoard) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void startOfTurn(Coords at, Board gameBoard, Players activePlayer) {
		// TODO Auto-generated method stub
			
		for (Square s : this.getAttackedSquares(at, gameBoard)) {
			switch (owner) {
			case P1:
				s.setAttackedByP1(true);
				break;
			case P2:
				s.setAttackedByP2(true);
				break;
			}
		}
	}

	@Override
	public void endOfTurn(Coords at, Board gameBoard, Players activePlayer) {
		// TODO Auto-generated method stub
		
	}



}

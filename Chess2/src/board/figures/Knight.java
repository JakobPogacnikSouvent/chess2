package board.figures;

import java.awt.Color;
import java.awt.Graphics2D;
import board.Board;
import board.Colour;
import board.Coords;
import board.Players;
import board.Square;

public class Knight extends Figure{
	public Knight(Colour colour, Players owner) {
		super(3, colour, owner);
		
		// TEMP
		draw = new char[] {'N'};
	}

	@Override
	public boolean isLegal(Coords from, Coords to, Board gameBoard) {
		if ((Math.abs(Coords.subtract(to, from).x) == 2 && Math.abs(Coords.subtract(to, from).y) == 1) || (Math.abs(Coords.subtract(to, from).x) == 1 && Math.abs(Coords.subtract(to, from).y) == 2)) {
			if (!gameBoard.at(to).isEmpty() && gameBoard.at(to).getFigure().colour == this.colour) return false;
			return true;
		}
		
		// Should never be called. Handle all cases in elifs.
		return false;	
	}
	
	@Override
	public boolean isAttacked(Coords from, Coords to, Board gameBoard) {
		if ((Math.abs(Coords.subtract(to, from).x) == 2 && Math.abs(Coords.subtract(to, from).y) == 1) || (Math.abs(Coords.subtract(to, from).x) == 1 && Math.abs(Coords.subtract(to, from).y) == 2)) {
			return true;
		}
		
		// Should never be called. Handle all cases in elifs.
		return false;
	}
	
	@Override
	public String toString() {
		return "  N  ";
	}
	
	@Override
	public void draw(Graphics2D g2, int pictureSize, Coords middle, Color borderClr, Color fillClr) {
		// TODO Auto-generated method stub

		g2.setColor(fillClr);
		
		g2.fillRect(middle.x - (pictureSize / 4), middle.y - (pictureSize / 2), pictureSize / 2, pictureSize);
		
		g2.setColor(borderClr);
		
		g2.drawRect(middle.x - (pictureSize / 4), middle.y - (pictureSize / 2), pictureSize / 2, pictureSize);
		
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

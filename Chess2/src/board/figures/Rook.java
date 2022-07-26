package board.figures;

import java.awt.Color;
import java.awt.Graphics2D;
import board.Board;
import board.Colour;
import board.Coords;
import board.Players;
import board.Square;

public class Rook extends Figure{
	
	private boolean moved;
	
	public boolean getMoved() {
		return moved;
	}
	
	public Rook(Colour colour, Players owner) {
		super(5, colour, owner);
		
		// TEMP
		draw = new char[] {'R'};
		
		moved = false;
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
	public boolean isAttacked(Coords from, Coords to, Board gameBoard) {
		if (Coords.subtract(to, from).x == 0 || Coords.subtract(to, from).y == 0) {
			
			Coords n = Coords.subtract(to, from).norm();
			Coords at = Coords.add(from, n);
			while (!at.equals(to)) {
				if (!gameBoard.at(at).isEmpty()) return false;
				at = Coords.add(at, n);
			}
			
			return true;
		}
		
		// Should never be called. Handle all cases in elifs.
		return false;	}
	
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
		
		moved = true;
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

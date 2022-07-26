package board.figures;

import java.awt.Color;
import java.awt.Graphics2D;
import board.Board;
import board.Colour;
import board.Coords;
import board.Players;
import board.Square;

public class King extends Figure {

	private boolean moved;
	
	public boolean getMoved() {
		return moved;
	}
	
	public King(Colour colour, Players owner) {
		super(Integer.MAX_VALUE, colour, owner);
		
		// TEMP
		draw = new char[] {'K'};
		
		moved = false;
	}

	@Override
	public boolean isLegal(Coords from, Coords to, Board gameBoard) {
		if (Math.abs(Coords.subtract(to, from).x) <= 1 && Math.abs(Coords.subtract(to, from).y) <= 1) {
			if (this.owner == Players.P1 && gameBoard.at(to).getAttackedByP2()) return false;
			if (this.owner == Players.P2 && gameBoard.at(to).getAttackedByP1()) return false;
			if (!gameBoard.at(to).isEmpty() && gameBoard.at(to).getFigure().colour == this.colour) return false;
			return true;
		}
		
		// Should never be called. Handle all cases in elifs.
		return false;	
	}
	
	@Override
	public boolean isAttacked(Coords from, Coords to, Board gameBoard) {
		if (Math.abs(Coords.subtract(to, from).x) <= 1 && Math.abs(Coords.subtract(to, from).y) <= 1) {
			return true;
		}
		
		// Should never be called. Handle all cases in elifs.
		return false;
	}
	
	public boolean validCastle(Coords from, Coords to, Board gameBoard) {
		
		// Checks if a move is a valid castling move
		if (this.moved) return false;
		if (gameBoard.at(to).isEmpty()) return false;
		
		Figure f = gameBoard.at(to).getFigure(); 
		
		if (!( f instanceof Rook)) return false;
		if (((Rook) f).getMoved()) return false;
		
		Coords n = Coords.subtract(to, from).norm();
		if (n.equals(new Coords(1, 0)) || n.equals(new Coords(-1, 0))) {
			
			// All squares between vacant
			Coords at = Coords.add(from, n);
			while (!at.equals(to)) {
				if (!gameBoard.at(at).isEmpty()) return false;
				at.add(n);
			}
			

			// Not castle through check
			Coords endSquare;
			if (n.equals(new Coords(1,0))) {
				endSquare = new Coords(7, from.y); // while loop breaks 1 before
			} else {
				// n.equals(new Coords(-1,0))
				endSquare = new Coords(1, from.y); // while loop breaks 1 before
			}
			
			at = Coords.add(from, n);
			
			switch (owner) {
			case P1:
				while (!at.equals(endSquare)) {
					if (gameBoard.at(at).getAttackedByP2()) {
						System.out.println("Cannot castle through check");
						return false;
					}
					at.add(n);
				}	
				break;
				
			case P2:
				while (!at.equals(endSquare)) {
					if (gameBoard.at(at).getAttackedByP1()) {
						System.out.println("Cannot castle through check");
						return false;
					}
					at.add(n);
				}
				break;
			}
			
			return true;
		}
			
		// set moved to true
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
	
	public boolean hasLegalMoves(Coords at, Board gameBoard) {
		// To check if it is checkmated
		if (this.getLegalMoves(at, gameBoard).size() > 0) {
			return true;
		}
		return false;
	}


}

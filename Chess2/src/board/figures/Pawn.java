package board.figures;

import java.awt.Color;
import java.awt.Graphics2D;
import javax.swing.JOptionPane;

import board.Board;
import board.Colour;
import board.Coords;
import board.Players;
import board.Square;

public class Pawn extends Figure {
	
	private boolean moved;
	
	private boolean canBeEnPassanted;
	
	public void setCanBeEnPassanted(boolean b) {
		canBeEnPassanted = b;
	}
	
	public boolean getCanBeEnPassanted() {
		return canBeEnPassanted;
	}
	
	public Pawn(Colour colour, Players owner) {
		super(1, colour, owner);
		
		moved = false;
		
		canBeEnPassanted = false;
		
		// TEMP
		draw = new char[] {'P'};
	}

	@Override
	public boolean isLegal(Coords from, Coords to, Board gameBoard) {
		switch (owner) {
		case P1:
		
		
			if (Coords.subtract(to, from).equals(new Coords(0, -1)) && gameBoard.at(to).isEmpty()) return true;
			if (!moved && Coords.subtract(to, from).equals(new Coords(0, -2)) && gameBoard.at(Coords.subtract(to, new Coords(0, -1))).isEmpty() && gameBoard.at(to).isEmpty()) {
				canBeEnPassanted = true;
				return true;		
			}
			if ((Coords.subtract(to, from).equals(new Coords(1, -1)) || Coords.subtract(to, from).equals(new Coords(-1, -1))) && !gameBoard.at(to).isEmpty()) {
				if (gameBoard.at(to).getFigure().colour == this.colour) return false;
				return true;
			}
			return false;
			// break;
		
		case P2:
			if (Coords.subtract(to, from).equals(new Coords(0, 1)) && gameBoard.at(to).isEmpty()) return true;
			if (!moved && Coords.subtract(to, from).equals(new Coords(0, 2)) && gameBoard.at(Coords.subtract(to, new Coords(0, 1))).isEmpty() && gameBoard.at(to).isEmpty()) {
				canBeEnPassanted = true;
				return true;
			}
			if ((Coords.subtract(to, from).equals(new Coords(1, 1)) || Coords.subtract(to, from).equals(new Coords(-1, 1))) && !gameBoard.at(to).isEmpty()) {
				if (gameBoard.at(to).getFigure().colour == this.colour) return false;
				return true;
			}
			return false;
			// break;
		
		
		}
		// Should never be called. Handle all cases in elifs.
		return false;
	}
	
	@Override
	public boolean isAttacked(Coords from, Coords to, Board gameBoard) {
		// TODO: Code handled in start turn
		return false;
	}
	
	public boolean validEnPassant(Coords from, Coords to, Board gameBoard) {
		// Returns boolean telling if a move from coords "from" to coords "to" is a valid en passant move
		Coords enPassantCoords;
		
		switch (owner) {
		case P1:
	
			enPassantCoords = Coords.add(to, new Coords(0, 1));
			if ((Coords.subtract(to, from).equals(new Coords(1, -1)) || Coords.subtract(to, from).equals(new Coords(-1, -1))) && !gameBoard.at(enPassantCoords).isEmpty()) {
				Figure f = gameBoard.at(enPassantCoords).getFigure();
				if (f instanceof Pawn) return ((Pawn) f).canBeEnPassanted;
			}
			
			break;
			
		case P2:
			enPassantCoords = Coords.add(to, new Coords(0, -1));
			if ((Coords.subtract(to, from).equals(new Coords(1, 1)) || Coords.subtract(to, from).equals(new Coords(-1, 1))) && !gameBoard.at(enPassantCoords).isEmpty()) {
				Figure f = gameBoard.at(enPassantCoords).getFigure();
				if (f instanceof Pawn) return ((Pawn) f).canBeEnPassanted;
			}
			break;
		}
		
		return false;
	}
	
	public Coords getEnPassantCoords(Coords to) {
		// Returns coords of enemy pawn if you are trying to do en passant by moving to Coords "to"
		switch (owner) {
		case P1:		
			return Coords.add(to, new Coords(0, 1));
			// break;
		case P2:
			return Coords.add(to, new Coords(0, -1));
			// break;
		}
		
		// Should never happen
		System.out.println("Null returned from getEnPassantCoords");
		return null;
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

	@Override
	public void startOfTurn(Coords at, Board gameBoard, Players activePlayer) {
		// TODO Auto-generated method stub
		if (activePlayer == this.owner) {
			canBeEnPassanted = false;			
		}
		
		Square fst, snd;
		
		switch (owner) {
		case P1:
			fst = gameBoard.at(Coords.add(at, new Coords(-1, -1)));
			snd = gameBoard.at(Coords.add(at, new Coords(1, -1)));
			
			if (fst != null) {
				fst.setAttackedByP1(true);
			}
			if (snd != null) {
				snd.setAttackedByP1(true);
			}
			
			break;
			
		case P2:
			fst = gameBoard.at(Coords.add(at, new Coords(-1, 1)));
			snd = gameBoard.at(Coords.add(at, new Coords(1, 1)));
			
			if (fst != null) {
				fst.setAttackedByP2(true);
			}
			if (snd != null) {
				snd.setAttackedByP2(true);
			}
			
			break;
		
		}
		
	}

	@Override
	public void endOfTurn(Coords at, Board gameBoard, Players activePlayer) {
		// TODO Auto-generated method stub
		
		if ((this.owner == Players.P1 && at.y == 0) || (this.owner == Players.P2 && at.y == 7)) {
			Figure promotionPiece = new Queen(this.colour, this.owner);

			String[] options = new String[] {"Bishop", "Knight", "Rook", "Queen"};
		     
			int playerChoice = JOptionPane.showOptionDialog(null, "Choose promotion piece:", "Promotion", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
		      
		      switch (playerChoice) {
		      case 0 :
		    	  promotionPiece = new Bishop(this.colour, this.owner);
		    	  break;
		      case 1:
		    	  promotionPiece = new Knight(this.colour, this.owner);
		    	  break;
		      case 2:
		    	  promotionPiece = new Rook(this.colour, this.owner);
		    	  break;
		      case 3:
		    	  promotionPiece = new Queen(this.colour, this.owner);
		    	  break;
		      }

			gameBoard.at(at).setFigure(promotionPiece);
			gameBoard.at(at).getFigure().endOfTurn(at, gameBoard, activePlayer);;
			
		} 
	}	
	
}

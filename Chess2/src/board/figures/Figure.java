package board.figures;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import board.Board;
import board.Colour;
import board.Coords;
import board.Players;
import board.Square;

public abstract class Figure {
	protected int pointValue;
	protected Colour colour;
	protected Players owner;
	
	// TEMP
	public char[] draw;
	
	public int getPointValue() {
		return pointValue;
	}
	
	public Colour getColour() {
		return colour;
	}
	
	public Players getOwner() {
		return owner;
	}
	
	public Figure(int pointValue, Colour colour, Players owner) {
		this.pointValue = pointValue;
		this.colour = colour;
		this.owner = owner;
	}
	
	public abstract boolean isLegal(Coords from, Coords to, Board gameBoard); // For checking if move is legal
	
	public abstract boolean isAttacked(Coords from, Coords to, Board gameBoard); // For checking where enemy king cant move
	
	public List<Square> getAttackedSquares(Coords at, Board gameBoard) {
    // TODO: Would be more optimized to make it figure specific 
		List<Square> out = new ArrayList<Square>();
		
		for (int x = 0; x < 8; x++) {
			for (int y = 0; y < 8; y++) {
				Coords to = new Coords(y, x);
				if (isAttacked(at, to, gameBoard)) {
					out.add(gameBoard.at(to));
				}
			}
		}
		
		return out;
	}
	
	public List<Coords> getLegalMoves(Coords at, Board gameBoard) {
    // TODO: Would be more optimized to make it figure specific 
		List<Coords> out = new ArrayList<Coords>();
		
		for (int x = 0; x < 8; x++) {
			for (int y = 0; y < 8; y++) {
				Coords to = new Coords(y, x);
				if (isLegal(at, to, gameBoard)) {
					out.add(to);
					
				} else if (this instanceof Pawn && ((Pawn) this).validEnPassant(at, to, gameBoard)) {
					out.add(to);
				} else if (this instanceof King && ((King) this).validCastle(at, to, gameBoard)) {
					out.add(to);
				}
			}
		}
		
		return out;
	}
	
	public List<Square> getLegalSquares(Coords at, Board gameBoard) {
	// Same as getLegalMoves except it returns squares
    // TODO: Would be more optimized to make it figure specific 
		List<Square> out = new ArrayList<Square>();
		
		for (int x = 0; x < 8; x++) {
			for (int y = 0; y < 8; y++) {
				Coords to = new Coords(y, x);
				if (isLegal(at, to, gameBoard)) {
					out.add(gameBoard.at(to));
					
				} else if (this instanceof Pawn && ((Pawn) this).validEnPassant(at, to, gameBoard)) {
					out.add(gameBoard.at(to));
				}
				// TODO: Castling
			}
		}
		
		return out;
	}
	
	public abstract void moved(Coords from, Coords to, Board gameBoard); // For doing special stuff after moving
	
	public abstract void startOfTurn(Coords at, Board gameBoard, Players activePlayer); // For doing special stuff at start of turn

	public abstract void endOfTurn(Coords at, Board gameBoard, Players activePlayer); // For doing special stuff at end of turn
	
	public abstract String toString();

	public abstract void draw(Graphics2D g2, int pictureSize, Coords middle, Color borderClr, Color fillClr);
}

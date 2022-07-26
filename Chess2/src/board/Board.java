package board;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import board.figures.*;

public class Board {
	private Square[][] gameBoard = new Square[8][8];
	
	public List<Figure> getFigures() {
		List<Figure> out = new ArrayList<Figure>();
		for (int x = 0; x < 8; x++) {
			for (int y = 0; y < 8; y++) {
				if (this.at(y, x).isEmpty()) {
					out.add(this.at(y,x).getFigure());
				}
			}
		}
		return out;
	}
	
	public Board() {
		for (int x = 0; x < 8; x++) {
			for (int y = 0; y < 8; y++) {
				this.gameBoard[x][y] = new Square(new Coords(x, y));
			}
		}
	}
	
	public static Board defaultChess() {
		Board b = new Board();
		b.at(0, 0).setFigure(new Rook(Colour.BLACK, Players.P2));
		b.at(0, 1).setFigure(new Knight(Colour.BLACK, Players.P2));
		b.at(0, 2).setFigure(new Bishop(Colour.BLACK, Players.P2));
		b.at(0, 3).setFigure(new Queen(Colour.BLACK, Players.P2));
		b.at(0, 4).setFigure(new King(Colour.BLACK, Players.P2));
		b.at(0, 5).setFigure(new Bishop(Colour.BLACK, Players.P2));
		b.at(0, 6).setFigure(new Knight(Colour.BLACK, Players.P2));
		b.at(0, 7).setFigure(new Rook(Colour.BLACK, Players.P2));
		
		for (int i = 0; i < 8; i++) {
			b.at(1, i).setFigure(new Pawn(Colour.BLACK, Players.P2));			
		}

		for (int i = 0; i < 8; i++) {
			b.at(6, i).setFigure(new Pawn(Colour.WHITE, Players.P1));			
		}
		
		b.at(7, 0).setFigure(new Rook(Colour.WHITE, Players.P1));
		b.at(7, 1).setFigure(new Knight(Colour.WHITE, Players.P1));
		b.at(7, 2).setFigure(new Bishop(Colour.WHITE, Players.P1));
		b.at(7, 3).setFigure(new Queen(Colour.WHITE, Players.P1));
		b.at(7, 4).setFigure(new King(Colour.WHITE, Players.P1));
		b.at(7, 5).setFigure(new Bishop(Colour.WHITE, Players.P1));
		b.at(7, 6).setFigure(new Knight(Colour.WHITE, Players.P1));
		b.at(7, 7).setFigure(new Rook(Colour.WHITE, Players.P1));

		
		return b;
	}
	
	public Square at(Coords c) {
		if (0 <= c.x && c.x <= 7 && 0 <= c.y && c.y <= 7) {
			return gameBoard[c.y][c.x];			
		} else {
			return null;
		}
	}
	
	public Square at(int y, int x) {
		return gameBoard[y][x];
	}
	
	public String toString() {
		return Arrays.deepToString(gameBoard).replace("], ", "]\n").replace("[", "").replace("]", "").replace(",", " |");
	}
}

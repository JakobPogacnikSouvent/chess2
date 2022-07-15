package board;

import board.figures.Figure;

public class Game {
	private Board gameBoard;	
	
	public Board getBoard() {
		return gameBoard;
	}
	
	public Game() {
		gameBoard = Board.defaultChess();
		
	}
	
	public boolean move(Coords from, Coords to) {
		Square sqFrom = gameBoard.at(from);
		Square sqTo = gameBoard.at(to);
		
		if (sqFrom == null || sqTo == null) return false;
		
		if (!sqFrom.isEmpty()) {
			Figure f = sqFrom.getFigure();
			if (f.isLegal(from, to, gameBoard)) {
				sqFrom.removeFigure();
				sqTo.setFigure(f);
				f.moved(from, to, gameBoard);
				return true;
			} else {
				System.out.println("Illegal move!");
				return false;
			}
		}
		
		return false;
	}
	

	public String toString() {
		return gameBoard.toString();
	}

}

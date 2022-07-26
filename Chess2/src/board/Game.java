package board;

import board.figures.Figure;
import board.figures.King;
import board.figures.Pawn;

public class Game {
	private Board gameBoard;
	private Players activePlayer;
	
	@SuppressWarnings("unused")
	private Gamestate state;
	
	public Board getBoard() {
		return gameBoard;
	}
	
	public Players getActivePlayer() {
		return activePlayer;
	}
	
	public Game() {
		gameBoard = Board.defaultChess();
		activePlayer = Players.P1;
		
		startMoveTriggers();
	}
	
	public boolean move(Coords from, Coords to) {
		Square sqFrom = gameBoard.at(from);
		Square sqTo = gameBoard.at(to);
		
		if (sqFrom == null || sqTo == null) return false;
		
		if (!sqFrom.isEmpty()) {
			Figure f = sqFrom.getFigure();
			
			if (f.getOwner() != activePlayer) return false;
			
			if (f instanceof Pawn) {
				// Check if en passant				
				if (((Pawn) f).validEnPassant(from, to, gameBoard)) {
					sqFrom.removeFigure();
					sqTo.setFigure(f);
					gameBoard.at(((Pawn) f).getEnPassantCoords(to)).removeFigure();
					
					endMove();
					return true;
				}
			}
			
			if (f instanceof King) {
				// Check to castle
				if (((King) f).validCastle(from, to, gameBoard)) {
					Square kingEndSquare = null;
					Square rookEndSquare = null;
					
					Coords n = Coords.subtract(to, from).norm();
					
					if (n.equals(new Coords(1,0))) {
						kingEndSquare = gameBoard.at(from.y, 6);
						rookEndSquare = gameBoard.at(from.y, 5);
					} else {
						// n.equals(new Coords(-1,0))
						kingEndSquare = gameBoard.at(from.y, 2);
						rookEndSquare = gameBoard.at(from.y, 3);
					}
					
					sqFrom.removeFigure();
					kingEndSquare.setFigure(f);
					rookEndSquare.setFigure(sqTo.removeFigure());
					
					endMove();
					return true;
				}
			}
			
			if (f.isLegal(from, to, gameBoard)) {
				
				sqFrom.removeFigure();
				sqTo.setFigure(f);
				f.moved(from, to, gameBoard);
				
				endMove();
				return true;
			} else {
				// System.out.println("Illegal move!");
				return false;
			}
		}
		
		return false;
	}
	
	private void endMove() {
		// Skips start of turn on first turn of the game
		
		endMoveTriggers();
		
		nextPlayer();
		
		startMoveTriggers();
		
	}
	
	private void endMoveTriggers() {
		// TODO: Check checkmate && stalemate
		
		for (int x = 0; x < 8; x++) {
			for (int y = 0; y < 8; y++) {
				gameBoard.at(y, x).resetAttackedBy();
			}
		}
		
		for (int x = 0; x < 8; x++) {
			for (int y = 0; y < 8; y++) {
				Coords c = new Coords(x, y);
				if (!gameBoard.at(c).isEmpty()) {
					Figure f = gameBoard.at(c).getFigure(); 
					f.endOfTurn(c, gameBoard, activePlayer);						

					// Check checkmate
					if (f instanceof King && f.getOwner() != activePlayer && gameBoard.at(c).attackedBy(activePlayer) && !((King) f).hasLegalMoves(c, gameBoard)) {
						
						switch (activePlayer) {
						case P1:
							endGame(Gamestate.P1_Win);							
							break;
						case P2:
							endGame(Gamestate.P2_Win);
							break;
						}
					}
				}
				
			}
		}
	}
	
	private void endGame(Gamestate g) {
		state = g;
		
		switch (g) {
		case P1_Win:
			System.out.println("Checkmate. White wins.");
			break;
		case P2_Win:
			System.out.println("Checkmate. Black wins.");
			break;
		case Draw:
			System.out.println("Draw");
			break;
		default:
			break;
		}
	}
	
	private void startMoveTriggers() {
		boolean activePlayerHasMoves = false;
		
		for (int x = 0; x < 8; x++) {
			for (int y = 0; y < 8; y++) {
				Coords c = new Coords(x, y);
				if (!gameBoard.at(c).isEmpty()) {
					Figure f = gameBoard.at(c).getFigure(); 
					f.startOfTurn(c, gameBoard, activePlayer);
					
					if (!activePlayerHasMoves && f.getLegalMoves(c, gameBoard).size() > 0) {
						activePlayerHasMoves = true;
					}
				}
			}
		}
		
		if (!activePlayerHasMoves) {
			endGame(Gamestate.Draw);
		}
		
	}
	
	private void nextPlayer() {
		// There may be more logic to switching players later in different gamemodes 
		
		if (activePlayer == Players.P1) {
			activePlayer = Players.P2;
		} else {
			activePlayer = Players.P1;
		}
	}
	
	public String toString() {
		return gameBoard.toString();
	}

}

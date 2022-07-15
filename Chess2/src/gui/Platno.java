package gui;

import javax.swing.JPanel;

import board.Colour;
import board.Coords;
import board.Game;
import board.figures.Figure;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.event.*;
import java.util.List;


@SuppressWarnings("serial") 
public class Platno extends JPanel implements MouseListener, MouseMotionListener, KeyListener {
	
	private Game game;

	private Color squareColourPrimary, squareColourSecondary, figureColourPrimary, figureColourSecondary;
	private Stroke edgeSize;
	
	
	private Figure selectedFigure;
	private Coords selectedFigureCoords;
	
	private Coords mouseCoords;
	private boolean select;
	
	private int fullWidth, fullHeight, boardSize, squareSize;
	private Coords boardZeroVector;
	
	public Platno(int sirina, int visina) {
		super();
		setPreferredSize(new Dimension(sirina, visina));

		game = null;
		
		squareColourPrimary = new Color(17, 59, 8);
		squareColourSecondary = new Color(13, 47, 7);
		
		figureColourPrimary = Color.WHITE;
		figureColourSecondary = Color.BLACK;
		
		edgeSize = new BasicStroke(1);
		
		setBackground(Color.LIGHT_GRAY);
		
		addMouseListener(this);
		addMouseMotionListener(this);
		addKeyListener(this);
		setFocusable(true);
	}
	
	public void setGame(Game g) {
		game = g;
		repaint();
	}
	
	public void setWindowVars() {
		fullWidth = getWidth();
		fullHeight = getHeight();
		boardSize = Math.min(fullWidth, fullHeight);
		squareSize = boardSize / 8;
		boardZeroVector = new Coords((Math.max(fullWidth, boardSize) - Math.min(fullWidth, boardSize))/2, (Math.max(fullHeight, boardSize) - Math.min(fullHeight, boardSize))/2);
	}

	@Override
	protected void paintComponent(Graphics g) {
		if (game == null) return;
		
		super.paintComponent(g);
		
		setWindowVars();
		
		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(edgeSize);
		
		List<Coords> legalMoves = null;
		
		if (selectedFigure != null) {
			 legalMoves = selectedFigure.getLegalMoves(selectedFigureCoords, game.getBoard());

		}
		
		for (int i = 0; i < 8; ++i) {
			for (int j = 0; j < 8; ++j) {				
				Coords squareZeroVector = Coords.add(boardZeroVector, new Coords(i * squareSize, j * squareSize));
				
				Coords c = new Coords(i, j);
				
				// Call square.paint
				
				if ((i + j) % 2 != 0) {
					g2.setColor(squareColourSecondary);										
				} else {
					g2.setColor(squareColourPrimary);					
				}
				
				if (!game.getBoard().at(c).isEmpty() && game.getBoard().at(c).getFigure() == selectedFigure) {
					g2.setColor(Color.blue);
				} else if (legalMoves != null) {
					for (Coords move : legalMoves) {
						if (c.equals(move)) {
							g2.setColor(Color.blue);
						}
					}
				}
				
				g2.fillRect(squareZeroVector.x, squareZeroVector.y, squareSize, squareSize);
				
			}
		}
		
		for (int i = 0; i < 8; ++i) {
			for (int j = 0; j < 8; ++j) {
				Coords squareZeroVector = Coords.add(boardZeroVector, new Coords(i * squareSize, j * squareSize));

				// figure.paint in square.paint
				
				if (!game.getBoard().at(j, i).isEmpty()) {
					
					Figure f = game.getBoard().at(j, i).getFigure();
										
					if (game.getBoard().at(j, i).getFigure().getColour() == Colour.BLACK) {
						g2.setColor(figureColourSecondary);						
					} else {
						g2.setColor(figureColourPrimary);												
					}
					
					Color borderClr;
					Color fillClr;
					
					if (f.getColour() == Colour.BLACK) {
						fillClr = figureColourSecondary;
						borderClr = figureColourPrimary;						
					} else {
						fillClr = figureColourPrimary;
						borderClr = figureColourSecondary;
					}
					
					if (selectedFigure != null && mouseCoords != null && f == selectedFigure) {
						f.draw(g2, (int) (squareSize * 0.75), mouseCoords, borderClr, fillClr);												
					} else {
						f.draw(g2, (int) (squareSize * 0.75), Coords.add(squareZeroVector, new Coords(squareSize / 2, squareSize / 2)), borderClr, fillClr);						
					}
					
				}
			}
		}

		
		
		// TODO: paint
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if (selectedFigure != null) {
			mouseCoords = new Coords(e.getX(), e.getY());
			
			if (select && Coords.divide(mouseCoords, squareSize).subtract(selectedFigureCoords).size2Norm() > 1) {
				select = false;
			}
		}
		repaint();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {

	}
	

	@Override
	public void mousePressed(MouseEvent e) {
		Coords c = Coords.subtract(new Coords(e.getX(), e.getY()), boardZeroVector).divide(squareSize);
		
		if (!game.getBoard().at(c).isEmpty()) {
			// TODO: Check if figure is of player currently on turn 
			if (selectedFigure != game.getBoard().at(c).getFigure()) {
				select = true;				
			}

			selectedFigureCoords = c;
			selectedFigure = game.getBoard().at(c).getFigure();
			
			
		} else if (selectedFigure != null) {
			game.move(selectedFigureCoords, c);
			selectedFigureCoords = null;
			selectedFigure = null;
			mouseCoords = null;
		} else {
			selectedFigureCoords = null;
			selectedFigure = null;
			mouseCoords = null;			
		}
		
		repaint();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		Coords c = Coords.subtract(new Coords(e.getX(), e.getY()), boardZeroVector).divide(squareSize);
				
		if (selectedFigureCoords != null) {
			if (!c.equals(selectedFigureCoords)) {
				game.move(selectedFigureCoords, c);
				selectedFigureCoords = null;
				selectedFigure = null;
			} else if (!select) {
				selectedFigureCoords = null;
				selectedFigure = null;
			}
			mouseCoords = null;
			select = false;
		}
		
		repaint();
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}

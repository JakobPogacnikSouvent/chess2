package tests;

import javax.swing.WindowConstants;

import gui.GameWindow;

public class GuiTest {
	public static void main(String[] args) {
		GameWindow okno = new GameWindow();
		okno.pack();
		okno.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		okno.setLocationRelativeTo(null);
		okno.setVisible(true);
	}

}

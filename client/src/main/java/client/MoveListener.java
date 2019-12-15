package client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Arrays;

import javax.swing.JButton;


public class MoveListener implements ActionListener {
	
	private static JButton currentlySelected;
	ClientGame game;
	
	public MoveListener(ClientGame game) {
		this.game = game;
	}
	
	public int[] getPieceLocation(JButton button) {
		int[] location = {-1, -1};
		for (int i=0; i < game.gameGUI.buttonGrid.length; i++) {
			for (int j=0; j < game.gameGUI.buttonGrid[i].length; j++) {
				if (button == game.gameGUI.buttonGrid[i][j]) {
					location[0] = i; location[1] = j;
					return location;
				}
			}
		}
		return location;
	}
	
	public void actionPerformed(ActionEvent arg0) {
		
		JButton clickedButton = (JButton) arg0.getSource();
		int[] location = getPieceLocation(clickedButton);
		
		if (currentlySelected == null) {
			
			
			char[][] board = game.getBoard();
			if (board[location[0]][location[1]] != 'e') {
				currentlySelected = clickedButton;
			}
		} else {
			
			int[] sourceCoordinates = getPieceLocation(currentlySelected);
			int[] destCoordinates = getPieceLocation(clickedButton);
			
			try {
				game.updateGameState(sourceCoordinates, destCoordinates);
				if (game.playerChoice != game.getTurn()) {
					int[] action = new int[4];
					if (game.playerChoice == 0 ) action = game.gameAI.Minimax_Decision(game.getBoard(), 1);
					else action = game.gameAI.Minimax_Decision(game.getBoard(), 0);
					game.updateGameState(Arrays.copyOfRange(action, 0, 2), Arrays.copyOfRange(action, 2, action.length));
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			currentlySelected = null;
		}
		
		
	}

}

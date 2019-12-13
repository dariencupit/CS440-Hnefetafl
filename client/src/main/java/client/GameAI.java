package client;

import java.util.ArrayList;

public class GameAI {
	// Action
	//		- An action is represented as an int array with a size of 4
	//		The first two slots in the array represent from, and the last two
	//		represent to in the form of y,x
	// State
	//		- State is represented as a double char array of characters
	ClientGame clientGame;
	int depth = 0;
	final int limit = 3;
	
	GameAI (ClientGame game) {
		this.clientGame = game;
		
	}
	
	public int[] Minimax_Decision(char[][] state) { // Implements Alpha-Beta Pruning
		
		return new int[0];
	}
	
	public double Max_Value(char[][] state, int player, double a, double b) {
		
		return 0.0;
	}
	
	public double Min_Value(char[][] state, int player, double a, double b) {
		// TODO Find where to increment this.depth and where to put it back to 0
		if (this.depth == this.limit) return Heuristic(state);
		double value = Double.MAX_VALUE;
		
		return 0.0;
	}
	
	public double Heuristic(char[][] state) {
		
		return 0.0;
	}
	
	public char[][] Result(char[][] state, int[] action) {
		
		return new char[0][0];
	}
	
	public int[][] Actions(char[][] state, int player) {
		
		/*
		 * Overview
		 * 
		 * loop through every piece that matches target
		 *   scans each direction 
		 *   	adds each empty location as an action to the output
		 *   	ends when not an empty space
		 *   
		 *   TODO - needs testing
		 */
		
		char targetChar = 'b';
		if(player == 1) { targetChar = 'w'; }
		
		ArrayList<int[]> output = new ArrayList<int[]>();
		
		// Loop through each piece
		for(int y = 0; y < state.length; y++) {
			for(int x = 0; x < state.length; x++) {
				// Check if piece is on the current team
				if(state[y][x] == targetChar) {
					// search each direction as long as there empty spaces
					for(int cy = y + 1; cy < state.length; cy++) {
						if(state[cy][x] == 'e') {
							int[] next = {y,x,cy,x};
							output.add(next);
						} else {
							break;
						}
					}
					for(int cy = y - 1; cy >= 0; cy--) {
						if(state[cy][x] == 'e') {
							int[] next = {y,x,cy,x};
							output.add(next);
						} else {
							break;
						}
					}
					for(int cx = x + 1; cx < state.length; cx++) {
						if(state[y][cx] == 'e') {
							int[] next = {y,x,y,cx};
							output.add(next);
						} else {
							break;
						}
					}
					for(int cx = x - 1; cx >= 0; cx--) {
						if(state[y][cx] == 'e') {
							int[] next = {y,x,y,cx};
							output.add(next);
						} else {
							break;
						}
					}
				}
			}
		}
		
		int[][] out = new int[1][output.size()];
		for(int i = 0; i < output.size(); i++) {
			out[i] = output.get(i);
		}
		
		return out;
	}
	
	public static void main(String[] args) {
		
	}

}

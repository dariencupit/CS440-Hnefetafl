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
	
	public double Max_Value(char[][] state, double a, double b) {
		
		return 0.0;
	}
	
	public double Min_Value(char[][] state, double a, double b) {
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
	
	public int[][] Actions(char[][] state, char player) {
		
		return new int[0][0];
	}
	
	public static void main(String[] args) {
		
	}

}

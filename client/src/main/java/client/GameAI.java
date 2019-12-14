package client;

import java.util.ArrayList;

public class GameAI {
	// Action
	//		- An action is represented as an int array with a size of 4
	//		The first two slots in the array represent from, and the last two
	//		represent to in the form of y,x
	// State
	//		- State is represented as a double char array of characters
	int depth = 0;
	final int limit = 10;
	
	public int[] Minimax_Decision(char[][] state, int player) { // Implements Alpha-Beta Pruning
		double[] a = {Double.MIN_VALUE};
		double[] b = {Double.MAX_VALUE};
		this.depth = 0;
		double value = this.Max_Value(state, player, a, b);
		int[][] actions = Actions(state, player);
		for (int[] action : actions) {
			double val = this.Heuristic(this.Result(state, action));
			if (val == value) return action;
		}
		return actions[0];
	}
	
	public double Max_Value(char[][] state, int player, double[] a, double b[]) {
		// TODO Find where to increment this.depth and where to put it back to 0
		if (this.depth == this.limit) return Heuristic(state);
		this.depth++;
		double value = Double.MIN_VALUE;
		int[][] actions = Actions(state, player);
		for (int[] action : actions) {
			value = Double.max(value, this.Min_Value(Result(state, action), this.Switch_Player(player), a, b));
			if (value >= b[0]) return value;
			a[0] = Double.max(a[0], value); // Change this to be a reference
		}
		return value;
	}
	
	public double Min_Value(char[][] state, int player, double[] a, double[] b) {
		// TODO Find where to increment this.depth and where to put it back to 0
		if (this.depth == this.limit) return Heuristic(state);
		this.depth++;
		double value = Double.MAX_VALUE;
		int[][] actions = Actions(state, player);
		for (int[] action : actions) {
			value = Double.min(value, this.Max_Value(Result(state, action), this.Switch_Player(player), a, b));
			if (value <= a[0]) return value;
			b[0] = Double.min(b[0], value); // Change this to be a reference
		}
		return value;
	}
	
	public int Switch_Player(int current_player) {
		int new_player;
		if (current_player == 0) new_player = 1;
		else new_player = 0;
		return new_player;
	}
	
	public double Heuristic(char[][] state) {
		
		return 0.0;
	}
	
	public char[][] Result(char[][] state, int[] action) {
		char temp = state[action[2]][action[3]];
		char[][] newState = new char[11][11];
		for(int i=0; i < 11; i++)
			  for(int j=0; j < 11; j++)
			    newState[i][j]=state[i][j];
		newState[action[2]][action[3]] = newState[action[0]][action[1]];
		newState[action[0]][action[1]] = temp;
		return newState;
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
					// search each direction as long as there are empty spaces
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
		
		// Convert ArrayList to char[][]
		int[][] out = new int[output.size()][];
		for(int i = 0; i < output.size(); i++) {
			out[i] = output.get(i);
		}
		
		return out;
	}
	
	public static void main(String[] args) {
		
	}

}

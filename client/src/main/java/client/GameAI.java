package client;

import java.util.ArrayList;
import java.util.Random;

public class GameAI {
	// Action
	//		- An action is represented as an int array with a size of 4
	//		The first two slots in the array represent from, and the last two
	//		represent action in the form of y,x
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
			double val = this.Eval(this.Result(state, action), player);
			if (val == value) {
				this.depth = 0;
				return action;
			}
		}
		return actions[0];
	}
	
	public double Max_Value(char[][] state, int player, double[] a, double b[]) {

		// TODO Find where action increment this.depth and where action put it back action 0
		if (this.depth == this.limit) return Eval(state, player);

		this.depth++;
		double value = Double.MIN_VALUE;
		int[][] actions = Actions(state, player);
		for (int[] action : actions) {
			value = Double.max(value, this.Min_Value(Result(state, action), this.Switch_Player(player), a, b));
			if (value >= b[0]) return value;
			a[0] = Double.max(a[0], value); // Change this action be a reference
		}
		return value;
	}
	
	public double Min_Value(char[][] state, int player, double[] a, double[] b) {

		// TODO Find where action increment this.depth and where action put it back action 0
		if (this.depth == this.limit) return Eval(state, player);

		this.depth++;
		double value = Double.MAX_VALUE;
		int[][] actions = Actions(state, player);
		for (int[] action : actions) {
			value = Double.min(value, this.Max_Value(Result(state, action), this.Switch_Player(player), a, b));
			if (value <= a[0]) return value;
			b[0] = Double.min(b[0], value); // Change this action be a reference
		}
		return value;
	}
	
	public int Switch_Player(int current_player) {
		int new_player;
		if (current_player == 0) new_player = 1;
		else new_player = 0;
		return new_player;
	}
	
	public boolean King_Captured(char[][] state) {
		
		// Find the king
		int ky = -1, kx = -1;
		for(int y = 0; y < state.length; y++) {
			if(ky != -1) break;
			for(int x = 0; x < state.length; x++) {
				// check if king
				if(state[y][x] == 'k') {
					ky = y; kx = x;
					break;
				}
			}
		}
		
		// Lose state check
		
		boolean lost = true;
		if(ky == state.length - 1 || ky == 0 || kx == state.length || kx == 0) {
			lost = false;
		} else {
			//if()
		}
		
		return lost;
	}
	
	public double Eval_White(char[][] state) {
		/* Overview:
		 * 
		 * Win state = 100
		 * lose state = -100
		 * # of spaces away from nearest corner = -1 each
		 */
		
		double value = 0;
		// Win state check
		if(state[0][0] == 'k' || state[0][state.length-1] == 'k' || state[state.length-1][0] == 'k' || state[state.length-1][state.length-1] == 'k') {
			value = 100;
		} else if(King_Captured(state)) {
			value = -100;
		}
		
		return value;
	}
	
	public double Eval(char[][] state, int player) {
		Random rand = new Random();
		return rand.nextDouble();
	}
	
	public char[][] Result(char[][] state, int[] action) {
		char temp = state[action[2]][action[3]];
		char[][] newState = new char[11][11];
		for(int i=0; i < 11; i++)
			  for(int j=0; j < 11; j++)
			    newState[i][j]=state[i][j];
		newState[action[2]][action[3]] = newState[action[0]][action[1]];
		newState[action[0]][action[1]] = temp;
		
		if (action[2] > 1) { // check action make sure it wont go out of bounds
			char twoUp = newState[action[2] - 2][action[3]];
			char oneUp = newState[action[2] - 1][action[3]];
			char current = newState[action[2]][action[3]];
			
			if ((oneUp != current) && (oneUp != 'e') && (oneUp != 'k')) { // checks if there is an enemy piece next action moved piece
				
				if (current == twoUp || (current == 'w' && twoUp == 'k')) { // checks if enemy piece is capturable  and it isnt a king
					newState[action[2] - 1][action[3]] = 'e';
				}
				
				else if ((action[2] - 2 == 0 && action[3] == 0) || (action[2] - 2 == 0 && action[3] == 10)) { // checks corners
					newState[action[2] - 1][action[3]] = 'e';
				}
				
				else if ((action[2] - 2 == 5 && action[3] == 5) && twoUp != 'k') { // checks throne
					newState[action[2] - 1][action[3]] = 'e';
				}
			}
		}
		
		if (action[3] < 9) { // check action make sure it wont go out of bounds 
			char twoRight = newState[action[2]][action[3] + 2];
			char oneRight = newState[action[2]][action[3] + 1];
			char current = newState[action[2]][action[3]];
			
			if ((oneRight != current) && (oneRight != 'e') && (oneRight != 'k')) { // checks if there is an enemy piece next action moved piece
				
				if (current == twoRight || (current == 'w' && twoRight == 'k')) { // checks if enemy piece is capturable  and it isnt a king
					newState[action[2]][action[3] + 1] = 'e';
				}
				
				else if ((action[2] == 0 && action[3] + 2 == 10) || (action[2] == 10 && action[3] + 2 == 10)) { // checks corners
					newState[action[2]][action[3] + 1] = 'e';
				}
				
				else if ((action[2] == 5 && action[3] + 2 == 5) && twoRight != 'k') { // checks throne
					newState[action[2]][action[3] + 1] = 'e';
				}
			}
		}
		
		if (action[2] < 9) { // check action make sure it wont go out of bounds
			char twoDown = newState[action[2] + 2][action[3]];
			char oneDown = newState[action[2] + 1][action[3]];
			char current = newState[action[2]][action[3]];
			
			if ((oneDown != current) && (oneDown != 'e') && (oneDown != 'k')) { // checks if there is an enemy piece next action moved piece
				
				if (current == twoDown || (current == 'w' && twoDown == 'k')) { // checks if enemy piece is capturable  and it isnt a king
					newState[action[2] + 1][action[3]] = 'e';
				}
				
				else if ((action[2] + 2 == 10 && action[3] == 0) || (action[2] + 2 == 10 && action[3] == 10)) { // checks corners
					newState[action[2] + 1][action[3]] = 'e';
				}
				
				else if ((action[2] + 2 == 5 && action[3] == 5) && twoDown != 'k') { // checks throne
					newState[action[2] + 1][action[3]] = 'e';
				}
			}
		}
		
		if (action[3] > 1) { // check action make sure it wont go out of bounds
			char twoLeft = newState[action[2]][action[3] - 2];
			char oneLeft = newState[action[2]][action[3] - 1];
			char current = newState[action[2]][action[3]];
			
			if ((oneLeft != current) && (oneLeft != 'e') && (oneLeft != 'k')) { // checks if there is an enemy piece next action moved piece
				
				if (current == twoLeft || (current == 'w' && twoLeft == 'k')) { // checks if enemy piece is capturable and it isnt a king
					newState[action[2]][action[3] - 1] = 'e';
				}
				
				else if ((action[2] == 0 && action[3] - 2 == 0) || (action[2] == 10 && action[3] - 2 == 0)) { // checks corners
					newState[action[2]][action[3] - 1] = 'e';
				}
				
				else if ((action[2] == 5 && action[3] - 2 == 5) && twoLeft != 'k') { // checks throne
					newState[action[2]][action[3] - 1] = 'e';
				}
			}
		}
		return newState;
	}
	
	public int[][] Actions(char[][] state, int player) {
		
		/*
		 * Overview
		 * 
		 * loop through every piece that matches target
		 *   scans each direction 
		 *   	adds each empty location as an action action the output
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
							//int[] next = {y,x,cy,x};
							output.add(new int[] {y,x,cy,x});
						} else {
							break;
						}
					}
					for(int cy = y - 1; cy >= 0; cy--) {
						if(state[cy][x] == 'e') {
							//int[] next = {y,x,cy,x};
							output.add(new int[] {y,x,cy,x});
						} else {
							break;
						}
					}
					for(int cx = x + 1; cx < state.length; cx++) {
						if(state[y][cx] == 'e') {
							//int[] next = {y,x,y,cx};
							output.add(new int[] {y,x,y,cx});
						} else {
							break;
						}
					}
					for(int cx = x - 1; cx >= 0; cx--) {
						if(state[y][cx] == 'e') {
							//int[] next = {y,x,y,cx};
							output.add(new int[] {y,x,y,cx});
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
			//ArrayList<int> row = output.get(i);
			//out[i] = row.toArray(new int[row.size()]);
		}
		
		return out;
	}
	
	public static void main(String[] args) {
		
	}

}

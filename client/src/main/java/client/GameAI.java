package client;

import java.util.ArrayList;
//import java.util.Random;
import java.lang.Math;

public class GameAI {
	// Action
	//		- An action is represented as an int array with a size of 4
	//		The first two slots in the array represent from, and the last two
	//		represent action in the form of y,x
	// State
	//		- State is represented as a double char array of characters
	final int limit = 3;
	int playerAI = 0;
	
	public int[] Minimax_Decision(char[][] state, int player) { // Implements Alpha-Beta Pruning
		
		playerAI = player;
		double a = Double.MIN_VALUE;
		double b = Double.MAX_VALUE;
		double value = Double.MIN_VALUE;
		int[] maxAction = {0,0,0,0};
		int[][] actions = Actions(state, player);
		for (int[] action : actions) {
			double val = Alpha_Beta(Result(state, action), Switch_Player(player), 1, a, b);
			if (val > value) {
				maxAction = action;
				value = val;
			}
		}
		return maxAction;
	}
	
	//  Russell, Stuart J.; Norvig, Peter (2003), Artificial Intelligence: A Modern Approach (2nd ed.), Upper Saddle River, New Jersey: Prentice Hall, ISBN 0-13-790395-2
	public double Alpha_Beta(char[][] state, int player, int depth, double a, double b) {
		
		if (depth == this.limit) return Eval(state, player);
		
		if (player == this.playerAI) {
			int[][] actions = Actions(state, player);
			for (int[] action : actions) {
				a = Double.max(a, Alpha_Beta(Result(state, action), Switch_Player(player), depth + 1, a, b));
				if (b <= a) break;
			}
			return a;
		}
		
		else {
			int[][] actions = Actions(state, player);
			for (int[] action : actions) {
				b = Double.min(b, Alpha_Beta(Result(state, action), Switch_Player(player), depth + 1, a, b));
				if (b <= a) break;
			}
			return b;
		}
	}
	
	/*public double Max_Value(char[][] state, int player, int depth) {

		if (depth == this.limit) return Eval(state, player);

		double value = Double.MIN_VALUE;
		int[][] actions = Actions(state, player);
		for (int[] action : actions) {
			value = Double.max(value, this.Min_Value(Result(state, action), this.Switch_Player(player), depth + 1));
		//	if (value >= this.b) {
		//		System.out.print("Depth: " + depth + " Action: ");
		//		for (int i = 0; i < action.length; i ++) System.out.print(action[i] + " ,");
		//		System.out.println();
		//		return value;
		//	}
		//	this.a = Double.max(this.a, value); // Change this to be a reference
		}
		return value;
	}*/
	
	/*public double Min_Value(char[][] state, int player, int depth) {
		
		if (depth == this.limit) return Eval(state, player);
		
		double value = Double.MAX_VALUE;
		int[][] actions = Actions(state, player);
		for (int[] action : actions) {
			value = Double.min(value, this.Max_Value(Result(state, action), this.Switch_Player(player), depth + 1));
			//if (value <= this.a) return value;
			//this.b = Double.min(this.b, value); // Change this to be a reference
		}
		return value;
	}*/
	
	public int Switch_Player(int current_player) {
		int new_player;
		if (current_player == 0) new_player = 1;
		else new_player = 0;
		return new_player;
	}
	
	public double Eval_Black(char[][] state) {
		
		return 2.0;
	}
	
	public double Eval(char[][] state, int player) {
		
		double score = 0.0;
		
		int blackCount = 0;
		int whiteCount = 0;
		int[] kingCoords = new int[2];
		
		for (int i = 0; i < 11; i++) {
			for (int j = 0; j < 11; j++) {
				if (state[i][j] == 'b') blackCount++;
				else if (state[i][j] == 'w') whiteCount++;
				else if (state[i][j] == 'k') {
					kingCoords[0] = i;
					kingCoords[1] = j;
				}
			}
		}
		
		if (this.playerAI == 0) score += blackCount;
		else score += whiteCount * 2;
		
		return score;
	}

	public int[] King_Coords(char[][] state) {
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
		int[] out = {ky, kx};
		return out;
	}
	
	public boolean King_Captured(char[][] state) {
		
		// Find the king
		int ky,kx;
		int[] kingCoords = King_Coords(state);
		ky = kingCoords[0]; kx = kingCoords[1];
		
		// Lose state check
		boolean lost = true;
		if(ky == state.length - 1 || ky == 0 || kx == state.length || kx == 0) {
			lost = false;
		} else {
			// For each direction, it checks to see if next to a black piece or the throne
			// TODO - needs testing
			if(state[ky+1][kx] != 'b' || (ky != 5-1 || kx != 5)) {
				lost = false;
			} else if(state[ky-1][kx] != 'b' || (ky != 5+1 || kx != 5)) {
				lost = false;
			} else if(state[ky][kx+1] != 'b' || (ky != 5 || kx != 5-1)) {
				lost = false;
			} else if(state[ky][kx-1] != 'b' || (ky != 5 || kx != 5+1)) {
				lost = false;
			}
		}
		
		return lost;
	}
	
	public double Eval_White(char[][] state) {
		/* Overview:
		 * 
		 * Win state = 100
		 * lose state = -100
		 * # of spaces away the center = 1 each
		 */
		
		double value = 0;
		// Win state check
		if(state[0][0] == 'k' || state[0][state.length-1] == 'k' || state[state.length-1][0] == 'k' || state[state.length-1][state.length-1] == 'k') {
			value = 100;
		} 
		// Lost state check
		else if(King_Captured(state)) {
			value = -100;
		}
		
		//number of spaces away from center calculation
		//TODO - needs testing
		int[] kingCoords = King_Coords(state);
		int spacesFromCenter = Math.abs(kingCoords[0] - 5) + Math.abs(kingCoords[1] - 5);
		value += spacesFromCenter;
		
		return value;
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
		}
		
		return out;
	}
	
	public static void main(String[] args) {
		
	}

}

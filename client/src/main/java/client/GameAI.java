package client;

import java.util.ArrayList;
import java.util.Arrays;
//import java.util.Random;
import java.lang.Math;

public class GameAI {
	// Action
	//		- An action is represented as an int array with a size of 4
	//		The first two slots in the array represent from, and the last two
	//		represent action in the form of y,x
	// State
	//		- State is represented as a double char array of characters
	final int limit = 4;
	int playerAI = 0;
	
	public int[] Minimax_Decision(char[][] state, int player) { // Implements Alpha-Beta Pruning
		playerAI = player;
		double a = Double.NEGATIVE_INFINITY;
		double b = Double.POSITIVE_INFINITY;
		double value = Double.NEGATIVE_INFINITY;
		int[] maxAction = {0,0,0,0};
		int[][] actions = Actions(state, player);
		for (int[] action : actions) {
			double val = Alpha_Beta(Result(state, action), Switch_Player(player), 1, a, b);
			//System.out.println("Action: " + Arrays.toString(action) + " Score: " + val);
			if (val > value) {
				maxAction = action;
				value = val;
			}
		}
		System.out.println("Final Action: " + Arrays.toString(maxAction) + "Final Score: " + value);
		return maxAction;
	}
	
	//  Russell, Stuart J.; Norvig, Peter (2003), Artificial Intelligence: A Modern Approach (2nd ed.), Upper Saddle River, New Jersey: Prentice Hall, ISBN 0-13-790395-2
	public double Alpha_Beta(char[][] state, int player, int depth, double a, double b) {
		
		if (depth == this.limit) return Eval(state);
		
		if (Win_State(state)) {
			if (player == this.playerAI) return -100;
			else return 100;
		}
		
		if (player == this.playerAI) {
			double value = Double.NEGATIVE_INFINITY;
			int[][] actions = Actions(state, player);
			for (int[] action : actions) {
				value = Double.max(value, Alpha_Beta(Result(state, action), Switch_Player(player), depth + 1, a, b));
				a = Double.max(a, value);
				if (b <= a) break;
			}
			return value;
		}
		
		else {
			double value = Double.POSITIVE_INFINITY;
			int[][] actions = Actions(state, player);
			for (int[] action : actions) {
				value = Double.min(value, Alpha_Beta(Result(state, action), Switch_Player(player), depth + 1, a, b));
				b = Double.min(b, value);
				if (b <= a) break;
			}
			return value;
		}
	}
	
	public boolean Win_State(char[][] state) {
		double value = Eval(state);
		if (value > 50 || value < -50)  {
			System.out.println("Win State Score: " + value);
			return true;
		}
		else return false;
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
		
		double value = Double.POSITIVE_INFINITY;
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
	
	public double Eval_Black(char[][] state, int[] kingCoords) {
		
		double score = 0.0;
		
		if(state[0][0] == 'k' || state[0][10] == 'k' || state[10][10] == 'k'||state[10][0] == 'k') {
			score -= 100;
		}
		
		int y = kingCoords[0];
		int x = kingCoords[1];
		if((y != 10 && y != 0) && (x != 10 && x != 0)) {
			if(state[y+1][x] == 'b' && state[y-1][x] == 'b' && state[y][x+1] == 'b' && state[y][x-1] == 'b') {
				score += 100;
			}
		}
		
		return score;
	}
	
	public double Eval(char[][] state) {
		
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
		
		if (this.playerAI == 0) {
			score += Eval_Black(state, kingCoords);
			//score += blackCount;
			int difference = blackCount - (whiteCount * 2);
			score += difference;
			if(blackCount < 4) {
				score -= 100;
			}
		}
		else {
			score += Eval_White(state, kingCoords);
			//score += whiteCount * 2;
			int difference = (whiteCount * 2) - blackCount;
			score += difference;
			if(blackCount < 4) {
				score += 100;
			}
		}
		return score;
	}
	
	/*public boolean King_Captured(char[][] state, int[] kingCoords) {
		
		// Find the king
		int ky,kx;
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
	}*/
	
	public double Eval_White(char[][] state, int[] kingCoords) {
		
		double score = 0;
		
		if(state[0][0] == 'k' || state[0][10] == 'k' || state[10][10] == 'k'||state[10][0] == 'k') {
			score += 100;
		}
		
		int y = kingCoords[0];
		int x = kingCoords[1];
		if ((y != 10 && y != 0) && (x != 10 && x != 0)) {
			if(state[y+1][x] == 'b' && state[y-1][x] == 'b' && state[y][x+1] == 'b' && state[y][x-1] == 'b') {
				score -= 100;
			}
		}
		
		return score;
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
			
			if (((oneUp != current && current != 'k') || (current == 'k' && oneUp == 'b')) && (oneUp != 'e') && (oneUp != 'k')) { // checks if there is an enemy piece next action moved piece
				
				if (current == twoUp || (current == 'w' && twoUp == 'k') || (current == 'k' && twoUp == 'w')) { // checks if enemy piece is capturable  and it isnt a king
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
			
			if (((oneRight != current && current != 'k') || (current == 'k' && oneRight == 'b')) && (oneRight != 'e') && (oneRight != 'k')) { // checks if there is an enemy piece next action moved piece
				
				if (current == twoRight || (current == 'w' && twoRight == 'k') || (current == 'k' && twoRight == 'w')) { // checks if enemy piece is capturable  and it isnt a king
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
			
			if (((oneDown != current && current != 'k') || (current == 'k' && oneDown == 'b')) && (oneDown != 'e') && (oneDown != 'k')) { // checks if there is an enemy piece next action moved piece
				
				if (current == twoDown || (current == 'w' && twoDown == 'k') || (current == 'k' && twoDown == 'w')) { // checks if enemy piece is capturable  and it isnt a king
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
			
			if (((oneLeft != current && current != 'k') || (current == 'k' && oneLeft == 'b')) && (oneLeft != 'e') && (oneLeft != 'k')) { // checks if there is an enemy piece next action moved piece
				
				if (current == twoLeft || (current == 'w' && twoLeft == 'k') || (current == 'k' && twoLeft == 'w')) { // checks if enemy piece is capturable and it isnt a king
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
		//if (Eval(newState, 0) < 0) System.out.println("Action; " + Arrays.toString(action) + " Score: " + Eval(newState, 0));
		return newState;
	}
	
	public double Forward_Search(char[][] state, int[] kingCoords) {
		
		ArrayList<int[]> output = new ArrayList<int[]>();
		
		int x = kingCoords[1];
		int y = kingCoords[0];
		int ymax = state.length;
		if((x == 0 || x == state.length-1)) ymax--;
		for(int cy = y + 1; cy < ymax; cy++) {
			if(state[cy][x] == 'e') {
					output.add(new int[] {y,x,cy,x});
			} else {
				break;
			}
		}
		// Up
		int ymin = 0;
		if((x == 0 || x == state.length-1)) ymin++;
		for(int cy = y - 1; cy >= ymin; cy--) {
			if(state[cy][x] == 'e') {
					output.add(new int[] {y,x,cy,x});
			} 
			else {
				break;
			}
		}
		// Right
		int xmax = state.length;
		if((y == 0 || y == state.length-1)) xmax--;
		for(int cx = x + 1; cx < xmax; cx++) {
			if(state[y][cx] == 'e') {
					output.add(new int[] {y,x,y,cx});
			}
			 else {
				break;
			}
		}
		// Left
		int xmin = 0;
		if((y == 0 || y == state.length-1)) xmin++;
		for(int cx = x - 1; cx >= xmin; cx--) {
			if(state[y][cx] == 'e') {
				
					output.add(new int[] {y,x,y,cx});
			}
			else {
				break;
			}
		}
		
		return 2.0;
	}
	
	public int[][] Actions(char[][] state, int player) {
		
		/*
		 * Overview
		 * 
		 * loop through every piece that matches target
		 *   scans each direction 
		 *   	adds each empty location as an action action the output
		 *   	ends when not an empty space
		 * Some tricky mechanics with corners, middle square, and king moves
 		 *	- only the king can move to corners and throne
		 *   
		 */
		
		char targetChar = 'b';
		if(player == 1) { targetChar = 'w'; }
		
		ArrayList<int[]> output = new ArrayList<int[]>();
		
		// Loop through each piece
		for(int y = 0; y < state.length; y++) {
			for(int x = 0; x < state.length; x++) {
				// Check if piece is on the current team
				boolean kingPiece = false;
				if(state[y][x] == 'k' && player == 1) kingPiece = true;
				if(state[y][x] == targetChar || kingPiece) {
					// Search each direction as long as there are empty spaces
					// Down
					int ymax = state.length;
					if((x == 0 || x == state.length-1) && !kingPiece) ymax--;
					for(int cy = y + 1; cy < ymax; cy++) {
						if(state[cy][x] == 'e') {
							if(cy == 5 && x == 5 && state[y][x] != 'k') {
								break;
							} else {
								output.add(new int[] {y,x,cy,x});
							}
						} else {
							break;
						}
					}
					// Up
					int ymin = 0;
					if((x == 0 || x == state.length-1) && !kingPiece) ymin++;
					for(int cy = y - 1; cy >= ymin; cy--) {
						if(state[cy][x] == 'e') {
							if(cy == 5 && x == 5 && state[y][x] != 'k') {
								break;
							} else {
								output.add(new int[] {y,x,cy,x});
							}
						} else {
							break;
						}
					}
					// Right
					int xmax = state.length;
					if((y == 0 || y == state.length-1) && !kingPiece) xmax--;
					for(int cx = x + 1; cx < xmax; cx++) {
						if(state[y][cx] == 'e') {
							if(y == 5 && cx == 5 && state[y][x] != 'k') {
								break;
							} else {
								output.add(new int[] {y,x,y,cx});
							}
						} else {
							break;
						}
					}
					// Left
					int xmin = 0;
					if((y == 0 || y == state.length-1) && !kingPiece) xmin++;
					for(int cx = x - 1; cx >= xmin; cx--) {
						if(state[y][cx] == 'e') {
							if(y == 5 && cx == 5 && state[y][x] != 'k') {
								break;
							} else {
								output.add(new int[] {y,x,y,cx});
							}
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

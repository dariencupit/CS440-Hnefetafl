package client;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.jupiter.api.Test;

class ClientGameTest {
	//TESTS FOR BOARD BUILD
	@Test 
	public void testBoardBuildCorners() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		ClientGame game =  new ClientGame(1, 0, "other");
		Method privateMethod = ClientGame.class.getDeclaredMethod("buildBoard");
		privateMethod.setAccessible(true);
		char[][] board = (char[][]) privateMethod.invoke(game);
		
		//CHECKS CORNERS
		assertEquals(board[0][0], 'e');
		assertEquals(board[0][10], 'e');
		assertEquals(board[10][0], 'e');
		assertEquals(board[10][10], 'e');
	}
	@Test 
	public void testBoardBuildKing() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		ClientGame game =  new ClientGame(1, 0, "other");
		Method privateMethod = ClientGame.class.getDeclaredMethod("buildBoard");
		privateMethod.setAccessible(true);
		char[][] board = (char[][]) privateMethod.invoke(game);
		
		//CHECKS KING
		assertEquals(board[5][5], 'k');
		
	}
	
	@Test 
	public void testBoardBuildBlackPiecesLeft() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		ClientGame game =  new ClientGame(1, 0, "other");
		Method privateMethod = ClientGame.class.getDeclaredMethod("buildBoard");
		privateMethod.setAccessible(true);
		char[][] board = (char[][]) privateMethod.invoke(game);
		
		//CHECKS LEFT SIDE BLACK PIECES
		assertEquals(board[3] [0], 'b');
		assertEquals(board[4] [0], 'b');
		assertEquals(board[5] [0], 'b');
		assertEquals(board[6] [0], 'b');
		assertEquals(board[7] [0], 'b');
		assertEquals(board[5] [1], 'b');
		
	}
	@Test 
	public void testBoardBuildBlackPiecesRight() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		ClientGame game =  new ClientGame(1, 0, "other");
		Method privateMethod = ClientGame.class.getDeclaredMethod("buildBoard");
		privateMethod.setAccessible(true);
		char[][] board = (char[][]) privateMethod.invoke(game);
		
		//CHECKS RIGHT SIDE BLACK PIECES
		assertEquals(board[3] [10], 'b');
		assertEquals(board[4] [10], 'b');
		assertEquals(board[5] [9], 'b');
		assertEquals(board[6] [10], 'b');
		assertEquals(board[7] [10], 'b');
		assertEquals(board[5] [10], 'b');
		
	}
	
	@Test 
	public void testBoardBuildBlackPiecesTop() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		ClientGame game =  new ClientGame(1, 0, "other");
		Method privateMethod = ClientGame.class.getDeclaredMethod("buildBoard");
		privateMethod.setAccessible(true);
		char[][] board = (char[][]) privateMethod.invoke(game);
		
		//CHECKS TOP SIDE BLACK PIECES
		assertEquals(board[0] [3], 'b');
		assertEquals(board[0] [4], 'b');
		assertEquals(board[0] [5], 'b');
		assertEquals(board[0] [6], 'b');
		assertEquals(board[0] [7], 'b');
		assertEquals(board[1] [5], 'b');
		
	}
	@Test 
	public void testBoardBuildBlackPiecesBottom() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		ClientGame game =  new ClientGame(1, 0, "other");
		Method privateMethod = ClientGame.class.getDeclaredMethod("buildBoard");
		privateMethod.setAccessible(true);
		char[][] board = (char[][]) privateMethod.invoke(game);
		
		//CHECKS BOTTOM SIDE BLACK PIECES
		assertEquals(board[10] [3], 'b');
		assertEquals(board[10] [4], 'b');
		assertEquals(board[10] [5], 'b');
		assertEquals(board[10] [6], 'b');
		assertEquals(board[10] [7], 'b');
		assertEquals(board[9] [5], 'b');
		
	}
	
	@Test 
	public void testMoveValidatorDownFalse(){ 
		ClientGame game = new ClientGame(1, 0, "other"); 
		int[]from = {1,7}; 
		int[]to = {2,7}; 
		//Checks that move is false when moving empty down 
		assertFalse(game.MoveValidator(from,to)); 
	}
	@Test 
	public void testMoveValidatorDownTrue() { 
		ClientGame game = new ClientGame(1, 0, "other"); 
		int[]from = {1,5}; 
		int[]to = {2,5}; 
		//Checks that move is false when moving empty down 
		assertTrue(game.MoveValidator(from,to)); 
		
	}
	@Test 
	public void testMoveValidatorUpFalse(){ 
		ClientGame game = new ClientGame(1, 0, "other"); 
		int[]from = {10,5}; 
		int[]to = {9,5}; 
		//Checks that move is false when moving up onto another piece
		assertFalse(game.MoveValidator(from,to)); 
	}
	@Test 
	public void testMoveValidatorUpTrue() { 
		ClientGame game = new ClientGame(1, 0, "other"); 
		int[]from = {9,5}; 
		int[]to = {8,5}; 
		//Checks that move is true when moving up 
		assertTrue(game.MoveValidator(from,to)); 
	}
	@Test 
	public void testMoveValidatorRightFalse(){ 
		ClientGame game = new ClientGame(1, 0, "other"); 
		int[]from = {5,5}; 
		int[]to = {5,6}; 
		//Checks that move is false when moving right onto another piece
		assertFalse(game.MoveValidator(from,to)); 
	}
	@Test 
	public void testMoveValidatoRightTrue() { 
		ClientGame game = new ClientGame(1, 1, "other"); 
		int[]from = {5,7}; 
		int[]to = {5,8}; 
		//Checks that move is true when moving right 
		assertTrue(game.MoveValidator(from,to)); 
	}
	@Test 
	public void testMoveValidatorLeftFalse(){ 
		ClientGame game = new ClientGame(1, 0, "other"); 
		int[]from = {5,10}; 
		int[]to = {5,9}; 
		//Checks that move is false when moving left onto another piece
		assertFalse(game.MoveValidator(from,to)); 
	}
	@Test 
	public void testMoveValidatoLeftTrue() { 
		ClientGame game = new ClientGame(1, 0, "other"); 
		int[]from = {7,10}; 
		int[]to = {7,9}; 
		//Checks that move is true when moving left 
		assertTrue(game.MoveValidator(from,to)); 
	}
	//test for debugging update game state method
	@Test 
	public void testMoveValidatoLeftUpdate() { 
		ClientGame game = new ClientGame(1, 0, "other"); 
		int[]from = {10,3}; 
		int[]to = {10,1}; 
		//Checks that move is true when moving left 
		assertTrue(game.MoveValidator(from,to)); 
	}
	@Test 
	public void testMovePieceDown() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		ClientGame game =  new ClientGame(1, 0, "other");
		int[] from = {0,7}; 
		int[] to = {1,7}; 
		char[][] board = game.getBoard();
        //get private method to be able to use it
		Method privateMethod = ClientGame.class.getDeclaredMethod("movePiece", from.getClass(), to.getClass());
		privateMethod.setAccessible(true);
		
		//call the private method from outside
		privateMethod.invoke(game, from, to);
		
		assertEquals(board[1] [7], 'b');
	}
	@Test 
	public void testMovePieceUp() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		ClientGame game =  new ClientGame(1, 0, "other");
		int[] from = {5,7}; 
		int[] to = {5,6}; 
		char[][] board = game.getBoard();
        //get private method to be able to use it
		Method privateMethod = ClientGame.class.getDeclaredMethod("movePiece", from.getClass(), to.getClass());
		privateMethod.setAccessible(true);
		
		//call the private method from outside
		privateMethod.invoke(game, from, to);
		
		assertEquals(board[5] [6], 'w');
	}
	@Test 
	public void testMovePieceRight() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		ClientGame game =  new ClientGame(1, 0, "other");
		int[] from = {5,7}; 
		int[] to = {5,8}; 
		char[][] board = game.getBoard();
        //get private method to be able to use it
		Method privateMethod = ClientGame.class.getDeclaredMethod("movePiece", from.getClass(), to.getClass());
		privateMethod.setAccessible(true);
		
		//call the private method from outside
		privateMethod.invoke(game, from, to);
		
		assertEquals(board[5] [8], 'w');
	}
	@Test 
	public void testMovePieceLeft() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		ClientGame game =  new ClientGame(1, 0, "other");
		int[] from = {10,3}; 
		int[] to = {10,2}; 
		char[][] board = game.getBoard();
        //get private method to be able to use it
		Method privateMethod = ClientGame.class.getDeclaredMethod("movePiece", from.getClass(), to.getClass());
		privateMethod.setAccessible(true);
		
		//call the private method from outside
		privateMethod.invoke(game, from, to);
		
		assertEquals(board[10] [2], 'b');
	}
	public void testMovePieceMultipleDown() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		ClientGame game =  new ClientGame(1, 0, "other");
		int[] from = {5,1}; 
		int[] to = {10,1};
		
		char[][] board = game.getBoard();
        //get private method to be able to use it
		Method privateMethod = ClientGame.class.getDeclaredMethod("movePiece", from.getClass(), to.getClass());
		privateMethod.setAccessible(true);
		
		//call the private method from outside
		privateMethod.invoke(game, from, to);
		
		assertEquals(board[10] [1], 'b');
	}
	public void testMovePieceMultipleUp() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		ClientGame game =  new ClientGame(1, 0, "other");
		int[] from = {5,3}; 
		int[] to = {5,1};
		
		char[][] board = game.getBoard();
        //get private method to be able to use it
		Method privateMethod = ClientGame.class.getDeclaredMethod("movePiece", from.getClass(), to.getClass());
		privateMethod.setAccessible(true);
		
		//call the private method from outside
		privateMethod.invoke(game, from, to);
		
		assertEquals(board[5] [1], 'w');
	}
	public void testMovePieceMultipleLeft() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		ClientGame game =  new ClientGame(1, 0, "other");
		int[] from = {3,5}; 
		int[] to = {1,5};
		
		char[][] board = game.getBoard();
        //get private method to be able to use it
		Method privateMethod = ClientGame.class.getDeclaredMethod("movePiece", from.getClass(), to.getClass());
		privateMethod.setAccessible(true);
		
		//call the private method from outside
		privateMethod.invoke(game, from, to);
		
		assertEquals(board[10] [1], 'w');
	}
	public void testMovePieceMultipleRight() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		ClientGame game =  new ClientGame(1, 0, "other");
		int[] from = {0,7}; 
		int[] to = {4,7};
		
		char[][] board = game.getBoard();
        //get private method to be able to use it
		Method privateMethod = ClientGame.class.getDeclaredMethod("movePiece", from.getClass(), to.getClass());
		privateMethod.setAccessible(true);
		
		//call the private method from outside
		privateMethod.invoke(game, from, to);
		
		assertEquals(board[4] [7], 'b');
	}
	//really specific test case to try to find bug in UpdateGameState 
	//THIS PASSES, PROBLEM IS IN FIRST IF STATEMENT OF MOVEVALIDATOR
	public void testMovePieceLeftForUpdateGame() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		ClientGame game =  new ClientGame(1, 0, "other");
		int[]from = {10,3}; 
		int[]to = {10,0}; 
		
		char[][] board = game.getBoard();
        //get private method to be able to use it
		Method privateMethod = ClientGame.class.getDeclaredMethod("movePiece", from.getClass(), to.getClass());
		privateMethod.setAccessible(true);
		
		//call the private method from outside
		privateMethod.invoke(game, from, to);
		
		assertEquals(board[10] [0], 'b');
	}
	public void testUpdateGameStateLeftMultiple() throws IOException{ 
		ClientGame game = new ClientGame(1, 0, "other"); 
		int[]from = {10,3}; 
		int[]to = {10,1}; 
		game.updateGameState(from, to);
		char[][] board = game.getBoard();
		//Checks that move is false when moving empty down 
		assertEquals(board[10] [1], 'b');
	}
	@Test
	public void testFindKing() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		ClientGame game =  new ClientGame(1, 0, "other");
        //get private method to be able to use it
		Method privateMethod = ClientGame.class.getDeclaredMethod("findKingLocation");
		privateMethod.setAccessible(true);
		
		//call the private method from outside
		int[] location = (int[])privateMethod.invoke(game);
		int[] actual = new int[] {5,5};
		assertEquals(location[0], actual[0]);
		assertEquals(location[1], actual[1]);
	}
	@Test
	public void testCountBlackPieces() {
		ClientGame game =  new ClientGame(1, 0, "other");
		assertEquals(game.countBlackPieces(), 24);
	}
	@Test
	public void testValidPieceT() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		ClientGame game =  new ClientGame(1, 0, "other");
		int x = 0;
		int y = 3;
        //get private method to be able to use it
		Method privateMethod = ClientGame.class.getDeclaredMethod("validPiece", int.class, int.class);
		privateMethod.setAccessible(true);
		boolean bool = (Boolean) privateMethod.invoke(game, x, y);
		//call the private method from outside
		assertTrue(bool);
	}
	@Test
	public void testValidPieceF() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		ClientGame game =  new ClientGame(1, 0, "other");
		int x = 5;
		int y = 4;
        //get private method to be able to use it
		Method privateMethod = ClientGame.class.getDeclaredMethod("validPiece", int.class, int.class);
		privateMethod.setAccessible(true);
		boolean bool = (Boolean) privateMethod.invoke(game, x, y);
		//call the private method from outside
		assertFalse(bool);
	}
}


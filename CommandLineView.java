import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * View and Controller for playing Minesweeper via the command line
 * @author Rachel Wiens 
 */
public class CommandLineView {
	
	private static Minesweeper game;
	
	public static void main(String[] args){
		game = new Minesweeper();
		System.out.println("Welcome to Minesweeper!");
		playGame();
	}
	
	private static void printBoard(){
		Tile[][] board = game.getBoard();
		int boardLength = game.getBoardLength();
		int boardHeight = game.getBoardHeight();
		
		for( int j=0; j<boardLength; j++ ){ System.out.print("-"); }
		System.out.println();
		for( int i=0; i<boardHeight; i++){
			System.out.print("|");
			for(int j=0; j<boardLength; j++){
				System.out.print(board[i][j].toString());
				
			}
			System.out.println("|");
		}
		for( int j=0; j<boardLength; j++ ){ System.out.print("-"); }
		System.out.println();
	}
	
	private static void playGame(){
		int x=-1;
		int y=-1;
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));;
		
		do{
			try {
				printBoard();
				System.out.println("Move: ");
				String moveString = reader.readLine();
				String[] positions = moveString.split(" ");
				if( positions.length<=1 ) positions = moveString.split(",");	// allow users to input either a comma or a space to seperate numbers
				if( positions.length>=2 ){ 			// must have at least 2 numbers
					x = Integer.parseInt(positions[0])-1;		// subtract 1 because our board starts counting at 0 and we expect the user to start counting at 1
					y = Integer.parseInt(positions[1])-1;
				}
			} catch (IOException e) {
				// do nothing
				System.err.print(e.getMessage());
			} catch (NumberFormatException e){
				// could not parse string into integers
				System.err.print(e.getMessage());
			}
		}while( game.makeMove(x,y) );		// loops until a valid move is able to be completed
		
		if(game.isGameWon()){
			System.out.println("Congratulations! You won the game.");
		} else {
			System.out.println("Oops! Sorry, you lose.");
		}
	}
	
	
}

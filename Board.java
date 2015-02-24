/**
 * Minesweeper board
 * @author Rachel Wiens
 */
public class Board {
	private final int height;
	private final int length;
	private final int totalMines;
	private Tile[][] board;
	
	public Board(int h, int l, int mines){
		height = h;
		length = l;
		totalMines = mines;
		
		board = new Tile[height][length];
		clearBoard();				//initialize board to unknowns 
	}
	
	public void clearBoard(){
		for( int i=0; i<height; i++){
			for(int j=0; j<length; j++){
				board[i][j]=Tile.UNKNOWN;
			}
		}
	}
	
	public void setTile(int i, int j, Tile t){
		board[i][j] = t;
	}
	
	public Tile getTile(int i, int j){
		return board[i][j];
	}
	
	public Tile[][] getBoard(){
		return board;
	}
	
	
}

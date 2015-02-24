/**
 * Minesweeper game logic class.
 * @author Rachel Wiens
 */
public class Minesweeper {
	private int time;
	private final int boardHeight;
	private final int boardLength;
	private final int numMines;
	private Board board;
	private boolean[][] minefield;
	private boolean firstMove;		// true if the first move in the game has yet to be made.
	
	public Minesweeper(){
		this(10, 10, 20);
	}
	
	public Minesweeper(int length, int height, int mines){
		boardLength = length;
		boardHeight = height;
		numMines = mines;
		board = new Board(boardHeight, boardLength, numMines);
		minefield = new boolean[boardHeight][boardLength];			// array of mines. Element is true if it is a mine, false otherwise
		firstMove = true;
	}
	
	/**
	 * Start a new game with the same board dimensions and number of mines as the previous game
	 */
	public void newGame(){
		board.clearBoard();
		firstMove = true;
	}
	
	/**
	 * Start a Beginner's level game
	 * @return Minesweeper
	 */
	public Minesweeper beginnerGame(){
		return new Minesweeper(10,10, 20);
	}
	
	/**
	 * Start a Intermediate's level game
	 * @return Minesweeper
	 */
	public Minesweeper intermediateGame(){
		return new Minesweeper(25,25, 125);
	}
	
	/**
	 * Start a Expert's level game
	 * @return Minesweeper
	 */
	public Minesweeper expertGame(){
		return new Minesweeper(100,100, 300);
	}
	
	public Tile[][] getBoard(){
		return board.getBoard();
	}
	
	public int getBoardLength(){
		return boardLength;
	}
	
	public int getBoardHeight(){
		return boardHeight;
	}
	
	/** 
	 * assign mines randomly to the minefield. A total of numMines is required.
	 * shuffleMines will be called after the first move in a new game.
	 * startX and startY are the indices of the first move, which cannot be a mine since that would not be fun.
	 * @param startX
	 * @param startY
	 */
	private void shuffleMines( int startX, int startY ){
		// clear previous mine board
		for( int i=0; i<boardHeight; i++){
			for(int j=0; j<boardLength; j++){
				minefield[i][j] = false;				
			}
		}		
		// add mines at random locations
		int minesAdded = 0;
		while( minesAdded < numMines ){
			int i = (int)(Math.random()*boardLength);
			int j = (int)(Math.random()*boardHeight);
			if( !minefield[i][j] && !( (i==startX) && (j==startY) ) ){	// mine has not already been added and is not the start position
				minefield[i][j] = true;
				minesAdded++;
			}
		}
	}
	
	/** 
	 * Flag a tile if it is unknown.
	 * Unflag a tile if it is flagged.
	 * Do nothing otherwise
	 * @param i
	 * @param j
	 */
	public void flag(int i, int j){
		Tile tile = board.getTile(i, j);
		if( tile == Tile.UNKNOWN){
			board.setTile(i, j, Tile.FLAGGED);
		} else if (tile == Tile.FLAGGED){
			board.setTile(i, j, Tile.UNKNOWN);
		}
	}
	
	/** 
	 * If all non-mine tiles are revealed (not UNKNOWN), game has been won and true is returned.
	 * Otherwise, returns false.
	 * @return boolean
	 */
	public boolean isGameWon(){
		for( int i=0; i<boardHeight; i++){
			for(int j=0; j<boardLength; j++){
				if( (board.getTile(i,j) == Tile.UNKNOWN) && (minefield[i][j] == false) ) return false;		// if tile is unrevealed and not a mine, game is not over		
			}
		}
		return true;
	}
	
	/**
	 * Reveal a tile (set it to its real value)
	 * @param i
	 * @param j
	 */
	private void revealTile( int i, int j){
		if( minefield[i][j] ){				// if tile is a mine
			board.setTile(i,j, Tile.MINE);
			return;
		}
		
		// tile is not a mine, so count the number of mines around it
		int numMines = 0;			// number of mines bordering the tile
		for(int k=-1; k<2; k++){	// check board[i][j]'s horizontal, vertical, and diagonal neighbours
			for( int l=-1; l<2; l++){
				if( (i+k >= 0) && (i+k < boardHeight) && (j+l >=0) && (j+l < boardLength)  ){		// if index is within bounds
					if( minefield[i+k][j+l] ) numMines++;	
				}
			}
		}
		
		board.setTile(i,  j, Tile.getTile(numMines));		
	}
	
	/**
	 * Reveal all 8 tiles around the tile at index i,j.
	 * @param i
	 * @param j
	 */
	private void revealNeighbours(int i, int j){
		for(int k=-1; k<2; k++){	// check [i][j]'s horizontal, vertical, and diagonal neighbours
			for( int l=-1; l<2; l++){
				if( (i+k >= 0) && (i+k < boardHeight) && (j+l >=0) && (j+l < boardLength)  ){		// if index is within bounds
					if( board.getTile(i+k, j+l) == Tile.UNKNOWN ){		// if tile is unknown, set it and check its neighbours
						revealTile(i+k, j+l);	// reveal tile
						if (board.getTile(i+k, j+l) == Tile.EMPTY) revealNeighbours(i+k, j+l); // if tile is empty, continue revealing its neighbours
					}
				}
			}
		}
	}
	
	/** 
	 * Reveal a block and its relevant neighbours.
	 * If the block is not unknown (ex. flagged, empty, number is already known) do nothing.
	 * If the game is over, return false. Otherwise return true
	 * @param i
	 * @param j
	 */
	public boolean makeMove(int i, int j){
		if( i<0 || j<0 || i>boardHeight || j>boardLength ) return true;		// outside bounds
		Tile tile = board.getTile(i, j);
		if( tile != Tile.UNKNOWN ) return true;		// if value of tile is already known, do nothing and return
		
		// if first move, initialize the minefield (since first move should never be a mine.)
		if( firstMove ) {
			shuffleMines( i, j );
			firstMove = false;
		}
		
		revealTile(i,j);
		
		if( board.getTile(i,j)  == Tile.MINE){			// if a mine has been hit
			return false;
		} else if (board.getTile(i,j)  == Tile.EMPTY){		// if tile is empty, continue revealing its neighbours
			revealNeighbours(i, j);
		}
		
		return !isGameWon();		// returns false if the game has been won		
	}
	
	// TODO: high score
	
}

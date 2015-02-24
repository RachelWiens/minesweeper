/** 
 * Minesweeper board tile
 * UNKNOWN : Tile has not been clicked so its contents is not known
 * EMPTY : Tile is not a mine and is not a neighbour of any mines
 * ONE - EIGHT : Tile is a neighbour of some number of mines. The number of mines is the value of the number
 * MINE : Tile is a mine.
 * FLAGGED : Tile is flagged as a suspected mine
 * @author Rachel Wiens
 *
 */
public enum Tile{ 
	UNKNOWN(-1), 
	EMPTY(0),  
	ONE(1), TWO(2) , THREE(3), FOUR(4), FIVE(5), SIX(6), SEVEN(7), EIGHT(8), 
	MINE(9),
	FLAGGED(10);

	private int number;
	
	Tile(int n){
		number = n;
	}
	
	public static Tile getTile(int n){
		switch(n){
			case 0:
				return Tile.EMPTY;
			case 1:
				return Tile.ONE;
			case 2:
				return Tile.TWO;
			case 3:
				return Tile.THREE;
			case 4:
				return Tile.FOUR;
			case 5:
				return Tile.FIVE;
			case 6:
				return Tile.SIX;
			case 7:
				return Tile.SEVEN;
			case 8:
				return Tile.EIGHT;
			case 9:
				return Tile.MINE;
			case 10:
				return Tile.FLAGGED;
			default:
				return Tile.UNKNOWN;
		}
	}
	
	int getNumber(){
		return number;
	}
	
	public String toString(){
		switch(this){
			case UNKNOWN:
				return " ";
			case MINE:
				return "*";
			case FLAGGED: 
				return "X";
			default:
				return Integer.toString(number);
		}
	}
};

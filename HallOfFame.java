/**
 * Stats for past games
 * @author Rachel Wiens
 *
 */
public class HallOfFame {
	private int gamesPlayed;
	private int gamesWon;
	private int bestTime;
	
	HallOfFame(){
		gamesPlayed=gamesWon=0;
		bestTime = Integer.MAX_VALUE;
	}
	
	/**
	 * Update the Hall of Fame records.
	 * Increment the number of games. Increment gamesWon if the game was won (gameWon==true). Set bestTime to time if it is lower.
	 * @param gameWon
	 * @param time
	 */
	public void addGame(boolean gameWon, int time){
		gamesPlayed++;
		gamesWon += gameWon ? 1 : 0;
		if(bestTime>time) bestTime = time;
	}
	
	public int getNumberOfGames(){
		return gamesPlayed;
	}
	
	public int getNumberOfWins(){
		return gamesWon;
	}
	
	/**
	 * Return the winning percentage, rounded to the nearest whole number.
	 * @return int
	 */
	public int getWinningPercentage(){
		double percentage = ((double)gamesWon)/gamesPlayed;
		return (int)(percentage*100);
	}
	
	public int getBestTime(){
		return bestTime;
	}
}

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

/**
 * View and Controller for playing Minesweeper user interface
 * @author Rachel Wiens
 *
 * TODO:
 *  - Add scoreboard
 */
public class UIView extends JFrame implements ActionListener{
		
	private static Minesweeper game;
	private JPanel pane = new JPanel(new BorderLayout(10, 10));
	private JPanel boardPane = new JPanel(new GridLayout(boardHeight+1, boardLength));
	private static int boardLength;
	private static int boardHeight;
	private static JLabel timeLbl = new JLabel("00:00:00");
	private static CountingTimer timer;
	private static JLabel infoLbl = new JLabel("Welcome to Minesweeper!");
	private static JButton newGameButton = new JButton("Start Game");
	private static JButton buttons[][];
	
	/**
	 * Mouse listener for board tile mouse clicks
	 */
	private static MouseListener mouseListener = new MouseAdapter() {
		public void mousePressed(MouseEvent event){
	    	JButton btn = (JButton)event.getSource();	    
	    	  
	    	if( SwingUtilities.isLeftMouseButton(event)){
	    		for( int y=0; y<boardHeight; y++){
				   	for( int x=0; x<boardLength; x++){
				   		if( btn.equals(buttons[y][x]) ){
				   			
				   	    	if( !timer.isRunning()){		// if first click of the game
				   				newGameButton.setText("New Game");
				   				timer.start();
				   			}
				   			infoLbl.setText(y + " " + x);
				   			boolean gameOver = ! game.makeMove(y,x);
				   			showTile(y,x);
				   			if(gameOver){
				   				gameOver();
				   			}
				   		}
				   	}
	    		}
	    	}else if( SwingUtilities.isRightMouseButton(event)){	// board tile right click   	
				for( int y=0; y<boardHeight; y++){
					for( int x=0; x<boardLength; x++){
						if( btn.equals(buttons[y][x]) ){
							infoLbl.setText(y + " " + x);
							game.flag(y, x);
							showTile(y,x);
						}
					}
				}
	  		}
		}
	      
	};
	
	/**
	 * Constructor.
	 * Sets up the UI.
	 */
	public UIView(){
		super("Minesweeper");
		
		// add top buttons and labels
		pane.add(infoLbl, BorderLayout.WEST);		
		pane.add(newGameButton, BorderLayout.CENTER);
		newGameButton.addActionListener(this);
		pane.add(timeLbl, BorderLayout.EAST);
		timer = new CountingTimer(1000, new ActionListener(){
			/**
			 * Called whenever the timer updates the clock
			 */
			public void actionPerformed(ActionEvent event){	
				timeLbl.setText(String.valueOf(timer.getTimeSinceStart()));	
			}
		});
		
		// add board buttons
		buttons = new JButton[boardHeight][boardLength];
		for( int i=0; i<boardHeight; i++){
			for( int j=0; j<boardLength; j++){
				buttons[i][j] = new BoardTileButton();
				buttons[i][j].addMouseListener(mouseListener);
				boardPane.add(buttons[i][j]);
			}
		}
		
		pane.add(boardPane, BorderLayout.SOUTH);
		Container container = this.getContentPane();
		container.add(pane);
		
		this.pack();
		this.setVisible(true);
	}

	/**
	 * Sets up the UI and runs the game.
	 * @param args
	 */
	public static void main(String[] args){
		game = new Minesweeper();
		boardLength = game.getBoardLength();
		boardHeight = game.getBoardHeight();
		JFrame view = new UIView();
	}
	
	/** 
	 * Called whenever any non-tile button is clicked.
	 */
	public void actionPerformed(ActionEvent event){	
	
		JButton btn = (JButton)event.getSource();
		
		if( btn.equals(newGameButton) ){
			timer.stop();
			game.newGame();
			updateBoard();
			newGameButton.setText("Start Game");
			timeLbl.setText(String.valueOf(timer.getTimeSinceStart()));
		}
	}
	
	/**
	 * Show the value of a tile at position (x,y). If it's empty, display the value of all tiles around it as well.
	 */
	private static void showTile(int x, int y){
		if( x<0 || y<0 || x>=boardLength || y>=boardHeight ) return;
		JButton btn = buttons[x][y];
		if( !btn.isEnabled() ) return;
		Tile val = game.getBoard()[x][y];
		btn.setText( val.toString() );
		if( val != Tile.FLAGGED && val != Tile.UNKNOWN ){
			btn.setEnabled(false);
		}
		// if tile is empty, show all tiles around it.
		if( val==Tile.EMPTY){
			for( int i=-1; i<2; i++ ){
				for( int j=-1; j<2; j++ ){
					showTile(x+i, y+j);
				}
			}
		}
	}
	
	/**
	 * Update all the View to match the Model's board
	 */
	private static void updateBoard(){
		Tile[][] board = game.getBoard();
		
		for( int y=0; y<boardHeight; y++){
			for( int x=0; x<boardLength; x++){
				Tile val = board[y][x];
				buttons[y][x].setText( val.toString() );	// button displays the board tile's value
				if( val==Tile.UNKNOWN || val==Tile.FLAGGED ){
					buttons[y][x].setEnabled(true);		// unknown or flagged buttons are clickable
				} else {
					buttons[y][x].setEnabled(false);	// all other buttons are unclickable (since their value is already known)
				}
				
			}
		}
	}
	
	/**
	 * The game is over. Print out the win/lose message.
	 */
	private static void gameOver(){
		timer.stop();
		if(game.isGameWon()){
			infoLbl.setText("Congratulations! You won the game.");
		} else {
			infoLbl.setText("Oops! Sorry, you lose.");
		}
		
		// disable all board tile buttons
		Tile[][] board = game.getBoard();
		for( int y=0; y<boardHeight; y++){
			for( int x=0; x<boardLength; x++){
				Tile val = board[y][x];
				buttons[y][x].setText( val.toString() );	// button displays the board tile's value
				buttons[y][x].setEnabled(false);	// all other buttons are unclickable (since their value is already known)	
			}
		}
	}
}


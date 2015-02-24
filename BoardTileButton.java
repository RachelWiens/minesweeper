import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

import javax.swing.JButton;

/**
 * Custom Button for Minesweeper board tiles.
 * @author Rachel Wiens
 */
class BoardTileButton extends JButton{
	private static final long serialVersionUID = 1L;
	private static final Color BTN_ENABLED_HIGHLIGHT_COLOR = new Color(148, 146, 214);
	private static final Color BTN_ENABLED_SHADOW_COLOR = new Color(97, 95, 176);
	private static final Color BTN_DISABLED_HIGHLIGHT_COLOR = new Color(243, 242, 247);
	private static final Color BTN_DISABLED_SHADOW_COLOR = new Color(175, 174, 212);
	
	BoardTileButton(){
        super(" ");
        setContentAreaFilled(false);
        setFocusPainted(false); 
        this.setPreferredSize(new Dimension(50, 50));
    }

    @Override
    protected void paintComponent(Graphics g){
        Graphics2D g2 = (Graphics2D)g.create();
        if( this.isEnabled() ){
        	g2.setPaint(new GradientPaint( new Point(0, 0), BTN_ENABLED_HIGHLIGHT_COLOR, new Point(0, getHeight()), BTN_ENABLED_SHADOW_COLOR));
        } else {
        	g2.setPaint(new GradientPaint( new Point(0, 0), BTN_DISABLED_HIGHLIGHT_COLOR, new Point(0, getHeight()), BTN_DISABLED_SHADOW_COLOR));
        }
    	g2.setStroke(new BasicStroke(2));
    	g2.fillRect(0, 0, getWidth(), getHeight());
        g2.dispose();
        super.paintComponent(g);
    }
    
    /* TODO:
     * 	- Darken font in disabled button.
     * 	- Make font larger
     */

    public static final BoardTileButton newInstance(){
        return new BoardTileButton();
    }
}

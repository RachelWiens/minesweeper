import java.awt.event.ActionListener;
import java.util.concurrent.TimeUnit;

import javax.swing.Timer;

/**
 * Timer that stores the number of seconds since the timer was started.
 * @author Rachel Wiens 
 */
public class CountingTimer extends Timer {
	
	private static final long serialVersionUID = 1L;
	private long startTime;
	
	CountingTimer(int delay, ActionListener actionListener){
		super(delay, actionListener);
	}
	
	public void start(){
		startTime = System.currentTimeMillis();
		super.start();
	}
	
	public void stop(){
		super.stop();
		startTime = 0;
	}
	
	/**
	 * Get the number of seconds since the timer started counting. The result is round to the nearest second.
	 * @return
	 */
	public long getSecondsSinceStart(){
		if( !this.isRunning() ){		// if timer is not running, 0 seconds have passed since it started.
			return 0;
		}
		long currentTime = System.currentTimeMillis();
		long elapsedTime = currentTime - startTime;			// elapsed time in milliseconds
		return elapsedTime/1000;
	}
	
	public String getTimeSinceStart(){
		if( !this.isRunning() ){		// if timer is not running, 0 seconds have passed since it started.
			return "00:00:00";
		}
		long currentTime = System.currentTimeMillis();
		long elapsedTime = currentTime - startTime;			// elapsed time in milliseconds
		long hours = TimeUnit.MILLISECONDS.toHours(elapsedTime);
		long minutes = TimeUnit.MILLISECONDS.toMinutes(elapsedTime) - TimeUnit.MILLISECONDS.toMinutes(hours);
		long seconds = TimeUnit.MILLISECONDS.toSeconds(elapsedTime) - TimeUnit.MILLISECONDS.toSeconds(TimeUnit.MILLISECONDS.toMinutes(elapsedTime));
		return String.format("%02d:%02d:%02d", hours, minutes, seconds);
	}

}

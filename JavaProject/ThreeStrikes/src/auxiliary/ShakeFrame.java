/**
 * @author Esperanto.
 * @since 2020.6.30
 * @version 1.0
 * 
 * Client Shake Frame is an auxiliary class
 * 
 * to finish shaking some chat friends.
 *
 */
package auxiliary;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.Timer;

public class ShakeFrame 
{
	/**Shaking distance.*/
	public static final int SHAKE_DISTANCE=5;
	
	/**Shaking cycle.*/
	public static final double SHAKE_CYCLE=10;
	
	/**Shaking duration.*/
	public static final int SHAKE_DURATION=600;
	
	private JFrame Frame;
	private Point Location;
	private long StartTime;
	private Timer ShakeTimer;
	
	public ShakeFrame(JFrame frame)
	{
		Frame=frame;
	}
	
	public void StartShake()
	{
		Location = Frame.getLocation();
		
		StartTime = System.currentTimeMillis();
		ShakeTimer = new Timer((int)(SHAKE_CYCLE/5),new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				// TODO 自动生成的方法存根
				long pass = System.currentTimeMillis()-StartTime;
				
				double ShakeOffset = (pass%SHAKE_CYCLE)/SHAKE_CYCLE;
				double Angle = ShakeOffset*Math.PI;
				
				int Shake_x = (int)(Math.sin(Angle)*SHAKE_DISTANCE+Location.x);
				int Shake_y = (int)(Math.sin(Angle)*SHAKE_DISTANCE+Location.y);
				Frame.setLocation(Shake_x,Shake_y);
				
				if(pass>=SHAKE_DURATION)
				{
					StopShake();
				}
			}
		});
		ShakeTimer.start();
	}
	
	public void StopShake()
	{
		ShakeTimer.stop();
		Frame.setLocation(Location);
	}
}

/**Nothing Wrong.*/

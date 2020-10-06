/**
 * @author Esperanto.
 * @since 2020.6.30
 * @version 1.0
 * 
 * Class ServerLauncher is the launcher of 
 * 
 * server program , whose functions are 
 * 
 * sending official messages and kicking 
 * 
 * out some users. 
 *
 */
package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.UIManager;

import auxiliary.ProcessUnit;

public class ServerLauncher 
{
	public static void main(String[] args)
	{
		//Initialize the server port.
		int ServerPort = Integer.parseInt(ServerDataStore.ServerProperty.getProperty("Port"));
		//In fact , the property file 'ServerProperty' is <ServerProperty.properties>.
		
		//Initialize the server socket.
		try
		{
			ServerDataStore.Ssocket = new ServerSocket(ServerPort);
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		
		new Thread(new Runnable()
		{
			@Override
			public void run() 
			{
				// TODO 自动生成的方法存根
				try
				{
					while(true)
					{
						Socket s = ServerDataStore.Ssocket.accept();
						System.out.println("An user whose IP is "+
						s.getInetAddress().getHostAddress()+
						" is coming from port No."+s.getPort()+".");
						
						new Thread(new ProcessUnit(s)).start();
					}
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
		}).start();
		
		/**Appearance.*/
		//Decorate main frame and pop up dialog.
		JFrame.setDefaultLookAndFeelDecorated(true);
        JDialog.setDefaultLookAndFeelDecorated(true);
        
        try
        {
        	//Cross platform means it can be displayed on different operating systems.
        	UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        }
        catch(Exception e)
        {
        	e.printStackTrace();
        }
        
        //Launch UI.
        new ServerUI();
	}
}

/**Nothing Wrong.*/
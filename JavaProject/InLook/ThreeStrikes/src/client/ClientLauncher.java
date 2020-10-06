/**
 * @author Esperanto.
 * @since 2020.6.30
 * @version 1.0
 * 
 * Class ClientLauncher is the launcher of 
 * 
 * client program , whose functions are 
 * 
 * providing register and log in page . 
 *
 */

package client;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.*;

public class ClientLauncher
{
	public static void Make_Connection()
	{
		String IP_addr = ClientDataStore.set_of_Property.getProperty("IP");
		int PortNum = Integer.parseInt(ClientDataStore.set_of_Property.getProperty("Port"));
		try
		{
			ClientDataStore.socket_to_Server=new Socket(IP_addr,PortNum);
			ClientDataStore.obj_OS=new ObjectOutputStream(ClientDataStore.socket_to_Server.getOutputStream());
			ClientDataStore.obj_IS=new ObjectInputStream(ClientDataStore.socket_to_Server.getInputStream());
		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(new JFrame(), "Fail to connect with server.","Error",JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}
	}
	
	public static void main(String[] args) 
	{
		//Connect with a Server.
		Make_Connection();
		
		//Client appearance.
		JFrame.setDefaultLookAndFeelDecorated(true);
		JDialog.setDefaultLookAndFeelDecorated(true);
		try
		{
			 String LookAndFeel = UIManager.getCrossPlatformLookAndFeelClassName();
	         UIManager.setLookAndFeel(LookAndFeel);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		new ClientLogin();
	}
}

/**Nothing Wrong.*/
//Nothing Wrong.